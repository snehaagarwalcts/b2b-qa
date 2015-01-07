package lscob2b.test.data

import java.util.Map;

import lscob2b.test.data.Product.ProductPrice;
import lscob2b.test.data.Product.ProductSizeVariant;
import groovy.json.JsonSlurper

class TestDataCatalog {

	static final String ADMIN_GROUP = "b2badmingroup"
	static List<User> users;
	static Map<String,B2BUnit> b2bUnits;
	static List<Product> products;

	static{
		b2bUnits = readB2BUnits("src/test/resources/testinput/B2BUnits.json")
		users = readUsers("src/test/resources/testinput/Users.json")
		products = readProducts("src/test/resources/testinput/Products.json")
	}

	private static List<User> readUsers(String path){
		List<User> userList = new ArrayList<User>()
		Map<String, Map> jsonData = (Map<String, Map>) new JsonSlurper().parseText(new File(path).text)
		jsonData.values().each {
			User user = new User()
			user.setEmail(it.email)
			user.setPassword(it.password)
			user.setName(it.name)
			user.setDefaultB2BUnit(b2bUnits.(it.defaultB2BUnit))
			user.setTitle(it.title)
			user.setGroups(it.groups)
			userList.add(user)
		}
		return userList
	}

	private static Map<String,B2BUnit> readB2BUnits(String path){
		Map<String,B2BUnit> b2bUnitMap = new HashMap<String,B2BUnit>()
		Map<String, Map> jsonData = (Map<String, Map>) new JsonSlurper().parseText(new File(path).text)
		jsonData.keySet().each {
			B2BUnit b2bUnit = new B2BUnit();
			b2bUnit.setUid(it)
			b2bUnit.setName(jsonData.get(it).name)
			b2bUnit.setCountry(jsonData.get(it).country)
			b2bUnit.setActive(jsonData.get(it).active)
			b2bUnit.setGroups(jsonData.get(it).groups)
			b2bUnit.setOrderBlock(jsonData.get(it).orderBlock)
			b2bUnit.setPaymentTerms(jsonData.get(it).paymentTerms)
			b2bUnit.setReportingOrganization(jsonData.get(it).reportingOrganization)
			b2bUnit.setUserPriceGroup(jsonData.get(it).userPriceGroup)
			b2bUnitMap.put(it, b2bUnit)
		}
		return b2bUnitMap
	}

	private static List<Product> readProducts(String path){
		List<Product> productList = new ArrayList<Product>()
		Map<String, Map> jsonData = (Map<String, Map>) new JsonSlurper().parseText(new File(path).text)
		jsonData.values().each {
			Product product = new Product()
			product.setCode(it.code)
			product.setCatalogVersion(it.catalogVersion)
			product.setCategories(it.categories)
			product.setAvailableAt(it.availableAt)

			Map<String,ProductPrice> prices = new HashMap<String,ProductPrice>()
			it.prices.keySet().each { priceGroup ->
				prices.put(priceGroup, new ProductPrice(product, it.prices.get(priceGroup).currency, it.prices.get(priceGroup).retailPrice, it.prices.get(priceGroup).wholesalePrice))
			}
			product.setPrices(prices)

			Map<String,ProductSizeVariant> sizes = new HashMap<String,ProductSizeVariant>()
			it.sizes.keySet().each { code ->
				sizes.put(code, new ProductSizeVariant(product, code,it.sizes.get(code).sizeDim1, it.sizes.get(code).sizeDim2, it.sizes.get(code).stock))
			}
			product.setSizes(sizes)

			productList.add(product)
		}
		return productList
	}

	static User getAnAdminUser(){
		getAUserWithGroup(ADMIN_GROUP)
	}

	static User getAUserWithGroup(String group){
		for (user in users) {
			if (user.groups.contains(group)) return user
		}
	}

	static User getALevisUser(){
		for (user in users) {
			if (user.defaultB2BUnit.isLevisUnit()) return user
		}
	}

	static User getAMultibrandUser(){
		for (user in users) {
			if (user.defaultB2BUnit.isMultibrandUnit()) return user
		}
	}

	static User getADockersUser(){
		for (user in users) {
			if (user.defaultB2BUnit.isDockersUnit()) return user
		}
	}

	static Product getAProductAvailableForUser(User user){
		for (product in products){
			if (product.availableAt.contains(user.defaultB2BUnit.userPriceGroup))
				return product
		}
	}
}
