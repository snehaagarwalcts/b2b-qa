//created by I844489 on 12/10/2014

package lscob2b.pages.myaccount

import geb.Page
import lscob2b.modules.MasterTemplate

class OrderHistoryPage extends Page{

	static url = "my-account/orders"

	static at = { waitFor { title == "Order History | LSCO B2B Site" } }

	static content = {
		
		masterTemplate { module MasterTemplate }

		orderHistoryBar { $("div.paginationBar").text() }
		orderHistoryListTable { $("table.orderListTable thead tr").text()}

		/* FORM */				
		searchForm { $("form#orderSearchForm") }	
		searchButton(to: OrderHistoryPage) { searchForm.find("button", type:'submit') }		
		clearButton { searchForm.find("a.clear") }		
		fieldOrderNumber { searchForm.find("input", name: 'orderNum') }		
		fieldPONumber { searchForm.find("input", name: 'poNum') }
	
		checkboxOrderStatusSubmitted { searchForm.find("input", type:'checkbox', name:'statuses',0) }
		checkboxOrderStatusCompleted { searchForm.find("input", type:'checkbox', name:'statuses',1) }
		checkboxOrderStatusInProgress { searchForm.find("input", type:'checkbox', name:'statuses',2) }
		checkboxOrderDate30 { searchForm.find("input", type:'radio', name:'duration',0) }
		checkboxOrderDate90 { searchForm.find("input", type:'radio', name:'duration',1) }
		checkboxOrderDateYear { searchForm.find("input", type:'radio', name:'duration',2) }
		checkboxOrderTypeAO { searchForm.find("input", type:'checkbox', name:'types',0) }
		checkboxOrderTypePB { searchForm.find("input", type:'checkbox', name:'types',1) }
		checkboxOrderSourceB2B { searchForm.find("input", type:'checkbox', name:'sources',0) }
		checkboxOrderSourceEDI { searchForm.find("input", type:'checkbox', name:'sources',1) }	
		checkboxOrderSourceSAP { searchForm.find("input", type:'checkbox', name:'sources',2) }		
		checkboxOrderSourceSFA { searchForm.find("input", type:'checkbox', name:'sources',3) }		
		checkboxOrderSourceLEO { searchForm.find("input", type:'checkbox', name:'sources',4) }			

		clickCheckbox  { $('.iCheck-helper', it) }

		/* Result */		
		resultTable(required: false) { $("table.orderListTable") }		
		invoiceNumber (require:false) { $("a.invoice") }
		
		/*  order history content */		
		ordersFoundLabel { $(".totalResults",0) }
		sortByLabel { $("#sort_form1>label") }
		datePlacedLabel { $("#header1") }
		orderNumberLabel { $("#header2") }
		orderStatusLabel { $("#header3") }
		orderTypeLabel { $("#header4") }
		totalLabel { $("#header5") }
		orderSourceLabel { $("#header6") }
		invoiceLabel { $("#header7") }		
		
	}
	
	def checkOrderHistoryData(){
		!masterTemplate.mainContainerLabel.empty
	}
	
	def isFormClear() {
		return !checkboxOrderSourceB2B.value() && !checkboxOrderSourceEDI.value() && !checkboxOrderSourceSAP.value() &&
		!checkboxOrderSourceSFA.value() && !checkboxOrderSourceLEO.value() && !checkboxOrderTypeAO.value() && 
		!checkboxOrderTypePB.value() && !checkboxOrderDate30.value() && !checkboxOrderDate90.value() &&
		!checkboxOrderDateYear.value() && !checkboxOrderStatusSubmitted.value() && !checkboxOrderStatusCompleted.value() &&
		!checkboxOrderStatusInProgress.value() && fieldOrderNumber.value() == "" && fieldPONumber.value() == ""
	}
	
	def switchOnForm() {		
		clickCheckbox(0).click()
		clickCheckbox(1).click()
		clickCheckbox(2).click()
		clickCheckbox(3).click()
		clickCheckbox(4).click()
		clickCheckbox(5).click()
		clickCheckbox(6).click()
		clickCheckbox(7).click()
		clickCheckbox(8).click()
		clickCheckbox(9).click()
		checkboxOrderDate30.click()
		checkboxOrderDate90.click()
		checkboxOrderDateYear.click()
		fieldPONumber.value("Test PO")
		fieldOrderNumber.value("Test Order")
	}
	
	def searchByOrderNumber(String orderNumber) {
		fieldOrderNumber.value(orderNumber)
		searchButton.click()
	}	

	def searchByOrderNumberAndOrderSource(String orderNumber, boolean b2b, boolean edi, boolean sap, boolean sfa, boolean leo) {
		if(checkboxOrderSourceB2B.value() != b2b) clickCheckbox(5).click()
		if(checkboxOrderSourceEDI.value() != edi) clickCheckbox(6).click()
		if(checkboxOrderSourceSAP.value() != sap) clickCheckbox(7).click()
		if(checkboxOrderSourceSFA.value() != sfa) clickCheckbox(8).click()
		if(checkboxOrderSourceLEO.value() != leo) clickCheckbox(9).click()
		fieldOrderNumber.value(orderNumber)
		searchButton.click()
	}
	
	def searchByOrderNumberAndOrderType(String orderNumber, boolean atOnce, boolean preBook) {
		if(checkboxOrderTypeAO.value() != atOnce) clickCheckbox(3).click()
		if(checkboxOrderTypePB.value() != preBook) clickCheckbox(4).click()
		fieldOrderNumber.value(orderNumber)
		searchButton.click()
	}
	
	def searchByOrderNumberAndOrderDate(String orderNumber, boolean last30, boolean last90, boolean lastYear) {
		if(last30) checkboxOrderDate30.click()
		if(last90) checkboxOrderDate90.click()
		if(lastYear) checkboxOrderDateYear.click()
		fieldOrderNumber.value(orderNumber)
		searchButton.click()
	}
	
	def searchByOrderNumberAndOrderStatus(String orderNumber, boolean submitted, boolean completed, boolean inProgress) {
		if(checkboxOrderStatusSubmitted.value() != submitted) clickCheckbox(0).click()
		if(checkboxOrderStatusCompleted.value() != completed) clickCheckbox(1).click()
		if(checkboxOrderStatusInProgress.value() != inProgress) clickCheckbox(2).click()
		fieldOrderNumber.value(orderNumber)
		searchButton.click()
	}
	
	def checkUniqueResult() {
		resultTable.find("tbody>tr").size() == 1
	}
	
	def checkEmptyResult() {
		resultTable.empty
	}
			
	def clickOnFirstOrder() {
		resultTable.find("tbody>tr a",0).click()
	}
	
	def clickOnInvoiceNumber(){
		invoiceNumber.click()
	}
		
}
