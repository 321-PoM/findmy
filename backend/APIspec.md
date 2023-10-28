## Module: User
Member variables
- User ID
    - Use userid to query stored friends list and user media?
- Google Account
- Reliability Score
- Friends
- Premium/Free user
- Wallet ID

### getUserByID
param: int - user id
static method
returns: User class

### getGoogleAccount
param: User class
returns: user’s associatedGoogleAccount

### getReliabilityScore
param: User class
returns: user’s reliability score

### updateReliabilityScore
param: user
returns: void

### updateInfo 
param: user id, user variable (this method can be overloaded to accept different variable inputs)
return true/false based on if successful

### getFriends
param: user id
list of users (friends)

### respondToFriendRequest
param: user id, bool(accept/deny)
return: true/false based on if success

### requestNewFriend
param: user id
return: void

### removeFriend
param: user id
return: true/false based on if user was removed
```
Module - POI Management
Handles functionalities regarding POIs, such as create, rate, and remove

POI
Member variables
Type: (Study Area, Microwave, etc.)
Score
Verified/Unlisted
Location
Photo
Description/Metadata

createPOI
param: location, bool (private/public,personalized), enum(type)
return: created POI/null

ratePOI
param: int(score)
return: POI

updatePOI
param: POI

changeOwner
param: user id
return: POI

Map display
POI

Module - Marketplace
Handles functionalities regarding the POI Marketplace feature, this requires users to sell/purchase personalized POIs that involve ownership.

Transaction 
ID
Wallet (seller)
Wallet (buyer)

Wallet
ID
MapBux (In-app currency)

Module - Payment
Handles functionalities regarding the external payment method (wrapper for external APIs to authenticate and service purchases)

CreditCard
Member variables
CVV
Card Number
Card Expiry Date

Wallet
Defined under marketplace
