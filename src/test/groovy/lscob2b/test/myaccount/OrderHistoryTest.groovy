package lscob2b.test.myaccount

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.myaccount.OrderDetailPage
import lscob2b.pages.myaccount.OrderHistoryPage
import lscob2b.pages.orderconfirmation.OrderConfirmationPage
import lscob2b.pages.productdetails.ProductDetailsPage
 
class OrderHistoryTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
	}

	def placeAnOrder(String productCode) {
		//Order Detail
		browser.go(baseUrl + "p/" + productCode)
		at ProductDetailsPage

		//Add To Cart
		addOrderQuantity("1")
		sizingGrid.buttonAddToCart.click()
		masterTemplate.goToCartLink.click()
		to CartPage
		
		//Cart
		at CartPage
		linkCheckout.click()

		//Checkout
		at CheckOutPage
		doPlaceOrder()

		//Order Details
		at OrderConfirmationPage
		return order.getOrder()
	}

	def goToOrderDetail(orderNumber) {
		browser.go(baseUrl + "my-account/order/" + orderNumber)
		at OrderDetailPage
	}

	/**
	 * Bug BB-604 Security Issue on "my-account/orders"
	 */
	def "Check denied access to OrderHistory for not [b2bcustomergroup]"() {
		setup:
			login(user)

		when: "At HomePage"
			at HomePage

		and: "Go to my-account/orders"
			browser.go(baseUrl + "my-account/orders")
		
		then:
		at HomePage

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | _
	}
	
	/**
	 * Bug BB-604 Security Issue on "my-account/orders"
	 */
	def "Check access to OrderHistory for [b2bcustomergroup]"() {
		setup:
			login(user)

		when: "At HomePage"
		at HomePage

		then: "Go to my-account/orders"
		browser.go(baseUrl + "my-account/orders")
		at OrderHistoryPage

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}
	
	def "Test clear functionality"() {
		setup:
			at LoginPage	
			login(user)		
			at HomePage
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_ORDER_HISTORY)
		
		when: "at OrderHistory page"
			at OrderHistoryPage

		and: "Click on all field"
			switchOnForm()

		and: "Click on clear button"
			clearButton.click()

		then: "Check form cleared"
			isFormClear()

		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * US BB-244 Link to Tessi to download an Invoice in PDF
	 * TC BB-893 Automated test: Link to Tessi to download an ivoice in pdf
	 */
	def "go to OrderHistory for [tessi user]"() {
		setup:
			login(user)

		when: "At HomePage"
		at HomePage

		then: "Go to my-account/orders"
		browser.go(baseUrl + "my-account/orders")
		
		when: "at order history page"
		at OrderHistoryPage
		
		and: "click on inovice number"
		clickOnInvoiceNumber()
		
		then: "we can go to another site to download pdf"
		waitFor {"#bandeauTop"}//Need to figure out the name of the site
		
		where:
		user | _
		UserHelper.getTessiUser() | _
	}
	
	/**
	 * US BB-38 As an admin customer user I want to set the default delivery address for my unit
	 * TC BB-425 Automated Test Case: Validate the content of the My Account - "User Order History" Page for any user.
	 */
	def "Test content of history"() {
		setup:
			login(user)

		when: "At HomePage"
		at HomePage

		then: "Go to my-account/orders"
		browser.go(baseUrl + "my-account/orders")
		
		when: "at order history page"
		at OrderHistoryPage
		
		then: "check content of order history page"
		ordersFoundLabel.displayed
		sortByLabel.displayed
		datePlacedLabel.displayed
		orderNumberLabel.displayed
		orderStatusLabel.displayed
		orderTypeLabel.displayed
		totalLabel.displayed
		orderSourceLabel.displayed
		invoiceLabel.displayed
		
		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}
	
	//TODO NOTE can't run last 3 tests as we shouldn't place an order	
	def "Test order creation in history"() {
		setup:
			at LoginPage
			login(user)		
			
		when: "At HomePage"
			at HomePage
			
		and: "Create a test order"	
			def currentOrder = placeAnOrder(productCode)
		
		and: "Go to order history"	
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_ORDER_HISTORY)
			
		then: "At OrderHistory page"
			at OrderHistoryPage

		when: "Check the test order in history"
			searchByOrderNumber(currentOrder.number)
			
		then: "Check for unique result"	
			checkUniqueResult()

		when: "Go to order detail"
			clickOnFirstOrder()

		then: "At OrderDetail page"
			at OrderDetailPage

		and: "Compare Orders"
			currentOrder.compare(order.getOrder())

		where:
			productCode | user | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-509 Automated test: User Can reorder from history page
	 */	
	//TODO NOTE can't run last 3 tests as we shouldn't place an order
	def "Test re-order functionality in history"() {
		setup:
		login(user)

		when: "At HomePage"
		at HomePage

		then: "Create a test order"
		def currentOrder = placeAnOrder(productCode)
		goToOrderDetail(currentOrder.number)

		when: "At OrderDetail page"
		at OrderDetailPage

		then: "Do a re-order"
		linkReOrder.click()

		when: "At checkout page"
		at CheckOutPage

		then: "Place the re-order"
		doPlaceOrder()

		when: "At OrderConfirmation page"
		at OrderConfirmationPage

		then: "Compare orders"
		currentOrder.compareWithoutNumber(order.getOrder())

		where:
		productCode | user | _
		ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) |	UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-601 Automated Test: Order Search
	 */	 
	//TODO NOTE can't run last 3 tests as we shouldn't place an order
	def "Test search functionality in history"() {
		setup:
			login(user)

		when: "At HomePage"
			at HomePage

		and: "Create a test order"
			def currentOrder = placeAnOrder(productCode)
			
		and:"Go to Order History"	
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_ORDER_HISTORY)

		then: "At OrderHistory page"
			at OrderHistoryPage

		when: "Test search by: order number"
			clearButton.click()
			searchByOrderNumber(currentOrder.number)

		then: "Check unique result"				
			checkUniqueResult()

		when: "Test search by: order number and order source b2b"
			clearButton.click()
			searchByOrderNumberAndOrderSource(currentOrder.number, true, false, false, false, false)
			
		then: "Check unique result"
			checkUniqueResult()

		when: "Test search by: order number and order source not b2b"
			clearButton.click()
			searchByOrderNumberAndOrderSource(currentOrder.number, false, true, true, true, true)
		
		then: "Check empty result"
			checkEmptyResult()
			
		when: "Test search by: order number and order type at once"
			clearButton.click()
			searchByOrderNumberAndOrderType(currentOrder.number, true, false)
			
		then: "Check unique result"
			checkUniqueResult()
			
		when: "Test search by: order number and order type pre book"
			clearButton.click()
			searchByOrderNumberAndOrderType(currentOrder.number, false, true)
			
		then: "Check empty result"
			checkEmptyResult()

		when: "Test search by: order number and order date last 30d"
			clearButton.click()
			searchByOrderNumberAndOrderDate(currentOrder.number, true, false, false)
			
		then: "Check unique result"
			checkUniqueResult()

		when: "Test search by: order number and order date last 90d"
			clearButton.click()
			searchByOrderNumberAndOrderDate(currentOrder.number, false, true, false)
			
		then: "Check unique result"
			checkUniqueResult()			

		when: "Test search by: order number and order date last year"
			clearButton.click()
			searchByOrderNumberAndOrderDate(currentOrder.number, false, false, true)
		
		then: "Check unique result"
			checkUniqueResult()		

		when: "Test search by: order number and order status submitted"
			clearButton.click()
			searchByOrderNumberAndOrderStatus(currentOrder.number, true, false, false)
		
		then: "Check unique result"
			checkUniqueResult()		

		when: "Test search by: order number and order status not submitted"
			clearButton.click()
			searchByOrderNumberAndOrderStatus(currentOrder.number, false, true, true)
		
		then: "Check empty result"
			checkEmptyResult()

		where:
		productCode | user | _
		ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) |	UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}


}
