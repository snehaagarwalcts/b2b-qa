package lscob2b.data

import java.util.List
import java.util.Map
import groovy.json.JsonSlurper
import lscob2b.test.data.Address
import lscob2b.test.data.B2BUnit
import lscob2b.test.data.Product
import lscob2b.test.data.User

class UserHelper  {

	public static final DATA_USER				= "src/test/resources/data/Users.json"
	public static final DATA_B2BUNIT			= "src/test/resources/data/B2BUnits.json"
	public static final DATA_ADDRESS			= "src/test/resources/data/Addresses.json"
	
	public static final ROLE_SUPER				= ["b2badmingroup","b2bcustomergroup","b2bfinancegroup"]
	public static final ROLE_ADMIN				= ["b2badmingroup"]
	public static final ROLE_CUSTOMER			= ["b2bcustomergroup"]
	public static final ROLE_FINANCE			= ["b2bfinancegroup"]
	public static final ROLE_TERMSANDCONDITION			= ["b2bcustomergroup"]
	
	public static final B2BUNIT_LEVIS			= "automated-unit-1"
	public static final B2BUNIT_DOCKERS			= "automated-unit-3"
	public static final B2BUNIT_MULTIBRAND		= "automated-unit-2"
	
	public static final COMPLIANT_PASSWORD ="Levi2016#"
	public static final UNCOMPLIANT_PASSWORD ="12341234"
	
	static User getUser(b2bUnit, role) {
		for(User u in users) {
			if(u.groups.size() == (role.size()+1) && u.defaultB2BUnit.uid == b2bUnit && u.groups.containsAll(role)) {
				return u
			}
		}
		return null
	}
	
	static User getInvalidUser() {
		new User(email:'username1', password:'password')
	}
	
	static User getUpdatePasswordUser(){
		new User(email: 'update@password-1', password:'Levis2015#')
	}
	
	static User getTermsAndConditionUser(){
		new User(email: 'term@condition-1', password:'Levis2015#')
	} 
	
	static User getBlcokedUserWithCode01(){
		new User (email:'super@unit-4', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCode02(){
		new User (email:'super@unit-5', password:'Levis2015#')
	}

	static User getBlcokedUserWithCode03(){
		new User (email:'super@unit-6', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCode99(){
		new User (email:'super@unit-7', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCodeZ1(){
		new User (email:'super@unit-8', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCodeZ2(){
		new User (email:'super@unit-9', password:'Levis2015#')
	}

	static User getBlcokedUserWithCodeZ3(){
		new User (email:'super@unit-11', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCodeZ4(){
		new User (email:'super@unit-12', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCodeZ5(){
		new User (email:'super@unit-13', password:'Levis2015#')
	}
	
	static User getBlcokedUserWithCodeZ6(){
		new User (email:'super@unit-14', password:'Levis2015#')
	}
	
	static User getCreditCardOnlyUser(){
		new User (email:'super@unit-15', password:'Levis2015#')
	}
	
	static User getDefaultCreditCardUser(){
		new User (email:'super@unit-10', password:'Levis2015#')
	}
	
	static User getDefaultCreditCardAndBlockedUser(){
		new User (email:'super@unit-16', password:'Levis2015#')
	}
	
	static User getTessiUser(){
		new User (email:'tessi.user@test.com', password:'Levis2015#')
	}
	
	static List<Address> getShippingAddress(User user) {
		List<Address> addressList = new ArrayList<Address>()
		
		def jsonData = new JsonSlurper().parseText(new File(DATA_ADDRESS).text)

		jsonData.get(user.defaultB2BUnit.uid).shipping.each {
			Address address = new Address()
			address.country = it.country
			address.region = it.region
			address.town = it.town
			address.postalcode = it.postalcode
			address.streetname = it.streetname
			address.firstname = it.firstname
			address.lastname = it.lastname
			
			addressList.add(address)
		}
		
		addressList
	}
	
	static List<Address> getBillingAddress(User user) {
		List<Address> addressList = new ArrayList<Address>()
		
		def jsonData = new JsonSlurper().parseText(new File(DATA_ADDRESS).text)

		jsonData.get(user.defaultB2BUnit.uid).billing.each {
			Address address = new Address()
			address.country = it.country
			address.region = it.region
			address.town = it.town
			address.postalcode = it.postalcode
			address.streetname = it.streetname
			address.firstname = it.firstname
			address.lastname = it.lastname
			
			addressList.add(address)
		}
		
		addressList
	}
	
	/* Private */
	
	private static User[] users
	private static Map<String,B2BUnit> b2bUnits;

	static{
		b2bUnits = readB2BUnits(DATA_B2BUNIT)
		users = readUsers(DATA_USER)
	}
	
	private static List<User> readUsers(String path){
		List<User> userList = new ArrayList<User>()
		Map<String, Map> jsonData = (Map<String, Map>) new JsonSlurper().parseText(new File(path).text)
		jsonData.values().each {
			User user = new User()
			user.setEmail(it.email)
			user.setPassword(it.password)
			user.setName(it.name)
			user.setSurname(it.surname)
			user.setCompanyname(it.companyname)
			user.setCustomernumber(it.customernumber)
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
	
}
