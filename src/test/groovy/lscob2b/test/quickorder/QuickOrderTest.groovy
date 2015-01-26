package lscob2b.test.quickorder

import static lscob2b.TestConstants.*
import geb.spock.GebReportingSpec
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.CheckOut.CheckOutPage
import lscob2b.pages.OrderConfirmation.OrderConfirmationPage
import lscob2b.pages.QuickOrder.QuickOrderPage
import lscob2b.pages.cart.CartPage
import lscob2b.test.data.TestDataCatalog
import lscob2b.test.data.TestHelper
import spock.lang.Ignore

//@Stepwise
class QuickOrderTest extends GebReportingSpec {

	def setupSpec() {
		browser.go(baseUrl + TestHelper.PAGE_LOGOUT)
	}
	
	def setup() {
		to LoginPage
	}

	def cleanup() {
		masterTemplate.doLogout()
	}
	
	def login(String username) {
		doLogin(username, defaultPassword)
	}

	def loginAsUserAndGoToQuickOrder(String user){
		login(user)
		at HomePage
		masterTemplate.clickQuickOrder()
		at QuickOrderPage
	}
	
	def doRemoveProduct(){
		doRemove()
	}
	
	def "Quick Order link is accessible"() {

		when: "logged in as any user"

		login(user)

		then: "Quick Order link is available"
		at HomePage
		masterTemplate.clickQuickOrder()
		at QuickOrderPage

		where:

		user << [levisUser]    // TODO change for user roles
	}
	
	def "Check the Quick Order page content"(){
		
		when: "Logging in and going to Quick Order page"
		loginAsUserAndGoToQuickOrder(user)
		
		then: "Correct sections/links should be visible"
		checkKeywordSearchLinkExists()
		checkAddLinkExists()
		checkSearchButtonLinkExists()
		checkPrdouctIdsOnlyLinkExists()
		checkQuantityLinkExists()
		checkTotalLinkExists()
		checkCartButtonsLinkExists()
		checkCheckOuLinkExists()
		
		where:
		user << [levisUser]
	}
	
	@Ignore
	def "Add to cart from Quick Order Page"(){
		when: "Logging in and going to Quick Order page"
		loginAsUserAndGoToQuickOrder(user)
		
		then: "Add to Cart"
		doSearch('00501-1615')
		sizingGrid.waitForSizingGridLoadedCompletely()
		sizingGrid.addOrderQuantity('10')
		sizingGrid.addToCart()
		
		and: "go to shopping cart page"
		masterTemplate.doGoToCart()
		at CartPage
		
		then: "Look at cart content"
		
		cartTemplate.checkItemNameExists()
		cartTemplate.checkItemStyleExists()
		cartTemplate.checkItemColorExists()
		cartTemplate.checkItemPriceExists()
		cartTemplate.checkItemQuantityExists()
		cartTemplate.checkItemTotalExists()
		
		where:
		user << [levisUser]
	}
	
	@Ignore
	def "Remove product from checkout Page"(){
		setup:
		loginAsUserAndGoToQuickOrder(user)
		doSearch('00501-1615')
		sizingGrid.waitForSizingGridLoadedCompletely()
		sizingGrid.addOrderQuantity('1')
		sizingGrid.addToCart()
		masterTemplate.doGoToCart()
		
		Thread.sleep(1000);
		
		when: "At Check out page"
		at CartPage
		Thread.sleep(1000);
		
		then: "Remove the product from the page and you should get a message cart is empty"
		cartTemplate.doRemove()
		waitFor(5){
			$('div.global-nav ul.global-nav-list').find("a", href: contains("/logout"))
		}
		
		
		where:
		user << [levisUser]
	}
	
	@Ignore
	def "Place an order from Quick Order Page"(){
		setup: 
		loginAsUserAndGoToQuickOrder(user)
		doSearch('00501-1615')
		sizingGrid.waitForSizingGridLoadedCompletely()
		sizingGrid.addOrderQuantity('1')
		sizingGrid.addToCart()
		
		when: "Checking out from quick order page"
		checkOut.doCheckOut()
		
		then: "Place an order"
		at CheckOutPage
		doPlaceOrder()
		at OrderConfirmationPage
		
		where:
		user << [levisUser]
	}
	
	@Ignore
	def "user that does not hold customer rights tries to place an order from quick order page"(){
		setup: "Log in"
		loginAsUserAndGoToQuickOrder(user)
		
		when: "add product to cart"
		doSearch('00501-1615')
		sizingGrid.waitForSizingGridLoadedCompletely()
		sizingGrid.addOrderQuantity('1')
		sizingGrid.addToCart()
		
		then: "check out"
		checkOut.doCheckOut()
		at CartPage
		checkAlertMessage1()
		checkAlertMessage2()
		
		where:
		user << [admin1]
	}
		
	@Ignore
	def "Quick order with multiple product ID's"(){
		setup:
			login(user)
			at HomePage
			masterTemplate.clickQuickOrder()
			
		when: "At QuickOrder Page"
			at QuickOrderPage
			
		then: "Search Multiple ID's"
			doMultipleSearch(product1, product2)
			getResultSize() == 2
			
		and: "Add first product to cart"	
			productSizingGrids[0].addOrderQuantity('1')
			productSizingGrids[0].addToCart()
			
		and: "Add second product to cart"
			productSizingGrids[1].addOrderQuantity('1')
			productSizingGrids[1].addToCart()
		
		and: "Go to cart page"
			gotoCartPage()
			//FIXME popup problem on page
						
		where:
			product1 | product2 | user
			"05527-0458" | "00501-1615" | TestDataCatalog.getALevisUser()
	}
}