import { PrismaClient } from '@prisma/client';

const setup = async (globalConfig, projectConfig) => {
    const prisma = new PrismaClient();

    // make 6 users
    prisma.User.create()
}

export default setup;