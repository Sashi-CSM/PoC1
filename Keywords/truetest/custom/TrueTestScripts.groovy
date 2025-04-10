package truetest.custom

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.json.JsonSlurper
import internal.GlobalVariable
import java.util.regex.Pattern

public class TrueTestScripts {
    
    public static void login() {
        try {
            // Trigger a Login test case
            // import com.kms.katalon.core.model.FailureHandling
            // import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
            // import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
            // WebUI.callTestCase(findTestCase('<path to testcase>/Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
            // Trigger a custom Login method
            // import your.package
            // import authentication.Login
            // call your custom login method
            // Login.login()
            // Trigger a custom Login keyword
            // CustomKeywords.login()
        } catch(Exception e) {
            if (e.getCause() instanceof WebElementNotFoundException) {
                KeywordUtil.logInfo(e.getMessage())
                KeywordUtil.markWarning(e.getMessage())
            } else {
                throw e
            }
        }
    }
    
    static Integer DELAY_TIME = 3 // in seconds
    
    private static String combineUrlQueryParams(Map<String, String> queryParams) {
        String queryParameters = "";
        try {
            queryParameters = GlobalVariable.query_params;
        }
        catch (Exception e) {
            KeywordUtil.logInfo(e.getMessage());
        }
        for (Map.Entry<String, String> param: queryParams) {
            if (queryParameters.length() > 0) {
                queryParameters += "&";
            }
            queryParameters += "${param.getKey()}=${param.getValue()}";
        }
        return queryParameters;
    }
    
    private static void do_navigate(String path, String queryParameters) {
        String applicationDomain = GlobalVariable.application_domain;
        if (path == null) {
            path = "";
        }
        if (!path.startsWith("/")) {
            path = "/$path";
        }
        String url = "$applicationDomain$path";
        if (queryParameters != null && queryParameters.length() > 0) {
            url = "$url?$queryParameters";
        }
        WebUI.navigateToUrl(url);
        WebUI.delay(DELAY_TIME);
    }
    
    public static void navigate(String path, Map<String, String> searchParams) {
        String queryParameters = this.combineUrlQueryParams(searchParams);
        this.do_navigate(path, queryParameters);
    }
    
    public static void navigate(String path) {
        this.do_navigate(path, "");
    }
    
    public static void selectOptionByValue(TestObject to, String rawValue) {
        def parsedValue = rawValue
        if (!parsedValue.startsWith("[")) {
            parsedValue = "[\"" + parsedValue + "\"]"
        }
        def jsonSlurper = new JsonSlurper()
        def options = jsonSlurper.parseText(parsedValue)
        for (option in options) {
            WebUI.selectOptionByValue(to, Pattern.quote(option), true)
        }
    }
    
    public static void setSliderValue(TestObject to, double value) {
        WebUI.delay(1)
        WebUI.enhancedClick(to)
        def sliderElement = WebUiCommonHelper.findWebElement(to, 30)
        double sliderWidth = Double.parseDouble(WebUI.executeJavaScript("return arguments[0].getBoundingClientRect().width", Arrays.asList(sliderElement)).toString())
        def sliderHandleElement = WebUI.executeJavaScript("return arguments[0].querySelector('[role=slider]')", Arrays.asList(sliderElement))
        double min =  Double.parseDouble(WebUI.executeJavaScript("return arguments[0].getAttribute('aria-valuemin')", Arrays.asList(sliderHandleElement)).toString())
        double max =  Double.parseDouble(WebUI.executeJavaScript("return arguments[0].getAttribute('aria-valuemax')", Arrays.asList(sliderHandleElement)).toString())
        double currentValue = Double.parseDouble(WebUI.executeJavaScript("return arguments[0].getAttribute('aria-valuenow')", Arrays.asList(sliderHandleElement)).toString())
        int offsetX = Math.floor(((value - currentValue) / (max - min)) * sliderWidth)
        WebUI.dragAndDropByOffset(to, offsetX, 0)
    }
}

