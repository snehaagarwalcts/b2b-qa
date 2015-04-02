package lscob2b.test.cartpage

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.productdetails.ProductDetailsPage
import spock.lang.IgnoreIf

class CartPageTest extends GebReportingSpec {
		
	def setup() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		to LoginPage
	}

	/*def cleanup() {
		masterTemplate.doLogout()
	}*/

	/**
	 * US BB-555 Text for empty cart 
	 * TC BB-779 Text for empty cart
	 */
	def "Check for empty cart message"(){
		setup:
		login(user)

		when:"at home page go to cart page"
		at HomePage
		masterTemplate.doGoToCart()

		then: "you'll be at cart page"
		at CartPage

		and: "Check empty cart message is displayed and also check page is empty"
		assert removeExistingProducts()  //remove existing products from cart(if any)
		emptyCartMessageExists()
		editQuantities.empty
		removeProductLink.empty
		linkCheckout.empty
		checkContinueShoppingButtonExists()

		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}

	//@IgnoreIf({System.getProperty("geb.browser") == "chrome"})
	def "Check the common content of Cart Page with products in cart"(){
		setup:
		at LoginPage
		login(user)
		
		when: "at homepage"
		at HomePage
		
		and: "go to the product details page of a product"
		PageHelper.gotoPageProductDetail(browser,baseUrl,productCode)
		
		then: "at product details page"
		at ProductDetailsPage
		
		and: "add quantity of 1 to sizing grid"
		waitFor { !sizingGrid.empty }
		sizingGrid.addLimitedStockQuantityToCart(1)
		
		and: "Go to Cart Page"		
		//masterTemplate.doGoToCart()  //issue with chrome
		to CartPage
		
		then: "at cart page"
		at CartPage

		and: "check common content of the page"
		checkItemNameExists()
		checkItemStyleExists()
		checkItemColorExists()
		checkItemPriceExists()
		checkItemQuantityExists()
		checkItemTotalExists()
		checkRemoveProductButtonExists()
		checkEditQuantitiesButtonExists()
		checkContinueShoppingButtonExists()
		checkCheckoutButtonExists()

		then: "Remove the product from cart"
		doRemove()
		waitFor { $('div.global-nav ul.global-nav-list').find("a", href: contains("/logout")) }

		where:
		user | productCode
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	}
}
