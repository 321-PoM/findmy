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
                userId: true,
                rating: true,
            }
        });
        
        // Calculate new weighted rating
        let sumWeight = await getUserReliabilityScore(userId)
        let weightedSumRating = rating * sumWeight;
        for(const rating of allRatings){
            let weight = await getUserReliabilityScore(rating['userId']);
            sumWeight += weight;
            weightedSumRating += rating['rating'] * weight;
        }
        return weightedSumRating / sumWeight;
    } catch (err) {
        throw err;
    }
}