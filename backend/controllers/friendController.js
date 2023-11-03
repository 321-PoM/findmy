import * as friendService from '../services/friendService.js';

export const listFriends = async (req, res) => {
    try {
        const list = await friendService.listFriends(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const listRequestsReceived = async (req, res) => {
    try {
        const list = await friendService.listRequestsReceived(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const listRequestsSent = async (req, res) => {
    try {
        const list = await friendService.listRequestsSent(req.params.userId)
        res.status(200).json(list);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const getFriendship = async (req, res) => {
    try {
        const friend = await friendService.getFriendship(req.params.friendshipId)
        res.status(200).json(friend);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const createFriendship = async (req, res) => {
    try {
        const friend = await friendService.createFriendship(req.body.userIdFrom, req.body.userIdTo)
        res.status(200).json(friend);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const handleFriendRequest = async (req, res) => {
    try {
        const handleRequest = await friendService.handleFriendRequest(req.params.friendshipId, req.params.acceptRequest)
        res.status(200).json(handleRequest);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const deleteFriendship = async (req, res) => {
    try {
        const del = await friendService.deleteFriendship(req.body.friendshipId);
        res.status(200).json(del);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
