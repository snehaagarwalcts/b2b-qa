package lscob2b.test.footerLinks

import spock.lang.IgnoreRest;
import spock.lang.Stepwise;
import lscob2b.data.PageHelper;
import lscob2b.data.UserHelper;
import lscob2b.pages.HomePage;
import lscob2b.pages.LoginPage;
import lscob2b.pages.SiteMapPage;
import geb.spock.GebReportingSpec;

class SiteMapTest extends GebReportingSpec{
	
	def setup(){
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
	}	

	/**
	 * US BB-594 Site Map
	 * TC BB-840 Automated Test Case: Check the content of SiteMap Page
	 */
	def "Check the content of SiteMap Page"(){
		setup:
		login(user)
		
		when:"at Home Page"
		at HomePage		
		
		then: "click on SiteMap Link"
		waitFor{ masterTemplate.siteMapLink.displayed }
		masterTemplate.siteMapLink.click()
		
		when: "at SiteMap page"
		at SiteMapPage
		
		then: "check all the Categories"		
		!menLink.empty
		!womenLink.empty
		!myAccountLink.empty	
		
		and: "check sub-categories under all the Categories"	
			//check all the subLinks
			for(def i=0; i<subCategoryLinksize.size(); i++){
				assert !subCategoryLink(i).empty				
			}
			//Account Section Check
			for(link in pageLinks) {
				assert hasPageLink(link)
			}
								
		where:
		user | pageLinks
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | ["profile", "address-book", "manage-users", "orders", "balance"]
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_ADMIN) | ["profile", "address-book", "manage-users" ]
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | ["profile", "address-book", "orders" ]
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_FINANCE) | ["profile", "address-book", "balance"]	
	}
}
