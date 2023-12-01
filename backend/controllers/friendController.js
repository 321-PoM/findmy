import * as friendService from '../services/friendService.js';
import { controllerErrorHandler } from "./controllerErrorHandler.js";


export const listFriends = async (req, res) => {
    // console.log("");
    try {
        const list = await friendService.listFriends(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const listRequestsReceived = async (req, res) => {
    // console.log("");
    try {
        const list = await friendService.listRequestsReceived(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const listRequestsSent = async (req, res) => {
    // console.log("");
    try {
        const list = await friendService.listRequestsSent(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const getFriendship = async (req, res) => {
    // console.log("");
    try {
        const friend = await friendService.getFriendship(req.params.friendshipId)
        res.status(200).json(friend);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const createFriendship = async (req, res) => {
    // console.log("");
    try {
        const friend = await friendService.createFriendship(req.body.userIdFrom, req.body.userIdTo)
        res.status(200).json(friend);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const handleFriendRequest = async (req, res) => {
    // console.log("");
    try {
        const handleRequest = await friendService.handleFriendRequest(req.params.userIdFrom, req.params.userIdTo, req.params.acceptRequest)
        res.status(200).json(handleRequest);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};

export const deleteFriendship = async (req, res) => {
    // console.log("");
    try {
        const del = await friendService.deleteFriendship(req.params.id);
        res.status(200).json(del);
    } catch (err) {
        controllerErrorHandler(err, req, res); 
    }
};
