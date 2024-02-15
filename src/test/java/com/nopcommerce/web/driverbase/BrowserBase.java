package com.nopcommerce.web.driverbase;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.yaml.snakeyaml.Yaml;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.nopcommerce.web.baseutils.ExcelUtilities;
import com.nopcommerce.web.baseutils.ExtentReporter;
import com.nopcommerce.web.reportutils.ReportListeners;

public class BrowserBase {

	ReportListeners ml = new ReportListeners();

	private ExtentReports extentReport;
	private ExtentTest extentTest;

	public static WebDriver driver;
	public static final long IMPLICIT_WAIT_TIME = 30;
	public static final long PAGE_LOAD_TIME = 30;
	private static String BROWSERSTACK_USERNAME ;
	private static String BROWSERSTACK_ACCESS_KEY ;
	private static final String CAPABILITIES_FILE_PATH = "src/test/resources/browserstack.yml";
	//public static final String URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

	public WebDriver getDriver(ITestContext context) throws IOException {
		if (driver == null) {
			initializeDriver(context);
		}
		return driver;
	}

	private void initializeDriver(ITestContext context) throws IOException {
		boolean executeInLocal = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("executeInLocal"));
		if (executeInLocal) {
			initializeLocalDriver();
		} else {
			initializeRemoteDriver();
		}
	}

	private void initializeLocalDriver() throws IOException {
		try {
			String browserName = ExcelUtilities.browserDataFromExcel("Browser", 1, 0);
			System.out.println("Executing Tests in " + browserName + " Browser");

			switch (browserName.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "safari":
				driver = new SafariDriver();
				break;
			default:
				throw new IllegalArgumentException("Invalid browser name: " + browserName);
			}
			getURL();
			System.out.println("Fetched the URL");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize browser and open URL: " + e.getMessage());
		}
	}


	private void initializeRemoteDriver() throws MalformedURLException {
	    try {
	        loadConfig(); // Load username and access key from YAML
	        String URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
	        Map<String, Object> config = loadConfig();
	        DesiredCapabilities capabilities = createCapabilities(config);
	        driver = new RemoteWebDriver(new URL(URL), capabilities);
	        getURL();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to initialize browser and open URL: " + e.getMessage());
	    }
	}



	private static Map<String, Object> loadConfig() throws IOException {
	    Yaml yaml = new Yaml();
	    try (InputStream inputStream = new FileInputStream(CAPABILITIES_FILE_PATH)) {
	        Map<String, Object> config = yaml.load(inputStream);
	        Map<String, Object> credentials = (Map<String, Object>) config.get("credentials");
	        
	        String browserstackUsername = (String) credentials.get("userName");
	        String browserstackAccessKey = (String) credentials.get("accessKey");
	        
	        BROWSERSTACK_USERNAME = browserstackUsername;
	        BROWSERSTACK_ACCESS_KEY = browserstackAccessKey;
	        return config;
	    }
	}


	private DesiredCapabilities createCapabilities(Map<String, Object> platformConfig) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		
		capabilities.setCapability("os", platformConfig.get("platformName"));
		capabilities.setCapability("osVersion", platformConfig.get("osVersion"));
		capabilities.setCapability("browserName", platformConfig.get("browserName"));
		capabilities.setCapability("browserVersion", platformConfig.get("browserVersion"));
		
		
		browserstackOptions.put("buildName", platformConfig.get("buildName"));
		browserstackOptions.put("projectName", platformConfig.get("projectName"));
		browserstackOptions.put("buildTag", platformConfig.get("buildTag"));
		
		capabilities.setCapability("bstack:options", browserstackOptions);

		return capabilities;
	}

	public void getURL() {
		try {
			String url = ExcelUtilities.browserDataFromExcel("browser", 1, 1);
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME));
			System.out.println("Fetched the URL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}

