import express from 'express';
import * as poiController from '../controllers/poiController.js';

const router = express.Router();

router.get('/pois', poiController.listPois); 
router.get('/poi/:id', poiController.getPoi);
router.get('/filteredPois/:longitude/:latitude/:poiType/:distance', poiController.listFilteredPois)
router.post('/poi', poiController.createPoi);
router.put('/poi/:id', poiController.updatePoi);
router.put('/poi/:id/report', poiController.reportPoi);
router.put('/poi/transaction/:transactionId', poiController.transactPoi);
router.delete('/poi/:id', poiController.deletePoi);

export default router;
