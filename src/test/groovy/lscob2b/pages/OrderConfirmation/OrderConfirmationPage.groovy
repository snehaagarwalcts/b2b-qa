package lscob2b.pages.OrderConfirmation

import geb.Page
import lscob2b.modules.MasterTemplate

class OrderConfirmationPage extends Page{
	
	static url = "/checkout/orderConfirmation/"

	static at = { title == "Order Confirmation | LSCO B2B Site" }

	static content = {
		masterTemplate {module MasterTemplate}
	}
}