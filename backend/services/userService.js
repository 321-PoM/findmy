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
        
        let reliabilityScore = 0; // TODO: What is our logic??? I don't know... Me no English...
        
        return reliabilityScore;

    } catch (err) {
        throw err; 
    }
};
