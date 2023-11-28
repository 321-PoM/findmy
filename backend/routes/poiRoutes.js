import express from 'express';
import multer from 'multer';
import * as poiController from '../controllers/poiController.js';

const router = express.Router();
/*
 * Multer middleware will store files in memeory temporarily,
 * since files will be uploaded to Azure Blob Storage afterward.
 */
const upload = multer({ storage: multer.memoryStorage() });

router.post('/poi', upload.single('image') ,poiController.createPoi);

router.get('/pois/:userId', poiController.listPois); 
router.get('/poi/:id/:userId', poiController.getPoi);
router.get('/filteredPois/:longitude/:latitude/:poiType/:distance/:userId', poiController.listFilteredPois);
router.put('/poi/:id', poiController.updatePoi);
router.put('/poi/:id/report', poiController.reportPoi);
router.put('/poi/:id/buy/:buyerId', poiController.buyPoi);
router.delete('/poi/:id', poiController.deletePoi);

export default router;
