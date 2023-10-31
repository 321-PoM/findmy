import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createListing = async (price, sellerId, poiId) => {
    return await prisma.marketListing.create({
        data: {
            price: price,
            sellerId: sellerId,
            poiId: poiId
        },
        select: { id: true },
    });
};

export const getOne = async (id) => {
    return await prisma.marketListing.findUnique({
        where: { id: Number(id) },
        select: {
            id: true,
            price: true,
            sellerId: true,
            poiId: true,
        } 
    });
};

export const getAll = async () => {
    return await prisma.marketListing.findMany({
        where: { isDeleted: false },
        select: { 
            id: true,
            price: true,
            sellerId: true,
            poiId: true,
        }
    });
};

export const getUserListings = async (userId) => {
    return await prisma.marketListing.findMany({
        where: {
            sellerId: userId,
            isDeleted: false,
        },
        select: {
            id: true,
            price: true,
            sellerId: true,
            poiId: true,
        }
    });
};

export const updateListing = async (id, data) => {
    return await prisma.marketListing.update({
        where: { id: Number(id) }, 
        data: data,
    });
};

export const deleteListing = async (id) => {
    return prisma.marketListing.update({
        where: { id: Number(id) },
        data: { 
            isActive: false,
            isDeleted: true,
        },
        select: { id: true },
    });
};