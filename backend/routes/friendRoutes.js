import express from 'express';
import * as friendController from '../controllers/friendController.js';

const router = express.Router();

router.get('/friends/:userId', friendController.listFriends);  
router.get('/friends/:userId/received', friendController.listRequestsReceived);
router.get('/friends/:userId/sent', friendController.listRequestsSent);
router.get('/friend', friendController.getFriendship);
router.post('/friend', friendController.createFriendship);
router.put('/friend', friendController.handleFriendRequest);
router.delete('/friend', friendController.deleteFriendship);

export default router;