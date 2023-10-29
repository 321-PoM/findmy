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
        // Query ratings from public POIs created by user
        const poiRatings = await prisma.poi.findMany({
            where: {
                ownerId: userId,
                status: 'verfied' // assuming 'verified' is the correct enum value
            },
            select: {
                rating: true
            }
        });
        
        // Query number of verified POIs reported by user
        const verifiedPoisReported = await prisma.poi.count({
            where: {
                ownerId: userId,
                status: 'verfied'
            }
        });
        if(verifiedPoisReported == 0) throw new Error("User has not reported any POIs");

        // return stdev and avg rating of verified POIs
        let avgRating;
        let stDev;

        const sumRating = poiRatings.reduce((sum, poi) => sum += poi['rating'], 0);
        avgRating = sumRating / verifiedPoisReported;
        
        let sumDeviation = poiRatings
            .map((poi) => Math.pow((poi['rating'] - avgRating), 2))
            .reduce((sum, distance) => sum += distance, 0);

        stDev = Math.pow((sumDeviation / verifiedPoisReported), 0.5);

        return {avgPOIRating: avgRating, stdev: stDev};
    } catch (err) {
        throw err; 
    }
};
