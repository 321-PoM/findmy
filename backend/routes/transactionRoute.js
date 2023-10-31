import express from 'express';
import * as transactionController from '../controllers/transactionController.js';

const router = express.Router();

router.post('/transaction', transactionController.createTransaction);
router.get('/transaction/:transactionId', transactionController.getOne);
router.get('/transactions/buyer/:userId', transactionController.listUserTransactions);
router.get('/transactions/listing/:listingId', transactionController.listListingTransactions);
router.put('/transaction/:transactionId', transactionController.updateTransaction);
router.delete('/transaction/:transactionId', transactionController.deleteTransaction);

export default router;
