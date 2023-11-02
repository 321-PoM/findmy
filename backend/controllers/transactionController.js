import * as transactionService from '../services/transactionService.js';

export const createTransaction = async (req, res) => {
    try{
        const transaction = await transactionService.createTransaction(req.body.buyerId, req.body.listingId);
        res.status(200).json(transaction);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getOne = async (req, res) => {
    try{
        const transaction = await transactionService.getOne(req.params.transactionId);
        res.status(200).json(transaction);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const listUserTransactions = async (req, res) => {
    try{
        const transactions = await transactionService.listUserTransactions(req.params.userId);
        res.status(200).json(transactions);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const listListingTransactions = async (req, res) => {
    try{
        const transactions = await transactionService.listUserTransactions(req.params.listingId);
        res.status(200).json(transactions);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const updateTransaction = async (req, res) => {
    try{
        const transaction = await transactionService.updateTransaction(req.params.transactionId, req.body.data);
        res.status(200).json(transaction);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const deleteTransaction = async (req, res) => {
    try{
        const del = await transactionService.deleteTransaction(req.params.transactionId);
        res.status(200).json(del);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};