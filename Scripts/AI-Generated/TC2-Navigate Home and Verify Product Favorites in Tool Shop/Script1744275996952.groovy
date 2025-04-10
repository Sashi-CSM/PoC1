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

"Step 2: Click on input selectCategory"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_home/input_selectCategory'))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 2-Click on input selectCategory.png')

"Step 3: Click on link viewProduct (viewProductSledgehammer) -> Navigate to page 'product#product/*'"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_home/link_viewProduct"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_home/link_viewProduct', ['link_viewProduct_dataTest': link_viewProduct_dataTest, 'product_id': product_id]))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 3-Click on link viewProduct viewProductSledgehammer - Navigate to page productproduct.png')

"Step 4: Click on link viewProductDetails (viewProductClawHammer)"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_product/link_viewProductDetails', ['link_viewProductDetails_internalHasText': link_viewProductDetails_internalHasText, 'product_id': product_id_1]))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 4-Click on link viewProductDetails viewProductClawHammer.png')

"Step 5: Click on link navigateHome -> Navigate to page '/'"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_product/link_navigateHome'))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 5-Click on link navigateHome - Navigate to page .png')

"Step 6: Click on link pageNavigation"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

WebUI.enhancedClick(findTestObject('AI-Generated/Page_home/link_pageNavigation'))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 6-Click on link pageNavigation.png')

"Step 7: Click on link viewProduct (viewProductOpenEndSpanners) -> Navigate to page 'product#product/*'"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + '?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_home/link_viewProduct"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_home/link_viewProduct', ['link_viewProduct_dataTest': link_viewProduct_dataTest_1, 'product_id': product_id_2]))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 7-Click on link viewProduct viewProductOpenEndSpanners - Navigate to page productproduct.png')

"Step 8: Click on button cartFavoritesAction (addToCart) -> Navigate to page ''"

// WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.application_domain + 'product/.*?/?(?:#.*)?(?:\\?.*)?$', true)

// Bind values to the variables in the locators of "AI-Generated/Dynamic Objects/Page_product/button_cartFavoritesAction"
WebUI.enhancedClick(findTestObject('AI-Generated/Dynamic Objects/Page_product/button_cartFavoritesAction', ['button_cartFavoritesAction_dataTest': button_cartFavoritesAction_dataTest]))

WebUI.takeScreenshot(reportLocation + '/TC2/Step 8-Click on button cartFavoritesAction addToCart - Navigate to page .png')

"Step 9: Take full page screenshot as checkpoint"

WebUI.takeFullPageScreenshotAsCheckpoint('TC2-Navigate Home and Verify Product Favorites in Tool Shop_visual_checkpoint')

'Terminate test session: Close browser'

@com.kms.katalon.core.annotation.TearDown
def teardown() {
	WebUI.closeBrowser()
}