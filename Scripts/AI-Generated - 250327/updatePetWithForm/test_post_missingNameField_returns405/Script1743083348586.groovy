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
def petPayload = '{"name": "pet_name__unique__", "photoUrls": ["photo_url__unique__"], "category": {"id": category_id, "name": "category_name__unique__"}}'
def updatePayload = '{"photoUrls": ["photo_url__unique__"]}'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def updatePetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')

addHeaderConfiguration(addCategoryRequest)
addHeaderConfiguration(addPetRequest)
addHeaderConfiguration(updatePetRequest)

addCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
updatePetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(updatePayload)))

def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)

WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 405)

println("Step 4 - Response Status Code: ${updatePetResponse.getStatusCode()}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

