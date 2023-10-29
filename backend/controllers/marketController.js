import * as marketService from '../services/marketService.js';

export const getMarketById = async (req, res) => {
    try {
        const market = await marketService.getMarket(req.params.marketId);
        res.status(200).json(market);
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const createNewMarket = async (req, res) => {
    try {
        const market = await marketService.createMarket(req.body);
        res.status(201).json(market);
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const updateExistingMarket = async (req, res) => {
    try {
        const market = await marketService.updateMarket(req.params.marketId, req.body);
        res.status(200).json(market);
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const deleteMarketById = async (req, res) => {
    try {
        await marketService.deleteMarket(req.params.marketId);
        res.status(204).send();
    } catch (err) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};

export const listMarkets = async (req, res) => {
    try {
        const users = await marketService.listMarkets();
        res.status(200).json(users);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error" });
    }
};
