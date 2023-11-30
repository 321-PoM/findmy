import { PrismaClient } from '@prisma/client';
import { request } from 'supertest';
import { app } from '../server.js';

let newPoiId;

// interface GET host/pois
describe("list pois", () => {

    // input: none
    // expected status code: 200
    // expected behaviour: queries for all pois in db
    // expected output: a list of pois
    test("list pois", async () => {
        const res = await request(app).get("/pois");
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((poi) => expect(poi["status"]).toBe("verified"));
    });
});

// interface GET host/poi/:id
describe("get poi", () => {

    // input: valid poiid
    // expected status code: 200
    // expected behaviour: queries for poi mathcing id
    // expected output: poi object
    test("valid id", async () => {
        const poiid = 25;
        const res = await request(app).get(`/poi/${poiid}`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        expect(res.body["id"]).toBe(poiid);
    });

    // input: invalid poiid -> string
    // expected status code: 500
    // expected behaviour: such poiid does not exist, cant find poi
    // expected output: error message
    test("invalid id", async () => {
        const poiid = "deadbeef";
        const res = await request(app).get(`/poi/${poiid}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/filteredPois/:longitude/:latitude/:poiType/:distance
describe("get filtered pois", () => {

    // input: valid filter values
    // expected status code: 200
    // expected behaviour: search for pois, then remove ones not fitting this filter
    // expected output: list of pois matching criteria
    test("valid info", async () => {
        const res = await request(app).get(`/filteredPois/50/-123/myPoi/20`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
    });

    // input: invalid params
    // expected status code: 500
    // expected behaviour: cannot search with incorrect filter fields
    // expected output: error message
    test("invalid input", async () => {
        const res = await request(app).get(`/filteredPois/dead/beef/foo/bar`);
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface POST host/poi
describe("create poi", () => {

    // input: body is valid for poi creation
    // expected status code: 200
    // expected behaviour: creates new poi in the db
    // expected output: return created poi
    test("valid body", async () => {
        const poidata = {
            latitude: 20,
            longitude: 20,
            category: 'myPoi',
            status: 'unlisted',
            description: 'unlisted',
            ownerId: 90,
            rating: 80,
            reports: 0,
        }
        const res = await request(app).post(`/poi`).send(poidata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("id");
        expect(res.body["ownerId"]).toBe(poidata.ownerId);
        newPoiId = res.body["id"];
    });

    // input: body is invalid for poi creation
    // expected status code: 500
    // expected behaviour: do not create poi
    // expected output: return error message
    test("invalid body", async () => {
        const poidata = {
            field: "wrong",
            foo: "bar",
            ownerId: 90,
        }
        const res = await request(app).post(`/poi`).send(poidata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/poi/:id
describe("update poi", () => {

    // input: id is valid for some poi, body is a valid update
    // expected status code: 200
    // expected behaviour: db is updated with new poi info
    // expected output: return poi with new info
    test("valid update", async () => {
        const poi = await request(app).get(`/poi/${newPoiId}`);
        let newdata = poi;
        newdata['description'] = "new desc";
        const res = await request(app).put(`/poi/${newPoiId}`).send(newdata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("description");
        expect(res.body["description"]).toBe("new desc");
    });

    // input: id is valid for some poi, body is incorrectly formatted
    // expected status code: 500
    // expected behaviour: db cannot be updated with incorrect fields
    // expected output: error message
    test("invalid update", async () => {
        const wrong = { foo: "bar" };
        const res = await request(app).put(`/poi/${newPoiId}`).send(wrong);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });

    // input: invalid id, poi does not exist
    // expected status code: 500
    // expected behaviour: db cannot be updated with poi that does not exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/poi/${wrong}`).send(wrong);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/poi/:id/report
describe("report poi", () => {

    // input: id is valid for some poi, body is a valid update
    // expected status code: 200
    // expected behaviour: db is updated with new poi info
    // expected output: return poi with new info
    test("valid id", async () => {
        const res = await request(app).put(`/poi/${newPoiId}/report`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id, poi does not exist
    // expected status code: 500
    // expected behaviour: db cannot be updated with poi that does not exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/poi/${wrong}/report`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/poi/:id/buy/:buyerId
describe("buy poi", () => {

    // input: valid buyer id and valid poi id that has a transaction
    // expected status code: 200
    // expected behaviour: backend handles transaction atomically and transfers funds and ownership
    // expected output: poi object that was transacted
    test("valid poi, valid buyer", async () => {
        const res = await request(app).put(`/poi/${newPoiId}/buy/90`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: cannot trasact this nonexistent poi, do nothing
    // expected output: error message
    test("invalid poi", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/poi/${wrong}/buy/90`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: cannot trasact this nonexistent poi, do nothing
    // expected output: error message
    test("invalid buyer", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/poi/15/buy/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface DELETE host/poi/:id
describe("delete poi", () => {

    // input: valid id pointing to a poi that is active
    // expected status code: 200
    // expected behaviour: poi is soft deleted
    // expected output: return success
    test("valid id", async () => {
        const res = await request(app).delete(`/poi/${newPoiId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id, poi does not exist
    // expected status code: 500
    // expected behaviour: poi doesnt exist cant be deleted
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).delete(`/poi/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});
