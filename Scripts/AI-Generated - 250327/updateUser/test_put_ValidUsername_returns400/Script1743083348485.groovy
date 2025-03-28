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

def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "test_user__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'))
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
createUserRequest.setBodyContent(userPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def invalidPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "test_user__unique__", "firstName": "Jane", "lastName": "Smith"}'))
def updateUserRequest = findTestObject('Object Repository/Swagger Petstore/updateUser', ['username': 'test_user__unique__'])
updateUserRequest.setBodyContent(invalidPayload)
addHeaderConfiguration(updateUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

