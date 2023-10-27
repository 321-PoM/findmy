import { updatepoi } from './updatepoi.js'

const getPOI = async (req, res) => {
    try{
        console.log(req.body);
        
        

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
        const poiID = req.query['id'];
        const field = req.query['type'];
        let success;

        //handling different updates -> check updatepoi.js
        if(!updatepoi.hasOwnProperty(field)){
            throw new Error("error: missing or invalid parameter for 'type'");
        } 
        success = await updatepoi[field](poiID);
        if(!success) throw new Error("Failed to update POI");

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
