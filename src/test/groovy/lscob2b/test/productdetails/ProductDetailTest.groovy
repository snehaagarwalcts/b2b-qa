package lscob2b.test.productdetails

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.productdetails.ProductDetailsPage
import lscob2b.test.data.Product
import lscob2b.test.data.User
import spock.lang.Ignore
import spock.lang.Stepwise

@Stepwise
class ProductDetailTest extends GebReportingSpec {
	
	def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	
	def static String targetProductCode = ProductHelper.getProduct(ProductHelper.BRAND_LEVIS)
	
	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		
		at LoginPage
		login(user)
		
		at HomePage		
	}
	
	/**
	 * TC BB-521 Automated test: User can add to cart from product details page
	 */
	def "Add to cart from product details page"(){
		setup:
			PageHelper.gotoPageProductDetail(browser,baseUrl,targetProductCode)
				
		when: "at product detail page"
			at ProductDetailsPage
			
		and: "get current cart count"
			def int cartCount = masterTemplate.cartItemCount.text().toInteger()
		
		and: "add product to cart"
			waitFor { !sizingGrid.empty }
			sizingGrid.addLimitedStockQuantityToCart(1)
			
		then: "check updated cart count"
			waitFor { masterTemplate.cartItemCount.text().toInteger() == (cartCount+1) }
			
	}
	
	def "Check cartpage status"(){
		setup:
			PageHelper.gotoPageProductDetail(browser,baseUrl,targetProductCode)
	
		when: "at product detail page"
			at ProductDetailsPage
		
		and: "click on cart link"	
			masterTemplate.cartItemLink.click()
			
		then: "at cart page"
			at CartPage
			
		and: "check product has been added to cart"		
			checkItemNameExists()
			checkItemStyleExists()
			checkItemColorExists()
			checkItemPriceExists()
			checkItemQuantityExists()
			checkItemTotalExists()		
	}
	
	/**
	 * TC BB-559 Automated test: be able to place an order from Product details page
	 */
	//TODO not possible to place an order (SAP Integration is missing)
	//@Ignore
	def "Place an order from product details page"(){
		when: "at Cart Page"
			at CartPage
			masterTemplate.waitForSometime()
			
		then: "click on checkout"
			linkCheckout.click()
		
		when: "at Checkout page"
			at CheckOutPage
			
		then: "Place Order"
			doPlaceOrder()
	}	
}
