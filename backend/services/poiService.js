import { PrismaClient } from "@prisma/client";
import { isPointWithinRadius } from "geolib";
import { getUser, updateUserBux, updateUser } from "./userService.js";
import { getOne, deleteListing } from "./marketListingService.js";
import { listFriends } from "./friendService.js";

const prisma = new PrismaClient();

export const createPoi = async (poiData) => {
    if(poiData.category == "myPOI") return createMyPoi(poiData);
    return await prisma.poi.create({
        data: {
            latitude: parseFloat(poiData.latitude),
            longitude: parseFloat(poiData.longitude),
            category: poiData.category,
            status: poiData.status,
            description: poiData.description,
            ownerId: parseInt(poiData.ownerId, 10),
            rating: parseInt(poiData.rating, 10),
            imageUrl: poiData.imageUrl,
        }
    });
};

const createMyPoi = async (poiData) => {
    const minDist = 100;
    const filteredPois = await filterPoisFinely(poiData.latitude, poiData.longitude, "myPOI", minDist);
    if(filteredPois.length > 0) throw new Error("A myPOI already exists in this area (" + minDist.toString() + "m radius)");
    return await prisma.poi.create({
        data: {
            latitude: parseFloat(poiData.latitude),
            longitude: parseFloat(poiData.longitude),
            category: poiData.category,
            status: poiData.status,
            description: poiData.description,
            ownerId: parseInt(poiData.ownerId, 10),
            rating: parseInt(poiData.rating, 10),
            imageUrl: poiData.imageUrl,
        }
    });
}

export const getPoi = async (poiId, userId) => {
    const poi = await prisma.poi.findUnique({
        where: {
            id: Number(poiId),
            isDeleted: false,
        }
    });
    if(poi.category != "myPOI") return poi;

    const friends = (await listFriends(userId)).map((friend) => Number(friend.id));
    const friendsAndMe = new Set(friends).add(Number(userId));
    if(friendsAndMe.has(Number(poi.ownerId))) return poi;

    poi.description = "locked";
    poi.imageUrl = null;
    return poi;
};

export const updatePoi = async (poiId, updateData) => {
    return await prisma.poi.update({
        where: { id: Number(poiId) },
        data: updateData,
    });
};

export const reportPoi = async (poiId) => {
    const updatedPoi = await prisma.poi.update({
        where: { id: Number(poiId) },
        data: { reports: { increment: 1 }},
    });

    let numReview = await prisma.Review.count({
        where: { poiId: updatedPoi['id'] }
    });

    // Need at least 3 reviews to calculate report - review ratio.
    if (numReview < 3) return 0;
    return updatedPoi.reports / numReview;
}

export const buyPoi = async (poiId, buyerId) => {
    console.log("");
    try{
        // get POI
        const poiOnSale = await prisma.poi.findUnique({
            where: {
                id: Number(poiId),
                category: "myPOI",
                isDeleted: false,
            }
        });
        if(!poiOnSale) throw new Error("Cannot find by poiId");

        // get Listing
        const listings = await prisma.marketListing.findMany({
            where: {
                poiId: Number(poiId),
                sellerId: Number(poiOnSale.ownerId),
                isDeleted: false,
            }
        });
        const poiListing = listings[0];
        if(!poiListing) throw new Error("Cannot find listing");

        // get Seller and Buyer
        const seller = await getUser(poiOnSale.ownerId);
        const buyer = await getUser(buyerId);
        if(!seller || !buyer) throw new Error("Cannot find seller/buyer");
        if(buyer.mapBux < poiListing.price) throw new Error("Buyer is too broke");

        // Update wallets
        const sellerWalletAfter = await updateUserBux(seller.id, true, poiListing.price);
        const buyerWalletAfter = await updateUserBux(buyer.id, false, poiListing.price);
        if(!sellerWalletAfter || !buyerWalletAfter){
            throw {
                position: 0, 
                buyer: buyer,
                seller: seller,
                message: "Transfer mapBux failed, putting funds back"
            };
        }
        // Update poi
        const poiWithNewOwner = await updatePoi(poiId, { ownerId: Number(buyerId) });
        if(!poiWithNewOwner || poiWithNewOwner.ownerId != buyerId){
            throw {
                position: 1,
                buyer: buyer,
                seller: seller,
                originalOwner: seller.id,
                poiId: id,
                message: "Change poi owner failed, putting funds and owner back now"
            }
        } 

        // Delete listing
        const del = await deleteListing(poiListing.id);
        if(!del) {
            throw {
                position: 2,
                listingId: poiListing.id,
                message: "Delete listing failed, transaction was successful"
            };
        } 

        return poiWithNewOwner;
    } catch (err) {
        if(!err.hasOwnProperty('position')) throw err;
        // Put money back if failed during transaction
        if(err.position === 0 || err.position === 1) {
            const putBackBuyer = await updateUser(err.buyer.id, err.buyer);
            const putBackSeller = await updateUser(err.seller.id, err.seller);
        }
        // Put owner back if failed after owner transfer
        if(err.position === 1) await updatePoi(err.poiId, { ownerId: Number(err.originalOwner) });
        // Try delete again if transaction suceeded but listing stays up
        if(err.position === 2) await deleteListing(err.listingId);
        throw new Error(err.message);
    }
}

export const deletePoi = async (poiId) => {
    return await prisma.poi.update({
        where: { id: Number(poiId) },
        data: { isDeleted: true },  // Soft-delete.
    });
};

export const listPois = async (userId) => {
    const pois = await prisma.poi.findMany({
        where: { isDeleted: false }
    });
    const friends = (await listFriends(userId)).map(friend => Number(friend.id));
    const friendsAndMe = new Set(friends).add(Number(userId));
    for(const poi of pois) {
        if(poi.category != "myPOI") continue;
        if(friendsAndMe.has(Number(poi.ownerId))) continue;
        poi.description = "locked";
        poi.imageUrl = null;
    }
    return pois;
};

export const listFilteredPois = async (currLong, currLat, poiType, distance, userId) => {
    const filteredList = await filterPois(currLong, currLat, poiType, distance);
    if(poiType != "myPOI" && poiType != "All") return filteredList;
    
    const friendsAndMe = new Set((await listFriends(userId)).map((friend) => friend.id)).add(userId);
    for(const poi of filteredList) {
        if(poi.category != "myPOI") continue;
        if(friendsAndMe.has(poi.ownerId)) continue;
        poi.description = "locked";
    }
    return filteredList;
}

// ChatGPT usage: Partial
export const filterPois = async (currLong, currLat, poiType, distance) => {

    const coords = getBoundingBox(parseFloat(currLat), parseFloat(currLong), parseInt(distance, 10));

    var bboxPois;

    if (poiType == "All") {
        bboxPois = await prisma.poi.findMany({
            where: {
                isDeleted: false,
                latitude: {
                    gt:coords.latMin,
                    lt:coords.latMax,
                },
                longitude: {
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
                longitude: {
                    gt: coords.lonMin,
                    lt: coords.lonMax,
                }
            },
        })
    }

    return bboxPois.filter(poi => 
        isPointWithinRadius({latitude: parseFloat(currLat), longitude: parseFloat(currLong)}, 
                            {latitude: poi.latitude, longitude: poi.longitude}, 
                            parseInt(distance, 10))
    );
}

export const filterPoisFinely = async (currLong, currLat, poiType, distance) => {

    const coords = getBoundingBox(parseFloat(currLat), parseFloat(currLong), parseInt(distance, 10));

    var bboxPois;

    if (poiType == "All") {
        bboxPois = await prisma.poi.findMany({
            where: {
                isDeleted: false,
                latitude: {
                    gt:coords.lonMax,
                    lt:coords.lonMin,
                },
                longitude: {
                    gt: coords.latMin,
                    lt: coords.latMax,
                }
            },
        })
    } else {
        bboxPois = await prisma.poi.findMany({
            where: {
                isDeleted: false,
                category: poiType,
                latitude: {
                    gt:coords.lonMax,
                    lt:coords.lonMin,
                },
                longitude: {
                    gt: coords.latMin,
                    lt: coords.latMax,
                }
            },
        })
    }
    
    return bboxPois;
}

// ChatGPT usage: Partial
const getBoundingBox = (lat, lon, distance) => {
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
    const allRatings = await prisma.review.findMany({
        where: { poiId: Number(poiId) }
    });
    if(allRatings.length < 1){
        const poi = await prisma.poi.findUnique({
            where: { poiId: Number(poiId) },
            include: { rating: true },
        });
        return poi.rating;
    }
    
    // Calculate new weighted rating
    let totalWeight = 0;
    let weightedSum = 0;
    for(const rating of allRatings){
        totalWeight += rating.reliabilityScore;
        weightedSum += rating.rating * rating.reliabilityScore;
    }
    return weightedSum / totalWeight;
}

export const getPoiByUser = async (userId) => {
    const uid = parseInt(userId, 10);

    if (isNaN(uid)) {
        return res.status(400).json({ message: "Error: getUser | user ID is not int." });
    }
    const allPois = await prisma.poi.findMany({
        where: { userId: userId },
    });
    return allPois
}

