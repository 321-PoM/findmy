import * as transactionService from '../services/transactionService.js';

export const create = async (req, res) => {
    const transaction = await transactionService.create(req.body);
    res.json(transaction);
};

export const getOne = async (req, res) => {
    const transaction = await transactionService.getOne(req.params.id);
    res.json(transaction);
};

export const getAll = async (req, res) => {
    const transactions = await transactionService.getAll();
    res.json(transactions);
};

export const update = async (req, res) => {
    const transaction = await transactionService.update(req.params.id, req.body);
    res.json(transaction);
};

export const deleteTransaction = async (req, res) => {
    await transactionService.deleteTransaction(req.params.id);
    res.json({ message: 'Transaction deleted successfully.' });
};
