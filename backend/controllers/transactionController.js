import * as transactionService from '../services/transactionService.js';
import { controllerErrorHandler } from './controllerErrorHandler.js';

export const createTransaction = async (req, res) => {
    console.log("");
    try{
        const transaction = await transactionService.createTransaction(req.body.buyerId, req.body.listingId);
        res.status(200).json(transaction);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const getOne = async (req, res) => {
    console.log("");
    try{
        const transaction = await transactionService.getOne(req.params.transactionId);
        res.status(200).json(transaction);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listUserTransactions = async (req, res) => {
    console.log("");
    try{
        const transactions = await transactionService.listUserTransactions(req.params.userId);
        res.status(200).json(transactions);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listListingTransactions = async (req, res) => {
    console.log("");
    try{
        const transactions = await transactionService.listUserTransactions(req.params.listingId);
        res.status(200).json(transactions);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updateTransaction = async (req, res) => {
    console.log("");
    try{
        const transaction = await transactionService.updateTransaction(req.params.transactionId, req.body.data);
        res.status(200).json(transaction);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const deleteTransaction = async (req, res) => {
    console.log("");
    try{
        const del = await transactionService.deleteTransaction(req.params.transactionId);
        res.status(200).json(del);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};
