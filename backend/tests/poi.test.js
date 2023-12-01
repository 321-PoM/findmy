import request from 'supertest';
import { app } from '../server.js'; // Adjust the import path as needed
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

// Define a test suite for testing POI (Points of Interest) related API endpoints
describe('POI API tests', () => {
    let createdPoiId; // Variable to store the ID of the created POI for further tests
    let testUserId;   // Variable to store the ID of the test user associated with the POI

    // Setup before running tests
    beforeAll(async () => {
        // Creating a test user for associating with the POI
        // input: New user data
        // expected status code: 200
        // expected behavior: Creates a new user in the database for testing
        // expected output: User object with an ID
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

        testUserId = parseInt(response.body.id, 10); // Store the ID of the created test user
    });

    // Cleanup after all tests have run
    afterAll(async () => {
        // Delete the created POI and user to clean up the test environment
        if (createdPoiId) {
            await prisma.poi.delete({ where: { id: createdPoiId } });
        }
        if (testUserId) {
            await prisma.user.delete({ where: { id: testUserId } });
        }
        await prisma.$disconnect(); // Disconnect the Prisma client
    });

    // Test for POST /poi endpoint
    // input: New POI data
    // expected status code: 200
    // expected behavior: Creates a new POI in the database
    // expected output: POI object with an ID
    test('Create POI', async () => {
        const newPoi = {
            latitude: 11.8566,
            longitude: 22.3522,
            category: 'Washroom',
            description: 'Eiffel Tower like washroom',
            ownerId: testUserId,
            rating: 5,
            reports: 0,
        };

        const response = await request(app)
            .post('/poi')
            .send(newPoi);

        expect(response.statusCode).toBe(200); // Check if the status code is 200
        expect(response.body).toHaveProperty('id'); // Verify the POI object has an ID
        createdPoiId = response.body.id; // Store the ID of the created POI for further tests
    });

    // Test for GET /poi/:id endpoint
    // input: valid POI ID
    // expected status code: 200
    // expected behavior: Retrieves a specific POI based on ID
    // expected output: POI object with the same ID
    test('Get POI', async () => {
        const response = await request(app)
            .get(`/poi/${createdPoiId}`);

        expect(response.statusCode).toBe(200); // Check if the status code is 200
        expect(response.body).toHaveProperty('id', createdPoiId); // Verify the retrieved POI has the correct ID
    });

});
