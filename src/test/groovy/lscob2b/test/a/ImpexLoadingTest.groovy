package lscob2b.test.a

import geb.spock.GebReportingSpec
import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.Stepwise
import de.hybris.geb.page.hac.HomePage
import de.hybris.geb.page.hac.LoginPage
import de.hybris.geb.page.hac.console.ImpexImportPage


@Stepwise
@IgnoreIf({System.getProperty("geb.browser") == "internet explorer"})
class ImpexLoadingTest extends GebReportingSpec {

	def "login at HAC"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
			
		when: "at HAC login page"
			at LoginPage
			
		and: "do login"	
			doLogin(browser.config.rawConfig.hacUsername, browser.config.rawConfig.hacPassword)
			
		then: "at HAC home page"
			at HomePage	
	}
	
	def "load impex [/impex/Users.impex]"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
			
		when: "at HAC home page"
			at HomePage
			
		and: "go to Console>ImpexImport page"
			browser.go(browser.config.rawConfig.hacUrl + "console/impex/import")
		
		and: "at ImpexImport page"
		waitFor { ImpexImportPage}
			at ImpexImportPage
		
		and: "load impex in HAC"
			setLegacyMode(true)
			importTextScript(getClass().getResource('/impex/Users.impex').text)
			
		then: "check import result"
			checkNotification()
	}
	
	def "load impex [/impex/LevisProducts.impex]"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
			
		when: "at HAC home page"
			at HomePage
			
		and: "go to Console>ImpexImport page"
			browser.go(browser.config.rawConfig.hacUrl + "console/impex/import")
		
		and: "at ImpexImport page"
			at ImpexImportPage
		
		and: "load impex in HAC"
			importTextScript(getClass().getResource('/impex/LevisProducts.impex').text)
			
		then: "check import result"
			checkNotification()
	}
	
	def "load impex [/impex/OutOfStock.impex]"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
			
		when: "at HAC home page"
			at HomePage
			
		and: "go to Console>ImpexImport page"
			browser.go(browser.config.rawConfig.hacUrl + "console/impex/import")
		
		and: "at ImpexImport page"
			at ImpexImportPage
		
		and: "load impex in HAC"
			importTextScript(getClass().getResource('/impex/OutOfStock.impex').text)
			
		then: "check import result"
			checkNotification()
	}
	
	def "load impex [/impex/Tessi_Test_Order.impex]"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
			
		when: "at HAC home page"
			at HomePage
			
		and: "go to Console>ImpexImport page"
			browser.go(browser.config.rawConfig.hacUrl + "console/impex/import")
		
		and: "at ImpexImport page"
			at ImpexImportPage
		
		and: "load impex in HAC"
			setLegacyMode(true)
			importTextScript(getClass().getResource('/impex/Tessi_Test_Order.impex').text)
			
		then: "check import result"
			checkNotification()
	}
	
	def "logout from HAC"() {
		setup:
			browser.go(browser.config.rawConfig.hacUrl)
		
		when: "at HAC home page"
			at HomePage
	
		and: "logout from HAC"	
			menu.logout()
			
//		and: "go to HAC home page"	
//			browser.go(browser.config.rawConfig.hacUrl)
			
		then: "at login page"
			at LoginPage	
			
	}
}
