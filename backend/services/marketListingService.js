import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const create = async (data) => {
    return prisma.marketListing.create({ data });
};

export const getOne = async (id) => {
    return prisma.marketListing.findUnique({ where: { id: Number(id) } });
};

export const getAll = async () => {
    return prisma.marketListing.findMany();
};

export const update = async (id, data) => {
    return prisma.marketListing.update({ where: { id: Number(id) }, data });
};

export const deleteListing = async (id) => {
    return prisma.marketListing.delete({ where: { id: Number(id) } });
};
