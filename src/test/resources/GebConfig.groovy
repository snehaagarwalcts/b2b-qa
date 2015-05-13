package test.resources

import geb.driver.SauceLabsDriverFactory
import geb.report.PageSourceReporter
import geb.report.ScreenshotReporter
import lscob2b.util.geb.DummyReporter

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.safari.SafariDriver


def sysProps = System.getProperties()

/**
 * Env Configuration
 */
def platform = env.get "SELENIUM_PLATFORM"
def version = env.get "SELENIUM_VERSION"
def browserName = env.get "SELENIUM_BROWSER"

//Extra check for profile management (see pom.xml)
if(platform == null || platform == "") {
	platform = sysProps.get "geb.platform"
}
if(version == null || version == "") {
	version = sysProps.get "geb.version"
}
if(browserName == null || browserName == "") {
	browserName = sysProps.get "geb.browser"
}


/**
 * Target Configuration
 */

/* Default Target URL */
baseUrl = "http://b2bqa-001-web-000.lsco-b2b.com/"

/* HAC Configuration (required for impex runtime loading) */
hacUrl = "http://b2bqa-001-cron-000.lsco-b2b.com:9001/hac/"
hacUsername = "admin"
hacPassword = "nimda"

// when system property 'geb.env' is set to 'integration000'
environments {
	preprod {
		//baseUrl = "https://b2bpreprod-000-web-000.lsco-b2b.com/"
		baseUrl = "http://preprod-origin.lsco-b2b.com/en/"
		hacUrl = "http://b2bpreprod-000-cron-000.lsco-b2b.com:9001/hac/"
		hacUsername = "admin"
		hacPassword = "nimda"
	}
	integration000 {
		baseUrl = "http://b2bint-000-store-000.lsco-b2b.com:9001/lscob2bstorefront/"
		hacUrl = "http://b2bint-000-store-000.lsco-b2b.com:9001/"
		hacUsername = "admin"
		hacPassword = "nimda"
	}
	integration001 {
		baseUrl = "http://b2bint-001-store-000.lsco-b2b.com:9001/lscob2bstorefront/"
		hacUrl = "http://b2bint-001-store-000.lsco-b2b.com:9001/"
		hacUsername = "admin"
		hacPassword = "nimda"
	}
	qa000 {
		baseUrl = "http://b2bqa-000-web-000.lsco-b2b.com/"
		hacUrl = "http://b2bqa-000-cron-000.lsco-b2b.com:9001/hac/"
		hacUsername = "admin"
		hacPassword = "nimda"
	}
	qa001 {
		baseUrl = "http://b2bqa-001-web-000.lsco-b2b.com/"
		hacUrl = "http://b2bqa-001-cron-000.lsco-b2b.com:9001/hac/"
		hacUsername = "admin"
		hacPassword = "nimda"
	}
}

/**
 * Browser Configuration
 */
def sauceBrowser = sysProps.get "geb.sauce.browser"
if (sauceBrowser && sauceBrowser != "false") {
	driver = {
		def env = System.getenv()
		def username = sysProps.get "geb.sauce.user"
		def accessKey = sysProps.get  "geb.sauce.access.key"
		def capabilities = [
			platform: platform,
			version: version,
			browserName: browserName,
			'max-duration': 7200, //2h
			name: "" + platform + "-" + browserName + "-" + version,
			"record-video": true,
			"video-upload-on-pass": false,
			"record-screenshots": false
		]
		new SauceLabsDriverFactory().create("", username, accessKey, capabilities)
	}
} else {

	//Create Driver
	if(browserName == "safari") {

		driver = { new SafariDriver() }

	} else if(browserName == "chrome") {
		//See https://sites.google.com/a/chromium.org/chromedriver/getting-started
		System.setProperty("webdriver.chrome.driver", "C:/Users/401685/WebDrivers/chromedriver.exe")
		driver = { new ChromeDriver() }

	} else if(browserName == "internet explorer") {

		System.setProperty("webdriver.ie.driver", "C:/Users/401685/WebDrivers/IEDriverServer.exe")
		driver = { new InternetExplorerDriver() }

	} else {

		driver = { new FirefoxDriver() }	//Default Driver!

	}

}

/**
 * CONFIGURATION
 */
autoClearCookies = true
//waiting {
//    presets {
//        slow {
//            timeout = 20
//            retryInterval = 1
//        }
//        quick {
//            timeout = 1
//        }
//    }
//}
waiting {
	timeout = 10
	retryInterval = 0.5
}


/**
 * REPORT
 */
def rep = sysProps.get "geb.reporter"
if(rep == "screenshot") {
	reporter = new ScreenshotReporter()
} else if(rep == "pageshot") {
	reporter = new PageSourceReporter()
} else {
	reporter = new DummyReporter() // Fastest Reporter :)
}
reportsDir = new File("target/geb-reports")
reportOnTestFailureOnly = true

