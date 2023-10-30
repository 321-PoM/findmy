import express from 'express';
import * as reviewController from '../controllers/reviewController.js';

const router = express.Router();

router.get('/reviews', reviewController.listReviews);       // expects /review?searchBy= user OR poi
router.get('/review', reviewController.getReview);
router.post('/review', reviewController.createReview);
router.put('/review', reviewController.updateReview);
router.delete('/review', reviewController.deleteReview);

export default router;