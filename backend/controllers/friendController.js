import * as friendService from '../services/friendService.js';

export const listFriends = async (req, res) => {
    try {
        
        res.status(200).json();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const getFriendship = async (req, res) => {
    try {
        
        res.status(200).json();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const createFriendship = async (req, res) => {
    try {
        
        res.status(200).json();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const updateFriendship = async (req, res) => {
    try {
        
        res.status(200).json();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const deleteFriendship = async (req, res) => {
    try {
        
        res.status(200).json();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};