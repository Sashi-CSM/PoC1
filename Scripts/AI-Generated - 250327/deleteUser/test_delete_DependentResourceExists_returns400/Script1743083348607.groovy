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

def petPayload = '{"id": 1, "name": "doggie__unique__", "photoUrls": ["photo_url"], "status": "available"}'
def petRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def petBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petBodyContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)

def orderPayload = '{"id": 1, "petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00.000Z", "status": "placed", "complete": true}'
def orderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def orderBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
orderRequest.setBodyContent(orderBodyContent)
addHeaderConfiguration(orderRequest)
def orderResponse = WSBuiltInKeywords.sendRequest(orderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(orderResponse, 200)

def userPayload = '{"id": 1, "username": "user1__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com", "password": "password", "phone": "1234567890", "userStatus": 1}'
def userRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def userBodyContent = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
userRequest.setBodyContent(userBodyContent)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def username = new JsonSlurper().parseText(userPayload)['username']
def deleteRequest = findTestObject('Object Repository/Swagger Petstore/deleteUser', ['username': username])
addHeaderConfiguration(deleteRequest)
def deleteResponse = WSBuiltInKeywords.sendRequest(deleteRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

