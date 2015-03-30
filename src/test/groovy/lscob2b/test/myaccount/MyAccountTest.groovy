package lscob2b.test.myaccount

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.myaccount.AddressBookPage
import lscob2b.pages.myaccount.MyAccountPage
import lscob2b.pages.myaccount.OrderHistoryPage
import lscob2b.pages.myaccount.ProfilePage
import lscob2b.pages.myaccount.admin.ManageUsersPage
import spock.lang.Ignore
import spock.lang.IgnoreRest

class MyAccountTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
	}

	/**
	 * TC BB-341 Automated Test Case: Any User should see the "My Account" link in the 
	 * right-side of the header section of the Application, that should redirect the user to the My Account Home Page.
	 * TC BB-362 Automated Test Case: Validate the Breadcrumb Trail from the My Account - "My Account" Page.
	 * TC BB-363 Automated Test Case: Validate the "My Account" Page common content for any user.
	 * TC BB-364 Automated Test Case: Validate the "My Account" Page extra content for B2BAdministrator.
	 * TC BB-365 Automated Test Case: Validate the "My Account" (All Pages) - Your Account left navigation section common content for any user.
	 * TC BB-366 Automated Test Case: Validate the "My Account" (All Pages) - Your Account left navigation section to contain extra content for B2BAdministrator.	
	 */
	def "Check [MyAccountPage] structure"() {
		when: "at LoginPage"
			at LoginPage	
			
		and: "do login"
			login(user) 

		then: "at homepage"	
			at HomePage
		
		and: "check link my-account"
			masterTemplate.myAccountLink.displayed		//TC BB-341
			
		when: "click to my-account"	
			masterTemplate.myAccountLink.click()
			
		and: "at my-account page"
			at MyAccountPage
			
		then: "check breadcrumb"						//TC BB-362
			masterTemplate.breadCrumbs.size() == 2
			assert !masterTemplate.breadCrumbHref("/").empty
			assert masterTemplate.breadCrumbActive.text() == "MY ACCOUNT"
			
		and: "check menu links"
			menuLinks.size() == masterTemplate.getMyAccountSubLinks().size()
			for(link in menuLinks) {
				assert !(masterTemplate.getMyAccountSubLink(link)).empty
			}
			
		and: "check page links"
			for(link in pageLinks) {
				assert hasPageLink(link)
			}
			
		where:
			user | menuLinks | pageLinks
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | ["profile", "address-book", "manage-users", "orders", "balance"] | ["profile", "update-password", "address-book", "create", "manage-users", "orders", "balance"]
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | ["profile", "address-book", "manage-users" ] | ["profile", "update-password", "address-book", "create", "manage-users"] 
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | ["profile", "address-book", "orders" ] | ["profile", "update-password", "address-book", "orders"]
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | ["profile", "address-book", "balance"] | ["profile", "update-password", "address-book", "balance"]		
	}

	/**
	 * TC BB-367 Automated Test Case: Validate the content of the My Account - "User Profile" Page for any user.
	 * TC BB-368 Automated Test Case: Validate the Breadcrumb Trail from the My Account - "User Profile" Page.
	 */
	def "Check [ProfilePage] structure"() {
		setup:
			at LoginPage
			login(user)
			
			at HomePage
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_PROFILE)
//			masterTemplate.getMyAccountSubLink(PageHelper.PAGE_PROFILE).click()
		
		when: "At profile page"
			at ProfilePage

		then: "Correct sections/links should be visible"
			checkProfileDataExists()
			checkUpdatePersonalDetailsLinkExists()
			checkChangeYourPasswordLinkExists()

		and: "Check breadcrumps"
			masterTemplate.breadCrumbs.size() == 3
			assert !masterTemplate.breadCrumbHref("/").empty
			assert !masterTemplate.breadCrumbHref("my-account").empty
			assert masterTemplate.breadCrumbActive.text() == "PROFILE"
			
		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) |_
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) |_
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) |_
	}

	/**
	 * TC BB-426 Automated Test Case: Validate the Breadcrumb Trail from the My Account - "Address Book" Page.
	 */
	def "Check [AddressBookPage] structure"(){
		setup:
			at LoginPage
			login(user)
			
			at HomePage
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_ADDRESS_BOOK)
//			masterTemplate.getMyAccountSubLink(PageHelper.PAGE_ADDRESS_BOOK).click()

		when: "At Address Book page"
			at AddressBookPage

		then: "Check breadcrumps"
			masterTemplate.breadCrumbs.size() == 3
			assert !masterTemplate.breadCrumbHref("/").empty
			assert !masterTemplate.breadCrumbHref("my-account").empty
			assert masterTemplate.breadCrumbActive.text() == "ADDRESS BOOK"

		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) |_
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) |_
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) |_
	}

	/**
	 * TC BB-424 Automated Test Case: Validate the content of the My Account - "Manage Users" Page for b2badministrator user.
	 * TC BB-427 Automated Test Case: Validate the Breadcrumb Trail from the My Account - "Manage Users" Page.
	 */
	def "Check [ManageUserPage] structure"(){
		setup:
			at LoginPage
			login(user)			
			at HomePage
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_MANAGE_USERS)
		
		when: "At ManageUser page"
			at ManageUsersPage	
			
		then: "Check breadcrumbs"
			masterTemplate.breadCrumbs.size() == 3
			assert !masterTemplate.breadCrumbHref("/").empty
			assert !masterTemplate.breadCrumbHref("my-account").empty
//			assert masterTemplate.breadCrumbActive.text() == "MANAGE USERS"		//FIXME missing css class 'active'
			
		and: "Check page element"
			!buttonCreateNewUser.empty
			//TODO check for others elements?
			
		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | _
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | _
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_ADMIN) | _
	}
	
	/**
	 * BB-428 Automated Test Case: Validate the Breadcrumb Trail from the My Account - "Order History" Page.
	 */
	def "Check [OrderHistoryPage] structure"(){
		setup:
			at LoginPage
			login(user)
			
			at HomePage
			PageHelper.gotoPage(browser,baseUrl,PageHelper.PAGE_ORDER_HISTORY)
			//masterTemplate.getMyAccountSubLink(PageHelper.PAGE_ORDER_HISTORY).click()

		when: "At Order History page"
			at OrderHistoryPage

		then: "Check breadcrumbs"
			masterTemplate.breadCrumbs.size() == 3
			assert !masterTemplate.breadCrumbHref("/").empty
			assert !masterTemplate.breadCrumbHref("my-account").empty
			assert masterTemplate.breadCrumbActive.text() == "ORDER HISTORY"

		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}
}
