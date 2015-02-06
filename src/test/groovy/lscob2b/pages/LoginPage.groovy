package lscob2b.pages

import geb.Page
import lscob2b.modules.MasterTemplate
import lscob2b.test.data.User;
import static lscob2b.TestConstants.*

class LoginPage extends Page {

	static url = "login"

	static at = { waitFor { title == "Login | LSCO B2B Site" } }

	static content = {
		// login form
		usernameInput { $("#j_username") }
		passwordInput { $("#j_password") }
		loginButton { $("#loginForm button") }

		// some error messages
		//globalMessages { $("#globalMessages") }
		errorMessage { $("div.alert-message") }
		errorMessageText { errorMessage.text().trim() }

		// forgotten password
		forgottenPasswordDialog(required: false) { $("#cboxLoadedContent") }
		forgottenPasswordDialogVisible(required: false) { forgottenPasswordDialog.present && forgottenPasswordDialog.displayed }
		forgottenYourPasswordButton { $("#loginForm a") }
		closeForgottenPasswordButton (required: false) { $('#cboxClose') }
		sendEmailButton(required: false) { $("#forgottenPwdForm button") }
		emailAddress { $("#forgottenPwdForm input") }

		// language selector
		langSelector { $("#lang-selector") }
		langSelectorFor { value -> $("#lang-selector option[value='${value}']") }
		langSelectorValueCount { $("#lang-selector option").size() }
		pageheading { $("#main-container h1").text() }
		
		// contact us
		contactUS { $('div.contact a') }
		
		//Password sent
		noteMessage { $("div.note-message") }
	}

	def login(User user){
		doLogin(user.email, user.password)
	}

	def login(String username) {
		doLogin(username, defaultPassword)
	}

	def doLogin(String username, String password) {
		usernameInput = username
		passwordInput = password
		loginButton.click()
	}

	def openForgottenPasswordDialog() {
		forgottenYourPasswordButton.click()

		waitFor {
			forgottenPasswordDialogVisible
			emailAddress
		}
	}

	def closeForgottenPasswordDialog() {
		closeForgottenPasswordButton.click()

		waitFor { !forgottenPasswordDialogVisible }
	}

	def sendForgottenPasswordEmail(String email) {
		emailAddress = email
		sendEmailButton.click()
	}
	
	def clickContactUS(){
		contactUS.click()
	}
}
