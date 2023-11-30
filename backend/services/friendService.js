import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export const listFriends = async (userId) => {
    return filterFriendships("both", userId);
};

export const listRequestsSent = async (userId) => {
    return filterFriendships("away", userId);
};

export const listRequestsReceived = async (userId) => {
    return filterFriendships("toward", userId);
};

const filterFriendships = async (direction, userId) => {
    const friendshipsFromMe = await prisma.friendship.findMany({ where: { userIdFrom: Number(userId) }});
    const userIdOfRecipients = new Set(friendshipsFromMe.map((friendship) => friendship.userIdTo));

    const friendshipsToMe = await prisma.friendship.findMany({ where: { userIdTo: Number(userId) }});
    const userIdOfSenders = new Set(friendshipsToMe.map((friendship) => friendship.userIdFrom));

    let userIdList;
    if(direction == "both") userIdList = [...userIdOfRecipients].filter(userId => userIdOfSenders.has(userId));
    else if(direction == "away") userIdList = [...userIdOfRecipients].filter(userId => !userIdOfSenders.has(userId));
    else if(direction == "toward") userIdList = [...userIdOfSenders].filter(userId => !userIdOfRecipients.has(userId));
    else throw new Error("Error: invalid input (direction)");

    const userPromises = userIdList.map((userId) => prisma.User.findUnique({where: {id: Number(userId)}}));
    return await Promise.all(userPromises);
}

export const getFriendship = async (friendshipId) => {
    return await prisma.friendship.findUnique({
        where: { friendshipId: Number(friendshipId) }
    });
};

export const createFriendship = async (userIdFrom, userIdTo) => {
    const friendship = await prisma.friendship.findFirst({
        where: {
            userIdFrom: userIdFrom,
            userIdTo: userIdTo
        }
    });
    if(friendship != null) throw new Error("Error: this friendship already exists");
    return await prisma.friendship.create({
        data: {
            userIdFrom: userIdFrom,
            userIdTo: userIdTo,
            status: "accepted",
        },
        select: { friendshipId: true },
    });
};

export const handleFriendRequest = async (userIdFrom, userIdTo, acceptRequest) => {
    if(acceptRequest) {
        // this creates the corresponding friendship in the opposite direction
        return await prisma.friendship.create({
            data: {
                userIdFrom: userIdTo,
                userIdTo: userIdFrom,
                status: "accepted"
            }
        });
    }
    else {
        // if not accept, delete the request
        return await prisma.friendship.delete({
            where: {
                userIdFrom: userIdFrom,
                userIdTo: userIdTo
            }
        });
    }
}

export const deleteFriendship = async (friendshipId) => {
    const del = await prisma.friendship.delete({
        where: { friendshipId: Number(friendshipId) },
        select: {
            userIdFrom: true,
            userIdTo: true
        }
    });
    return await prisma.friendship.delete({
        where: {
            userIdFrom: del.userIdTo,
            userIdTo: del.userIdFrom,
        }
    });
};
