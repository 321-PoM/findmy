import express from 'express';
import * as transactionController from '../controllers/transactionController.js';

const router = express.Router();

router.post('/transaction', transactionController.create);
router.get('/transaction/:id', transactionController.getOne);
router.get('/transactions', transactionController.getAll);
router.put('/transaction/:id', transactionController.update);
router.delete('/transaction/:id', transactionController.deleteTransaction);

export default router;
