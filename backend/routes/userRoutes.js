import express from "express";
import * as userController from "../controllers/userController.js";

const router = express.Router();

router.get("/users", userController.listUsers); // It's user's'.
router.get("/user/:id", userController.getUser);
router.get("/userPoiWithMarketListing/:id", userController.getUserPoiWithMarketListing);
router.get("/user/email/:email/:shouldCreate", userController.getUserByEmail);
router.post("/user", userController.createUser);
router.put("/user/:id", userController.updateUser);
router.put("/user/:id/updateUserBux", userController.updateUserBux);
router.delete("/user/:id", userController.deleteUser);
router.delete("/user/:id/all", userController.deleteUserAndRefs);
router.delete("/user/email/:email/all", userController.deleteUserAndRefsWithEmail);
router.get("/rscore/:userId", userController.getReliabilityScore);

export default router;
