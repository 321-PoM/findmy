import * as marketListingService from '../services/marketListingService.js';

export const createListing = async (req, res) => {
    try{
        const listing = await marketListingService.createListing(req.body.price, req.body.sellerId, req.body.poiId);
        res.status(200).json(listing);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getOne = async (req, res) => {
    try{
        const listing = await marketListingService.getOne(req.params.listingId);
        res.status(200).json(listing);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getAll = async (req, res) => {
    try{
        const listings = await marketListingService.getAll();
        res.status(200).json(listings);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getUserListings = async (req, res) => {
    try{
        const listings = await marketListingService.getUserListings(req.params.userId);
        res.status(200).json(listings);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const updateListing = async (req, res) => {
    try{
        const listing = await marketListingService.updateListing(req.params.listingId, req.body.data);
        res.status(200).json(listing);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const deleteListing = async (req, res) => {
    try{
        const listing = await marketListingService.deleteListing(req.params.listingId);
        res.status(200).json(listing);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getMarketListingsByPoi = async (req, res) => {
    try {
        const listings = await marketListingService.getMarketListingsByPoiId(req.params.poiId);
        res.status(200).json(listings);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
