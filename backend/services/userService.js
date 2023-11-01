import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createUser = async (userData) => {
    console.log(userData);
    return await prisma.User.create({
        data: userData,
    });
};

export const getUser = async (userId) => {
    const uid = parseInt(userId);

    if (isNaN(uid)) {
        return res.status(400).json({ message: "Error: getUser | user ID is not int." });
    }

    return await prisma.User.findUnique({
        where: {
            id: uid,
        },
    });
};

export const getUserByEmail = async (email) => {
    try{
        const user = prisma.User.findUnique({
            where: { email: email },
        })
        if(user.length >= 1) return user;
        const createdUser = prisma.User.create({
            data: { email: email }
        })
        return createdUser;
    } catch (err) {
        throw err;
    }
};

export const updateUser = async (userId, updateData) => {
    return await prisma.User.update({
        where: { id: userId },
        data: updateData,
    });
};

export const updateUserBux = async (userId, polarity, amount) => {
    return await prisma.User.update({
        where: { id: userId },
        data: {
            mapBux: (polarity) ? { increment: amount } : { decrement: amount }
        },
        select: { mapBux: true }
    })
}

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
        if(reviewsByUser.length < 1) return 100;

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
    if(poiRatings.length < 4) return 0;

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
