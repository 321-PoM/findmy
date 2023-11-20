import { PrismaClient } from '@prisma/client'
import dotenv from 'dotenv';

dotenv.config();

const testprisma = new PrismaClient({
    datasources: { 
        db: { url: process.env.MOCK_DATABASE_URL }
    }
})
export default testprisma
