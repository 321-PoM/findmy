import { PrismaClient } from '@prisma/client';
import { getUserReliabilityScore } from './userService.js';
import { calcPoiRating } from './poiService.js';

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
            id: Number(id),
            isDeleted: false,
        }
    })
}

export const createReview = async (poiId, userId, rating, description) => {
    await doesReviewAlreadyExist(poiId, userId);
    await adjustPastReviewerRScores(poiId, rating);

    const newReview = await prisma.Review.create({
        userId: Number(userId),
        poiId: Number(poiId),
        rating: Number(rating),
        description: description
    });
    const newRating = await calcPoiRating(poiId);
    await prisma.poi.update({
        where: { id: Number(poiId) },
        data: { rating: Number(newRating) }
    });
    return newReview;
};

const adjustPastReviewerRScores = async (poiId, rating) => {
    const SECONDS = 60;
    const MILLISECONDS = 1000;

    const pastReviews = await prisma.Review.findMany({
        where: { poiId: Number(poiId) }
    });

    const rscoreUpdatePromises = pastReviews.map(review => {
        // feel like dtime might b a big big number
        const dTime = Math.abs(Date.now() - new Date(review.createdAt).getTime()) / (SECONDS * MILLISECONDS);
        const timeBias = Math.exp(2 * dTime);

        const adjustment = (-1 * Math.abs(rating - review.rating)) + 2;
        return prisma.User.update({
            where: { id: review.userId }, 
            data: { 
                reliabilityScore: { increment: adjustment * timeBias }
            }
        })
    });
    await Promise.all(rscoreUpdatePromises);
    return;
}

const doesReviewAlreadyExist = async (poiId, userId) => {
    const doesThisExist = await prisma.Review.findFirst({
        where: {
            poiId: poiId,
            userId: userId,
            isDeleted: false,
        }
    });
    if(doesThisExist != null) throw new Error("Error: You already left a review for this poi");
    return;
}

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

