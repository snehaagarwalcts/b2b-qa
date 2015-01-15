package lscob2b.test.MultibrandUser

import static lscob2b.TestConstants.*
import geb.spock.GebReportingSpec
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.test.data.TestDataCatalog
import spock.lang.Ignore

class MultibrandUserTest extends GebReportingSpec {

	def setup() {
		to LoginPage
	}

	def cleanup() {
		masterTemplate.doLogout()
	}

	@Ignore
	def "Check switch to dockers is present"() {
		setup:
		login (multibrandUser)

		when: "at home"

		at HomePage

		then: "Levis theme and switch to dockers is present"

		checkSwitchTo()
		dockersLogo
	}

	@Ignore
	def "Switch to Dockers theme"(){
		setup:
		login (multibrandUser)
		at HomePage
		dockersLogo

		when: "click switch to"

		clickSwitchTo()

		then: "Levis logo is present"
		
		levisLogo
		
	}
	
	@Ignore
	def "Swtich to Levis theme"(){
		setup:
		login (multibrandUser)
		at HomePage
		clickSwitchTo()
		levisLogo

		when: "click switch to"
		clickSwitchTo()
		

		then: "Levis logo is present"
		dockersLogo		
		
	}
	
	@Ignore
	def "Check if switch to dockers is present using Levis customer"(){
		setup:
		login (TestDataCatalog.getALevisUser())
		
		when: "at home"
		
		at HomePage
				
		then: "Switch to dockers should not be present"
		
		!switchToLink.displayed
		
	}
	
	@Ignore
	def "Check if switch to dockers is present using Dockers customer"(){
		setup:
		login (TestDataCatalog.getADockersUser())
		
		when: "at home"
		
		at HomePage
				
		then: "Switch to dockers should not be present"
		
		!switchToLink.displayed
		
	}
}