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

def categoryPayload = '{"name": "category_name__unique__"}'
def categoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def categoryPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload))
categoryRequest.setBodyContent(categoryPayloadContent)
addHeaderConfiguration(categoryRequest)
def categoryResponse = WSBuiltInKeywords.sendRequest(categoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(categoryResponse, 200)
def categoryJson = new JsonSlurper().parseText(categoryResponse.getResponseText())
def categoryId = categoryJson.id

def petPayload = '{"name": "pet_name__unique__", "photoUrls": ["photo_url"], "category": {"id": ' + categoryId + ', "name": ' + categoryPayload + '}}'
def petRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def petPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
petRequest.setBodyContent(petPayloadContent)
addHeaderConfiguration(petRequest)
def petResponse = WSBuiltInKeywords.sendRequest(petRequest)
WSBuiltInKeywords.verifyResponseStatusCode(petResponse, 200)
def petJson = new JsonSlurper().parseText(petResponse.getResponseText())
def petId = petJson.id

def invalidPetPayload = '{"name": "pet_name__unique__", "category": {"id": ' + categoryId + ', "name": ' + categoryPayload + '}}'
def invalidPetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
def invalidPetPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(invalidPetPayload))
invalidPetRequest.setBodyContent(invalidPetPayloadContent)
addHeaderConfiguration(invalidPetRequest)
def invalidPetResponse = WSBuiltInKeywords.sendRequest(invalidPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(invalidPetResponse, 405)
def invalidPetStatusCode = invalidPetResponse.getStatusCode()
println("Step 4 - Response Status Code: " + invalidPetStatusCode)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

