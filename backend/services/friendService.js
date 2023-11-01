import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export const listFriends = async (userId) => {
    return await prisma.Friend.findMany({
        where: {
            userIdFrom: Number(userId),
            status: "accepted",
        },
        include: {
            friendshipId: true,
            userIdTo: true,
        },
    });
};

export const listRequestsSent = async (userId) => {
    return await prisma.Friend.findMany({
        where: {
            userIdFrom: Number(userId),
            status: "requested",
        },
        include: {
            friendshipId: true,
            userIdTo: true,
        },
    });
};

export const listRequestsReceived = async (userId) => {
    return await prisma.Friend.findMany({
        where: {
            userIdTo: Number(userId),
            status: "requested",
        },
        include: {
            friendshipId: true,
            userIdFrom: true,
        },
    });
};

export const getFriendship = async (friendshipId) => {
    return await prisma.Friend.findUnique({
        where: {
            friendshipId: Number(friendshipId),
        },
        include: {
            userIdFrom: true,
            userIdTo: true,
            status: true,
            createdAt: true,
        },
    });
};

export const createFriendship = async (userIdFrom, userIdTo) => {
    return await prisma.Friend.create({
        data: {
            userIdFrom: userIdFrom,
            userIdTo: userIdTo,
            status: "accepted",
        },
        select: { friendshipId: true },
    });
};

export const handleFriendRequest = async (friendshipId, accept) => {
    try {
        if (accept) {
            const accepted = await prisma.Friend.update({
                where: { friendshipId: Number(friendshipId) },
                data: { status: accept },
                include: {
                    userIdFrom: true,
                    userIdTo: true,
                    status: true,
                },
            });
            return await prisma.Friend.create({
                data: {
                    userIdFrom: accepted.userIdTo,
                    userIdTo: accepted.userIdFrom,
                    status: accepted.status,
                },
            });
        } else {
            return await prisma.Friend.update({
                where: { friendshipId: friendshipId },
                data: { status: rejected },
            });
        }
    } catch (err) {
        throw err;
    }
};

export const deleteFriendship = async (friendshipId) => {
    const del = await prisma.Friend.update({
        where: { friendshipId: Number(friendshipId) },
        data: { isDeleted: true }, // Soft-delete.
        include: {
            userIdFrom: true,
            userIdTo: true,
        },
    });
    return await prisma.Friend.update({
        where: {
            userIdFrom: del.userIdTo,
            userIdTo: del.userIdFrom,
        },
        data: { isDeleted: true },
    });
};
