package lscob2b.pages.checkout

import geb.Page
import lscob2b.modules.MasterTemplate

class CheckOutPage extends Page{

	static url = "/checkout/single/summary"

	static at = { waitFor { title == "Checkout | LSCO B2B Site" } }
 
	static content = {
		
		masterTemplate {module MasterTemplate}
		
		//placeOrderLink { $("div.cartButtons a.placeOrderButton") }
		placeOrderLink { $('.button-large.placeOrderButton') }
		
		cartItems { $("div.cartItem").collect { module CartModule, it  } }
		
		//Checkout page labels
		total { $('.total span') }
		subTotal { $('.subtotal span') }
		including { $('#ajaxCart .including') } //For this to work order simulation has to be turned on
		remove { $("#RemoveProduct_0") }
		removeConfirm { $("#RemoveProduct_0",1) }
		
		//Payment Terms
		creditCardPayment(required: false) { $('#PaymentTypeSelection_CARD') }
		invoicePayment(required: false) { $('#PaymentTypeSelection_ACCOUNT') }
		creditCardDefault { $('#PaymentTypeSelection_CARD').attr('checked')}
		
		//Select Delivery address
		alternateDeliveryAddressSelect(required: false) { $('.button.editButton')}
		useAddressSelect{ $('div.addressEntry:nth-child(1) button.useAddress')}
		
		//No delivery address selected
		noDeliveryAddressSelected { $('#globalMessages .alert-message') }
	}

	def doPlaceOrder(){
		waitFor { alternateDeliveryAddressSelect.displayed }
		alternateDeliveryAddressSelect.click()
		waitFor { useAddressSelect.displayed }
		useAddressSelect.click()
		waitFor { placeOrderLink.displayed }
		Thread.sleep(3000)
		placeOrderLink.click()
	}
	
	def checkTotalExists(){
		!total.empty
	}
	
	def checkSubTotalExists(){
		!subTotal.empty
	}
	
	def checkIncludingExists(){
		!including.empty
	}
	
	def checkCreditCardPaymentButtonExists(){
		!creditCardPayment.empty
	}
	
	def checkInvoicePaymentButtonExists(){
		!invoicePayment.empty
	}
	
	def checkNoDeliveryAddressSelectedExists(){
		!noDeliveryAddressSelected.empty
	}
		
}
