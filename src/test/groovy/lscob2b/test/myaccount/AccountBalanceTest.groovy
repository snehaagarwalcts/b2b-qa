package lscob2b.test.myaccount

import geb.spock.GebReportingSpec
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.myaccount.AccountBalancePage
import lscob2b.pages.myaccount.MyAccountPage

class AccountBalanceTest extends GebReportingSpec {
	
	def setup() {
		to LoginPage
	}

	def cleanup() {
		masterTemplate.doLogout()
	}

	def loginAndGoToPage(user) {
		login(user)
		
		at HomePage
		masterTemplate.clickMyAccount()
				
		at MyAccountPage
		accountBalanceLink.click()
	}
	
	/**
	 * TC BB-799 Text for Account Balance
	 */	
	def "Check the content of the Account balance page"(){
		setup: 
		loginAndGoToPage(user)
		
		when: "At Account Balance page"
		at AccountBalancePage
		
		then: "You Should see the text"		//TODO Dipen check also if balance has value > 0
		checkAccountBalanceExists()		
		checkTotalBalanceExists()
		checkTotalOverdueExists()
		checkCreditLimitExists()
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_FINANCE) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_SUPER) | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_FINANCE) | _
	}
}
