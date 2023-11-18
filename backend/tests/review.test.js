import app from '../server.js';

// interface GET host/reviews/:searchby/:id
describe("Search reviews by user or by poi", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid search + valid id", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid search + valid id", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid search + invalid id", async () => {
        const res = await app.post();
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
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface POST host/reivew
describe("Create review", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid params", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid params", async () => {
        const res = await app.post();
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
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + valid params", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + invalid params", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface PUT host/reivew/:id/rating
describe("update rating", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("big rating", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("tiny rating", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("negative rating", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await app.post();
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
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});