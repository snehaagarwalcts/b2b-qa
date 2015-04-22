package lscob2b.data

import groovy.json.JsonSlurper


class LanguageHelper {

	def static final DATA_LANG = "src/test/resources/data/Language_full.json"
	
	def static getDefaultLang() {
		return defaultLang
	}
	
	def static getLanguages() {
		return languages
	}
	
	/* PRIVATE */
	
	private static String defaultLang
	private static List<String> languages
	
	static {
		def jsonData = new JsonSlurper().parseText(new File(DATA_LANG).text)
		defaultLang = jsonData["default"]
		languages = jsonData["languages"]
	}
		
}
