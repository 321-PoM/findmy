import { PrismaClient } from '@prisma/client';

const prismaClient = new PrismaClient({
    datasources: {
        db: {url: ''}
    }
})

const getPOI = async (req, res) => {
    try{
        console.log(req.body);
        
        const findPOIResp = await prismaClient.poi.findMany({
            select: '',     // all fields are optional
            include: '',    
            where: '',      
            orderBy: '',    
            cursor: '',     
            take: '',       
            skip: '',       
            distinct: ''    
        });
        console.log(findPOIResp);
        

        res.status(200).send({"message": "GET success: getPOI"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const addPOI = async (req, res) => {
    try{
        console.log(req.body);
        const createPOIResp = await prismaClient.poi.create({
            data: {                             // required field
                
            },
        });
        console.log(createPOIResp);

        res.status(200).send({"message": "POST success: addPOI"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const updatePOI = async (req, res) => {
    try{
        console.log(req.body);
        let updatedData = {};
        // create new updated data based on input

        //handling different updates -> check updatepoi.js
        const updateUserResp = await prismaClient.user.update({
            data: updatedData,                  // required field
            where: {                            // required field

            },
            select: '',                         // optional field
            include: ''                         // optional field
        });
        console.log(updateUserResp);

        res.status(200).send({"message": "PUT success: updatePOI"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const removePOI = async (req, res) => {
    try{
        console.log(req.body);
        const deletePOIResp = await prismaClient.poi.delete({
            where: {                            // required field

            },
            select: '',                         // optional field
            include: ''                         // optional field
        });
        console.log(deletePOIResp);

        res.status(200).send({"message": "DELETE success: removePOI"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

export const poi = {
	get: getPOI,
    post: addPOI,
    put: updatePOI,
    delete: removePOI
}
