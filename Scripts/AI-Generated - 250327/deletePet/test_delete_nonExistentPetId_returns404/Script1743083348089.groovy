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

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "TestPet__unique__", "photoUrls": ["http://example.com/photo"], "category": {"id": 1, "name": "TestCategory__unique__"}}'))
def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
addPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def petId = new JsonSlurper().parseText(addPetResponse.getResponseText())['id']

def deletePetRequest = findTestObject('Object Repository/Swagger Petstore/deletePet', ['petId': petId])
addHeaderConfiguration(deletePetRequest)
def deletePetResponse = WSBuiltInKeywords.sendRequest(deletePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deletePetResponse, 404)

assert deletePetResponse.getStatusCode() == 404

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

