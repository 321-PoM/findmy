import express    from 'express';
import http       from 'http';
import https      from 'https';
import fs         from 'fs';
import userRoutes from './routes/userRoutes.js';
import poiRoutes  from './routes/poiRoutes.js';
import marketRoutes from './routes/marketRoutes.js';
import reviewRoutes from './routes/reviewRoutes.js';

const app = express();
app.use(express.json());

// API Routers
app.use(userRoutes);
app.use(poiRoutes);
app.use(marketRoutes);
app.use(reviewRoutes);

// // Friendlist
// app.get(   "/user/friend", async(req, res) => {await friend['get'](req, res)});
// app.post(  "/user/friend", async(req, res) => {await friend['post'](req, res)});
// app.put(   "/user/friend", async(req, res) => {await friend['put'](req, res)});
// app.delete("/user/friend", async(req, res) => {await friend['delete'](req, res)});

// Payment

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

