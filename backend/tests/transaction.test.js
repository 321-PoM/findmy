import { PrismaClient } from '@prisma/client';
import { request } from 'supertest';
import app from '../server.js';

// interface POST host/transaction
describe("create transaction", () => {

    // input: valid buyer + listing
    // expected status code: 200
    // expected behaviour: create a transaction object in db
    // expected output: newly created object
    test("valid buyer + valid listing", async () => {
        const body = {
            buyer: 20,
            listing: 20
        }
        const res = await request(app).post("/transaction").send(body);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body).toHaveProperty("id");
    });

    // input: invalid buyer / listing
    // expected status code: 500
    // expected behaviour: buyer / listing do not exist, transaction is not created in db
    // expected output: error message
    test("invalid input", async () => {
        const body = {
            buyer: -1,
            listing: -1
        }
        const res = await request(app).post("/transaction").send(body);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/transaction/:transactionId
describe("get a transaction", () => {

    // input: valid id
    // expected status code: 200
    // expected behaviour: find the db entry with corresponding id
    // expected output: transaction entry object
    test("valid id", async () => {
        const transactionId = 0;
        const res = await request(app).get(`/transaction/${transactionId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body.isDeleted).toBeFalsy();
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: no such db entry exists with id
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/transaction/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/transactions/buyer/:userId
describe("list transacitons of user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query transactions to find ones with given userid as buyer
    // expected output: all transactions with this buyer (can be empty)
    test("valid id", async () => {
        const userId = 1;
        const res = await request(app).get(`/transactions/buyer/${userId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: can query db but won't find anything as user with such id cannot exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/transactions/buyer/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/transactions/listing/:listingId
describe("list transacitons of listing", () => {

    // input: valid listing id
    // expected status code: 200
    // expected behaviour: query transactions to find ones with given listing
    // expected output: all transactions with this listing (can be empty)
    test("valid id", async () => {
        const listingId = 0;
        const res = await request(app).get(`/transactions/listing/${listingId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid listing id
    // expected status code: 500
    // expected behaviour: cannot find such a transaction as such listing cannot exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).get(`/transactions/listing/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/transaction/:transactionId
describe("update transaction", () => {

    // input: valid id
    // expected status code: 200
    // expected behaviour:
    // expected output
    test("valid id + valid data", async () => {
        const res = await request(app).put("/transaction/0");
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid update", async () => {
        const res = await request(app).put("/transaction/0");
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/transaction/:transactionId
describe("delete transaction", () => {

    // input: valid id
    // expected status code: 200
    // expected behaviour: delete transaction from db
    // expected output: success
    test("valid id", async () => {
        const transactionId = 1;
        const res = await request(app).delete(`/transaction/${transactionId}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: cannot delete a transaction that doesn't exist, do nothing
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).delete(`/transaction/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});