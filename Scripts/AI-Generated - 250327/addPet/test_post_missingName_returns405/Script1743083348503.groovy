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

def categoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "name": "category__unique__"}'))
def createCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
createCategoryRequest.setBodyContent(categoryPayload)
addHeaderConfiguration(createCategoryRequest)
def createCategoryResponse = WSBuiltInKeywords.sendRequest(createCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCategoryResponse, 200)

def petPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"category": {"id": 1, "name": "category__unique__"}, "photoUrls": ["url1", "url2"]}'))
def createPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
createPetRequest.setBodyContent(petPayload)
addHeaderConfiguration(createPetRequest)
def createPetResponse = WSBuiltInKeywords.sendRequest(createPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createPetResponse, 200)

def petPayloadMissingName = new HttpTextBodyContent(replaceSuffixWithUUID('{"category": {"id": 1, "name": "category__unique__"}, "photoUrls": ["url1", "url2"]}'))
def createPetMissingNameRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
createPetMissingNameRequest.setBodyContent(petPayloadMissingName)
addHeaderConfiguration(createPetMissingNameRequest)
def createPetMissingNameResponse = WSBuiltInKeywords.sendRequest(createPetMissingNameRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createPetMissingNameResponse, 405)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

