import { PrismaClient } from '@prisma/client';
import { getUserReliabilityScore } from './userService';

const prisma = new PrismaClient();

export const listReviews = async (searchBy, id) => {
    try{
        if(searchBy != 'user' || searchBy != 'poi') throw new Error("Invalid or missing input parameter")

        let inputParams = (searchBy == 'user') ? 
            {isDeleted: false, userId: id} : 
            {isDeleted: false, poiId: id};

        return await prisma.review.findMany({
            where: inputParams,
            include: {
                rating: true,
                poiId: (searchBy != 'poi'),
                userId: (searchBy != 'user'),
                description: true,
                reliabilityScore: true,
            },
        });
    } catch (err) {
        throw err
    }
};

export const getReview = async (poiId, userId) => {
    return await prisma.review.findUnique({
        where: {
            poiId: poiId,
            userId: userId,
        },
        include: {
            rating: true,
            description: true,
            reliabilityScore: true,
        },
    })
}

export const createReview = async (data) => {
    try{
        let rScore = getUserReliabilityScore(data.userId);
        return await prisma.review.create({
            data: {
                poiId: data.poiId,
                userId: data.userId,
                rating: data.rating,
                description: data.desc,
                reliabilityScore: rScore,
            }
        });
    } catch (err) {
        throw err;
    }
};

export const updateReview = async (poiId, userId, updateData) => {
    return await prisma.review.update({
        where: { 
            poiId: poiId,
            userId: userId,
        },
        data: updateData,
    });
}

export const deleteReview = async (poiId, userId) => {
    return await prisma.review.update({
        where: {
            poiId: poiId,
            userId: userId,
        },
        data: {isDeleted: true},
    })
}

