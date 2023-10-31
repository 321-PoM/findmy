import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const create = async (data) => {
    return prisma.transaction.create({ data });
};

export const getOne = async (id) => {
    return prisma.transaction.findUnique({ where: { id: Number(id) } });
};

export const getAll = async () => {
    return prisma.transaction.findMany();
};

export const update = async (id, data) => {
    return prisma.transaction.update({ where: { id: Number(id) }, data });
};

export const deleteTransaction = async (id) => {
    return prisma.transaction.delete({ where: { id: Number(id) } });
};
