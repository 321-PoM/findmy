import { OAuth2Client } from 'google-auth-library';
import { PrismaClient } from '@prisma/client';

const oauthClient = new OAuth2Client();
const prismaClient = new PrismaClient({
    datasources: {
        db: {url: 'url to user db'} 
    }
});

const getUser = async (req, res) => {
    try{
        console.log(req.body);
        // const token = req.body.userToken;
        // const tokenResp = await oauthClient.verifyIdToken({
        //     idToken: token,
        //     audience: process.env.CLIENT_ID
        // });
        
        // check database for user
        const findUserResp = await prismaClient.user.findMany({
            select: '',     // all fields are optional
            include: '',    
            where: '',      
            orderBy: '',    
            cursor: '',     
            take: '',       
            skip: '',       
            distinct: ''    
        })
        console.log(findUserResp);
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

        const createUserResp = await prismaClient.user.create({
            data: {                             // required field
                userid: '',     
                name: 'testuser',
                email: 'muhan@prisma.io',
                rscore: '',
                friends: '',
                status: '',
            },
        });
        console.log(createUserResp);

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
        const updateUserResp = await prismaClient.user.update({
            data: {                             // required field
                userid: '',                     
                name: '',
                email: '',
                rscore: '',
                friends: '',
                status: '',
            },
            where: {                            // required field

            },
            select: '',                         // optional field
            include: ''                         // optional field
        });
        console.log(updateUserResp);

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
        const deleteUserResp = await prismaClient.user.delete({
            where: {                            // required field

            },
            select: '',                         // optional field
            include: ''                         // optional field
        });
        console.log(deleteUserResp);

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
