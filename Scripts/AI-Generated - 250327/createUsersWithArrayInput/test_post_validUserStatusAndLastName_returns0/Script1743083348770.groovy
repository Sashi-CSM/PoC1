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
def userPayload = '{"username": "user__unique__", "userStatus": 0, "lastName": "lastname__unique__"}'
def arrayPayload = '[' + userPayload + ']'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def createUsersWithArrayInputRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(placeOrderRequest)
addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(createUsersWithArrayInputRequest)

addCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
placeOrderRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload)))
createUserRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(userPayload)))
createUsersWithArrayInputRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(arrayPayload)))

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
def createUsersWithArrayInputResponse = WSBuiltInKeywords.sendRequest(createUsersWithArrayInputRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createUsersWithArrayInputResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

