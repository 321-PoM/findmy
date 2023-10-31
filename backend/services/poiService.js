import { PrismaClient } from '@prisma/client';
import { isPointWithinRadius } from 'geolib';
import { updateUserBux } from './userService';

const prisma = new PrismaClient();

export const createPoi = async (poiData) => {
    return await prisma.poi.create({
        data: poiData,
    });
};

export const getPoi = async (poiId) => {
    return await prisma.poi.findUnique({
        where: {
            id: poiId,
        },
        include: {
            image: true,
            owner: true,
        },
    });
};

export const updatePoi = async (poiId, updateData) => {
    return await prisma.poi.update({
        where: { id: poiId },
        data: updateData,
    });
};

export const reportPoi = async (poiId) => {
    try{
        const numReports = await prisma.poi.update({
            where: { poiId: poiId },
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
            where: { id: transactionId },
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

export const listFilteredPois = async (currLong, currLat, poiType, distance) => {

    latMin, latMax, lonMin, lonMax = getBoundingBox(currLat, currLong, distance);

    const bboxPois = await prisma.poi.findMany({
        where: {
            isDeleted: false,
            category: poiType,
            latitude: {
                gt:latMin,
                lt:laxMax,
            },
            longitude: {
                gt: lonMin,
                lt: lonMax,
            }
        },
    })

    return bboxPois.filter(poi => isPointWithinRadius({latitude: currLat, longitude: currLong}, {latitude: poi.latitude, longitude: poi.longitude}, distance));
}

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
      latMin,
      latMax,
      lonMin,
      lonMax,
    };
  }

export const calcPoiRating = async (poiId) => {
    try{
        const allRatings = await prisma.review.findMany({
            where: {
                poiId: poiId,
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

