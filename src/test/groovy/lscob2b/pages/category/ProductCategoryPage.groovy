package lscob2b.pages.category

import lscob2b.modules.MasterTemplate
import geb.Page
import groovy.lang.MetaClass;

class ProductCategoryPage extends Page {

	static at = {
		waitFor { browser.currentUrl.contains("Categories") }
	}

	static content = {
		
		masterTemplate {module MasterTemplate}
		
		firstProductLink {$("a.productMainLink", 0)}
		
		keylookItems { $("div#keylook_slider ul li.org") }
		
		keylookLink { index -> keylookItems[index].find("a") }
		
	}

	def clickFirstProductLink() {
		firstProductLink.click()
	}
	
}
