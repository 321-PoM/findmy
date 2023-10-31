import * as marketListingService from '../services/marketListingService.js';

export const create = async (req, res) => {
    const listing = await marketListingService.create(req.body);
    res.json(listing);
};

export const getOne = async (req, res) => {
    const listing = await marketListingService.getOne(req.params.id);
    res.json(listing);
};

export const getAll = async (req, res) => {
    const listings = await marketListingService.getAll();
    res.json(listings);
};

export const update = async (req, res) => {
    const listing = await marketListingService.update(req.params.id, req.body);
    res.json(listing);
};

export const deleteListing = async (req, res) => {
    await marketListingService.deleteListing(req.params.id);
    res.json({ message: 'Listing deleted successfully.' });
};

