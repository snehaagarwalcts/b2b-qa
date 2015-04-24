package lscob2b.test.myaccount

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.cart.CartPage
import lscob2b.pages.checkout.CheckOutPage
import lscob2b.pages.myaccount.admin.EditUserDetailsPage
import lscob2b.pages.myaccount.admin.ManageUsersPage
import lscob2b.pages.myaccount.admin.ViewUserDetailsPage
import lscob2b.pages.quickorder.QuickOrderPage

class ManageUserTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
	}

	/**
	 * Bug BB-523 Security Problem on "my-account""
	 * TC BB-605 Automated Testcase: Test security access on "my-account/manage-users"
	 */
	def "Check access to ManageUserPage for [b2badmingroup]"() {
		setup:
		login(user)

		when: "At HomePage"
		at HomePage

		and: "Go to my-account/manage-users"
		masterTemplate.clickMyAccount()

		then: "At ManageUsers page"
		at ManageUsersPage

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_ADMIN) | _
	}

	/**
	 * Bug BB-523 Security Problem on "my-account""
	 * TC BB-605 Automated Testcase: Test security access on "my-account/manage-users"
	 */
	def "Check denied access to ManageUserPage for not [b2badmingroup]"() {
		setup:
		login(user)

		when: "At HomePage"
		at HomePage

		and: "Go to my-account/manage-users"
		PageHelper.gotoPage(browser, baseUrl, PageHelper.PAGE_MANAGE_USERS)

		then: "Redirect to home page"
		at HomePage

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_FINANCE) | _
	}

	/**
	 * TC BB-507 Automated Test Case: The admin customer user for a B2B group should be 
	 * able to assign a default delivery address for all customers of the group.
	 */
	def "Change default delivery address"() {
		setup:
		login(loginUser)
		at HomePage
		PageHelper.gotoPageViewUserDetail(browser, baseUrl, targetUser.email)

		when: "At UserDetail page"
		at ViewUserDetailsPage

		and: "Edit current user"
		def currentUser = userDetails.getUser()
		userDetails.editUserButton.click()

		then: "At EditUserDetail page"
		at EditUserDetailsPage

		when: "At EditUserDetail page"
		at EditUserDetailsPage

		and: "Change delivery address"
		userDetails.changeDefaultDeliveryAddress()

		and: "Save User"
		userDetails.saveButton.click()

		then: "At UserDetail page"
		at ViewUserDetailsPage

		//This might be the issue
		and: "Check updated delivery address"
		currentUser.address != userDetails.getUser().address

		where:
		loginUser | targetUser
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	}

	/**
	 * US BB-501 BB-38 Remove default delivery address
	 * TC BB-778 Remove default delivery address
	 */
	def "Remove default delivery address"() {
		setup:
		login(loginUser)
		at HomePage
		PageHelper.gotoPageViewUserDetail(browser, baseUrl, targetUser.email)

		when: "At UserDetail page"
		at ViewUserDetailsPage

		and: "Edit current user"
		def currentUser = userDetails.getUser()
		userDetails.editUserButton.click()

		then: "At EditUserDetail page"
		at EditUserDetailsPage

		when: "At EditUserDetail page"
		at EditUserDetailsPage

		and: "Remove delivery address"
		userDetails.removeDefaultDeliveryAddress()

		and: "Save User"
		userDetails.saveButton.click()

		then: "At UserDetail page"
		at ViewUserDetailsPage

		and: "Check updated delivery address"
		currentUser.address != userDetails.getUser().address

		where:
		loginUser | targetUser
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)
	}
	
	/*this test continues from step above. after removing the default address you should see alternative address select*/
	def "sign in with levis customer and check alternative delivery address displayed"(){
		setup:
		login(user)
		at HomePage
		
		masterTemplate.clickQuickOrder()
		at QuickOrderPage
		
		when:"at quickorder page"
		at QuickOrderPage

		and: "search for a product"
		doSearch(product, true)
		at QuickOrderPage

		and: "add item to cart"
		waitFor { !productSizingGrids.empty }
		addLimitedStockQuantityToCart(0,1)
		masterTemplate.waitForSometime()

		then:"click checkout"
		checkOutLink.click() //issue with firefox
		to CheckOutPage

		when: "at Checkout page"
		at CheckOutPage
		masterTemplate.waitForSometime()
		
		then: "check alternative delivery address displayed"
		alternateDeliveryAddressSelect.displayed
		
		and: "Go to CartPage and remove product"
		to CartPage
		at CartPage
		assert removeExistingProducts()
		
		where:
		user | product
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | ProductHelper.getQuickOrderProduct(ProductHelper.BRAND_LEVIS)[0]
	}
}
