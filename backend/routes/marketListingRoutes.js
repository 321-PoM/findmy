import express from 'express';
import * as marketListingController from '../controllers/marketListingController.js';

const router = express.Router();

router.post('/marketListing', marketListingController.create);
router.get('/marketListing/:id', marketListingController.getOne);
router.get('/marketListings', marketListingController.getAll);
router.put('/marketListing/:id', marketListingController.update);
router.delete('/marketListing/:id', marketListingController.deleteListing);

export default router;
