import http  from 'http';
import https from 'https';
import app from '../server.js'

const teardown = async () => {
    app.close();
}

export default teardown;