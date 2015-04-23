package lscob2b.modules

import geb.Module
import geb.navigator.Navigator

import org.openqa.selenium.Keys

/**
 * Created by i303936 on 11/20/14.
 */
class MasterTemplate extends Module {

	static content = {
		
		homeLink { $("#breadcrumb li a", href: endsWith('/')) }

		myAccountLink {$('a[href="/en/my-account"]')}

		//myAccountLink {$('div.global-nav ul.global-nav-list').find("a.global-nav-hasmenu", href: contains("/my-account"),0)}				
		
		myAccountSubLink { $('ul.global-nav-list>li.yCmsComponent:nth-child(3) li')}
		
		//manageUsersLink { $('div.global-nav a', href: endsWith('manage-users/')) }
		
		manageUsersLink { $('div.global-nav a[href*="manage-users"]') }

		logoutLink { $('div.global-nav ul.global-nav-list').find("a", href: contains("/logout"))}

		/* BreadCrumb */
		
		breadCrumbs { $('div#breadcrumb').find('li').not('li.separator') }
		
		breadCrumbHref { String href -> $('div#breadcrumb').find("a", href:endsWith(href),0)}
		
		breadCrumbActive { $('div#breadcrumb').find("li.active",0) }

		logoAltTag {$('header h1 a img').attr('alt')}

		themeForm(required: false) { $('#theme-form') }

		quickOrderLink { $('header h2').find("a", href: contains("/advanced")) }

		goToCartLink { $("div.mini-cart") }

		brandSelectionInput { $("form#theme-form").find("input", type: "hidden", name: "code") }
		
		switchBrandLink {$("a#switchTheme")}		
		
		searchInput { $("#input-search") }
		
		searchLink { $('a.search-icon') } 
		
		/*Contact us after login*/
		
		//contactUs { $('.yCmsComponent').find('a', href:endsWith('/contactus')) }
		
		contactUs { $('div.footer a[href*="/contactus"]') }

		/* WaitList */
		
		waitListItem { $("li#waitlist-container") }
		
		waitListLink { waitListItem.find("a",class:"miniWaitlist miniWaitlistLink",0) }
		
		waitListItemCount { waitListItem.find("span.count") }
		
		/* Cart */
		
		cartItem { $("li#cart-container") }
		
		cartItemLink { cartItem.find("a",0) }
		
		cartItemCount { cartItem.find("span.count") }
		
		/* SwitchTo Link */
		
		dockersLogo(required:false) { $("a.logo-dockers") }
		
		levisLogo(required: false) { $("a.logo-levis") }
		
		/* SubNav Menu */
		
		subNav { $("div.subnav") }
		
		quickOrderLink { subNav.find("ul.subnav-list li.subnav-quickorder").find("a") }
		
		categoryItems { subNav.find("ul.subnav-list li").not(".subnav-quickorder") }

		categoryLink { categoryName ->  subNav.find("a", href: endsWith(categoryName)) }
				
		//subCategoryLink { categoryName,subCategoryName -> subNav.find("a", href: endsWith(categoryName)).parent().parent().find("a",href: endsWith(subCategoryName)) }
		
		subCategoryLink { categoryName,subCategoryName -> subNav.find("a", href: endsWith(categoryName)).parent().parent().find("a",title: contains(subCategoryName))}
		
		/*Help*/
		
		helpLink { $('.yCmsComponent').find("a", href: endsWith('/help')) }
		
		/* Footer Links  */
		
		siteMapLink {$('div.footer a[href*="sitemap"]')}
			
		aboutUsLink {$('div.footer a[href*="levistrauss.com/who-we-are"]')}
		
		termsAndConditionsLink {$('div.footer a[href*="/termsAndConditions"]')}
		
		privacyPolicyLink {$('div.footer a[href*="/privacyPolicy"]')}	
		
		/* Common Links */
		mainContainerLabel { $('#main-container>h1') }
		
		introContainerLabel { $('.intro-container') }
		
		alertMessage { $('div.alert-message') }
		
		noteMessage { $('div.note-message') }
		
		requiredMessageText { $('.required') }
		
	}
	
	def gotoHelpPage(){
		helpLink.click()
	}
	
	def checkHelpLinkExists(){
		!helpLink.empty
	}
	def doContactUs(){
		waitFor {
			contactUs.displayed
		}
		contactUs.click()
	}
	
	def doSearch(String productID){
		searchInput = productID
		searchInput << Keys.ENTER
	}

	def getBreadCrumbByUrl(String url) {
		breadCrumbs.find('a', href: endsWith(url))
	}

	def isBreadCrumbActive(String text) {
		breadCrumbs.filter('li.active').text().toUpperCase() == text.toUpperCase()
	}

	def clickMyAccount() {
		waitFor{myAccountLink.displayed}
		myAccountLink.click()
	}

	def clickQuickOrder(){
		quickOrderLink.click()
	}

	def doLogout() {
		logoutLink.click()
	}

	def doGoToCart(){
		waitFor { goToCartLink.displayed  }
		goToCartLink.click()
	}

	def switchBrand(){
		switchBrandLink.click()
	}

	def void switchBrandIfNecessary(brand){
		if (brandSelectionInput.value() == brand){
			switchBrand()
		}
	}

	def Navigator getSubCategoryLink(subCategory){
		$("div.menu a", href: endsWith(subCategory))
	}

	def subCategoryLinkExists(subCategory){
		!getSubCategoryLink(subCategory).empty
	}

	def Navigator getParentCategory(parentCategory){
		$("div.subnav ul.subnav-list li>h2>a", href: endsWith(parentCategory))
	}

	def mouseOverParentCategory(parentCategory){
		getParentCategory(parentCategory).jquery.mouseover()
	}

	def mouseOverMyAccountMenuItem(){
		myAccountLink.jquery.mouseover()
	}
	
	def getCategoryLink(parentCategory) {
		$("a", href: endsWith(parentCategory))
	}
	
	def getSubcategoryLink(parentCategory, subCategory) {
		def item = getCategoryLink(parentCategory)
		def menu = item.parent().parent().find("div.menu")
		item.jquery.mouseover()
		waitFor {
			menu.displayed
			return menu.find("a", href: endsWith(subCategory))
		}
	}
	
	def selectManageUsers(){
		mouseOverMyAccountMenuItem()
		waitFor {
			manageUsersLink.displayed
			manageUsersLink.click()
		}
	}
	
	/* MY ACCOUNT */	
	def getMyAccountSubLink(String link) {
		waitFor {
			!myAccountLink.parent().find("ul").empty
		}
		myAccountLink.parent().find("a", href: endsWith(link))
	}
	
	def getMyAccountSubLinks() {
		waitFor {
			!myAccountSubLink.empty
		}
		myAccountSubLink.find("a")
	}
	
	def waitForSometime(){
		Thread.sleep(3000)
	}
			
}
