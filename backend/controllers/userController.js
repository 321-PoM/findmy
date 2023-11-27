import * as userService from '../services/userService.js';
import * as poiService from '../services/poiService.js';
import * as marketListingService from '../services/marketListingService.js';
import { controllerErrorHandler } from './controllerErrorHandler.js';

export const getUser = async (req, res) => {
    console.log("");
    try {
        const user = await userService.getUser(req.params.id);
        res.status(200).json(user);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getUserByEmail = async (req, res) => {
    console.log("");
    try{
        const user = await userService.getUserByEmail(req.params.email);
        res.status(200).json(user);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}

export const createUser = async (req, res) => {
    console.log("");
    try {
        const user = await userService.createUser(req.body);
        res.status(201).json(user);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updateUser = async (req, res) => {
    console.log("");
    try {
        const user = await userService.updateUser(req.params.id, req.body);
        res.status(200).json(user);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updateUserBux = async (req, res) => {
    console.log("");
    try {
        const bux = await userService.updateUserBux(req.params.id, req.body.polarity, req.body.amount);
        res.status(200).json(bux);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const deleteUser = async (req, res) => {
    console.log("");
    try {
        await userService.deleteUser(req.params.id);
        res.status(204).send();
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listUsers = async (req, res) => {
    console.log("");
    try {
        const users = await userService.listUsers();
        res.status(200).json(users);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const deleteUserAndRefs = async (req, res) => {
    console.log("");
    try{
        await userService.deleteAllRefs(req.params.id);
        res.status(204).send();
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}

export const getReliabilityScore = async (req, res) => {
    console.log("");
    try {
        const score = await userService.getUserReliabilityScore(req.params.userId);
        res.status(200).json({ reliabilityScore: score });
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getUserPoiWithMarketListing = async (req, res) => {
    console.log("");
    try {
        const pois = await poiService.getPoiByUser(req.params.id);
        const mlistings = await marketListingService.getMarketListingByUser(req.params.id);
        res.status(200).json({
            pois: pois,
            marketListings: mlistings
        });
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}
