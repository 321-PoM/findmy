import { PrismaClient } from '@prisma/client';
import { getUserReliabilityScore } from './userService';

const prisma = new PrismaClient();

// I think I would prefer to list reviews of user or reviews on a poi through the user/poi module
// BUt will keep this function here for now
export const listReviews = async (searchBy, id) => {
    try{
        if(searchBy != 'user' || searchBy != 'poi') throw new Error("Invalid or missing input parameter")

        let inputParams = (searchBy == 'user') ? 
            {isDeleted: false, userId: id} : 
            {isDeleted: false, poiId: id};

        return await prisma.review.findMany({
            where: inputParams,
            include: {
                id: true,
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

export const getReview = async (id) => {
    return await prisma.review.findUnique({
        where: {
            id: id
        },
        include: {
            userId: true,
            poiId: true,
            rating: true,
            description: true,
            reliabilityScore: true,
        },
    })
}

export const createReview = async (poiId, data) => {
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

export const updateReview = async (id, updateData) => {
    return await prisma.review.update({
        where: { 
            id: id,
        },
        data: updateData,
    });
}

export const deleteReview = async (id) => {
    return await prisma.review.update({
        where: { id: id },
        data: { isDeleted: true },
        select: { poiId: true },
    })
}

