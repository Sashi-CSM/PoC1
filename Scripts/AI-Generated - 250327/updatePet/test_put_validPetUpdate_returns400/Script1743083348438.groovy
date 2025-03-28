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

def categoryPayload = '{"id": 3, "name": "Third Category__unique__"}'
def petPayload = '{"id": 102, "name": "Pet To Update__unique__", "photoUrls": ["url1", "url2"], "category": {"id": 3}}'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addCategoryPayload = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
addCategoryRequest.setBodyContent(addCategoryPayload)
addHeaderConfiguration(addCategoryRequest)
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
addPetRequest.setBodyContent(addPetPayload)
addHeaderConfiguration(addPetRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def updatePetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
def updatePetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
updatePetRequest.setBodyContent(updatePetPayload)
addHeaderConfiguration(updatePetRequest)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 400)

assert updatePetResponse.getStatusCode() == 400

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

