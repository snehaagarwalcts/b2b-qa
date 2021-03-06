package lscob2b.test.footerLinks

import lscob2b.data.PageHelper;
import lscob2b.data.UserHelper;
import lscob2b.pages.HomePage;
import lscob2b.pages.LoginPage;
import lscob2b.pages.TermsConditionsAndPrivacyPage;
import geb.spock.GebReportingSpec;

class TermsConditionsAndPrivacyTest extends GebReportingSpec{
		
	def setup(){
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
	}

	/**
	 * TC BB-858 Automated Test Case: Verify the URL of Terms and Conditions Page
	 */
	def "Check user navigates to correct TermsAndConditions URL"(){
		setup:
		login(user)
		
		when:"at Home Page"
		at HomePage
		
		and: "click on TermsAndConditions Link"
		waitFor{ masterTemplate.termsAndConditionsLink.displayed }
		masterTemplate.termsAndConditionsLink.click()
		browser.go(baseUrl+link)
		
		then:"check that user navigates to TermsAndConditions page"
		at TermsConditionsAndPrivacyPage
		
		where:
		user | link
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | "termsAndConditions"
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | "termsAndConditions"
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | "termsAndConditions"
	}

}
