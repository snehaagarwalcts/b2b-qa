package lscob2b.test.productdetails

import geb.spock.GebReportingSpec
import lscob2b.data.PageHelper
import lscob2b.data.ProductHelper
import lscob2b.data.UserHelper
import lscob2b.pages.HomePage
import lscob2b.pages.LoginPage
import lscob2b.pages.productdetails.ProductDetailsPage
import lscob2b.test.data.User
import spock.lang.Stepwise

class ProductDetailsPageNotPartOfBrandAssortmentTest extends GebReportingSpec {

	def static User user = UserHelper.getUser(UserHelper.B2BUNIT_LEVIS, UserHelper.ROLE_CUSTOMER)

	def static String targetProductCode = ProductHelper.getNotPartOfYourBrandAssortment(ProductHelper.BRAND_LEVIS)

	def setupSpec() {
		PageHelper.gotoPageLogout(browser,baseUrl)
		at LoginPage
		login(user)
		at HomePage
	}

	/**
	 * TC BB-867 As a customer I should see only products that are assigned to my customer profile
	 */
	def "Product not part of your brand assortment"(){
		setup:
		PageHelper.gotoPageProductDetail(browser,baseUrl,targetProductCode)

		when: "at product detail page"
		at ProductDetailsPage

		then: "Product is not part of your brand assortment"
		!notYourBrandAssortmentProduct.empty
	}
}


