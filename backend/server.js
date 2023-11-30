import express    from 'express';
import http       from 'http';
import https      from 'https';
import fs         from 'fs';
import userRoutes from './routes/userRoutes.js';
import poiRoutes  from './routes/poiRoutes.js';
import marketListingRoutes from './routes/marketListingRoutes.js';
import transactionRoutes from './routes/transactionRoute.js';
import reviewRoutes from './routes/reviewRoutes.js';
import friendRoutes from './routes/friendRoutes.js';
import dotenv from 'dotenv';

dotenv.config();

const app = express();
app.use(express.json());

// API Routers
app.use(userRoutes);
app.use(poiRoutes);
app.use(marketListingRoutes);
app.use(transactionRoutes);
app.use(reviewRoutes);
app.use(friendRoutes);

var key_cert_path = process.env.KEY_CERT_PATH

var server_opt = {
    key: fs.readFileSync(process.env.KEY_PATH),
    cert: fs.readFileSync(process.env.CERT_PATH)
};

var ports = {
    http: process.env.TEST_ENV ? process.env.HTTP_PORT : 80,
    https: process.env.TEST_ENV ? process.env.HTTPS_PORT : 443,
}

const httpServer = http.createServer(app).listen(ports.http);
const httpsServer = https.createServer(server_opt, app).listen(ports.https);

console.log(`Server running at http port:${ports.http}; https port:${ports.https}`);

export { httpServer, httpsServer, app };
