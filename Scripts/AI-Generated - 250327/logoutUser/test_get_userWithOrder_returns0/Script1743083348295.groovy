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

def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')
def user_payload = [{"username": "test_user__unique__", "userStatus": 1}]
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(user_payload)))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def createOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def order_payload = {"id": 1, "petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}
def createOrderPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(order_payload)))
createOrderRequest.setBodyContent(createOrderPayload)
addHeaderConfiguration(createOrderRequest)
def createOrderResponse = WSBuiltInKeywords.sendRequest(createOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createOrderResponse, 200)

def logoutRequest = findTestObject('Object Repository/Swagger Petstore/logoutUser')
addHeaderConfiguration(logoutRequest)
def logoutResponse = WSBuiltInKeywords.sendRequest(logoutRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutResponse, 200)

WSBuiltInKeywords.verifyResponseStatusCode(logoutResponse, 0)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

