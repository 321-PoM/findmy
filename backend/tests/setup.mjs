import { PrismaClient } from '@prisma/client';

const setup = async (globalConfig, projectConfig) => {
    process.env.TEST_ENV = 'true';
    process.env.HTTP_PORT = '8888'; 
    process.env.HTTPS_PORT = '8999'; 

    const prisma = new PrismaClient();

    // make 6 users
    prisma.User.create()
}

export default setup;
