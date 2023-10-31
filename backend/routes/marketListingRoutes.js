import express from 'express';
import * as marketListingController from '../controllers/marketListingController.js';

const router = express.Router();

router.post('/marketListing', marketListingController.createListing);
router.get('/marketListing/:listingId', marketListingController.getOne);
router.get('/marketListings', marketListingController.getAll);
router.get('/marketListings/:userId', marketListingController.getUserListings);
router.put('/marketListing/:listingId', marketListingController.updateListing);
router.delete('/marketListing/:listingId', marketListingController.deleteListing);

export default router;
