import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import truetest.custom.TrueTestScripts

def reportLocation = RunConfiguration.getReportFolder()

'Initialize test session: Open browser and set view port'

@com.kms.katalon.core.annotation.SetUp
def setup() {
	WebUI.openBrowser('')
	WebUI.setViewPortSize(1920, 1080)	
}

"Step 1: Navigate to /"

TrueTestScripts.navigate("/")

"Step 2: Click on link dynamicObject (page4)"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_home/link_dynamicObject"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_home/link_dynamicObject', ['link_dynamicObject_internalLabel': link_dynamicObject_internalLabel]))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 2-Click on link dynamicObject page4.png')

"Step 3: Click on link productView (crossHeadScrews) -> Navigate to page 'product#product/*'"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_home/link_viewProduct"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_home/link_viewProduct', ['link_viewProduct_dataTest': link_viewProduct_dataTest, 'product_id': product_id]))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 3-Click on link productView crossHeadScrews - Navigate to page productproduct.png')

"Step 4: Click on button increaseQuantity"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_product/button_increaseQuantity'))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 4-Click on button increaseQuantity.png')

"Step 5: Click on button addToFavorites"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_product/button_addToFavorites'))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 5-Click on button addToFavorites.png')

"Step 6: Click on link productDetails (m4NutsMoreInfo)"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails', ['link_viewProductDetails_internalHasText': link_viewProductDetails_internalHasText, 'product_id': product_id_1]))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 6-Click on link productDetails m4NutsMoreInfo.png')

"Step 7: Click on link productDetails (washersMoreInfo)"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails', ['link_viewProductDetails_internalHasText': link_viewProductDetails_internalHasText_1, 'product_id': product_id_2]))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 7-Click on link productDetails washersMoreInfo.png')

"Step 8: Click on p fastenersForgeFlexTools -> Navigate to page ''"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_product/p_fastenersForgeFlexTools'))

WebUI.takeScreenshot(reportLocation + '/TC3/Step 8-Click on p fastenersForgeFlexTools - Navigate to page .png')

"Step 9: Take full page screenshot as checkpoint"

WebUI.takeFullPageScreenshotAsCheckpoint('TC3-Explore Product Features and Add to Favorites in Tool Shop_visual_checkpoint')

'Terminate test session: Close browser'

@com.kms.katalon.core.annotation.TearDown
def teardown() {
	WebUI.closeBrowser()
}