import express from 'express';
import poiRoutes from '../routes/poiRoutes.js';
const app = express();
app.use(poiRoutes);

// interface GET host/pois
describe("list pois", () => {

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

// interface GET host/poi/:id
describe("get poi", () => {

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

// interface GET host/filteredPois/:longitude/:latitude/:poiType/:distance
describe("get filtered pois", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid info", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("bad latlng", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("filter too specific", async () => {
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

// interface POST host/poi
describe("create poi", () => {

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

// interface PUT host/poi/:id
describe("update poi", () => {

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

// interface PUT host/poi/:id/report
describe("report poi", () => {

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
    test("valid id, already reported", async () => {
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

// interface PUT host/poi/:id/buy/:buyerId
describe("buy poi", () => {

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid poi, valid buyer", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("not a listing", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("valid poi, invalid buyer", async () => {
        const res = await app.post();
        expect(res.status).toStrictEqual();
        expect();
    });

    // input
    // expected status code
    // expected behaviour
    // expected output
    test("invalid poi, valid buyer", async () => {
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

// interface DELETE host/poi/:id
describe("delete poi", () => {

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