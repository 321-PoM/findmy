import { PrismaClient } from '@prisma/client';

const setup = async (globalConfig, projectConfig) => {
    const prisma = new PrismaClient();

    // make 6 users and 6 pois
    for(let i = 0; i < 6; i++) {
        await prisma.User.create({
            name: `${i}`,
            email: "test@email.com",
            avatar: "none",
            biography: `test user ${i}`,
            reliabilityScore: 100,
        });

        await prisma.poi.create({
            latitude: parseFloat(i * 10),
            longitude: parseFloat(120 - (i * 10)),
            category: (i % 2 == 0) ? "myPoi" : "Washroom",
            status: "verified",
            description: `test poi ${i}`,
            ownerId: i,
            rating: 100 - (i*5),
            imageUrl: "none"
        });
    }
}

export default setup;