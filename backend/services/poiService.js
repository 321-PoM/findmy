import { PrismaClient } from '@prisma/client';
import { getUserReliabilityScore } from './userService';

const prisma = new PrismaClient();

export const createPoi = async (poiData) => {
    return await prisma.poi.create({
        data: poiData,
    });
};

export const getPoi = async (poiId) => {
    return await prisma.poi.findUnique({
        where: {
            id: poiId,
        },
        include: {
            image: true,
            owner: true,
        },
    });
};

export const updatePoi = async (poiId, updateData) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: updateData,
    });
};

export const deletePoi = async (poiId) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: { isDeleted: true },  // Soft-delete.
    });
};

export const listPois = async () => {
    return await prisma.poi.findMany({
        where: {
            isDeleted: false,
        },
        include: {
            image: true,
            owner: true,
        },
    });
};

export const calcPoiRating = async (poiId) => {
    try{
        const allRatings = await prisma.review.findMany({
            where: {
                poiId: poiId,
            },
            include: {
                rating: true,
                reliabilityScore: true,
            }
        });
        
        // Calculate new weighted rating
        let totalWeight = 0;
        let weightedSum = 0;
        for(const rating of allRatings){
            totalWeight += rating['reliabilityScore'];
            weightedSum += rating['rating'] * rating['reliabilityScore'];
        }
        return weightedSum / totalWeight;
    } catch (err) {
        throw err;
    }
}