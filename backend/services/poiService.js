import { PrismaClient } from '@prisma/client';
import { isPointWithinRadius } from 'geolib';
import { updateUserBux } from './userService.js';

const prisma = new PrismaClient();

export const createPoi = async (poiData) => {
    return await prisma.poi.create({
        data: poiData,
    });
};

export const getPoi = async (poiId) => {
    return await prisma.poi.findUnique({
        where: {
            id: Number(poiId),
        },
        include: {
            image: true,
            owner: true,
        },
    });
};

export const updatePoi = async (poiId, updateData) => {
    return await prisma.poi.update({
        where: { id: Number(poiId) },
        data: updateData,
    });
};

export const reportPoi = async (poiId) => {
    try{
        const numReports = await prisma.poi.update({
            where: { poiId: Number(poiId) },
            data: { reports: { increment: 1 }},
            include: { 
                id: true,
                reports: true,
            },
        });
    
        const numReview = prisma.Review.count({
            where: { poiId: numReports['id'] }
        });
        return numReports / numReview;
    } catch (err) {
        throw err;
    }
}

export const transferPoi = async (transactionId) => {
    try{
        const transaction = prisma.Transaction.findUnique({
            where: { id: Number(transactionId)},
            include: { 
                buyerId: true,
                listingId: true,
            }
        });

        const listedPrice = prisma.marketListing.findUnique({
            where: { id: transaction.listingId },
            include: { price: true },
        });

        const buyerWallet = prisma.User.findUnique({
            where: { id: transaction.buyerId },
            include: { mapBux: true },
        });

        if(buyerWallet.mapBux < listedPrice) throw new Error("If you're broke just say that");

        const updateBux = await updateUserBux(transaction.buyerId, false, listedPrice.price);
        return updateBux;
    } catch (err) {
        throw new err;
    }
}

export const deletePoi = async (poiId) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: { isDeleted: true },  // Soft-delete.
    });
};

export const listPois = async () => {
    return await prisma.poi.findMany({
        where: {
            isDeleted: false,
        },
        include: {
            image: true,
            owner: true,
        },
    });
};

// ChatGPT usage: Partial
export const listFilteredPois = async (currLong, currLat, poiType, distance) => {

    const coords = getBoundingBox(parseFloat(currLat), parseFloat(currLong), parseInt(distance));

    var bboxPois;

    if (poiType == "All") {
        bboxPois = await prisma.poi.findMany({
            where: {
                isDeleted: false,
                latitude: {
                    gt:coords.latMin,
                    lt:coords.latMax,
                },
                longitudes: {
                    gt: coords.lonMin,
                    lt: coords.lonMax,
                }
            },
        })
    } else {
        bboxPois = await prisma.poi.findMany({
            where: {
                isDeleted: false,
                category: poiType,
                latitude: {
                    gt:coords.latMin,
                    lt:coords.latMax,
                },
                longitudes: {
                    gt: coords.lonMin,
                    lt: coords.lonMax,
                }
            },
        })
    }


    return bboxPois.filter(poi => isPointWithinRadius({latitude: parseFloat(currLat), longitude: parseFloat(currLong)}, {latitude: poi.latitude, longitude: poi.longitudes}, parseInt(distance)));
}

// ChatGPT usage: Partial
function getBoundingBox(lat, lon, distance) {
    // Radius of the Earth in meters
    const earthRadius = 6371000;
  
    // Convert distance from meters to radians
    const angularDistance = distance / earthRadius;
  
    // Convert latitude and longitude from degrees to radians
    const latRad = lat * (Math.PI / 180);
    const lonRad = lon * (Math.PI / 180);
  
    // Calculate the latitude range
    const latMin = lat - (angularDistance * (180 / Math.PI));
    const latMax = lat + (angularDistance * (180 / Math.PI));
  
    // Calculate the longitude range
    const lonMin = lon - (angularDistance * (180 / Math.PI) / Math.cos(latRad));
    const lonMax = lon + (angularDistance * (180 / Math.PI) / Math.cos(latRad));
  
    return {
        latMin: parseFloat(latMin),
        latMax: parseFloat(latMax),
        lonMin: parseFloat(lonMin),
        lonMax: parseFloat(lonMax),
    };
  }

export const calcPoiRating = async (poiId) => {
    try{
        const allRatings = await prisma.review.findMany({
            where: {
                poiId: Number(poiId),
            },
            include: {
                rating: true,
                reliabilityScore: true,
            }
        });
        
        // Calculate new weighted rating
        let totalWeight = 0;
        let weightedSum = 0;
        for(const rating of allRatings){
            totalWeight += rating['reliabilityScore'];
            weightedSum += rating['rating'] * rating['reliabilityScore'];
        }
        return weightedSum / totalWeight;
    } catch (err) {
        throw err;
    }
}

export const getPoiByUser = async (userId) => {
    const uid = parseInt(userId);

    if (isNaN(uid)) {
        return res.status(400).json({ message: "Error: getUser | user ID is not int." });
    }

    try {
        const allPois = await prisma.poi.findMany({
            where: {
                userId: userID,
            },
        });
        return allPois
    } catch (err) {
        return err;
    }
}

