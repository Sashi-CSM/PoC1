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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 999, "category": {"id": 1, "name": "category__unique__"}, "name": "Test Pet", "photoUrls": ["url1", "url2"], "tags": [{"id": 1, "name": "tag__unique__"}], "status": "available"}'))
def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def invalidPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 9999, "category": {"id": 1, "name": "category__unique__"}, "name": "Test Pet", "photoUrls": ["url1", "url2"], "tags": [{"id": 1, "name": "tag__unique__"}], "status": "available"}'))
def updatePetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
updatePetRequest.setBodyContent(invalidPetPayload)
addHeaderConfiguration(updatePetRequest)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 404)

println("Step 3 - Response status code: ${updatePetResponse.getStatusCode()}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

