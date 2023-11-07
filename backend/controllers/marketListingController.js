import * as marketListingService from '../services/marketListingService.js';
import { controllerErrorHandler } from './controllerErrorHandler.js';

export const createListing = async (req, res) => {
    console.log("");
    try{
        const listing = await marketListingService.createListing(req.body.price, req.body.sellerId, req.body.poiId);
        res.status(200).json(listing);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getOne = async (req, res) => {
    console.log("");
    try{
        const listing = await marketListingService.getOne(req.params.listingId);
        res.status(200).json(listing);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getAll = async (req, res) => {
    console.log("");
    try{
        const listings = await marketListingService.getAll();
        res.status(200).json(listings);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getUserListings = async (req, res) => {
    console.log("");
    try{
        const listings = await marketListingService.getUserListings(req.params.userId);
        res.status(200).json(listings);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updateListing = async (req, res) => {
    console.log("");
    try{
        const listing = await marketListingService.updateListing(req.params.listingId, req.body.data);
        res.status(200).json(listing);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const deleteListing = async (req, res) => {
    console.log("");
    try{
        const listing = await marketListingService.deleteListing(req.params.listingId);
        res.status(200).json(listing);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getMarketListingsByPoi = async (req, res) => {
    console.log("");
    try {
        const listings = await marketListingService.getMarketListingsByPoiId(req.params.poiId);
        res.status(200).json(listings);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};
