//created by I844489 on 12/10/2014

package lscob2b.pages.myaccount

import geb.Page
import lscob2b.modules.MasterTemplate
import lscob2b.modules.OrderModule
import lscob2b.pages.checkout.CheckOutPage

class OrderDetailPage extends Page{

	static at = { waitFor { title == "Order Details | LSCO B2B Site" } }

	static content = {
		
		masterTemplate { module MasterTemplate }
		
		order {module OrderModule}
		
		linkReOrder(required: false) { $("a#reorder") }		
		
	}
		
}
