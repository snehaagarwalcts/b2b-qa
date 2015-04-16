package lscob2b.data

import groovy.json.JsonSlurper


class ProductHelper  {
	
	public static final DATA_PRODUCT				= "src/test/resources/data/Product.json"
	
	def static String BRAND_LEVIS = "levis"
	def static String BRAND_DOCKERS = "dockers"
	
	def private static Map<String, Map> products;
	
	static {
		products = (Map<String, Map>) new JsonSlurper().parseText(new File(DATA_PRODUCT).text)
	}
	
	static void main(String[] args) {
		println "code: " + getProduct(BRAND_LEVIS)
		println "cross: " + getCrossSelling(BRAND_LEVIS)
	}
	
	/**
	 * Product for pdp check
	 */
	def static String getProduct(brand) {
		products.get(brand).get("baseProduct").get("code")
	}			
	
	def static List<String> getCrossSellings(brand) {
		products.get(brand).get("crossSelling")
	}
	
	def static List<String> getUpSellings(brand) {
		products.get(brand).get("upSelling")
	}
	
	def static getProductColor(brand) {
		if(brand == BRAND_LEVIS) {
			return [
				code: '00501-0039',
				related: ["00501-0039","00501-0113"]
			]
		}
		null
	}
	
	def static getNotPartOfYourBrandAssortment(brand) {
		//if(brand == BRAND_LEVIS) return "00501-0101"
		products.get(brand).get("brandAssortment")
	}
	
	def static getWaitlistProduct(brand) {
		if(brand == BRAND_LEVIS) return "00501-0039"
		if(brand == BRAND_DOCKERS) return ""
		null
	}	
	
	def static getOrderHistoryProduct(brand) {
		if(brand == BRAND_LEVIS) return "00501-0039"
		if(brand == BRAND_DOCKERS) return ""
		null
	}
	
	def static String[] getQuickOrderProduct(brand) {
		if(brand == BRAND_LEVIS) return [ "00501-0039", "00501-0113" ]
		if(brand == BRAND_DOCKERS) return ""
		null
	}
	
	def static String[] getBulkOrderProduct(brand) {
		if(brand == BRAND_LEVIS) return [ "00501-0039", "00501-0113" , "00501-0114", "04511-0709","04700-0078"]
		// ,"05510-0417", "04700-0079","04700-0170" , "16078-0001", "00501-1963"
			
		null
	}
	
}
