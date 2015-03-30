package lscob2b.test.quickorder

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

@Stepwise
class QuickOrderTest extends GebReportingSpec {
	
	def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	
	def static String targetProductCode = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	
	def static String[] multipleTargetProductCode = ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)
	
	def static String targetProductString = "Levis 501"
	
	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		to LoginPage
		
		at LoginPage
		login(user)
		at HomePage
		
		masterTemplate.clickQuickOrder()
		at QuickOrderPage
	}
	
	
	def "Search for string in quick-order page"() {
		when: "at QuickOrder page"
			at QuickOrderPage
			
		and: "Search for a product"	
			doSearch(targetProductString, false)
			at QuickOrderPage
			
		then: "Check multiple result"
			waitFor { !productSizingGrids.empty }
			productSizingGrids.size() > 0
		
	}
	
	def "Search for product-code by ids in quick-order page"() {
		when: "at QuickOrder page"
			at QuickOrderPage
		
		and: "Search for a product"
			doSearch(targetProductCode, true)

		then: "at quickorder page"
			at QuickOrderPage
			
		and: "Check unique result"
			checkResultSize(1)
	}

	/**
	 * TC BB-456 Automated Test Case: be able to add to cart from quick order page
	 * TC BB-438 Automated Test Case: Place an order from "Quick Order" Page.
	 */
	def "Add to cart from quick-order page"() {
		when: "at QuickOrder page"
			at QuickOrderPage
	
		and: "get current cart item"
			def int cartCount = masterTemplate.cartItemCount.text().toInteger()
		
		and: "add item to cart"
			waitFor { !productSizingGrids.empty }
			addLimitedStockQuantityToCart(0,1)
			
		then: "check cart item count"
			waitFor { masterTemplate.cartItemCount.text().toInteger() == (cartCount+1) }
	}
	
	/**
	 * TC BB-448 Automated Test Case: Place an order from "Quick Order" Page with multiple product ID's
	 */
	def "Search for multiple product-code by ids in quick-order page"() {
		when: "at QuickOrder page"
			at QuickOrderPage
		
		and: "Search for a product"
			doMultipleSearch(multipleTargetProductCode[0], multipleTargetProductCode[1])
			
		then: "At quickorder page"
			at QuickOrderPage
		
		and: "Check only 2 results"
			checkResultSize(2)
	}

	/**
	 * TC BB-477 Automated Test Case: Remove product from Check Out page
	 * TC BB-621 Automated test: In the checkout the total order value is visible
	 */
	def "Check Total, Subtotal and remove product from checkout page"() {
		when: "at QuickOrder page"
			at QuickOrderPage
			
		and: "click checkout"
			checkOutLink.click()
			
		then: "at Checkout page"
			at CheckOutPage
		
		and: "check page element"
			checkTotalExists() //BB-621
			checkSubTotalExists()
			waitFor {remove.displayed}
			remove.click()	
			removeConfirm.click()
	}		
}
