// interface POST host/transaction
describe("create transaction", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid buyer + valid listing", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid buyer + valid listing", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid buyer + invalid listing", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/transaction/:transactionId
describe("get a transaction", () => {

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

// interface GET host/transactions/buyer/:userId
describe("list transacitons of user", () => {

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
    test("valid id, no transactions", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface GET host/transactions/listing/:listingId
describe("list transacitons of listing", () => {

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
    test("valid id, no transactions", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface PUT host/transaction/:transactionId
describe("update transaction", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + valid data", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid id + valid data", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid id + invalid data", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });
});

// interface DELETE host/transaction/:transactionId
describe("delete transaction", () => {

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