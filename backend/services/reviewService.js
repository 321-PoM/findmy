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
    const uid = Number(userId);
    const pid = Number(poiId);

    /* Logger */
    console.log("Given userId (type:", typeof userId, "):", userId);
    console.log("userId (type:", typeof uid, "):", userId);
    console.log("poiId (type:", typeof poiId, "):", poiId);

    await doesReviewAlreadyExist(pid, uid);
    await adjustPastReviewerRScores(pid, rating);

    let author = await prisma.User.findUnique({
            where: {
                id: uid,
            },
            select: {
                reliabilityScore: true,
            }
        });
    
    const userRscore = author ? Number(author.reliabilityScore) : 0;

    /* Logger */
    console.log("userRscore:", userRscore);
    console.log("rating:", rating);
    console.log("description:", description);

    const newReview = await prisma.Review.create({
        data: {
            userId: uid,
            poiId: pid,
            reliabilityScore: userRscore,
            rating: Number(rating),
            description: description
        }
    });

    const newRating = await calcPoiRating(pid);
    await prisma.poi.update({
        where: { id: Number(pid) },
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
        const timeBias = Math.exp(-2 * dTime);  // Should be exponential decay.

        const adjustment = (-1 * Math.abs(rating - review.rating)) + 2;
        const incrementBy = Math.round(adjustment * timeBias) * 10; // Must be integer.
        const userPromise = prisma.User.update({
            where: { id: review.userId }, 
            data: { reliabilityScore: { increment: incrementBy }}
        });
        // todo: max min check of user rscore and review rscore. (0~100)
        const reviewPromise = prisma.Review.update({
            where: { reviewId: Number(review.reviewId) },
            data: { reliabilityScore: { increment: incrementBy }}
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

