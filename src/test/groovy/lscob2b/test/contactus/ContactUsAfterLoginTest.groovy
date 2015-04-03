package lscob2b.test.contactus

import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.ContactUsPage
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import geb.spock.GebReportingSpec

class ContactUsAfterLoginTest extends GebReportingSpec {
	
	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
	}

	/**
	 * US BB-585 Contact us after login
	 * TC BB-784 Contact us after login
	 */
	def "Make sure requried fields are prepoulated"(){
		setup:
		to LoginPage
		at LoginPage
		login(user)

		when: "At HomePage"
		at HomePage

		then: "Click on contact us"
		masterTemplate.doContactUs()

		and: "at Contact Us page"
		at ContactUsPage

		then: "Compare user data"
		//titleText == "Ms." //user.title
		firstNameAfterLogin.text() == user.name
		lastNameAfterLogin.text()  == user.surname
		emailTextAfterLogin.text()  == user.email
		companyNameAfterLogin.text()  == user.companyname
		customerNumberAfterLogin.text()  == user.customernumber
		//country == "United Kingdom"//user.country

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | _
	}
}
