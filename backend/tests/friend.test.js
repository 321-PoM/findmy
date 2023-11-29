import { PrismaClient } from '@prisma/client';
import { request } from 'supertest';
import app from '../server.js';

// interface GET host/friends/:userId
describe("List friends of user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query friendships that include user id, true friends are friendships that are bidirectional
    // expected output: a list of user objects that are all friends with the input id
    test("valid id", async () => {
        const userId = 90;
        const res = await request(app).get(`/friends/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        res.body.forEach((user) => expect(user.isDeleted).toBeFalsy());
    });

    // input: invalid user id
    // expected status code: 500
    // expected behaviour: user doesn't exist, cannot find anything
    // expected output: return error message
    test("invalid id", async () => {
        const wrong = 0;
        const res = await request(app).get(`/friends/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/friends/:userId/received
describe("List friend requests received by user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query friendship db for requests that match userid in the field userIdTo
    // expected output: a list of friendships with status requested sent to the input userid
    test("valid id", async () => {
        const userId = 90;
        const res = await request(app).get(`/friends/${userId}/received`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        res.body.forEach((friendship) => expect(friendship.status).toBe("requested"));
    });

    // input: invalid user id
    // expected status code: 500
    // expected behaviour: query returns nothing since userid is not real
    // expected output: error message
    test("invalid id", async () => {
        const wrong = 0;
        const res = await request(app).get(`/friends/${wrong}/received`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/friends/:userId/sent
describe("List friend requests sent by user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query friendships where userid is the sender
    // expected output: a list of friendships with status as requested
    test("valid id", async () => {
        const userId = 90;
        const res = await request(app).get(`/friends/${userId}/sent`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        res.body.forEach((friendship) => expect(friendship.status).toBe("requested"));
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: query cant find anything as user doesnt exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = 0;
        const res = await request(app).get(`/friends/${wrong}/sent`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/friend/:friendshipId
describe("get a specific friendship", () => {

    // input: valid friendship id
    // expected status code: 200
    // expected behaviour: query database to find corresponding friendship entry
    // expected output: one friendship object
    test("valid id", async () => {
        const friendshipId = 10;
        const res = await request(app).get(`/friend/${friendshipId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("userIdFrom");
        expect(res.body).toHaveProperty("userIdTo");
    });

    // input: invalid friendship id
    // expected status code: 500
    // expected behaviour: cannot find a friendship with such id
    // expected output: error message
    test("invalid id", async () => {
        const wrong = 0;
        const res = await request(app).get(`/friend/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface POST host/friend
describe("create friendship", () => {

    // input: two valid userids
    // expected status code: 200
    // expected behaviour: create new friendship object with user from and user to
    // expected output: return friendshipId
    test("valid from + valid to", async () => {
        const from = 89;
        const to = 90;
        const res = await request(app).post("/friend").send({userIdFrom: from, userIdTo: to});
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: two invalid userids
    // expected status code: 500
    // expected behaviour: do not create friendship object since ids are invalid
    // expected output: error message
    test("invalid combo", async () => {
        const badfrom = 0;
        const badto = -1;
        const res = (await request(app).post("/friend")).send({userIdFrom: badfrom, userIdTo: badto});
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/friend/:friendshipId/:acceptRequest
describe("accept request", () => {

    // input: valid friendship request and accept request
    // expected status code: 200
    // expected behaviour: accept -> corresponding other direction is created
    // expected output: updated object
    test("valid id + accept", async () => {
        const friendshipId = 10;
        const accept = true;
        const res = await request(app).put(`/friend/${friendshipId}/${accept}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: valid friendship request and request
    // expected status code: 200
    // expected behaviour: reject -> object is updated to rejeceted
    // expected output: updated object
    test("valid id + reject", async () => {
        const friendshipId = 10;
        const accept = false;
        const res = await request(app).put(`/friend/${friendshipId}/${accept}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id
    // expected status code: 500
    // expected behaviour: cannot find such friendship object, do nothing
    // expected output: error message
    test("invalid", async () => {
        const wrong = -1;
        const accept = true;
        const res = await request(app).put(`/friend/${wrong}/${accept}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface DELETE host/friend/:id
describe("delete friend", () => {

    // input: valid id to delete
    // expected status code: 200
    // expected behaviour: successful delete from database
    // expected output: success
    test("valid id", async () => {
        const id = 20;
        const res = await request(app).delete(`/friend/${id}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id to delete
    // expected status code: 500
    // expected behaviour: can't delete what doesn't exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = -1;
        const res = await request(app).delete(`/friend/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});