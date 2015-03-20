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
import spock.lang.Ignore
import spock.lang.IgnoreRest;
 
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
		assert ordersFoundLabel.text() - ~/\d/ == " ORDERS FOUND"
		assert sortByLabel.text()=="SORT BY:"
		assert datePlacedLabel.text()=="DATE PLACED"
		assert orderNumberLabel.text()=="ORDER NUMBER"
		assert orderStatusLabel.text()=="ORDER STATUS"
		assert orderTypeLabel.text()=="ORDER TYPE"
		assert totalLabel.text()=="TOTAL"
		assert orderSourceLabel.text()=="ORDER SOURCE"
		assert invoiceLabel.text()=="INVOICE"
		
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
	@IgnoreRest
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
			clearForm()
			searchByOrderNumber(currentOrder.number)

		then: "Check unique result"				
			checkUniqueResult()

		when: "Test search by: order number and order source b2b"
			clearForm()
			searchByOrderNumberAndOrderSource(currentOrder.number, true, false, false, false, false)
			//searchByOrderNumberAndOrderSource(currentOrder.number)
			
		then: "Check unique result"
			checkUniqueResult()

		when: "Test search by: order number and order source not b2b"
			clearForm()
			searchByOrderNumberAndOrderSource(currentOrder.number, false, true, true, true, true)
		
		then: "Check empty result"
			checkEmptyResult()
			
		when: "Test search by: order number and order type at once"
			clearForm()
			searchByOrderNumberAndOrderType(currentOrder.number, true, false)
			
		then: "Check unique result"
			checkUniqueResult()
			
		when: "Test search by: order number and order type pre book"
			clearForm()
			searchByOrderNumberAndOrderType(currentOrder.number, false, true)
			
		then: "Check empty result"
			checkEmptyResult()

		when: "Test search by: order number and order date last 30d"
			clearForm()
			searchByOrderNumberAndOrderDate(currentOrder.number, true, false, false)
			
		then: "Check unique result"
			checkUniqueResult()

		when: "Test search by: order number and order date last 90d"
			clearForm()
			searchByOrderNumberAndOrderDate(currentOrder.number, false, true, false)
			
		then: "Check unique result"
			checkUniqueResult()
			

		when: "Test search by: order number and order date last year"
			clearForm()
			searchByOrderNumberAndOrderDate(currentOrder.number, false, false, true)
		
		then: "Check unique result"
			checkUniqueResult()
			

		when: "Test search by: order number and order status submitted"
			clearForm()
			searchByOrderNumberAndOrderStatus(currentOrder.number, true, false, false)
		
		then: "Check unique result"
			checkUniqueResult()
		

		when: "Test search by: order number and order status not submitted"
			clearForm()
			searchByOrderNumberAndOrderStatus(currentOrder.number, false, true, true)
		
		then: "Check empty result"
		checkEmptyResult()

		where:
		productCode | user | _
		ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) |	UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}


}
