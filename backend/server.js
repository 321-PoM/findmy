const express          = require("express");
const { OAuth2Client } = require('google-auth-library');
const os               = require("os");

import { user } from "user/user.js"

require("dotenv").config();
const app = express();
app.use(express.json());

// User
app.get(   "/user", async(req, res) => {user.get});
// app.post(  "/user", async(req, res) => {makeUser(req, res)});
// app.put(   "/user", async(req, res) => {updateUser(req, res)});
// app.delete("/user", async(req, res) => {removeUser(req, res)});

// app.get(   "/user/Rscore", async(req, res) => {getUserReliabilityScore(req, res)});

// app.get(   "/user/friend", async(req, res) => {getFriendList(req, res)});
// app.put(   "/user/friend", async(req, res) => {addFriend(req, res)});
// app.delete("/user/friend", async(req, res) => {removeFriend(req, res)});



// POI

// Market

// Payment

const run = async () => {
    try{
        let server = app.listen(8081, (req, res) => {
            let host = server.address().address;
            let port = server.address().port;

            console.log(`Server running at: https://${host}:${port}`);
        })
    }
    catch(err){
        console.error(err);
    }
}

run();

