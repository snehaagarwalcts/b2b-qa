package lscob2b.test.checkout

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage;
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.quickorder.QuickOrderPage

class CheckOutPageTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		to LoginPage
	}

	/**
	 * US BB-593 A customer which on credit card payment cannot choose Invoice
	 * TC BB-834 A customer which on credit card payment cannot choose Invoice
	 * 
	 */
	def "B2B unit with payment term code ZCC0 should only display credit card as payment option"() {
		setup:
		at LoginPage
		login(user)

		when: "at Home page"
		at HomePage

		and:"click on quick order link"
		masterTemplate.clickQuickOrder()

		and:"at quick order page"
		at QuickOrderPage

		then: "search for a product"
		doSearch(targetProductCode, true)

		when: "at quick order page"
		at QuickOrderPage

		and: "add product to cart"
		masterTemplate.waitForSometime()
		def int cartCount = masterTemplate.cartItemCount.text().toInteger()
		waitFor { !productSizingGrids.empty }
		addLimitedStockQuantityToCart(0,1)
		def int upCartCount = masterTemplate.cartItemCount.text().toInteger()
		upCartCount == (cartCount+1)

		then: "at QuickOrderPage"
		at QuickOrderPage

		when: "click checkout"
		masterTemplate.waitForSometime()
		checkOutLink.click()

		and: "at Checkout page"
		at CheckOutPage

		then: "check page elements"
		checkCreditCardPaymentButtonExists()
		invoicePayment.empty
		
		and: "Go to CartPage and remove product"
		to CartPage
		at CartPage
		assert removeExistingProducts()

		where:
		user | targetProductCode
		UserHelper.getCreditCardOnlyUser() | ProductHelper.getProduct(ProductHelper.BRAND_LEVIS)
	}	
}
