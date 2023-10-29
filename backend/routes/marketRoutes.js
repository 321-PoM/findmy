import express from 'express';
import * as marketController from '../controllers/marketController.js';

const router = express.Router();

router.get('/markets', marketController.listMarkets);  // It's market's'. 
router.get('/market/:marketId', marketController.getMarketById);
router.post('/market', marketController.createNewMarket);
router.put('/market/:marketId', marketController.updateExistingMarket);
router.delete('/market/:marketId', marketController.deleteMarketById);

export default router;
