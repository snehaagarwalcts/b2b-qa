package lscob2b.modules

import geb.Module
import geb.navigator.Navigator

class KeylookModule extends Module{

	static content = {
			
		thumb { $("div.thumb") }
		
		itemName { $("div.itemName") }
		
		style { $("div.itemStyle") }
		
		color { $("div.itemColor") }
		
		priceWholesale { $("div.itemPrice") }
		
		//priceRetail { $("div.recommended-retail-price") }
		
	}

}
