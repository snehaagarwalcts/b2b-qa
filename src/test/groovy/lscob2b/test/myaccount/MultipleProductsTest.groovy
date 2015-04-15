package lscob2b.test.myaccount

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.quickorder.QuickOrderPage
import lscob2b.test.data.User
import spock.lang.Stepwise
import spock.lang.IgnoreIf

@IgnoreIf({System.getProperty("geb.env") == "preprod"})
class MultipleProductsTest extends GebReportingSpec {
	
	def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	
	def static String targetProductCode = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	def static String targetProductCode1 = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[1]
	//def static String targetProductCode2 = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[2]
	
	//def static String[] multipleTargetProductCode = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)
	
	def static String targetProductString = "Levis 501"
	
	
	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		to LoginPage
		
		at LoginPage
		login(user)
		at HomePage
		
		masterTemplate.clickQuickOrder()
		at QuickOrderPage	
		
		and: "Search for a product"
		doSearch(targetProductCode , true)
		
		then:
		waitFor { !productSizingGrids.empty }
		addLimitedStockQuantityToCart(0,1)	
		
		
		
	}
	
	
}
