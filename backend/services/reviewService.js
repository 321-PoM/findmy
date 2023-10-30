import { PrismaClient } from '@prisma/client';


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
        },
    })
}

export const createReview = async (poiId, userId, rating) => {
    return await prisma.review.create({
        data: {
            poiId: poiId,
            userId: userId,
            rating: rating,
        }
    });
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

