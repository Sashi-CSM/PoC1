import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

// Step 1: Create a new Pet
def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def pet_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "doggie__unique__", "photoUrls": ["https://example.com/photo1"]}'))
addPetRequest.setBodyContent(pet_payload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

// Step 2: Create a new Order
def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def order_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}'))
placeOrderRequest.setBodyContent(order_payload)
addHeaderConfiguration(placeOrderRequest)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 200)

// Step 3: Create a new Category
def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addCategory')
def category_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "category__unique__"}'))
addCategoryRequest.setBodyContent(category_payload)
addHeaderConfiguration(addCategoryRequest)
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

// Step 4: Create a new User
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def user_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "user1__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'))
createUserRequest.setBodyContent(user_payload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

// Step 5: Make a GET request to /user/login with invalid credentials
def loginUserRequest = findTestObject('Object Repository/Swagger Petstore/loginUser')
def invalid_username = "invalid_user"
def invalid_password = "invalid_password"
def loginUserResponse = WSBuiltInKeywords.sendRequest(loginUserRequest, ['username': invalid_username, 'password': invalid_password])
WSBuiltInKeywords.verifyResponseStatusCode(loginUserResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

