package lscob2b.test.helppage

import spock.lang.IgnoreRest;
import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.ContactUsPage
import lscob2b.pages.FAQPage
import lscob2b.pages.HelpPage
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.TrainingPage

class HelpPageTest extends GebReportingSpec {

	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
	}

	def setup() {
		to LoginPage
	}

	def cleanup() {
		masterTemplate.doLogout()
	}
	
	/**
	 * US BB-675 Getting help
	 * TC BB-847 Getting Help - check help link is present at top right corner
	 */
	def "Check Help link is available on the page"(){
		setup: 
		at LoginPage
		login(user)
		
		when: "At homepage"
		at HomePage
		
		then: "We should be able to see Help on top of the page"
		masterTemplate.checkHelpLinkExists()
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
	
	/**
	 * TC BB-848 Getting Help - Clicking on Help link should bring you to help page
	 */
	def "clicking on Help link should bring you to help page"(){
		setup:
		at LoginPage
		login(user)
		
		when: "At homepage"
		at HomePage
		
		and: "We should click on help link"
		masterTemplate.gotoHelpPage()
		
		then: "We should be at help page"
		at HelpPage
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
	
	/**
	 * TC BB-849 Getting Help - check common content of help page
	 */
	def "check the common content of Help page"(){
		setup:
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.gotoHelpPage()
		
		when: "At help page"
		at HelpPage
				
		then: "We see FAQ, training, and contact us"
		checkFAQLinkExists()
		checkContactUsLinkExists()
		checkTrainingLinkExists()
		
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
	
	/**
	 * TC BB-850 Getting Help - Clicking on FAQ button should bring you to FAQ page
	 */
	
	def "Clicking on FAQ button should bring you to FAQ page"(){
		setup:
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.gotoHelpPage()
		
		when: "At help page"
		at HelpPage
				
		and: "click on FAQ button"
		clickFAQLink()
		
		then: "at FAQ page"
		at FAQPage
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
	
	/**
	 * TC BB-851 Getting Help - Clicking on contact us button should bring you to contact us page
	 */
	def "Clicking on contact us button should bring you to contact us page"(){
		setup:
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.gotoHelpPage()
		
		when: "At help page"
		at HelpPage
				
		and: "click on contact us button"
		clickContactUsLink()
		
		then: "at FAQ page"
		at ContactUsPage
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
	
	/**
	 * TC BB-852 Getting Help - Clicking on training button should bring you to training page
	 */
	def "Clicking on training button should bring you to training page"(){
		setup:
		at LoginPage
		login(user)
		at HomePage
		masterTemplate.gotoHelpPage()
		
		when: "At help page"
		at HelpPage
				
		and: "click on training button"
		clickTrainingLink()
		
		then: "at FAQ page"
		at TrainingPage
		
		where:
		user |_
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_SUPER) | _
	}
}
