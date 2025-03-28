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

def userPayload = [{"username": "test_user__unique__", "userStatus": 1}]
def createUserRequest = findTestObject('createUsersWithArrayInput')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(userPayload)))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def petPayload = {"name": "test_pet__unique__", "photoUrls": ["https://example.com/photo"], "category": {"id": 1, "name": "test_category__unique__"}}
def createPetRequest = findTestObject('addPet')
def createPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(petPayload)))
createPetRequest.setBodyContent(createPetPayload)
addHeaderConfiguration(createPetRequest)
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)

def logoutUserRequest = findTestObject('logoutUser')
addHeaderConfiguration(logoutUserRequest)
def logoutUserResponse = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 200)

def verifyStatusCode = logoutUserResponse.getStatusCode()
if (verifyStatusCode == 0) {
    println("Step 4 - Verify Status Code: Passed")
} else {
    println("Step 4 - Verify Status Code: Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

