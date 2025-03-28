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

def petPayload = '{"id": 101, "name": "Pet Without Category", "photoUrls": ["url1", "url2"]}'
def petPayloadWithoutCategory = '{"id": 101, "name": "Pet Without Category", "photoUrls": ["url1", "url2"]}'

def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(addPetPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def updatePetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
def updatePetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayloadWithoutCategory))
addHeaderConfiguration(updatePetRequest)
updatePetRequest.setBodyContent(updatePetPayload)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 200)

def verifyPetResponseStatusCodeRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
def verifyPetResponseStatusCodeResponse = WSBuiltInKeywords.sendRequest(verifyPetResponseStatusCodeRequest)
WSBuiltInKeywords.verifyResponseStatusCode(verifyPetResponseStatusCodeResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

