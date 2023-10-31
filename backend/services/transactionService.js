import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createTransaction = async (buyerId, listingId) => {
    return await prisma.Transaction.create({
        data: {
            buyerId: buyerId,
            listingId: listingId,
        },
        select: { id: true },
    });
};

export const getOne = async (transactionId) => {
    return await prisma.Transaction.findUnique({
        where: { id: Number(transactionId) },
        select: {
            buyerId: true,
            listingId: true,
        }
    });
};

export const listUserTransactions = async (userId) => {
    return await prisma.Transaction.findMany({
        where: { buyerId: userId },
        select: {
            id: true,
            listingId: true
        },
    });
};

export const listListingTransactions = async (listingId) => {
    return await prisma.Transaction.findMany({
        where: { listingId: listingId },
        select: {
            id: true,
            buyerId: true,
        }
    });
}

export const updateTransaction = async (transactionId, data) => {
    return await prisma.Transaction.update({
        where: { id: Number(transactionId) }, 
        data: data,
    });
};

export const deleteTransaction = async (id) => {
    return await prisma.Transaction.delete({
        where: { id: Number(id) } 
    });
};
