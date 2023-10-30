import * as userService from '../services/userService.js';

export const getUser = async (req, res) => {
    try {
        const user = await userService.getUser(req.params.id);
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const createUser = async (req, res) => {
    try {
        const user = await userService.createUser(req.body);
        res.status(201).json(user);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const updateUser = async (req, res) => {
    try {
        const user = await userService.updateUser(req.params.id, req.body);
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const deleteUser = async (req, res) => {
    try {
        await userService.deleteUser(req.params.id);
        res.status(204).send();
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const listUsers = async (req, res) => {
    try {
        const users = await userService.listUsers();
        res.status(200).json(users);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const getReliabilityScore = async (req, res) => {
    try {
        const score = await userService.getUserReliabilityScore(req.params.userId);
        res.status(200).json({ reliabilityScore: score });
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};