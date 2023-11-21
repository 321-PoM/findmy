import request from 'supertest';
import app from '../server.js';

// interface GET host/reviews/:searchby/:id
describe("Search reviews by user or by poi", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid search + valid id", async () => {
        const res = await request(app).get("/reviews/true/10");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid search", async () => {
        const res = await request(app).get("/reviews/true/10");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/reivew/:id
describe("Get review", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id", async () => {
        const res = await request(app).get("/review/1");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await request(app).get("/review/1");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface POST host/review
describe("Create review", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid params", async () => {
        const res = await request(app).post("/review");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid params", async () => {
        const res = await request(app).post("/review");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface PUT host/reivew/:id
describe("update review", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + valid params", async () => {
        const res = await request(app).put("/review/0");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid update", async () => {
        const res = await request(app).put("/review/0");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface PUT host/review/:id/rating
describe("update rating", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id", async () => {
        const res = await request(app).put("/review/0/10");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await request(app).put("/review/0/10");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/review/:id
describe("delete rating", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id", async () => {
        const res = await request(app).delete("/review/0");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await request(app).delete("/review/0");
        expect(res.status).toStrictEqual();
        expect();
    });
});