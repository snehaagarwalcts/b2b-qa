package lscob2b.pages.myaccount.admin

import lscob2b.modules.MasterTemplate
import geb.Page

class UpdatePersonalDetailsPage extends Page {

	static url = "my-account/update-profile"

	static at = { waitFor { title == "Profile | LSCO B2B Site" } }

	static content = {
		
		masterTemplate { module MasterTemplate }
				
	}	
}
