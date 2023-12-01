import { PrismaClient } from '@prisma/client';
import request from 'supertest';
import { app } from '../server.js';

const prisma = new PrismaClient();

let user1;
let user2;

beforeEach(async () => {
    user1 = await prisma.User.create({
        data: {
            name: "test1",
            email: "test1",
            avatar: "none",
            biography: "none",
            reliabilityScore: 0,
        }
    });
    user2 = await prisma.User.create({
        data: {
            name: "test2",
            email: "test2",
            avatar: "none",
            biography: "none",
            reliabilityScore: 0,
        }
    });
});

afterEach(async () => {
    await prisma.User.delete({
        where: { id: user1.id }
    });
    await prisma.User.delete({
        where: { id: user2.id }
    });
});

// interface GET host/friends/:userId
describe("List friends of user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query friendships that include user id, true friends are friendships that are bidirectional
    // expected output: a list of user objects that are all friends with the input id
    test("real userid", async () => {
        const res = await request(app).get(`/friends/${user1.id}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        res.body.forEach((user) => expect(user.isDeleted).toBeFalsy());
    });

    // input: valid user id, but not of a real user
    // expected status code: 500
    // expected behaviour: user doesn't exist, cannot find anything
    // expected output: return error message
    test("user does not exist", async () => {
        const wrong = -1;
        const res = await request(app).get(`/friends/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveLength(0);
    });

    // input: invalid user id
    // expected status code: 500
    // expected behaviour: invalid input type, cannot make a query
    // expected output: return error message
    test("not a valid userId", async () => {
        const wrong = "deadbeef";
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
        await prisma.friendship.create({
            data: {
                userIdFrom: user1.id,
                userIdTo: user2.id,
                status: "accepted"
            }
        });

        const res = await request(app).get(`/friends/${user2.id}/received`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body[0]).toHaveProperty("name");

        await prisma.friendship.deleteMany({
            where: {
                userIdFrom: user1.id,
                userIdTo: user2.id
            }
        });
    });

    // input: valid user id, but not of a real user
    // expected status code: 200
    // expected behaviour: query friendship db, cannot find anything 
    // expected output: error message
    test("valid id, not real user", async () => {
        const wrong = -1;
        const res = await request(app).get(`/friends/${wrong}/received`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveLength(0);
    });
});

// interface GET host/friends/:userId/sent
describe("List friend requests sent by user", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: query friendships where userid is the sender
    // expected output: a list of friendships with status as requested
    test("valid id", async () => {
        await prisma.friendship.create({
            data: {
                userIdFrom: user1.id,
                userIdTo: user2.id,
                status: "accepted"
            }
        });

        const res = await request(app).get(`/friends/${user1.id}/sent`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);

        await prisma.friendship.deleteMany({
            where: {
                userIdFrom: user1.id,
                userIdTo: user2.id
            }
        })
    });

    // input: valid user id, but not of a real user
    // expected status code: 200
    // expected behaviour: query friendship db, cannot find anything 
    // expected output: error message
    test("valid id, not real user", async () => {
        const wrong = -1;
        const res = await request(app).get(`/friends/${wrong}/sent`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveLength(0);
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
        const wrong = -1;
        const res = await request(app).get(`/friend/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// // interface POST host/friend
// describe("create friendship", () => {

//     // input: two valid userids
//     // expected status code: 200
//     // expected behaviour: create new friendship object with user from and user to
//     // expected output: return friendshipId
//     test("valid from + valid to", async () => {
//         const from = 89;
//         const to = 90;
//         const res = await request(app).post("/friend").send({userIdFrom: from, userIdTo: to});
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(200);
//     });

//     // input: two valid userids
//     // expected status code: 500
//     // expected behaviour: find existing friendship for the two userIds, do not create duplicate
//     // expected output: error message
//     test("make duplicate friendship", async () => {
//         const from = 1;
//         const to = 2;
//         await request(app).post("/friend").send({userIdFrom: from, userIdTo: to});
//         const res = await request(app).post("/friend").send({userIdFrom: from, userIdTo: to});
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(500);
//         expect(res.body).toHaveProperty("message");
//     });

//     // input: two invalid userids
//     // expected status code: 500
//     // expected behaviour: do not create friendship object since ids are invalid
//     // expected output: error message
//     test("invalid userIds", async () => {
//         const badfrom = 0;
//         const badto = -1;
//         const res = (await request(app).post("/friend")).send({userIdFrom: badfrom, userIdTo: badto});
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(500);
//         expect(res.body).toHaveProperty("message");
//     });
// });

// // interface PUT host/friend/:friendshipId/:acceptRequest
// describe("accept request", () => {
//     let user1;
//     let user2;

//     beforeAll(async () => {
//         user1 = await prisma.User.create({
//             data: {
//                 name: "test1",
//                 email: "test1",
//                 avatar: "none",
//                 biography: "none",
//                 reliabilityScore: 0,
//             }
//         });
//         user2 = await prisma.User.create({
//             data: {
//                 name: "test2",
//                 email: "test2",
//                 avatar: "none",
//                 biography: "none",
//                 reliabilityScore: 0,
//             }
//         });
//     })

//     // input: valid userTo and userFrom, accept request
//     // expected status code: 200
//     // expected behaviour: accept -> corresponding other direction is created
//     // expected output: updated object
//     test("valid id + accept", async () => {
//         // TODO: create users in db for this test
//         const accept = true;
//         await prisma.friendship.create({
//             data: {
//                 userIdFrom: user1.id,
//                 userIdTo: user2.id,
//                 status: "accepted"
//             }
//         });
//         const res = await request(app).put(`/friend/${user1.id}/${user2.id}/${accept}`);
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(200);
//     });

//     // input: valid id, reject
//     // expected status code: 200
//     // expected behaviour: reject -> object is updated to rejeceted
//     // expected output: updated object
//     test("valid id + reject", async () => {
//         const friendshipId = 10;
//         const accept = false;
//         const res = await request(app).put(`/friend/${friendshipId}/${accept}`);
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(200);
//     });

//     // input: invalid id
//     // expected status code: 500
//     // expected behaviour: cannot find such friendship object, do nothing
//     // expected output: error message
//     test("invalid", async () => {
//         const wrong = -1;
//         const accept = true;
//         const res = await request(app).put(`/friend/${wrong}/${accept}`);
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(500);
//         expect(res.body).toHaveProperty("message");
//     });
// });

// // interface DELETE host/friend/:id
// describe("delete friend", () => {

//     // input: valid id to delete
//     // expected status code: 200
//     // expected behaviour: successful delete from database
//     // expected output: success
//     test("valid id", async () => {
//         const id = 20;
//         const res = await request(app).delete(`/friend/${id}`);
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(200);
//     });

//     // input: invalid id to delete
//     // expected status code: 500
//     // expected behaviour: can't delete what doesn't exist
//     // expected output: error message
//     test("invalid id", async () => {
//         const wrong = -1;
//         const res = await request(app).delete(`/friend/${wrong}`);
//         expect(res).not.toBeNull();
//         expect(res.status).toStrictEqual(500);
//         expect(res.body).toHaveProperty("message");
//     });
// });
