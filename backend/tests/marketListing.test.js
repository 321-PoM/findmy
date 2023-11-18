import app from '../server.js'

// interface POST host/marketlisting
describe("create market listing", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/marketListing/:listingId
describe("get one market listing", () => {

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

// interface GET host/marketListings
describe("get all market listings", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("get all test", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/marketListings/:userId
describe("get all listings of a user", () => {

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

// interface GET host/marketListing/poi/:poiId
describe("get all listings of a poi", () => {

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

// interface PUT host/marketListing/:listingId
describe("update listing", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id, valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id, valid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id, invalid body", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/marketListing/:listingId
describe("delete listing", () => {

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