package lscob2b.test.myaccount

import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.productdetails.ProductDetailsPage
import lscob2b.test.data.User
import geb.spock.GebReportingSpec

class BulkOrderCreation extends GebReportingSpec{
	
	def static User user = new User(email: 'bulk@order-1', password:'Levis2015#')
	
	def static String productCode = ProductHelper.getProduct(ProductHelper.BRAND_LEVIS)
	
	def "Place Bulk Order"(){
	  setup:
  		  PageHelper.gotoPageLogout(browser, baseUrl)
		  to LoginPage
		  login(user)

		when: "At HomePage"
			at HomePage

		then: "Create a test order"
			placeAnOrder(productCode)
			
		where:
	    i << (1..1000)
	}
	
	def placeAnOrder(String productCode) {
		//Order Detail
		browser.go(baseUrl + "p/" + productCode)
		at ProductDetailsPage

		//Add To Cart
		addFullStockOrderQuantity("1")
		sizingGrid.buttonAddToCart.click()
		masterTemplate.goToCartLink.click()
		to CartPage
		
		//Cart
		at CartPage
		linkCheckout.click()

		//Checkout
		at CheckOutPage
		doPlaceOrder()

	}
}
