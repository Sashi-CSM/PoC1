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

def create_user_request = findTestObject('Object Repository/Swagger Petstore/createUsersWithArrayInput')
addHeaderConfiguration(create_user_request)
def create_user_payload = new HttpTextBodyContent(replaceSuffixWithUUID('[{"id": 1, "username": "test_user__unique__", "userStatus": 1}]'))
create_user_request.setBodyContent(create_user_payload)
def create_user_response = WSBuiltInKeywords.sendRequest(create_user_request)
WSBuiltInKeywords.verifyResponseStatusCode(create_user_response, 200)

def get_user_request = findTestObject('Object Repository/Swagger Petstore/getUserByName', ['username': 'invalid_username__unique__'])
addHeaderConfiguration(get_user_request)
def get_user_response = WSBuiltInKeywords.sendRequest(get_user_request)
WSBuiltInKeywords.verifyResponseStatusCode(get_user_response, 400)
WSBuiltInKeywords.verifyResponseStatusCode(get_user_response, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

