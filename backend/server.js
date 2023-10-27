import express      from 'express';
import { user }     from './user/user.js';
import { friend }   from './user/friend.js';
import { poi }      from './poi/poi.js'
import { rscore }   from './user/rscore.js'
import { market }   from './market/market.js'

const app = express();
app.use(express.json());

// User
app.get(   "/user", async(req, res) => {await user['get'](req, res)});
app.post(  "/user", async(req, res) => {await user['post'](req, res)});
app.put(   "/user", async(req, res) => {await user['put'](req, res)});
app.delete("/user", async(req, res) => {await user['delete'](req, res)});

// Reliability Score
app.get(   "/user/rscore", async(req, res) => {await rscore(req, res)});

// Friendlist
app.get(   "/user/friend", async(req, res) => {await friend['get'](req, res)});
app.post(  "/user/friend", async(req, res) => {await friend['post'](req, res)});
app.put(   "/user/friend", async(req, res) => {await friend['put'](req, res)});
app.delete("/user/friend", async(req, res) => {await friend['delete'](req, res)});

// POI
app.get(   "/poi", async(req, res) => {await poi['get'](req, res)});
app.post(  "/poi", async(req, res) => {await poi['post'](req, res)});
app.put(   "/poi", async(req, res) => {await poi['put'](req, res)});
app.delete("/poi", async(req, res) => {await poi['delete'](req, res)});

// Market
app.get(   "/market", async(req, res) => {await market['get'](req, res)});
app.post(  "/market", async(req, res) => {await market['post'](req, res)});
app.put(   "/market", async(req, res) => {await market['put'](req, res)});
app.delete("/market", async(req, res) => {await market['delete'](req, res)});

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

