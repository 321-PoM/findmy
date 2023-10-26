import express from 'express';
import { user } from './user/user.js';
import { friend } from './user/friend.js';

const app = express();
app.use(express.json());

// User
app.get(   "/user", async(req, res) => {await user['get'](req, res)});
app.post(  "/user", async(req, res) => {await user['post'](req, res)});
app.put(   "/user", async(req, res) => {await user['put'](req, res)});
app.delete("/user", async(req, res) => {await user['delete'](req, res)});

// Reliability Score
// app.get(   "/user/Rscore", async(req, res) => {getUserReliabilityScore(req, res)});

// Friendlist
app.get(   "/user/friend", async(req, res) => {await friend['get'](req, res)});
app.post(  "/user/friend", async(req, res) => {await friend['post'](req, res)});
app.put(   "/user/friend", async(req, res) => {await friend['put'](req, res)});
app.delete("/user/friend", async(req, res) => {await friend['delete'](req, res)});



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

