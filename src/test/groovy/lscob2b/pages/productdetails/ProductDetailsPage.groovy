package lscob2b.pages.productdetails

import geb.Page
import lscob2b.modules.CheckOutModule
import lscob2b.modules.MasterTemplate
import lscob2b.modules.PDPBuyStackModule
import lscob2b.modules.SellModule
import lscob2b.modules.SizingGridModule
import lscob2b.modules.SizingTableModule

class ProductDetailsPage extends Page {

	static String numberRegex = "\\d+(\\.|,)\\d+((\\.|,)\\d+)?"

	static at = {
		waitFor { browser.currentUrl.contains("/Categories/") || browser.currentUrl ==~ /^.*\/p\/[\-\w]+$/ }
	}

	static content = {

		masterTemplate { module MasterTemplate }

		sizingGrid { module SizingGridModule}		//TODO remove it [CHECK SIMONE]

		sizingTable { module SizingTableModule, $("div.single_grid_three_dimensions") }

		checkOut { module CheckOutModule}

		upSelling(required: false) { module SellModule, $("div.up-sell") }

		crossSelling(required: false) { module SellModule, $("div.cross-sell") }

		buyStack { module PDPBuyStackModule, $("div.pdp-buystack") }

		wholesalePriceText { $("div.wholesale-price > span").text()}
		
		recommendedRetailPriceText { $("div.recommended-retail-price > span").text()}
		
		//orderQuantity{ $(".grid_three_dimensions").find("tr > td > .sku-quantity",0) }
		
		recommendedRetailPriceValue { parsePrice(recommendedRetailPriceText) }
		
		wholesalePriceValue { parsePrice(wholesalePriceText) }

		//OverLay

		overlayWaitList { $("div.popup_box") }

		overlayTable { overlayWaitList.find("table.grid_three_dimensions") }

		overlayButtonAdd { overlayWaitList.find("a#add_to_waitlist_button") }
		
		overlayClosePopUp { $('#popupBoxClose')}

		notYourBrandAssortmentProduct { $('.alert-message') }

	}

	def recommendedRetailPriceExist(){
		containsPositiveDigit(recommendedRetailPriceText);
	}

	def wholesalePriceExist(){
		containsPositiveDigit(wholesalePriceText);
	}

	def containsPositiveDigit(text){
		text ==~ /.*[1..9]+.*/ //check price text actually contains some non zero numbers
	}

	def parsePrice(String priceText) {
		String s = priceText.find(numberRegex).replace(",","").replace(".","")
		Double.parseDouble(s.substring(0, s.length()-2) + "." + s.substring(s.length()-2))
	}

	def addOrderQuantity(String quantity){
		sizingTable.addLimitedStockQuantity(quantity.toInteger())
	}
	

	def findLinkColor(String href) {
		$("ul.color-swatches").find("a",href:endsWith(href),0)
	}

	def addOutOfStockQuantityToWaitList(int quantity) {
		waitFor { sizingGrid.displayed }
		waitFor { sizingGrid.buttonNotifyMe.displayed }

		sizingGrid.buttonNotifyMe.click()

		waitFor { overlayWaitList.displayed }

		waitFor { overlayTable.displayed }

		overlayTable.find("td.Red",0).find("input.sku-quantity").value(quantity)

		overlayButtonAdd.click()
		
		overlayClosePopUp.click()

	}
	//srilatha 
	
	def addOrderQuantity2(String quantity){
		sizingTable.addFullStockQuantity(quantity.toInteger())
	}
	

}
