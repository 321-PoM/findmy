import * as userService from '../services/userService.js';
import * as poiService from '../services/poiService.js';
import * as marketListingService from '../services/marketListingService.js';

export const getUser = async (req, res) => {
    try {
        const user = await userService.getUser(req.params.id);
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getUserByEmail = async (req, res) => {
    try{
        const user = await userService.getUserByEmail(req.params.email);
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
}

export const createUser = async (req, res) => {
    try {
        const user = await userService.createUser(req.body);
        res.status(201).json(user);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const updateUser = async (req, res) => {
    try {
        const user = await userService.updateUser(req.params.id, req.body);
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const updateUserBux = async (req, res) => {
    try {
        const bux = await userService.updateUserBux(req.params.id, req.body.polarity, req.body.amount);
        res.status(200).json(bux);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const deleteUser = async (req, res) => {
    try {
        await userService.deleteUser(req.params.id);
        res.status(204).send();
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const listUsers = async (req, res) => {
    try {
        const users = await userService.listUsers();
        res.status(200).json(users);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getReliabilityScore = async (req, res) => {
    try {
        const score = await userService.getUserReliabilityScore(req.params.userId);
        res.status(200).json({ reliabilityScore: score });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getUserPoiWithMarketListing = async (req, res) => {
    try {
        const pois = await poiService.getPoiByUser(req.params.id);
        const mlistings = await marketListingService.getMarketListingByUser(req.params.id);
        res.status(200).json({
            pois: pois,
            marketListings: mlistings
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
}
