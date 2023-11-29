import http  from 'http';
import https from 'https';

const teardown = async () => {
    http.Server.close();
    https.Server.close();
}

module.exports = teardown;
