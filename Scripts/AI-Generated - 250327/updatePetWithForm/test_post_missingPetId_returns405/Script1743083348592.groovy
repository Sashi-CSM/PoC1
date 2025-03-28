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

def petPayload = '{"name": "TestPet__unique__", "photoUrls": ["http://example.com/photo1"]}'
def petRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)
def petId = new JsonSlurper().parseText(petResponse.getResponseText())['id']

def missingPetIdPayload = '{"name": "TestPet__unique__", "photoUrls": ["http://example.com/photo1"]}'
def missingPetIdRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def missingPetIdPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(missingPetIdPayload))
missingPetIdRequest.setBodyContent(missingPetIdPayloadContent)
addHeaderConfiguration(missingPetIdRequest)
def missingPetIdResponse = WSBuiltInKeywords.sendRequest(missingPetIdRequest)
WSBuiltInKeywords.verifyResponseStatusCode(missingPetIdResponse, 405)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

