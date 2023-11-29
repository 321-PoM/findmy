import { httpServer, httpsServer } from "../server.js"

const teardown = async () => {
    httpServer.close();
    httpsServer.close();
}

export default teardown;