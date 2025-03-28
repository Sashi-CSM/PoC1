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

def categoryPayload = '{"id": 1, "name": "category__unique__"}'
def petPayload = '{"name": "pet__unique__", "photoUrls": ["url1", "url2"], "category": ' + categoryPayload + '}'
def orderPayload = '{"petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}'
def userPayload = '{"username": "user__unique__", "userStatus": 0}'

def createCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def createPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def createOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def createUserWithArrayRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')

addHeaderConfiguration(createCategoryRequest)
addHeaderConfiguration(createPetRequest)
addHeaderConfiguration(createOrderRequest)
addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(createUserWithArrayRequest)

createCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
createPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
createOrderRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload)))
createUserRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(userPayload)))
createUserWithArrayRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID('[' + userPayload + ']')))

def createCategoryResponse = WSBuiltInKeywords.sendRequest(createCategoryRequest)
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
def createOrderResponse = WSBuiltInKeywords.sendRequest(createOrderRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
def createUserWithArrayResponse = WSBuiltInKeywords.sendRequest(createUserWithArrayRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createCategoryResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createOrderResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createUserWithArrayResponse, 200)

if (createUserWithArrayResponse.getStatusCode() == 0) {
    println("Step 6 - Verify Status Code: Passed")
} else {
    println("Step 6 - Verify Status Code: Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

