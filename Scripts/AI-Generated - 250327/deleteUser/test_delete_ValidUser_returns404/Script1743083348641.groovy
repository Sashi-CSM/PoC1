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

def userPayload = [
    "id": 1,
    "username": "test_user__unique__",
    "firstName": "John",
    "lastName": "Doe",
    "email": "johndoe@example.com",
    "password": "password123",
    "phone": "1234567890",
    "userStatus": 1
]

def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUser')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(userPayload)))
addHeaderConfiguration(createUserRequest)
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def username = userPayload["username"]
def deleteUserRequest = findTestObject('Object Repository/Swagger Petstore/deleteUser', ["username": username])
addHeaderConfiguration(deleteUserRequest)
def deleteUserResponse = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteUserResponse, 200)

if (deleteUserResponse.getStatusCode() == 404) {
    println("User successfully deleted")
} else {
    println("User deletion failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

