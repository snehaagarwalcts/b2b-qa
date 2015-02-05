//created by I844489 on 12/10/2014

package lscob2b.pages.myaccount.admin

import geb.Page
import geb.navigator.Navigator;
import lscob2b.modules.EditUserDetailsModule;
import lscob2b.modules.MasterTemplate

class EditUserDetailsPage extends Page{

	static at = { waitFor { title == "LSCO B2B Site" } }

	static content = {
		masterTemplate { module MasterTemplate }
		
		userDetails { module EditUserDetailsModule}
		
	}

	
}
