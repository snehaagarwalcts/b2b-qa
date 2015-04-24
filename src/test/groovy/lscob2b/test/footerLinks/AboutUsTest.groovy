package lscob2b.test.footerLinks

import lscob2b.data.PageHelper;
import lscob2b.data.UserHelper;
import lscob2b.pages.HomePage;
import lscob2b.pages.LoginPage;
import geb.spock.GebReportingSpec;

class AboutUsTest extends GebReportingSpec{
		
	def setup(){
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
	}
	
	/**
	 * TC BB-854 Automated Test Case: Verify the URL of About Us Page
	 */
	def "Check user navigates to correct AboutUs URL"(){
		setup:
		login(user)
		
		when:"at Home Page"
		at HomePage
		
		and: "click on AboutUs Link"
		waitFor{ masterTemplate.aboutUsLink.displayed }
		masterTemplate.aboutUsLink.click()
		browser.go(PageHelper.PAGE_ABOUT_US)
		
		then:"check that user navigates to AboutUs page"
		waitFor { title == "Who We Are | Levi Strauss" }
		browser.currentUrl==PageHelper.PAGE_ABOUT_US
		
		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}
}
