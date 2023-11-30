import { PrismaClient } from '@prisma/client';
import { request } from 'supertest';
import { app } from '../server.js';

// interface POST host/marketListing
describe("create market listing", () => {

    // input: valid body
    // expected status code: 200
    // expected behaviour: create new market listing with input info
    // expected output: return created object
    test("valid body", async () => {
        const body = {
            price: 200,
            sellerId: 90,
            poiId: 33,
        }
        const res = await request(app).post("/marketListing").send(body);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body).toHaveProperty("sellerId");
        expect(res.body).toHaveProperty("poiId");
    });

    // input: invalid creation input
    // expected status code: 500
    // expected behaviour: cannot create a listing from invalid body
    // expected output: error message
    test("invalid body", async () => {
        const wrong = {
            foo: "bar",
            fizz: "buzz"
        }
        const res = await request(app).post("/marketListing").send(wrong);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/marketListing/:listingId
describe("get one market listing", () => {

    // input: valid id
    // expected status code: 200
    // expected behaviour: finds the market listing corresponding to input id
    // expected output: market listing object
    test("valid id", async () => {
        const listingId = 15;
        const res = await request(app).get(`/marketListing/${listingId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: can't find listing for a nonexistent id
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/marketListing/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/marketListings
describe("get all market listings", () => {

    // input: nothing
    // expected status code: 200
    // expected behaviour: queries the marketListing db for everything
    // expected output: returns a list of listings
    test("get all test", async () => {
        const res = await request(app).get("/marketListings");
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((listing) => expect(listing.isDeleted).isFalsy());
    });
});

// interface GET host/marketListings/:userId
describe("get all listings of a user", () => {

    // input: valid userid
    // expected status code: 200
    // expected behaviour: queries database and filter listings that are not by this userid
    // expected output: return list of user's listings
    test("valid id", async () => {
        const userId = 89;
        const res = await request(app).get(`/marketListing/${userId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((listing) => expect(listing.isDeleted).isFalsy());
    });

    // input: invalid userid
    // expected status code: 500
    // expected behaviour: invalid user id impossible to find listings of
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/marketListing/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/marketListing/poi/:poiId
describe("get all listings of a poi", () => {

    // input: valid poiid
    // expected status code: 200
    // expected behaviour: queries database and filte rlistings that are not of this poi
    // expected output: return list of poi listings
    test("valid id", async () => {
        const poiId = 29;
        const res = await request(app).get(`/marketListing/${poiId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((listing) => expect(listing.isDeleted).isFalsy());
    });

    // input: invalid poiid
    // expected status code: 500
    // expected behaviour: invalid poi id impossible to find listings of
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/marketListing/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/marketListing/:listingId
describe("update listing", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id, valid body", async () => {
        const res = await request(app).put("/marketListing/0");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid update", async () => {
        const res = await request(app).put("/marketListing/0");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/marketListing/:listingId
describe("delete listing", () => {

    // input: valid id for deletion
    // expected status code: 200
    // expected behaviour: delete the corresponding listing object from db
    // expected output: success
    test("valid id", async () => {
        const listingId = 20;
        const res = await request(app).delete(`/marketListing/${listingId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id for deletion
    // expected status code: 500
    // expected behaviour: cannot delete what doesn't exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).delete(`/marketListing/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});
