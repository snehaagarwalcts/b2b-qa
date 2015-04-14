package lscob2b.test.category

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.LoginPage
import lscob2b.pages.category.KeyLookPage

public class KeylookTest extends GebReportingSpec {

	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
	}
	
	/**
	 * BB-648 Automated test case: Key Look page
	 */
	def "Test Key-Look page"() {
		setup:
			to LoginPage
			login(user)		
			browser.go(baseUrl + link)
											
		when: "At KeyLook page"
			at KeyLookPage
			
		then: "Check product info data"		
			keylookHeroTitle.text() != ""
			keylookHeroDescription.text() != ""
		
		and: "Check related product data"
			for(item in keylookItems) {
				item.itemName.text() != ""
				item.style.text() != ""
				item.color.text() != ""
				item.priceWholesale.text() != ""
			}	
			
		where:
			user | link
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | "keylook/levisKeyLook1" 
	}			
}
