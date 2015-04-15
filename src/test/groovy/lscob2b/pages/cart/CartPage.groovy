package lscob2b.pages.cart

import geb.Page
import lscob2b.modules.MasterTemplate

class CartPage extends Page{

	static url = "/cart"

	static at = { waitFor { title == "Your Shopping Cart | LSCO B2B Site" || title =="DE_Your Shopping Cart | LSCO B2B Site" } }

	static content = {
		masterTemplate {module MasterTemplate}
				
		//cartItems { $("div.cartItem").collect { module CartModule, it  } }
		
		alertMessage1 { $(".alert-message h2") }
		
		alertMessage2 { $(".alert-message p") }
		
		continueShopping { $(".cartButtons").find('a', href: endsWith('/')) }
		
		linkCheckout(required: false) { $(".button-large.checkout") }
		
		editQuantities(required: false) { $(".itemButtons a.btn-white") }
		
		//Cart items
		itemName { $("div.itemName") }
		itemStyle { $("div.itemAttributes .itemStyle span") }
		itemColor { $("div.itemAttributes .itemColor span") }
		itemPrice { $("div.itemAttributes .itemPrice span") }
		itemQuantity { $("div.itemSummary .quantity span.label")}
		itemTotal { $("div.itemSummary .total span.label") }
		
		//Empty Cart messages
		emptyCart (required: false) { $("#main-container .blankSlate h2") }
		alertContainer { $("div.alert-container") }
		alertMessage { alertContainer.find("div.alert-message") }
		
		//Remove product from cart
		removeProductLink(required: false) { $(".itemButtons a.btn-warning") }
		removeProducts { $(".dialogueButtons #RemoveProduct_0") }
		
	}
	
	
	def boolean removeProduct(productID) {
		String replaced = productID.collectReplacements{ if(it == '-') { '' } else { null } }
		println(replaced)
	}
	
	def doRemove(){
		waitFor{ removeProductLink.displayed }
		removeProductLink.click()
		waitFor{ removeProducts.displayed }
		removeProducts.click()
	}
	
	def checkItemNameExists(){
		!itemName.empty
	}
	
	def checkItemStyleExists(){
		!itemStyle.empty
	}
	
	def checkItemColorExists(){
		!itemColor.empty
	}

	def checkItemPriceExists(){
		!itemPrice.empty
	}
	
	def checkItemQuantityExists(){
		!itemQuantity.empty
	}

	def checkItemTotalExists(){
		!itemTotal.empty
	}
	
	def checkContinueShoppingButtonExists(){
		!continueShopping.empty
	}
	
	def checkCheckoutButtonExists(){
		!linkCheckout.empty
	}
	
	def checkEditQuantitiesButtonExists(){
		!editQuantities.empty
	}
	
	def checkRemoveProductButtonExists(){
		!removeProductLink.empty
	}
	
	def editQuantitiesButtonclick(){
		editQuantities.click()
	}
	
	def emptyCartMessageExists(){
		waitFor { emptyCart.displayed }		
	}
	
	def boolean removeExistingProducts()
	{
		if (!removeProductLink.empty)
		{
			removeProductLink.click()
			removeProducts.click()

		}
		return true
	}
}
