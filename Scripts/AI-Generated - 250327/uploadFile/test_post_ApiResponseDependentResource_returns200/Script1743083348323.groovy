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

def addPetRequest = findTestObject('Object Repository/Swagger Petstore/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "TestCategory"}'))
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(addPetPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def updatePetRequest = findTestObject('Object Repository/Swagger Petstore/updatePet')
def updatePetPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"name": "TestPet", "photoUrls": ["url1", "url2"], "category": {"id": 1, "name": "TestCategory"}}'))
addHeaderConfiguration(updatePetRequest)
updatePetRequest.setBodyContent(updatePetPayload)
def updatePetResponse = WSBuiltInKeywords.sendRequest(updatePetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updatePetResponse, 200)

def placeOrderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def placeOrderPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}'))
addHeaderConfiguration(placeOrderRequest)
placeOrderRequest.setBodyContent(placeOrderPayload)
def placeOrderResponse = WSBuiltInKeywords.sendRequest(placeOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(placeOrderResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

