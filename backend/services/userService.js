import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createUser = async (userData) => {
    return await prisma.user.create({
        data: userData,
    });
};

export const getUser = async (userId) => {
    return await prisma.user.findUnique({
        where: {
            id: userId,
        },
    });
};

export const updateUser = async (userId, updateData) => {
    return await prisma.user.update({
        where: { id: userId },
        data: updateData,
    });
};

export const deleteUser = async (userId) => {
    return await prisma.user.update({
        where: { id: userId },
        data: { isDeleted: true },  // Soft-delete user.
    });
};

export const listUsers = async () => {
    return await prisma.user.findMany({
        where: {
            isDeleted: false,
            isActive: true
        }
    });
};

export const getUserReliabilityScore = async (userId) => {
    try {
        // Query for Pois reviewed by the user
        const reviewsByUser = await prisma.rating.findMany({
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
        let totalNonOutliers = reviewsByUser.filter(review => !isOutlier(review));
        return totalNonOutliers / totalReviews;
    } catch (err) {
        throw err; 
    }
};

const isOutlier = async (review) => {
    let poiId = review['poiId'];
    let rating = review['rating'];

    const poiReviews = await prisma.rating.findMany({
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
    let sumDist = poiRatings.reducce((sum, rating) => {sum += Math.pow((rating - mean), 2)}, 0);
    let stdev = Math.pow((sumDist / numRatings), 0.5);

    let max = mean + 1.5 * stdev;
    let min = mean - 1.5 * stdev;

    return rating > max || rating < min;
}