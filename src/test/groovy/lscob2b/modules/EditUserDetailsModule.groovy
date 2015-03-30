package lscob2b.modules

import lscob2b.test.data.User;
import geb.Module
import geb.navigator.Navigator

class EditUserDetailsModule extends Module{

	static content = {
		
		customerForm { $("form#b2BCustomerForm") }
		
		titleOption { customerForm.find("select#user\\.title") }
		
		firstNameInput { customerForm.find("input#user\\.firstName") }
		
		lastNameInput { customerForm.find("input#user\\.lastName") }
		
		emailInput { customerForm.find("input#user\\.email") }
		
		selectDDA { customerForm.find("select#text\\.company\\.user\\.default\\.shipping\\.address") }

		saveButton { $("button", type: "submit") }
			
	}

	def void setUser(User user) {
		customerForm.titleCode = user.title
		customerForm.firstName = user.name
		customerForm.lastName = user.surname
		if(!(emailInput.@disabled == 'true')) { 	
			customerForm.email = user.email
		}
	}
	
	def getAddress() {
		selectDDA.find('option', value:selectDDA.value()).text()
	}
	
	def changeDefaultDeliveryAddress() {
		waitFor { selectDDA.displayed }
		selectDDA.value(selectDDA.find("option",value:notContainsWord(selectDDA.value()),0).value())
	}
	
	//This is similar to changeDefaultDeliveryAddress
	def removeDefaultDeliveryAddress() {
		selectDDA.value(selectDDA.find("option",value:notContainsWord(selectDDA.value()),0).value())
	}
	
}
