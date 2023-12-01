import express from "express";
import * as userController from "../controllers/userController.js";

const router = express.Router();

router.post("/user", userController.createUser);
router.get("/user/:id", userController.getUser);

// router.put("/user/:id/updateUserBux", userController.updateUserBux);
// router.get("/users", userController.listUsers); // It's user's'.
// router.get("/userPoiWithMarketListing/:id", userController.getUserPoiWithMarketListing);
// router.get("/user/email/:email/:shouldCreate", userController.getUserByEmail);
// router.put("/user/:id", userController.updateUser);
// router.delete("/user/:id", userController.deleteUser);
// router.delete("/user/:id/all", userController.deleteUserAndRefs);
// router.delete("/user/email/:email/all", userController.deleteUserAndRefsWithEmail);
// router.get("/rscore/:userId", userController.getReliabilityScore);

export default router;
