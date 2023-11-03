import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createListing = async (price, sellerId, poiId) => {
    return await prisma.marketListing.create({
        data: {
            price: price,
            sellerId: Number(sellerId),
            poiId: Number(poiId)
        },
        select: { id: true },
    });
};

export const getOne = async (id) => {
    return await prisma.marketListing.findUnique({
        where: { 
            id: Number(id),
            isDeleted: false,
            isActive: true,
        },
        include: { 
            poi: true,
            seller: true,
        }
    });
};

export const getAll = async () => {
    return await prisma.marketListing.findMany({
        where: { 
            isDeleted: false, 
            isActive: true 
        },
        include: { 
            poi: true,
            seller: true,
        }
    });
};

export const getUserListings = async (userId) => {
    return await prisma.marketListing.findMany({
        where: {
            sellerId: Number(userId),
            isDeleted: false,
            isActive: true,
        },
        include: { 
            poi: true,
            seller: true,
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
    return await prisma.marketListing.update({
        where: { id: Number(id) },
        data: { 
            isActive: false,
            isDeleted: true,
        },
        select: { id: true },
    });
};

export const getMarketListingByUser = async (userId) => {
    const uid = parseInt(userId);

    if (isNaN(uid)) {
        return res.status(400).json({ message: "Error: getUser | user ID is not int." });
    }

    return await prisma.poi.findMany({
        where: {
            isDeleted: false,
            isActive: true,
            ownerId: uid,
        },
        include: { 
            poi: true,
            seller: true,
        }
    });
};

export const getMarketListingsByPoiId = async (poiId) => {
    return await prisma.marketListing.findMany({
        where: {
            poiId: Number(poiId),
            isActive: true,
            isDeleted: false
        }
    });
};
