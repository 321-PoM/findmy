const express          = require("express");
const { OAuth2Client } = require('google-auth-library');
const os               = require("os");

require("dotenv").config();
const app = express();
app.use(express.json());

// User
app.get("/username", getUser());
app.post("/username", makeUser());
app.put("/username", updateUser());

// POI

// Market

// Payment

const run = async () => {
    try{
        let server = app.listen(8081, (req, res) => {
            let host = server.address().address;
            let port = server.address().port;

            console.log(`Server running at: https://${host}:${port}`);
        })
    }
    catch(err){
        console.error(err);
    }
}

run();

