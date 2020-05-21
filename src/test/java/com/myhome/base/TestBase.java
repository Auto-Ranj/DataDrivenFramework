package com.myhome.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.myhome.utilities.ExcelReader;
import com.myhome.utilities.ExtentManager;
import com.myhome.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	/*////
	 * Webdriver
	 * loading properties files
	 * logs-log4j.properties, logger class
	 * default testng report
	 * reportNG
	 * extent reports
	 * excel read write using data provider
	 * capturing screenshot
	 * Listeners
	 * Running as Maven instead of running on testng.xml as testng suite
	 * Handling multiple failures, hard assertion(how we implement soft assertion using hard itself)
	 * setting up run modes for test suite(for test methods)(using excel)
	 * setting up run modes for test data (using excel)
	 * implementing HashTable for data provider
	 */

	public static WebDriver driver;

	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;

	public static Logger log = Logger.getLogger("devpinoyLogger");

	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");

	public static WebDriverWait wait;

	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			// Load Config properties
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Load OR properties
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				or.load(fis);
				log.debug("OR file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (config.getProperty("browser").equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome Launched");
		} else if (config.getProperty("browser").equals("firefox")) {
			driver = new FirefoxDriver();
			log.debug("Firefox Launched");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitWait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	// to verify whether an element present or not as no ready made method is not
	// availiable
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {

			return false;
		}
	}

	public void click(String locator) {

		if (locator.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(or.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(or.getProperty(locator))).click();
		}

		test.log(LogStatus.INFO, "Clicking on = " + locator);

	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		}

		if (locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO, "Typing in = " + locator + " and the value entered is = " + value);
	}

	public static WebElement dropdown;
	
	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {

			dropdown = driver.findElement(By.cssSelector(or.getProperty(locator)));
		}

		if (locator.endsWith("_XPATH")) {

			dropdown = driver.findElement(By.xpath(or.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from the dropdown = " + locator + " and the value selected is = " + value);
	}
	
	public static void verifyEquals(String actual, String expected) throws IOException {
		
		try {

			Assert.assertEquals("abc", "xyz");

		} catch (Throwable t) {
			
			TestUtil.captureScreenshot();
			
			//ReportNG
			Reporter.log("<br>"+"Verification failure = "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+"><img src="+TestUtil.screenShotName+" height=200 weight=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//Extent Reports
			test.log(LogStatus.FAIL, "Verification failed with exception = "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName)); //to print screenshot on failure
			
		}
	}

	@AfterSuite
	public void teardown() {

		// it will execute only if the session is active
		if (driver != null) {
			driver.quit();

		}
		log.debug("Executed tearDown method");

	}

}
