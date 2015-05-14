package testing.performance

import de.hybris.geb.page.hac.console.ImpexImportPage
import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.productdetails.ProductDetailsPage
import lscob2b.test.data.User

class AddToWaitList extends GebReportingSpec{
	
	def static targetProductCode = ProductHelper.getWaitlistBulkProduct()
	
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
			
		cleanup:
			Thread.sleep(500)
			menu.logout()
	}
	
	def "Add products to waitlist for multiple users"(){
		setup:
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
		login(new User(email: 'perf@superuser-'+i, password:'Levis#101'))

		when: "At HomePage"
		at HomePage
		
		then: "Add product to waitlist"
		for(productCode in targetProductCode){
			browser.go(baseUrl + "p/" + productCode)
			at ProductDetailsPage
			addOutOfStockQuantityToWaitList(1)
		  }	
		
		where:
		i << (1..100)
	}
	
	def "load impex [/impex/UpdateInStock_WaitList.impex]"() {
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
			importTextScript(getClass().getResource('/impex/UpdateInStock_WaitList.impex').text)
			
		then: "check import result"
			checkNotification()
			
		cleanup:
			Thread.sleep(500)
			menu.logout()
	}

}
