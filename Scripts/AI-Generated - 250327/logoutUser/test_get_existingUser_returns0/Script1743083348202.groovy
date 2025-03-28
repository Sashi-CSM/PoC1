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

def user_payload = [{"username": "test_user__unique__", "userStatus": 1}]

def createWithArrayRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')
def createWithArrayPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(user_payload)))
createWithArrayRequest.setBodyContent(createWithArrayPayload)
addHeaderConfiguration(createWithArrayRequest)
def createWithArrayResponse = WSBuiltInKeywords.sendRequest(createWithArrayRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createWithArrayResponse, 200)

def logoutUserRequest = findTestObject('Object Repository/Swagger Petstore/logoutUser')
addHeaderConfiguration(logoutUserRequest)
def logoutUserResponse = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 200)

def verifyResponseStatusCode = logoutUserResponse.getResponseText().contains("\"code\":0")
if (verifyResponseStatusCode) {
    println("Test Passed")
} else {
    println("Test Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

