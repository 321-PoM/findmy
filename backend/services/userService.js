import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createUser = async (userData) => {
    console.log(userData);
    return await prisma.User.create({
        data: userData,
    });
};

export const getUser = async (userId) => {
    return await prisma.User.findUnique({
        where: {
            id: userId,
        },
    });
};

export const updateUser = async (userId, updateData) => {
    return await prisma.User.update({
        where: { id: userId },
        data: updateData,
    });
};

export const deleteUser = async (userId) => {
    return await prisma.User.update({
        where: { id: userId },
        data: { isDeleted: true },  // Soft-delete user.
    });
};

export const listUsers = async () => {
    return await prisma.User.findMany({
        where: {
            isDeleted: false,
            isActive: true
        }
    });
};

export const getUserReliabilityScore = async (userId) => {
    try {
        // Query for Pois reviewed by the user
        const reviewsByUser = await prisma.Review.findMany({
            where: {
                userId: userId,
                isDeleted: false,
            },
            select: {
                poiId: true,
                rating: true
            }
        });

        // iterate through every single poi reviewed by user
        let totalReviews = reviewsByUser.length;
        let sumDist = reviewsByUser
                        .map(review => distFromSafeZone(review))
                        .reduce((sum, dist) => sum += dist, 0);
                        
        return 100 - sumDist / totalReviews;
    } catch (err) {
        throw err; 
    }
};

const distFromSafeZone = async (review) => {
    let poiId = review['poiId'];
    let rating = review['rating'];

    const poiReviews = await prisma.Review.findMany({
        where: {
            poiId: poiId,
            isDeleted: false,
        },
        select: {
            rating: true
        }
    });
    const poiRatings = poiReviews.map(review => review['rating']);

    // find mean
    let sumRating = poiRatings.reduce((sum, rating) => sum += rating, 0);
    let numRatings = poiRatings.length;
    let mean = sumRating / numRatings;

    // find stdev
    let sumDist = poiRatings.reduce((sum, rating) => sum += Math.pow((rating - mean), 2), 0);
    let stdev = Math.pow((sumDist / numRatings), 0.5);

    let max = mean + 1.5 * stdev;
    let min = mean - 1.5 * stdev;

    if(rating > max) return rating - max;
    else if(rating < min) return min - rating;
    return 0;
}
