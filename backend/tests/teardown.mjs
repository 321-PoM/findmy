import { PrismaClient } from '@prisma/client';
import { httpServer, httpsServer } from "../server.js"

const tableNames = [
    "Friendship",
    "Image",
    "Review",
    "Transaction",
    "User",
    "marketListing",
    "poi"
]

const teardown = async (globalConfig, projectConfig) => {
    const prisma = new PrismaClient();
    const deletion = tableNames.map((table) => prisma.$queryRawUnsafe(`Truncate "${table}" restart identity cascade;`));
    await Promise.all(deletion);

    httpServer.close();
    httpsServer.close();
}

export default teardown;