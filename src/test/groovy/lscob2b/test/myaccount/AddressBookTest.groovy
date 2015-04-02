package lscob2b.test.myaccount

import spock.lang.IgnoreIf;
import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.myaccount.AddressBookPage
import lscob2b.pages.myaccount.MyAccountPage
import lscob2b.test.data.Address


class AddressBookTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
	}

	/*def cleanup() {
		masterTemplate.doLogout()
	}*/
	
	def loginAndGoToPage(user) {
		login(user)
		
		at HomePage
		masterTemplate.clickMyAccount()
				
		at MyAccountPage
		addressBookLink.click()
	}
	
	/**
	 * TC BB-496 Automated Test: Validate "Address Book" data presentation.
	 * TC BB-423 Automated Test Case: Validate the content of the My Account - "User Address Book" Page for any user.
	 */
	//@IgnoreIf({System.getProperty("geb.browser") == "internet explorer"})
	@IgnoreIf({System.getProperty("geb.env") == "integration000"})
	def "Check Users Shipping Address"() {
		setup:
			loginAndGoToPage(user)
			def List<Address> shippingData = UserHelper.getShippingAddress(user)
		
		when: "At Address Book page"
			at AddressBookPage
	
		then: "Test content of Shipping Address Header and Shipping Data Size"
			assert shippingHeader.text()=="VIEW YOUR DELIVERY ADDRESSES"
			assert addressShipping.size() == shippingData.size();
		
		and: "Test Shipping Data Content"
		for(int i = 0; i < addressShipping.size(); i++) {
			
			boolean findAddress = false
			for(int j = 0; j < shippingData.size() && !findAddress; j++) {
				findAddress = getShippingFirstname(i) == shippingData[j].firstname &&
						getShippingStreetname(i) == shippingData[j].streetname &&
						getShippingLastname(i) == shippingData[j].lastname &&
						getShippingTown(i) == shippingData[j].town &&
						getShippingRegion(i) == shippingData[j].region &&
						getShippingPostalcode(i) == shippingData[j].postalcode &&
						getShippingCountry(i) == shippingData[j].country
			} 
			assert findAddress
		}
		
		where:
			user | _
			UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
			UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
			UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}
	
	/**
	 * TC BB-496 Automated Test: Validate "Address Book" data presentation.
	 * TC BB-423 Automated Test Case: Validate the content of the My Account - "User Address Book" Page for any user.
	 */
	@IgnoreIf({System.getProperty("geb.env") == "integration000"})
	def "Check Users Billing Address on AddressBook page"() {
		setup:
		loginAndGoToPage(user)
		def List<Address> billingData = UserHelper.getBillingAddress(user)
		
		when: "At Address Book page"
		at AddressBookPage
	
		then: "Test content of Billing Address Header and Test Billing Data Size"
		assert billingHeader.text()=="VIEW YOUR BILLING ADDRESSES"
		assert addressBilling.size() == billingData.size();
		
		and: "Test Billing Data Content"
		for(int i = 0; i < addressBilling.size(); i++) {
			
			boolean findAddress = false
			for(int j = 0; j < billingData.size() && !findAddress; j++) {
				
				findAddress = getBillingFirstname(i) == billingData[j].firstname &&
				getBillingStreetname(i) == billingData[j].streetname &&
				getBillingLastname(i) == billingData[j].lastname &&
				getBillingTown(i) == billingData[j].town &&
				getBillingRegion(i) == billingData[j].region &&
				getBillingPostalcode(i) == billingData[j].postalcode &&
				getBillingCountry(i) == billingData[j].country
				
			}
			
			assert findAddress
		}
		
		where:
			user | _
				UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER) | _
				UserHelper.getUser(UserHelper.B2BUNIT_DOCKERS, UserHelper.ROLE_CUSTOMER) | _
				UserHelper.getUser(UserHelper.B2BUNIT_MULTIBRAND, UserHelper.ROLE_CUSTOMER) | _
	}
	
}
