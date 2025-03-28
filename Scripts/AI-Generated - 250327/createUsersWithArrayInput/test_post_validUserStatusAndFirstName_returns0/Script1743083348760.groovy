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

// Step 1: Create a new Category
def categoryPayload = '{"id": 1, "name": "category__unique__"}'
def createCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def categoryBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
createCategoryRequest.setBodyContent(categoryBodyContent)
addHeaderConfiguration(createCategoryRequest)
def createCategoryResponse = WSBuiltInKeywords.sendRequest(createCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCategoryResponse, 200)

// Step 2: Create a new Pet
def petPayload = '{"name": "pet__unique__", "photoUrls": ["url1", "url2"], "category": ' + categoryPayload + '}'
def createPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def petBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
createPetRequest.setBodyContent(petBodyContent)
addHeaderConfiguration(createPetRequest)
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)

// Step 3: Create a new Order
def orderPayload = '{"petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}'
def createOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def orderBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
createOrderRequest.setBodyContent(orderBodyContent)
addHeaderConfiguration(createOrderRequest)
def createOrderResponse = WSBuiltInKeywords.sendRequest(createOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createOrderResponse, 200)

// Step 4: Create a new User
def userPayload = '{"username": "user__unique__", "userStatus": 0, "firstName": "firstName__unique__"}'
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def userBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(userBodyContent)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

// Step 5: Create User with Array
def arrayPayload = '[' + userPayload + ']'
def createUserWithArrayRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')
def arrayBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(arrayPayload))
createUserWithArrayRequest.setBodyContent(arrayBodyContent)
addHeaderConfiguration(createUserWithArrayRequest)
def createUserWithArrayResponse = WSBuiltInKeywords.sendRequest(createUserWithArrayRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserWithArrayResponse, 200)

// Step 6: Verify Response Status Code
println("Step 6 - Verify Response Status Code: " + createUserWithArrayResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

