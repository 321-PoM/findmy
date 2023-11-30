import { PrismaClient } from '@prisma/client';
// import { getUserReliabilityScore } from './userService.js';
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
            reviewId: Number(id),
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

    let rscoreUpdatePromises = [];
    for(const review of pastReviews) {
        const dTime = Math.abs(Date.now() - new Date(review.createdAt).getTime()) / (SECONDS * MILLISECONDS);
        const timeBias = Math.exp(2 * dTime);

        const adjustment = (-1 * Math.abs(rating - review.rating)) + 2;

        const userPromise = prisma.User.update({
            where: { id: review.userId }, 
            data: { reliabilityScore: { increment: adjustment * timeBias }}
        });
        const reviewPromise = prisma.Review.update({
            where: { id: Number(review.id) },
            data: { reliabilityScore: { increment: adjustment * timeBias }}
        });
        rscoreUpdatePromises.push(userPromise);
        rscoreUpdatePromises.push(reviewPromise);
    }
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

