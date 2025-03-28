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
def createUserRequest = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')
addHeaderConfiguration(createUserRequest)
createUserRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(userPayload))))
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def petPayload = {"id": 1, "category": {"id": 1, "name": "test_category__unique__"}, "name": "test_pet__unique__", "photoUrls": ["test_url"], "tags": [{"id": 1, "name": "test_tag__unique__"}], "status": "available"}
def createPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
addHeaderConfiguration(createPetRequest)
createPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(petPayload))))
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)

def logoutUserRequest = findTestObject('Object Repository/Swagger Petstore/logoutUser')
addHeaderConfiguration(logoutUserRequest)
def logoutUserResponse = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 200)

if (logoutUserResponse.getStatusCode() == 0) {
    println("Step 4 - Verify Status Code: Passed")
} else {
    println("Step 4 - Verify Status Code: Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

