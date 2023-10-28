# Module: User
### User Object
```
{
    userid: string,
    reliabilityScore: int,
    premiumStatus: bool,
    metadata: {
        biography: string,
        avatar: string,     // link to image?
        ...
    }
}
```
- Use userid to query for a user's wallet and friends list
- Storage url of the user's profile picture can be in metadata or in its own field? (pls decide)
- Use userid in quering for POI, which can be:
    - made by (userid)
    - owned by (userid)

### User Methods
`GET /user`
```
param: {
    authToken: 'string'
}
returns: {
    body: {userObject}      // as listed above
}
```
- assumes user already exists
- authenticate user with their corresponding google token
- returns the user object that represents user

`POST /user` 
- **N/A**
- In the front end, do we just want to force users to sign in with their google account
    - Then we can just call `createUser` in the backend if we notice a google account is new

`PUT /user`
```
param: {
    userid: 'string',
    field: 'string',        // must correspond to one of the fields in userobject
    input: 'string'         // conver this to whatever type that is demanded by the field
                            // for metadata, enter a json string(?) 
}
returns: {
    success: true           // or err
}
```
- Update user object fields with new info

`DELETE /user`
```
param: {
    userid: 'string'
}
return: {
    success: true           // or err
}
```
- Delete a user lol
- Should there be some logic to handle the authToken? don't know how that'll work

### Reliability Score Methods
`GET /user/rscore`
```
param: {
    userid: 'string'
}
```
- calculated based on POIs user created and POIs user has reviewed?

### Friend List Methods
`GET /user/friend`
```
param: {
    userid: 'string' 
}
returns: {
    friend1: 'userid',
    friend2: 'userid',
    ...
}
```
- allow `GET /user/friend?limit=MAX`, where `MAX` is the max number of userid (friends) to return
- if not specified, `MAX=20`, this can be set arbitrarily

`POST /user/friend`
- **NOTE**: not sure whether adding a friend should be POST or PUT
    - since we are *updating* the friend list
    - but we are *creating* a friend request
```
param: {
    userid: 'string'        // friend to send request to
}
returns: {
    success: true           // or err? 
}
```
- Need to decided how to handle if user is already a friend

`PUT /user/friend`
```
param: {
    userid: 'string'        // userid of person who sent request
    accept: bool            // true of false
}
returns: {
    success: true           // true or err
}
```

`DELETE /user/friend`
```
param: {
    userid: 'string'
}
returns: {
    success: true           // true or err
}
```
- removes friend from friend list

### POI Object
```
{
    poiid: string,
    latlng: {long, long},
    rating: int,            // 1 to 100
    category: string        // toilet, microwave, etc
    status: string          // unlisted, verfied, private
    ownerid: string        // userid or owned by devs
    metadata: {
        description: string
        images: string      // link to storage of images?
        ...
    }
}
```
- Not sure how the data lake works but I'm assuming kinda like S3 bucket style (where you can use a link to grab the stored item?)

### POI Methods
`GET /poi`
```
param: {
    filter: {
        ownerid:
        latlng:
        category:
        status:
        rating: 
    }
}
returns: {

}
```
- I'm not sure how the filtering will be done right now

`POST /poi`
```
param: {
    latlng: {long, long},   // required
    category: string,       // required
    ownerid: string,        // required
    metadata: {             // optional
        description:    
        image:          
    }
}
returns: {
    poiid: string
}
```
- rating, status, poiid, will be generated

`PUT /poi`
```
param: {
    poiid: 'string',
    input: {
        latlng:             // all optional
        rating:
        category:
        status:
        ownerid:
        metadata:
    }
}
returns: {
    success: true           // or err
}
```
- I think we will need a module for rating

`DELETE /poi`
```
param: {
    poiid: string
}
returns: {
    success: true           // or err
}
```
- delete a poi