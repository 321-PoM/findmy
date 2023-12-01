import request from 'supertest';
import { app } from '../server.js';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();
// Tests for User related API endpoints
describe('User API tests', () => {
    let createdUserId;

    beforeAll(async () => {
        // If we need to init before all tests, we can do it here.
    });

    afterAll(async () => {
        // Post-test cleanup
        await prisma.user.deleteMany();
        await prisma.$disconnect();
    });

    // Test for POST /user endpoint
    // input: New user data
    // expected status code: 200
    // expected behavior: Creates a new user in the database
    // expected output: User object with an ID
    test('Create user', async () => {
        const newUser = {
            name: 'Github action test user',
            email: 'lovememore@example.com',
            avatar: 'handsome avatar',
            biography: 'I am happy CPEN student',
            reliabilityScore: 100,
            premiumStatus: false,
            mapBux: 50,
            isActive: true,
            isDeleted: false,
        };

        const response = await request(app)
            .post('/user')
            .send(newUser);

        expect(response.statusCode).toBe(200);
        expect(response.body).toHaveProperty('id');
        // Store created user ID for further tests
        createdUserId = response.body.id;
    });

    // Test for GET /user/:id endpoint
    // input: valid user ID
    // expected status code: 200
    // expected behavior: Retrieves a specific user based on ID
    // expected output: User object with the same ID
    test('Get user', async () => {
        const response = await request(app)
            .get(`/user/${createdUserId}`);

        expect(response.statusCode).toBe(200);
        expect(response.body).toHaveProperty('id', createdUserId);
    });

    // TODO: Add more tests here.
});
