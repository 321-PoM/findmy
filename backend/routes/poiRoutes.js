import express from 'express';
import * as poiController from '../controllers/poiController.js';

const router = express.Router();

router.get('/pois', poiController.listPois); 
router.get('/poi/:id', poiController.getPoi);
router.post('/poi', poiController.createPoi);
router.put('/poi/:id', poiController.updatePoi);
router.delete('/poi/:id', poiController.deletePoi);

export default router;