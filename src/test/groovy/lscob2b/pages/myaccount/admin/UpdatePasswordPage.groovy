package lscob2b.pages.myaccount.admin

import lscob2b.modules.MasterTemplate
import geb.Page;

class UpdatePasswordPage extends Page {

	static url = "my-account/update-password"

	static at = { waitFor { title == "Profile | LSCO B2B Site" } }

	static content = {
		
		masterTemplate { module MasterTemplate }
		
		currentPasswordText { $('div.b2BCustomerFormList>div.control-group:nth-child(1) div.controls>input') }
		//currentPasswordText { $('.controls input',0) }
		
		newPasswordText { $('#profile-newPassword') }
		
		confirmNewPasswordText { $('div.b2BCustomerFormList>div.control-group:nth-child(4) div.controls>input') }
		//confirmNewPasswordText { $('.controls input',2) }
		
		updatePasswordButton{ $('.button.save') }
		
		errorMessage { $('div.b2BCustomerFormList>div.control-group.error>:nth-of-type(2)>span') }
		
	}
	
	def doUpdatePassword(oldPassword, newPassword)
	{
		currentPasswordText.value(oldPassword)
		newPasswordText.value(newPassword)
		confirmNewPasswordText.value(newPassword)
		updatePasswordButton.click()
	}

}
