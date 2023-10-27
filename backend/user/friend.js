const getFriendlist = async (req, res) => {
    try{
        console.log(req.body);
        
        // query db for friend list? 
        // OR should friend information come already with getUser
        // friend is a premium feature so i think having a friend object is good 
        // in order to keep functionality separated

        res.status(200).send({"message": "GET success: getFriendlist"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const addFriend = async (req, res) => {
    try{
        console.log(req.body);
        
        // add a new userid into list of users (friendlist)
        // mark a status as request sent
        // when other user accpets update this status automatically

        res.status(200).send({"message": "POST success: addFriend"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const respondFriendRequest = async (req, res) => {
    try{
        console.log(req.query);
        const response = req.query['response'];
        switch(response){
            case "accept":
                acceptRequest();
                res.status(200).send({"message": "POST success: respondFriendRequest(accept)"});
                break;
            case "reject":
                rejectRequest();
                res.status(200).send({"message": "POST success: respondFriendRequest(reject)"});
                break;
            default:
                throw new Error("error: missing or invalid parameter for 'response'");
        }
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const removeFriend = async (req, res) => {
    try{
        console.log(req.body);
        // check db for user

        // remove

        res.status(200).send({"message": "DELETE success: removeFriend"});
        return;
    }catch(err){
        console.error(err);
        res.status(400).send(err);
        return;
    }
}

const acceptRequest = async () => {
    return;
}

const rejectRequest = async () => {
    return;
}

export const friend = {
	get: getFriendlist,
    post: addFriend,
    put: respondFriendRequest,
    delete: removeFriend
}
