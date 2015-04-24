package lscob2b.test.footerLinks

import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.PrivacyPolicyPage
import geb.spock.GebReportingSpec

class PrivacyPolicyTest extends GebReportingSpec{
		
		def setup(){
			PageHelper.gotoPageLogout(browser, baseUrl)
			to LoginPage
			at LoginPage
		}

		/**
		 * TC BB-856 Automated Test Case: Verify the URL of Privacy Policy Page
		 */
		def "Check user navigates to correct PrivacyPolicy URL"(){
			setup:
			login(user)
			
			when:"at Home Page"
			at HomePage
			
			and: "click on PrivacyPolicy Link"
			waitFor{ masterTemplate.privacyPolicyLink.displayed }
			masterTemplate.privacyPolicyLink.click()
			browser.go(baseUrl+link)
			
			then:"check that user navigates to PrivacyPolicy page"
			at PrivacyPolicyPage
			
			where:
			user | link
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | "privacyPolicy"
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_ADMIN) | "privacyPolicy"
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | "privacyPolicy"
		}

}
