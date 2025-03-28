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

def create_user_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "test_user__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'))
def create_user_request = findTestObject('Object Repository/Swagger Petstore/createUser')
create_user_request.setBodyContent(create_user_payload)
addHeaderConfiguration(create_user_request)
def create_user_response = WSBuiltInKeywords.sendRequest(create_user_request)
WSBuiltInKeywords.verifyResponseStatusCode(create_user_response, 200)

def update_user_payload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "", "firstName": "Jane", "lastName": "Smith", "email": "jane.smith@example.com", "password": "newpassword456", "phone": "9876543210", "userStatus": 2}'))
def update_user_request = findTestObject('Object Repository/Swagger Petstore/updateUser', ['username': 'test_user__unique__'])
update_user_request.setBodyContent(update_user_payload)
addHeaderConfiguration(update_user_request)
def update_user_response = WSBuiltInKeywords.sendRequest(update_user_request)
WSBuiltInKeywords.verifyResponseStatusCode(update_user_response, 400)

def response_status_code = update_user_response.getStatusCode()
println(response_status_code)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

