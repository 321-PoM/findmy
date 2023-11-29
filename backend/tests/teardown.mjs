import http  from 'http';
import https from 'https';

export const teardown = async () => {
    http.Server.close();
    https.Server.close();
}
