import express from 'express';
import * as reviewController from '../controllers/reviewController.js';

const router = express.Router();

router.get('/reviews/:searchBy/:id', reviewController.listReviews);       // searchBy = "user" OR "poi"
router.get('/review/:poiId/:userId', reviewController.getReview);
router.post('/review/:poiId', reviewController.createReview);
router.put('/review/:poiId/:userId', reviewController.updateReview);
router.delete('/review/:poiId/:userId', reviewController.deleteReview);

export default router;