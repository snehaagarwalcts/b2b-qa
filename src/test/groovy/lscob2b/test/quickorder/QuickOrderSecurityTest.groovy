package lscob2b.test.quickorder

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.quickorder.QuickOrderPage
import spock.lang.IgnoreIf

class QuickOrderSecurityTest extends GebReportingSpec {
	
	def setup() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		to LoginPage 
	}
	
	//@IgnoreIf({System.getProperty("geb.browser") == "internet explorer"})
	def "User that does not hold customer rights tries to place an order from quick order page"() {
		setup: "Log in"
			at LoginPage	
			login(user)
			
			at HomePage
			masterTemplate.clickQuickOrder()
			
		when: "at quickorder page"
			at QuickOrderPage
			
		and: "search for a product"
			doSearch(productCode, true)
			
		then: "at quickorder page"
			at QuickOrderPage
			
		and: "check unique result"
			waitFor { !productSizingGrids.empty }
			productSizingGrids.size() > 0

		when: "add product to cart"
			waitFor { !productSizingGrids.empty }
			addLimitedStockQuantityToCart(0,1)
			
		and: "click checkout"
			//to QuickOrderPage //For qa000
			waitFor { !checkOutLink.empty }
			masterTemplate.waitForSometime()
			checkOutLink.click()
			
		then: "at cart page"	
			at CartPage

		and: "check error message"
			alertMessage.displayed
			
		then: "remove from cart page"
			doRemove()

		where:
		user | productCode
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[0]
		//UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_FINANCE) | _
	}

}


