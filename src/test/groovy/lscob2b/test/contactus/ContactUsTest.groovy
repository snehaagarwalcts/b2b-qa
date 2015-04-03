package lscob2b.test.contactus

import lscob2b.data.PageHelper;
import lscob2b.pages.ContactUsPage;
import lscob2b.pages.LoginPage;
import geb.spock.GebReportingSpec;

class ContactUsTest extends GebReportingSpec {

	def setup() {
		PageHelper.gotoPageLogout(browser, baseUrl)
		to LoginPage
		at LoginPage
		clickContactUS()
	}
	
	/**
	 * TC BB-610 Automated test: BB-412 New customer requesting access - content test
	 */
	def "Check the content of contact us page"(){
		
		when: "At contact us page"
		at ContactUsPage
		
		then: "required content should be present"
		checkRequiredContent()
	}
	
	/**
	 * TC BB-611 Automated Test: BB-412 New customer requesting access
	 */
	def "New customer requesting access"(){
		
		when: "At contact us page"
		at ContactUsPage
		
		then: "fill out the form and request access"
		def title = selectTitleOption(2)
		fillOutFirstName('Customer')
		fillOutlastName('Unit')
		fillOutEmail('sagarwal1@levi.com')
		fillOutPhone('111-222-3333')
		fillOutCompanyName('Levis')
		//fillOutCustomerNumber('111-222-3333')
		def country = selectCountryOption(234)
		fillOutComments('test')
		clickSendButton()
		checkNoteMessageExists()//App error in int001
	}
	
	/**
	 * TC BB-612 Automated test: BB-412 New customer requesting access
	 */
	def "New customer tried to submit form without filling out all required information"(){
		
		when: "At contact us page fill out most of the information"
		at ContactUsPage
		//def title = selectTitleOption(3)
		//fillOutFirstName('Cust')
		fillOutlastName('Unit')
		fillOutEmail('cust@unit-5')
		fillOutPhone('111-222-3333')
		fillOutCompanyName('Levis')
		//fillOutCustomerNumber('111-222-3333')
		def country = selectCountryOption(18)
		clickSendButton()
		
		then: "We should get an alert message"
		checkAlertMessageExists()
	}
}
