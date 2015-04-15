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
	
	def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	//def static User user = new User(email: 'bulk@order-1', password:'Levis2015#')
	
	def static String productCode = ProductHelper.getBulkOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	
	def "Place Bulk Order"(){
		  setup:	
		  PageHelper.gotoPageLogout(browser, baseUrl)
		  to LoginPage
		  at LoginPage
		  login(user)
		  browser.go(baseUrl + "p/" + productCode)
		  
		  when: "at Product Details Page"
		  at ProductDetailsPage
		  
		  then: "Add product to cart"
		  waitFor { !sizingGrid.empty }
		  sizingGrid.addLimitedStockQuantityToCart(1)

		  and: "go to Checkout Page"
		  browser.go(baseUrl + "checkout/single/summary")
		  
		  when: "at CheckOut Page"
		  at CheckOutPage
		  
		  then: "Place Order"
		  doPlaceOrder()
		  
		  where:
		  i << (1..2)
	  }
}
