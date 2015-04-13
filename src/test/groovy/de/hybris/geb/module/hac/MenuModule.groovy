package de.hybris.geb.module.hac

import geb.Module

class MenuModule extends Module {

	static content = {

		//Console Menu
		
		console { $("a#console") }

		consoleImpexImport { $("a", href: endsWith("/console/impex/import")) }
		
	}
	
	def goToConsoleImpexImport() {
		console.click();
		consoleImpexImport.click()
	}
		
	def logout() {
		//Different version of logout button on each environment
//		if(System.getProperty("geb.env") == null || System.getProperty("geb.env")== "local") {
//			waitFor { !$("input", value:"logout").empty }
//			$("input", value:"logout").click()
//		} else {
			waitFor { !$("div#loginInfo").empty }
			$("div#loginInfo").click()
			browser.go(browser.config.rawConfig.hacUrl + "/j_spring_security_logout")
		}
//	}
}
