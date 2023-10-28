import express      from 'express';
import { user }     from './components/user/user.js';
import { friend }   from './components/user/friend.js';
import { poi }      from './components/poi/poi.js'
import { rscore }   from './components/user/rscore.js'
import { market }   from './components/market/market.js'
import http         from 'http';
import https        from 'https';
import fs           from 'fs';

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

// read env variable for ssl key and cert
var key_cert_path = process.env.KEY_CERT_PATH

var server_opt = {
    key: fs.readFileSync(key_cert_path + '/privkey.pem'),
    cert: fs.readFileSync(key_cert_path + '/fullchain.pem')
};

var ports = {
    http:  80,
    https: 443,
}

http.createServer(app).listen(ports.http);
https.createServer(server_opt, app).listen(ports.https);

console.log(`Server running at http port:${ports.http}; https port:${ports.https}`);

