import request from 'supertest';
import app from '../server.js';

let newUserId;

// interface GET host/users
describe("List users", () => {

    // input: nothing
    // expected status code: 200
    // expected behaviour: function query DB, return list of users, return empty array if no users
    // expected output: [user1, user2, user3, ...], each user has a field isActive that is true
    test("list users", async () => {
        const res = await request(app).get("/users");
        expect(res).not.toBeNull();
        expect(res.statusCode).toStrictEqual(200);
        res.body.forEach((user) => expect(user.isActive).toBeTruthy());
    });
});

// interface GET host/user/:id
describe("Get user", () => {

    // input: param :userId should be a valid user Id
    // expected status code: 200
    // expected behaviour: queries database for user with matching ID
    // expected output: returns user with matching ID
    test("validId", async () => {
        const userId = 90;
        const res = await request(app).get(`/user/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("id");
        expect(res.body["id"]).toStrictEqual(90);
    });

    // input: param :userId should be a valid user Id
    // expected status code: 200
    // expected behaviour: queries database for user with matching ID
    // expected output: returns empty 
    test("empty resp", async () => {
        const userId = -10;
        const res = await request(app).get(`/user/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });

    // input: param :userId should be something that's not an int
    // expected status code: 500
    // expected behaviour: catches that userId is not an int and throw error
    // expected output: returns error message
    test("bad request", async () => {
        const userId = "deadbeef";
        const res = await request(app).get(`/user/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/userPoiWithMarketListing/:userId
describe("Get user's poi with market listing", () => {

    // input: param :userId should be a valid user Id
    // expected status code: 200
    // expected behaviour: queries pois and listings for one's made by a user
    // expected output: returns a list of pois and a list of marketlistings made by user id
    test("validId", async () => {
        const userId = 88;
        const res = await request(app).get(`/userPoiWithMarketListing/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("pois");
        expect(res.body).toHaveProperty("marketListings");
        expect(res.body["pois"][0]["ownerId"]).toStrictEqual(userId);
        expect(res.body["marketListings"][0]["sellerId"]).toStrictEqual(userId);
    });

    // input: param :userId should be a valid user Id
    // expected status code: 200
    // expected behaviour: queries pois and listings for one's made by a user
    // expected output: returns empty 
    test("empty resp", async () => {
        const userId = 0;
        const res = await request(app).get(`/userPoiWithMarketListing/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("pois");
        expect(res.body).toHaveProperty("marketListings");
    });

    // input: param :userId should be a invalid userId -> not int
    // expected status code: 500
    // expected behaviour: queries pois and listings for one's made by a user
    // expected output: returns error message
    test("bad request", async () => {
        const userId = "deadbeef";
        const res = await request(app).get(`/userPoiWithMarketListing/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/user/email/:email
describe("Get user by email", () => {

    // input: param :email should be a valid email
    // expected status code: 200
    // expected behaviour: queries users and finds the user with matching email
    // expected output: returns a single user
    test("valid email - user exists", async () => {
        const userEmail = "cpen321ubc@gmail.com";
        const res = await request(app).get(`/user/email/${userEmail}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("email");
        expect(res.body["email"]).toStrictEqual(userEmail);
    });

    // input: param :email should be a valid email
    // expected status code: 200
    // expected behaviour: queries users and finds the user with matching email
    // expected output: returns nothing since user doesn't exist
    test("valid email - user doesn't exist", async () => {
        const userEmail = "notreal@gmail.com";
        const res = await request(app).get(`/user/email/${userEmail}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: param :email should be a invalid value -> int
    // expected status code: 500
    // expected behaviour: queries users and finds the user with matching email
    // expected output: returns error message
    test("badcall", async () => {
        const userEmail = "deadbeef";
        const res = await request(app).get(`/user/email/${userEmail}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface POST host/user
describe("create user", () => {

    // input: body is valid for user creation
    // expected status code: 200
    // expected behaviour: creates new user in the db
    // expected output: return created user
    test("valid body", async () => {
        const userdata = {
            name: 'test1',
            email: 'test1@findmy.com',
            avatar: 'none',
            biography: 'none',
            reliabilityScore: 100
        }
        const res = await request(app).post(`/user`).send(userdata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("id");
        expect(res.body["name"]).toBe(userdata.name);
        newUserId = res.body["id"];
    });

    // input: body is invalid for user creation
    // expected status code: 500
    // expected behaviour: do not create user
    // expected output: return error message
    test("invalid body", async () => {
        const userdata = {
            field: "wrong",
            name: "wrong",
            foo: "bar",
        }
        const res = await request(app).post(`/user`).send(userdata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/user/:id
describe("update user", () => {

    // input: id is valid for some user, body is a valid update
    // expected status code: 200
    // expected behaviour: db is updated with new user info
    // expected output: return user with new info
    test("valid update", async () => {
        const user = await request(app).get(`/user/${newUserId}`);
        let newdata = user;
        newdata['name'] = "newname";
        const res = await request(app).put(`/user/${newUserId}`).send(newdata);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
        expect(res.body).toHaveProperty("name");
        expect(res.body["name"]).toBe("newname");
    });

    // input: id is valid for some user, body is incorrectly formatted
    // expected status code: 500
    // expected behaviour: db cannot be updated with incorrect fields
    // expected output: error message
    test("invalid update", async () => {
        const wrong = { foo: "bar" };
        const res = await request(app).put(`/user/${newUserId}`).send(wrong);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });

    // input: invalid id, user does not exist
    // expected status code: 500
    // expected behaviour: db cannot be updated with user that does not exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/user/${deadbeef}`).send(deadbeef);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface PUT host/user/:id/updateUserBux
describe("update user BUX", () => {

    // input: valid id, and valid input params
    // expected status code: 200
    // expected behaviour: db finds the referenced user and upadtes their mapBux
    // expected output: return new mapBux value
    test("valid update", async () => {
        const body = { polarity: true, amount: 1 }
        const res = await request(app).put(`/user/${newUserId}/updateUserBux`).send(body);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: valid id, invalid input params
    // expected status code: 500
    // expected behaviour: db finds referenced user but does not update
    // expected output: error message
    test("invalid update", async () => {
        const body = { foo: "bar" }
        const res = await request(app).put(`/user/${newUserId}/updateUserBux`).send(body);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });

    // input: invalid id, user does not exist
    // expected status code: 500
    // expected behaviour: db cannot be updated with user that does not exist
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).put(`/user/${wrong}/updateUserBux`).send(deadbeef);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface DELETE host/user/:id
describe("delete user", () => {

    // input: valid id pointing to a user that is active
    // expected status code: 200
    // expected behaviour: user is soft deleted
    // expected output: return success
    test("valid id", async () => {
        const res = await request(app).delete(`/user/${newUserId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid id, user does not exist
    // expected status code: 500
    // expected behaviour: user doesnt exist cant be deleted
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).delete(`/user/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});

// interface GET host/rscore/:userId
describe("get user reliability score", () => {

    // input: valid user id
    // expected status code: 200
    // expected behaviour: calculates user reliability score based on designed algorithm
    // expected output: return int value out of 100
    test("valid id", async () => {
        const userId = 90;
        const res = await request(app).get(`/rscore/${userId}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(200);
    });

    // input: invalid user id
    // expected status code: 500
    // expected behaviour: user does not exist 
    // expected output: error message
    test("invalid id", async () => {
        const wrong = "deadbeef"
        const res = await request(app).get(`/rscore/${wrong}`);
        expect(res).not.toBeNull();
        expect(res.status).toStrictEqual(500);
        expect(res.body).toHaveProperty("message");
    });
});
