package lscob2b.test.multibranduser

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.OrderSearchPage

class MultibrandUserTest extends GebReportingSpec {

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
	 * TC BB-512 Automated Test: Multibrand user should be able to switch between Levis and Dockers
	 */
	def "Check switch to dockers is present"() {
		setup:
		login (user)

		when: "At HomePage"
		at HomePage

		then: "Check SwitchTo Link"
		waitFor {
			masterTemplate.levisLogo.empty
			!masterTemplate.dockersLogo.empty
		}

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-512 Automated Test: Multibrand user should be able to switch between Levis and Dockers
	 */
	def "Switch to Dockers theme"(){
		setup:
		login (user)

		when: "At HomePage"
		at HomePage

		then: "Check SwitchTo Dockers"
		waitFor {
			masterTemplate.levisLogo.empty
			!masterTemplate.dockersLogo.empty
		}

		and: "Switch to dockers brand"
		masterTemplate.switchBrand()

		when: "at HomePage"
		at HomePage

		then: "Check SwitchTo Levis"
		waitFor {
			!masterTemplate.levisLogo.empty
			masterTemplate.dockersLogo.empty
		}

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-512 Automated Test: Multibrand user should be able to switch between Levis and Dockers
	 */
	def "Switch to Levis theme"(){
		setup:
		login (user)

		when: "At HomePage"
		at HomePage

		then: "Switch to dockers brand"
		masterTemplate.switchBrand()

		when: "At Homepage"
		at HomePage

		then: "Check SwitchTo Levis"
		waitFor {
			!masterTemplate.levisLogo.empty
			masterTemplate.dockersLogo.empty
		}

		and: "Switch to levis brand"
		masterTemplate.switchBrand()

		when: "At Homepage"
		at HomePage

		then: "Check SwitchTo Dockers"
		waitFor {
			masterTemplate.levisLogo.empty
			!masterTemplate.dockersLogo.empty
		}

		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-752 Automated Test: Check if switch to dockers/levis is present using dockers/levis users
	 */
	def "Check if switch to dockers is not present using Levis customer"(){
		setup:
		login (user)

		when: "at home"
		at HomePage

		then: "Switch to dockers should not be present"
		waitFor {
			masterTemplate.levisLogo.empty
			masterTemplate.dockersLogo.empty
		}
	
		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
	}

	/**
	 * TC BB-752 Automated Test: Check if switch to dockers/levis is present using dockers/levis users
	 */
	def "Check if switch to levis is not present using Dockers customer"(){
		setup:
		login (user)

		when: "at home"
		at HomePage

		then: "Switch to dockers should not be present"
		waitFor {
			masterTemplate.levisLogo.empty
			masterTemplate.dockersLogo.empty
		}
		
		where:
		user | _
		UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
	}

	/** 
	 * TC BB-595 Automated test: BB-209 As a multibrand user I see only products from my active brand
	 */
	def "Check if correct products/catalogs are displayed on Levis Theme"(){
		setup:
		login (user)

		when: "At Homepage"
		at HomePage

		then: "Check SwitchTo Docker"
		waitFor {masterTemplate.levisLogo.empty
				!masterTemplate.dockersLogo.empty}
		
		when: "Search for Levis products"
		masterTemplate.doSearch('501 Levis Original Fit Homestead')
		
		then: "at OrderSearchPage"
		at OrderSearchPage

		when: "Search for Dockers products"
		masterTemplate.doSearch('dockers')
		
		then: "at OrderSearchPage"
		at OrderSearchPage

		where:
		user = UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER)

	}

	/**
	 * TC BB-595 Automated test: BB-209 As a multibrand user I see only products from my active brand
	 */
	def "Check if correct products/catalogs are displayed on Dockers Theme"(){
		setup:
		login (user)

		when: "At Homepage"
		at HomePage

		then: "Switch Brand"
		masterTemplate.switchBrand()

		when: "At Homepage"
		at HomePage

		then: "Check SwitchTo Levis"
		waitFor {!masterTemplate.levisLogo.empty
				 masterTemplate.dockersLogo.empty}
		
		when: "Search for Dockers products"
		masterTemplate.doSearch('Bogan Woven-Leather Belt')
		
		then: "at OrderSearchPage"
		at OrderSearchPage

		when: "Search for Levis products"
		masterTemplate.doSearch('501 Levis Original Fit Homestead')
		
		then: "at OrderSearchPage"
		at OrderSearchPage

		where:
		user = UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER)

	}
}
