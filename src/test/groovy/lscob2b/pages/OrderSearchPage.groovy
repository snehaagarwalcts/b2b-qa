package lscob2b.pages

import geb.Page
import lscob2b.modules.CheckOutModule
import lscob2b.modules.MasterTemplate
import lscob2b.modules.SizingGridModule



class OrderSearchPage extends Page {

	static String numberRegex = "\\d+(\\.|,)\\d+((\\.|,)\\d+)?"

	static at = {
		waitFor { title.endsWith("LSCO B2B Site") }
	}

	static content = {
		masterTemplate { module MasterTemplate }
		sizingGrid { module SizingGridModule}
		checkOut { module CheckOutModule}
				
		messageText { $('div.content h2') }
		
		paginationBar { $("div.paginationBar") }
		
		sortOptions { $("select#sortOptions1") }
		
	}
	
	def checkMessageTextExists(){
		messageText.displayed
	}
}