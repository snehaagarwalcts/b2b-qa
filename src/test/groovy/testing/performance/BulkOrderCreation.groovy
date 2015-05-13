package testing.performance

import spock.lang.Ignore
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
	
	//def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	//def static User user = new User(email: 'bulk@order-1', password:'Levis2015#')
	def static User user = new User(email: 'aroy3@levi.com', password:'Password@1')
	
	def static String productcode = ProductHelper.getBulkOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	
	def static targetProductCode = ProductHelper.getBulkOrderProduct(ProductHelper.BRAND_LEVIS)
	
	def setup(){
		PageHelper.gotoPageLogout(browser, baseUrl)
	}
	
	@Ignore
	def "Place Bulk Order with 1 product in cart"(){
		  setup:		  
		  to LoginPage
		  at LoginPage
		  login(user)
		  browser.go(baseUrl + "p/" + productcode)
		  
		  when: "at Product Details Page"
		  at ProductDetailsPage
		  
		  then: "Add product to cart"
//		  waitFor { !sizingGrid.empty }
//		  sizingGrid.addLimitedStockQuantityToCart(1)
		  addFullStockOrderQuantity("1")
		  sizingGrid.buttonAddToCart.click()

		  and: "go to Checkout Page"
		  browser.go(baseUrl + "checkout/single/summary")
		  
		  when: "at CheckOut Page"
		  at CheckOutPage
		  
		  then: "Place Order"
		  //doPlaceOrder()
		  Thread.sleep(2000)
		  waitFor { placeOrderLink.displayed }
		  placeOrderLink.click()
		  Thread.sleep(2000)
		  waitFor { placeOrderLink.displayed }
		  placeOrderLink.click()
		  
		  where:
		  i << (1..2)
	  }
	
	def "Place Bulk Order with multiple products in cart"(){
		setup:
		to LoginPage
		at LoginPage
		login(user)

		when: "At HomePage"
		at HomePage
		
		then: "Add multiple products to cart"
		for(productCode in targetProductCode){
		  browser.go(baseUrl + "p/" + productCode)
		  at ProductDetailsPage
//		  waitFor { !sizingGrid.empty }
//		  sizingGrid.addLimitedStockQuantityToCart(1)
		  addFullStockOrderQuantity("1")
		  sizingGrid.buttonAddToCart.click()
		}

		and: "go to Checkout Page"
		browser.go(baseUrl + "checkout/single/summary")
		
		when: "at CheckOut Page"
		at CheckOutPage
		
		then: "Place Order"
		//doPlaceOrder()
		Thread.sleep(2000)
		waitFor { placeOrderLink.displayed }
		placeOrderLink.click()
		Thread.sleep(2000)
		waitFor { placeOrderLink.displayed }
		placeOrderLink.click()
		
		where:
		i << (1..2)
	 }
}
