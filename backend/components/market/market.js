const getListings = async (req, res) => {
    try{
        console.log(req.body);
        
        

        res.status(200).send({"message": "GET success: getListings"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const createListing = async (req, res) => {
    try{
        console.log(req.body);
        
        

        res.status(200).send({"message": "POST success: createListing"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const updateListing = async (req, res) => {
    try{
        console.log(req.body);
        

        res.status(200).send({"message": "PUT success: updateListing"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const removeListing = async (req, res) => {
    try{
        console.log(req.body);
        

        res.status(200).send({"message": "DELETE success: removeListing"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

export const market = {
	get: getListings,
    post: createListing,
    put: updateListing,
    delete: removeListing
}
