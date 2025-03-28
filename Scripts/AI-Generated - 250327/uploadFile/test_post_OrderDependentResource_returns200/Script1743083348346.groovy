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

def orderPayload = '{"petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": true}'
def orderRequest = findTestObject('Object Repository/Swagger Petstore/placeOrder')
def orderPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
orderRequest.setBodyContent(orderPayloadContent)
addHeaderConfiguration(orderRequest)
def orderResponse = WSBuiltInKeywords.sendRequest(orderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(orderResponse, 200)

def imagePayload = '{"image": "image_data_here"}'
def imageRequest = findTestObject('Object Repository/Swagger Petstore/uploadFile', ['petId': 1])
def imagePayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(imagePayload))
imageRequest.setBodyContent(imagePayloadContent)
addHeaderConfiguration(imageRequest)
def imageResponse = WSBuiltInKeywords.sendRequest(imageRequest)
WSBuiltInKeywords.verifyResponseStatusCode(imageResponse, 200)

println("Step 3 - Verify that the response status code is 200")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

