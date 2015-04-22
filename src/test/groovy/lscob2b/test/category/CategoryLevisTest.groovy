package lscob2b.test.category

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage

class CategoryLevisTest extends GebReportingSpec {

	def setupSpec(){
		PageHelper.gotoPageLogout(browser, baseUrl)
		
		to LoginPage
		login(UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER))
	}
	
	/**
	 * US BB-34 As product owner I want to see SAP categories in hybris
	 * TC BB-372 Automated Test Case: Validate the existance of the Levis Company Categories and Subcategories for the Spring-Summer 2015 Season for each brand in the Application StoreFront.
	 */
	def "Levis categories should be displayed"(){
		when: "at home page"
			at HomePage
			
		and: "mouse over category"
			masterTemplate.categoryLink(category).jquery.mouseover()     //Issue with Firefox 35
			interact { moveToElement(masterTemplate.categoryLink(category)) }  //Issue with Chrome
			
		then: "check all subcategories"
			masterTemplate.subCategoryLink(category, subCategory).displayed
			
		where:
			[category, subCategory] << new File("src/test/resources/data/LevisCategories.txt").readLines()*.tokenize() //TODO update category
	}
	
}
