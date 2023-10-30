import { PrismaClient } from '@prisma/client';
const prisma = new PrismaClient();

export const getMarket = async (marketId) => {
    return await prisma.market.findUnique({
        where: { id: marketId },
    });
};

export const createMarket = async (data) => {
    return await prisma.market.create({
        data,
    });
};

export const updateMarket = async (marketId, data) => {
    return await prisma.market.update({
        where: { id: marketId },
        data,
    });
};

export const deleteMarket = async (marketId) => {
    return await prisma.market.delete({
        where: { id: marketId },
    });
};

export const listMarkets = async () => {
    return await prisma.market.findMany({
        where: {
            isDeleted: false
        }
    });
};
