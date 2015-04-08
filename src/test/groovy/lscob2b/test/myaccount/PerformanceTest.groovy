package lscob2b.test.myaccount

import geb.spock.GebReportingSpec;
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

class PerformanceTest extends GebReportingSpec {

	
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
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			ProductHelper.getOrderHistoryProduct(ProductHelper.BRAND_LEVIS) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
		}
}
