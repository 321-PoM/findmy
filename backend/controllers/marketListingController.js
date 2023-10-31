import * as marketListingService from '../services/marketListingService.js';

export const createListing = async (req, res) => {
    try{
        const listing = await marketListingService.createListing(req.body.price, req.body.sellerId, req.body.poiId);
        res.send(200).json(listing);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};

export const getOne = async (req, res) => {
    try{
        const listing = await marketListingService.getOne(req.param.listingId);
        res.send(200).json(listing);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};

export const getAll = async (req, res) => {
    try{
        const listings = await marketListingService.getAll();
        res.send(200).json(listings);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};

export const getUserListings = async (req, res) => {
    try{
        const listings = await marketListingService.getUserListings(req.param.userId);
        res.send(200).json(listings);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};

export const updateListing = async (req, res) => {
    try{
        const listing = await marketListingService.updateListing(req.param.listingId, req.body.data);
        res.send(200).json(listing);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};

export const deleteListing = async (req, res) => {
    try{
        const listing = await marketListingService.deleteListing(req.param.listingId);
        res.send(200).json(listing);
    } catch (error) {
        res.send(500).json({ message: error.message });
    }
};