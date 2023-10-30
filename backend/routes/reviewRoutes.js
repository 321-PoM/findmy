import express from 'express';
import * as reviewController from '../controllers/reviewController.js';

const router = express.Router();

router.get('/reviews/:searchBy/:id', reviewController.listReviews);       // searchBy = "user" OR "poi"
router.get('/review/:id', reviewController.getReview);
router.post('/review', reviewController.createReview);
router.put('/review/:id', reviewController.updateReview);
router.put('/review/:id/rating', reviewController.updateRating);
router.delete('/review/:id', reviewController.deleteReview);

export default router;