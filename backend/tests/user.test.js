import { app } from '../server.js'

// interface GET host/users
describe("List users", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/user/:id
describe("Get user", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("validId", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalidId", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/userPoiWithMarketListing/:userId
describe("Get user's poi with market listing", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("validId", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalidId", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/user/email/:email
describe("Get user by email", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid email - user exists", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid email - user doesn't exist", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("string is not an email", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface POST host/user
describe("create user", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid body - all fields exist", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid body - some fields are empty", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid body - duplicate info", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid body - erroneous fields", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface PUT host/user/:id
describe("update user", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // TODO: probably is more cases for this one
});

// interface PUT host/user/:id/updateUserBux
describe("update user BUX", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // TODO: probably is more cases for this one
});

// interface DELETE host/user/:id
describe("delete user", () => {

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

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/user/:id
describe("delete user", () => {

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

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/rscore/:userId
describe("get user reliability score", () => {

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

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("empty request", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});