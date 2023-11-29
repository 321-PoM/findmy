import { PrismaClient } from '@prisma/client';
import { request } from 'supertest';
import app from '../server.js';

// interface GET host/reviews/:searchby/:id
describe("Search reviews by user or by poi", () => {

    // input: a valid user id
    // expected status code: 200
    // expected behaviour: search reviews made by specific user
    // expected output: list of reviews all made by the user specified by input userid
    test("valid search by user", async () => {
        const userId = 90;
        const res = await request(app).get(`/reviews/user/${userId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((review) => expect(review).toHaveProperty("poi"));
    });

    // input: a valid poi id
    // expected status code: 200
    // expected behaviour: search reviews made by specific poi
    // expected output: list of reviews all made about the poi specified by input poiid
    test("valid search by poi", async () => {
        const poiId = 20;
        const res = await request(app).get(`/reviews/user/${poiId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((review) => expect(review).toHaveProperty("user"));
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: cannot query an invalid user/poi id
    // expected output: error message
    test("invalid search", async () => {
        const wrong = -1;
        const res = await request(app).get(`/reviews/user/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/review/:id
describe("Get review", () => {

    // input: review id
    // expected status code: 200
    // expected behaviour: query database based on unique id to find review entry
    // expected output: review object
    test("valid id", async () => {
        const reviewId = 1;
        const res = await request(app).get(`/review/${reviewId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body.isDeleted).toBeFalsy();
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: query db and find nothing
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`review/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface POST host/review
describe("Create review", () => {

    // input: valid poiid, userid, rating, description
    // expected status code: 200
    // expected behaviour: check if review already exists, if not, create according to inputs
    // expected output: newly created object
    test("valid params", async () => {
        const body = {
            poiId: 1,
            userId: 1,
            rating: 100, 
            description: "poop"
        }
        const res = await request(app).post("/review").send(body);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body.isDeleted).toBeFalsy();
    });

    // input: invalid inputs, either all or some
    // expected status code: 500
    // expected behaviour: try to create db entry, cannot since params are mismatched
    // expected output: error message
    test("invalid params", async () => {
        const body = {
            poiId: -1,
            foo: "bar",
            reliabilityScore: 20000
        }
        const res = await request(app).post("/review").send(body);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/reivew/:id
describe("update review", () => {

    // input: valid id with valid update
    // expected status code: 200
    // expected behaviour: update db entry with new data
    // expected output: 
    test("valid update", async () => {
        const reviewId = 0;
        const body = { description: "new desc" }
        const res = await request(app).put(`/review/${reviewId}`).send(body);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid update body with valid id
    // expected status code: 500
    // expected behaviour: should not try to make invalid updates to an existing db entry
    // expected output: error message
    test("invalid update", async () => {
        const reviewId = 0;
        const body = { foo: "bar" }
        const res = await request(app).put(`/review/${reviewId}`).send(body);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/review/:id/rating
describe("update rating", () => {

    // input: valid id with new rating
    // expected status code: 200
    // expected behaviour: update rating for this review, then update the weighted rating for the poi
    // expected output: review with new rating
    test("valid id", async () => {
        const reviewId = 1;
        const newRating = 90;
        const res = await request(app).put(`/review/${reviewId}/${newRating}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: should not update the db as there is no matching entry with id
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).put(`/review/${wrong}/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface DELETE host/review/:id
describe("delete rating", () => {

    // input: valid id 
    // expected status code: 200
    // expected behaviour: delete corresponding entry from db
    // expected output: success
    test("valid id", async () => {
        const id = 1;
        const res = await request(app).delete(`/review/${id}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: no corresponding entry to delete, do nothing
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).delete(`/review/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});