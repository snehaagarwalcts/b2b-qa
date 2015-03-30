package lscob2b.pages

import javax.management.modelmbean.RequiredModelMBean;

import geb.Page
import lscob2b.modules.MasterTemplate
import geb.navigator.Navigator

class ContactUsPage extends Page{

	static url = "/contactus"

	static at = { waitFor { title == "LSCO B2B Site" } }

	static content = {
		masterTemplate {module MasterTemplate}
		
		contactUsForm { $('#contactUsForm div div label.control-label') }
		
		titleText { $('.controls select[name="titleCode"] option', it) }
		firstName { $('.controls input', 0) }
		lastName { $('.controls input', 1) }
		emailText { $('.controls input', 2) }
		phoneText { $('.controls input', 3) }
		companyName { $('.controls input', 4) }
		customerNumber { $('.controls input', 5) }
		country { $('.dropdown-container select[name="countryCode"] option', it) }
		comments { $('#contactUsForm div.controls textarea') }
		
		firstNameAfterLogin { $('.controls', 1) }
		lastNameAfterLogin { $('.controls', 2) }
		emailTextAfterLogin { $('.controls', 3) }
		phoneTextAfterLogin { $('.controls', 4) }
		companyNameAfterLogin { $('.controls', 5) }
		customerNumberAfterLogin { $('.controls', 6) }
		
		sendButton { $('button.button') }

	}
	
	def checkRequiredContent(){
		!masterTemplate.mainContainerLabel.empty
		!masterTemplate.introContainerLabel.empty
		!masterTemplate.requiredMessageText.empty
		!contactUsForm.empty
	}
	
	def checkAlertMessageExists(){
		!masterTemplate.alertMessage.empty
	}
	
	def checkNoteMessageExists(){
		!masterTemplate.noteMessage.empty
	}
	
	def fillOutFirstName(String firstname){
		firstName.value(firstname)
	}
	
	def fillOutlastName(String lastname){
		lastName.value(lastname)
	}
	
	def fillOutEmail(String email){
		emailText.value(email)
	}
	
	def fillOutPhone(String phone){
		phoneText.value(phone)
	}
	
	def fillOutCompanyName(String companyname){
		companyName.value(companyname)
	}
	
	def fillOutCustomerNumber(String customernumber){
		customerNumber.value(customernumber)
	}
	
	def fillOutComments(String comment){
		comments.value(comment)
	}
	
	def clickSendButton(){
		sendButton.click()
	}
	
	/**
	 * @param index starts from 1
	 * @return
	 */
	def String selectTitleOption(int index){
		titleCode = titleText(index).text()
	}
	
	/**
	 * @param index starts from 1
	 * @return
	 */
	def String selectCountryOption(int index){
		countryCode = country(index).text()
	}
}
