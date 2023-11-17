import requests

BASE_URL = "https://findastar.westus2.cloudapp.azure.com"

sample_userId = 1
sample_marketId = 1
sample_poiId = 1
sample_reviewId = 1
sample_longitude = 0.0
sample_latitude = 0.0
sample_poiType = "TODO"
sample_distance = 10

def display_response(route, response):
    print("=" * 50)
    print(f"Response for route {route}:")
    print(response.status_code, response.text)
    print("-" * 50)

# API calls
# Friends routes
display_response("/friends/:userId", requests.get(f"{BASE_URL}/friends/{sample_userId}"))
display_response("/friends/:userId/received", requests.get(f"{BASE_URL}/friends/{sample_userId}/received"))
display_response("/friends/:userId/sent", requests.get(f"{BASE_URL}/friends/{sample_userId}/sent"))
display_response("/friend", requests.get(f"{BASE_URL}/friend"))
display_response("/friend (POST)", requests.post(f"{BASE_URL}/friend"))
display_response("/friend (PUT)", requests.put(f"{BASE_URL}/friend"))
display_response("/friend (DELETE)", requests.delete(f"{BASE_URL}/friend"))

# Market routes
display_response("/markets", requests.get(f"{BASE_URL}/markets"))
display_response("/market/:marketId", requests.get(f"{BASE_URL}/market/{sample_marketId}"))
display_response("/market (POST)", requests.post(f"{BASE_URL}/market"))
display_response("/market/:marketId (PUT)", requests.put(f"{BASE_URL}/market/{sample_marketId}"))
display_response("/market/:marketId (DELETE)", requests.delete(f"{BASE_URL}/market/{sample_marketId}"))

# PoI routes
display_response("/pois", requests.get(f"{BASE_URL}/pois"))
display_response("/poi/:id", requests.get(f"{BASE_URL}/poi/{sample_poiId}"))
display_response("/filteredPois/:longitude/:latitude/:poiType/:distance", requests.get(f"{BASE_URL}/filteredPois/{sample_longitude}/{sample_latitude}/{sample_poiType}/{sample_distance}"))
display_response("/poi (POST)", requests.post(f"{BASE_URL}/poi"))
display_response("/poi/:id (PUT)", requests.put(f"{BASE_URL}/poi/{sample_poiId}"))
display_response("/poi/:id (DELETE)", requests.delete(f"{BASE_URL}/poi/{sample_poiId}"))

# Review routes
searchBy = "user"
display_response("/reviews/:searchBy/:id", requests.get(f"{BASE_URL}/reviews/{searchBy}/{sample_reviewId}"))
display_response("/review/:id", requests.get(f"{BASE_URL}/review/{sample_reviewId}"))
display_response("/review (POST)", requests.post(f"{BASE_URL}/review"))
display_response("/review/:id (PUT)", requests.put(f"{BASE_URL}/review/{sample_reviewId}"))
display_response("/review/:id/rating (PUT)", requests.put(f"{BASE_URL}/review/{sample_reviewId}/rating"))
display_response("/review/:id (DELETE)", requests.delete(f"{BASE_URL}/review/{sample_reviewId}"))

# User routes
display_response("/users", requests.get(f"{BASE_URL}/users"))
display_response("/user/:id", requests.get(f"{BASE_URL}/user/{sample_userId}"))
display_response("/user (POST)", requests.post(f"{BASE_URL}/user"))
display_response("/user/:id (PUT)", requests.put(f"{BASE_URL}/user/{sample_userId}"))
display_response("/user/:id (DELETE)", requests.delete(f"{BASE_URL}/user/{sample_userId}"))
display_response("/rscore/:userId", requests.get(f"{BASE_URL}/rscore/{sample_userId}"))
