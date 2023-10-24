const express          = require("express");
const { OAuth2Client } = require('google-auth-library');

app.get(   "/user", async(req, res) => {getUser(req, res)});
app.post(  "/user", async(req, res) => {makeUser(req, res)});
app.put(   "/user", async(req, res) => {updateUser(req, res)});
app.delete("/user", async(req, res) => {removeUser(req, res)});
export const user = {
    get: getUser,
    post: makeUser,
    put: updateUser,
    delete: removeUser
}

const getUser = async (req, res) => {
    
}

const makeUser = async (req, res) => {

}

const updateUser = async (req, res) => {

}

const removeUser = async (req, res) => {

}
