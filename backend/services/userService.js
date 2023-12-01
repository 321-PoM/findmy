import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

export const createUser = async (userData) => {
    // Use toISOString() directly for compatibility with Prisma DateTime
    userData["mapBuxUpdate"] = new Date().toISOString();
    console.log(userData);
    return await prisma.User.create({
        data: userData,
    });
};

export const getUser = async (userId) => {
    const uid = parseInt(userId, 10);

    if (isNaN(uid)) throw new Error("Error: getUser | user ID is not int.");

    return await prisma.User.findUnique({
        where: {
            id: uid,
            isDeleted: false,
            isActive: true,
        },
    });
};

// export const getUserByEmail = async (email, shouldCreate) => {
//     const user = await prisma.User.findFirst({
//         where: { 
//             email: email,
//             isDeleted: false,
//             isActive: true,
//         },
//     });
//     if (user) return user;

//     if (shouldCreate != "true") {
//         throw new Error("Could not find user with email: " + email);
//     }
    
//     return await prisma.User.create({
//         data: { 
//             name: email,
//             email: email,
//             avatar: 'none',
//             biography: 'none',
//             reliabilityScore: 100,
//         }
//     });
// };

// export const updateUser = async (userId, updateData) => {
//     return await prisma.User.update({
//         where: { id: Number(userId) },
//         data: updateData,
//     });
// };

export const updateUserBux = async (userId, polarity, amount) => {
    const SECONDS = 60;
    const MILLISECONDS = 1000;
    const timeCapInMinutes = 2;

    const lastUpdate = await prisma.User.findUnique({
        where: { id: Number(userId) },
        select: { mapBuxUpdate: true }
    });
    console.log(lastUpdate);
    if((Date.now() - new Date(lastUpdate.mapBuxUpdate).getTime()) < timeCapInMinutes * SECONDS * MILLISECONDS) {
        throw new Error(`Error: You may only claim MapBux every ${timeCapInMinutes} minutes`);
    }

    return await prisma.User.update({
        where: { id: Number(userId) },
        data: {
            mapBux: (polarity) ? { increment: amount } : { decrement: amount },
            mapBuxUpdate: new Date(),
        },
        select: { mapBux: true }
    })
}

// export const deleteUser = async (userId) => {
//     return await prisma.User.update({
//         where: { id: Number(userId) },
//         data: { isDeleted: true },  // Soft-delete user.
//     });
// };

// TODO: deal with this deletion 
// export const deleteAllRefs = async (userId) => {
//     const listings = await prisma.marketListing.findMany({
//         where: {
//             sellerId: userId,
//             isDeleted: false
//         },
//         select: { id: true }
//     });
//     const delTransactions = listings.map((listing) => {
//         prisma.transaction.delete({
//             where: { listingId: Number(listing.id) }
//         });
//     })
//     await Promise.all(delTransactions);

//     const pois = await prisma.poi.findMany({
//         where: { 
//             ownerId: Number(userId),
//             isDeleted: false,
//         },
//         select: { id: true }
//     });
//     const delReviews = pois.map((poi) => {
//         prisma.Review.delete({
//             where: { poiId: Number(poi.id) }
//         });
//     })
//     await Promise.all(delReviews);

//     await prisma.poi.delete({
//         where: { ownerId: Number(userId) }
//     });

//     await prisma.marketListing.delete({
//         where: { sellerId: Number(userId) }
//     });
//     await prisma.transaction.delete({
//         where: { buyerId: Number(userId) }
//     });
//     await prisma.Review.delete({
//         where: { userId: Number(userId) }
//     });
//     await prisma.friendship.delete({
//         where: {
//             OR: [
//                 { userIdFrom: Number(userId) },
//                 { userIdTo: Number(userId) }
//             ]
//         }
//     });
//     await prisma.User.delete({
//         where: { id: Number(userId) }
//     });
// }

// export const deleteAllRefsWithEmail = async (email) => {
//     const users = prisma.User.findMany({
//         where: { email: email },
//         select: { id: true }
//     });
//     const delUsers = users.map((user) => deleteAllRefs(user.id));
//     await Promise.all(delUsers);
// }

// export const listUsers = async () => {
//     return await prisma.User.findMany({
//         where: {
//             isDeleted: false,
//             isActive: true
//         }
//     });
// };

// export const getUserReliabilityScore = async (userId) => {
//     // Query for Pois reviewed by the user
//     const reviewsByUser = await prisma.Review.findMany({
//         where: {
//             userId: Number(userId),
//             isDeleted: false,
//         }
//     });

//     if(reviewsByUser.length < 1) return 100;

//     // Create an array of promises for distFromSafeZone for each review
//     const distPromises = reviewsByUser.map(review => distFromSafeZone(review.poiId, review.rating));

//     // Use Promise.all to wait for all distFromSafeZone promises to resolve
//     const dists = await Promise.all(distPromises);

//     // Calculate the mean distance from safezone
//     let sumDist = dists.reduce((sum, dist) => sum + dist, 0);
//     let meanDist = (sumDist === 0) ? 0 : parseInt((sumDist / reviewsByUser.length), 10);

//     return 100 - meanDist;
// };

// const distFromSafeZone = async (poiId, rating) => {
//     const poiReviews = await prisma.Review.findMany({
//         where: {
//             poiId: poiId,
//             isDeleted: false,
//         }
//     });
//     const poiRatings = poiReviews.map(review => review.rating);
//     if(poiRatings.length < 4) return 0;

//     // find mean
//     let sumRating = poiRatings.reduce((sum, rating) => sum += rating, 0);
//     let numRatings = poiRatings.length;
//     let mean = sumRating / numRatings;

//     // find stdev
//     let sumDist = poiRatings.reduce((sum, rating) => sum += Math.pow((rating - mean), 2), 0);
//     let stdev = Math.pow((sumDist / numRatings), 0.5);

//     let max = mean + stdev;
//     let min = mean - stdev;

//     if(rating > max) return rating - max;
//     else if(rating < min) return min - rating;
//     return 0;
// }
