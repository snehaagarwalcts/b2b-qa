package lscob2b.modules

import geb.Module
import geb.navigator.Navigator

class SizingTableModule extends Module {

	//ROOT ELEMENT IS div.single_grid_three_dimensions
	
	static content = {
		
		table { $("table.grid_three_dimensions") }
		
	}

	
	def void addLimitedStockQuantity(int quantity) {
		table.find("td.Yellow",0).find("input.sku-quantity").value(quantity)
	}
	
	def void addOutOfStockStockQuantity(int quantity) {
		table.find("td.Red",0).find("input.sku-quantity").value(quantity)
	}
	
	def int getOutOfStockQuantity() {
		table.find("td.Red",0).find("input.sku-quantity").value().toInteger()
	}
	
	//srilatha
	def void addFullStockQuantity(int quantity){
		table.find("td.Green",0).find("input.sku-quantity").value(quantity)
	}
	
}
