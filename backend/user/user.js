import { OAuth2Client } from 'google-auth-library';

const oauthClient = new OAuth2Client();

const getUser = async (req, res) => {
    try{
        console.log(req.body);
        // const token = req.body.userToken;
        // const tokenResp = await oauthClient.verifyIdToken({
        //     idToken: token,
        //     audience: process.env.CLIENT_ID
        // });
        
        // check database for user
        // return to client userinfo for user page
        res.status(200).send({"message": "GET success: getUser"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const makeUser = async (req, res) => {
    try{
        console.log(req.body);
        // const token = req.body.userToken;
        // const tokenResp = await oauthClient.verifyIdToken({
        //     idToken: token,
        //     audience: process.env.CLIENT_ID
        // });

        // put new user info into db

        res.status(200).send({"message": "POST success: makeUser"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const updateUser = async (req, res) => {
    try{
        console.log(req.body);
        // const token = req.body.userToken;
        // const tokenResp = await oauthClient.verifyIdToken({
        //     idToken: token,
        //     audience: process.env.CLIENT_ID
        // });

        // update db

        res.status(200).send({"message": "PUT success: updateUser"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const removeUser = async (req, res) => {
    try{
        console.log(req.body);
        // check db for user

        // remove

        res.status(200).send({"message": "DELETE success: removeUser"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

export const user = {
	get: getUser,
    post: makeUser,
    put: updateUser,
    delete: removeUser
}
