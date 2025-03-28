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

def order_id = "non_integer_value"
def get_order_endpoint = "/store/order/${order_id}"
def get_order_request = findTestObject("Object Repository/Swagger Petstore/getOrderById", ["orderId": order_id])
addHeaderConfiguration(get_order_request)
def get_order_response = WSBuiltInKeywords.sendRequest(get_order_request)
WSBuiltInKeywords.verifyResponseStatusCode(get_order_response, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

