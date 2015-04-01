package lscob2b.test.footerLinks

import lscob2b.data.PageHelper;
import lscob2b.data.UserHelper;
import lscob2b.pages.HomePage;
import lscob2b.pages.LoginPage;
import geb.spock.GebReportingSpec;

class FooterLinksTest extends GebReportingSpec{
		
	def setup(){
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
	}
	
	/**
	 * TC BB-853 Automated Test Case: Verify AboutUs Link in Footer section
	 * TC BB-855 Automated Test Case: Verify Privacy Policy Link in Footer section
	 * US BB-594 Site Map-->TC BB-839 Automated Test Case: Verify Site Map Link in Footer section
	 * TC BB-857 Automated Test Case: Verify Terms and Conditions Link in Footer section
	 */
	def "Make sure AboutUs, Privacy Policy, SiteMap, Terms and Conditions Links are present for all the users"(){
		setup:
		login(user)
		
		when:"at Home Page"
		at HomePage
		
		then:"check all the Links"
		waitFor{ masterTemplate.aboutUsLink.displayed }
		waitFor{ masterTemplate.privacyPolicyLink.displayed }
		waitFor{ masterTemplate.siteMapLink.displayed }	
		waitFor{ masterTemplate.termsAndConditionsLink.displayed }
				
		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_ADMIN) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_FINANCE) | _
	}
}
