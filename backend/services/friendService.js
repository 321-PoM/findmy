import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const listFriends = async (filter, userId) => {
    return await prisma.poi.findMany({
        where: {
            OR : [
                { userId1: userId },
                { userId2: userId },
            ],
            status: filter,             // enum('requested', 'accepted', 'rejected')
        },
        include: {
            friendshipId: true,
        },
    });
};

export const createFriendship = async (userId1, userId2) => {
    return await prisma.poi.create({
        data: {
            userId1: userId1,
            userId2: userId2,
        }
    });
};

export const getFriendship = async (friendshipId) => {
    return await prisma.poi.findUnique({
        where: {
            id: friendshipId,
        },
        include: {
            userId1: true,
            userId2: true,
            status: true,
            createdAt: true,
        },
    });
};

export const updateFriendship = async (friendshipId, status) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: updateData,
    });
};

export const deletePoi = async (poiId) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: { isDeleted: true },  // Soft-delete.
    });
};


