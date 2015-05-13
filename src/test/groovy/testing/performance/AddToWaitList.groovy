package testing.performance

import de.hybris.geb.page.hac.console.ImpexImportPage
import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.LoginPage
import lscob2b.pages.productdetails.ProductDetailsPage
import lscob2b.test.data.User

class AddToWaitList extends GebReportingSpec{
	
	def "load impex [/impex/OutOfStock_WaitList.impex]"() {
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
			importTextScript(getClass().getResource('/impex/OutOfStock_WaitList.impex').text)
			
		then: "check import result"
			checkNotification()
	}
	
	def "Add products to waitlist for multiple users"(){
		setup:
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
		login(user)
		browser.go(baseUrl + "p/" + productcode)
		
		when: "at Product Details Page"
		at ProductDetailsPage
		
		then: "Add product to waitlist"
		addOutOfStockQuantityToWaitList(2)
		
		where:
		user | productcode
		new User(email: 'perf@superuser-1', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[0]
		new User(email: 'perf@superuser-2', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[1]
		new User(email: 'perf@superuser-3', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[2]
		new User(email: 'perf@superuser-4', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[3]
		new User(email: 'perf@superuser-5', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[4]
		new User(email: 'perf@superuser-6', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[5]
		new User(email: 'perf@superuser-7', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[6]
		new User(email: 'perf@superuser-8', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[7]
		new User(email: 'perf@superuser-9', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[8]
		new User(email: 'perf@superuser-10', password:'Levis#101') | ProductHelper.getWaitlistBulkProduct()[9]
	}
	
	def "load impex [/impex/UpdateInStockLowerQuantity.impex]"() {
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
			importTextScript(getClass().getResource('/impex/UpdateInStockLowerQuantity.impex').text)
			
		then: "check import result"
			checkNotification()
	}

}
