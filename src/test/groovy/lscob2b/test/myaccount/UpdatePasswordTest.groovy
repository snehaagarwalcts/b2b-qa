package lscob2b.test.myaccount

import de.hybris.geb.page.hac.console.ImpexImportPage
import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.myaccount.MyAccountPage
import lscob2b.pages.myaccount.admin.UpdatePasswordPage
import spock.lang.IgnoreIf

class UpdatePasswordTest extends GebReportingSpec {
	
	//@IgnoreIf({System.getProperty("geb.browser") == "internet explorer"})
	def "load impex [/impex/UpdateUsers.impex]"() {
		setup:
		browser.go(browser.config.rawConfig.hacUrl + "/j_spring_security_logout")
		browser.go(browser.config.rawConfig.hacUrl)
		at de.hybris.geb.page.hac.LoginPage
	
		doLogin(browser.config.rawConfig.hacUsername, browser.config.rawConfig.hacPassword)
		at de.hybris.geb.page.hac.HomePage
			
		when: "at HAC home page"
		at de.hybris.geb.page.hac.HomePage
			
		and: "go to Console>ImpexImport page"
		browser.go(browser.config.rawConfig.hacUrl + "console/impex/import")
		
		and: "at ImpexImport page"
		waitFor { ImpexImportPage}
		at ImpexImportPage
		
		and: "load impex in HAC"
		setLegacyMode(true)
		importTextScript(getClass().getResource('/impex/UpdateUsers.impex').text)
			
		then: "check import result"
		checkNotification()
		
		cleanup:
		Thread.sleep(500)
		menu.logout()
	}
	
	/**
	 * US BB-746 Password rules
	 * TC BB-860 Verify Password Update functionality with non-compliant password
	 */
	def "Check Password Update functionality with non-compliant password"(){
		setup:
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.clickMyAccount()
		
		when: "at My account page"
		at MyAccountPage
		
		then: "click on change password link"
		changeYourPassword.click()
		
		when: "at update Password"
		at UpdatePasswordPage
		
		and: "Try to update with a password that does not comply with password requirements"
		doUpdatePassword(user.password,uncompliantPassword)
		
		then: "check error message"
		errorMessage.displayed
		
		when: "Login with the same non-compliant password"
		PageHelper.gotoPageLogout(browser, baseUrl)
		at LoginPage
		doLogin(user.email,uncompliantPassword)
		
		then: "We should still be at Login page"
		at LoginPage
		
		and: "We should see an error message"
		errorMessage.displayed
		
		where:
		user | uncompliantPassword
		UserHelper.getUpdatePasswordUser() | UserHelper.UNCOMPLIANT_PASSWORD
	}
	
	/**
	 * US BB-746 Password rules
	 * TC BB-861 Verify Password Update functionality with a compliant password
	 */
	def "Check Password Update functionality with compliant password"(){
		setup:
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.clickMyAccount()
		
		when: "at My account page"
		at MyAccountPage
		
		then: "click on change password link"
		changeYourPassword.click()
		
		when: "at update Password"
		at UpdatePasswordPage
		
		and: "Try to update password "
		doUpdatePassword(user.password,compliantPassword)
		
		then: "Password Successfully Updated message appears "
		masterTemplate.noteMessage.displayed
		
		when: "Login with new password"
		PageHelper.gotoPageLogout(browser, baseUrl)
		at LoginPage
		doLogin(user.email,compliantPassword)
		
		then: "at home page"
		at HomePage
		
		where:
		user | compliantPassword
		UserHelper.getUpdatePasswordUser() | UserHelper.COMPLIANT_PASSWORD
	}	
}
