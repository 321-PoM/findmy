import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export const listFriends = async (userId) => {
    try{
        const friendshipsFromMe = await prisma.friendship.findMany({
            where: {
                userIdFrom: Number(userId),
                status: "accepted",
            }
        });

        const friendshipsToMe = await prisma.friendship.findMany({
            where: {
                userIdTo: Number(userId),
                status: "accepted",
            }
        });

        const friendsFromMe = new Set(friendshipsFromMe.map(friendship => friendship.userIdTo));
        const friendsToMe = new Set(friendshipsToMe.map(friendship => friendship.userIdFrom));

        return friendsFromMe.filter(userId => friendsToMe.has(userId));
    } catch (err) {
        throw err;
    }
};

export const listRequestsSent = async (userId) => {
    return await prisma.friendship.findMany({
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
    return await prisma.friendship.findMany({
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
    return await prisma.friendship.findUnique({
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
    return await prisma.friendship.create({
        data: {
            userIdFrom: userIdFrom,
            userIdTo: userIdTo,
            status: "accepted",
        },
        select: { friendshipId: true },
    });
};

export const handleFriendRequest = async (friendshipId, acceptRequest) => {
    try {
        if (acceptRequest.toLowerCase() == "true") {
            const accepted = await prisma.friendship.update({
                where: { friendshipId: Number(friendshipId) },
                data: { status: accept },
                include: {
                    userIdFrom: true,
                    userIdTo: true,
                    status: true,
                },
            });
            return await prisma.friendship.create({
                data: {
                    userIdFrom: accepted.userIdTo,
                    userIdTo: accepted.userIdFrom,
                    status: accepted.status,
                },
            });
        } else {
            return await prisma.friendship.update({
                where: { friendshipId: friendshipId },
                data: { status: rejected },
            });
        }
    } catch (err) {
        throw err;
    }
};

export const deleteFriendship = async (friendshipId) => {
    const del = await prisma.friendship.update({
        where: { friendshipId: Number(friendshipId) },
        data: { isDeleted: true }, // Soft-delete.
        include: {
            userIdFrom: true,
            userIdTo: true,
        },
    });
    return await prisma.friendship.update({
        where: {
            userIdFrom: del.userIdTo,
            userIdTo: del.userIdFrom,
        },
        data: { isDeleted: true },
    });
};
