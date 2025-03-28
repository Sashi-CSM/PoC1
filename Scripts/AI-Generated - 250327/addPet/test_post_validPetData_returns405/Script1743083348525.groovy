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

def categoryPayload = '{"id": 1, "name": "category_name__unique__"}'
def petPayload = '{"category": {"id": 1, "name": "category_name__unique__"}, "name": "pet_name__unique__", "photoUrls": ["photo_url__unique__"]}'

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

def postPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def postPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
postPetRequest.setBodyContent(postPetPayload)
addHeaderConfiguration(postPetRequest)
def postPetResponse = WSBuiltInKeywords.sendRequest(postPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(postPetResponse, 405)

def verifyStatusCode = postPetResponse.getStatusCode()
println("Step 4 - Verify status code is 405: ${verifyStatusCode}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

