import { PrismaClient } from '@prisma/client';
import { getUserReliabilityScore } from './userService.js';

const prisma = new PrismaClient();

// I think I would prefer to list reviews of user or reviews on a poi through the user/poi module
// BUt will keep this function here for now
export const listReviews = async (searchBy, id) => {
    if(searchBy != 'user' || searchBy != 'poi') throw new Error("Invalid or missing input parameter")

    let inputParams = (searchBy == 'user') ? 
        {isDeleted: false, userId: id} : 
        {isDeleted: false, poiId: id};

    return await prisma.Review.findMany({
        where: inputParams
    });
};

export const getReview = async (id) => {
    return await prisma.Review.findUnique({
        where: {
            reviewId: Number(id),
            isDeleted: false,
        }
    })
}

export const createReview = async (poiId, userId, rating, description) => {
    const doesThisExist = await prisma.Review.findFirst({
        where: {
            poiId: poiId,
            userId: userId,
            isDeleted: false,
        }
    });
    if(doesThisExist != null) throw new Error("Error: You already left a review for this poi");
    let rScore = await getUserReliabilityScore(Number(userId));
    return await prisma.Review.create({
        data: {
            poiId: Number(poiId),
            userId: Number(userId),
            rating: rating,
            description: description,
            reliabilityScore: rScore,
        }
    });
};

export const updateReview = async (id, updateData) => {
    return await prisma.Review.update({
        where: { 
            id: Number(id),
        },
        data: updateData,
    });
}

export const updateRating = async (id, newRating) => {
    return await prisma.Review.update({
        where: { id: Number(id) },
        data: { rating: newRating}, 
        select: { poiId: true },
    });
}

export const deleteReview = async (id) => {
    return await prisma.Review.update({
        where: { id: Number(id) },
        data: { isDeleted: true },
        select: { poiId: true },
    })
}

