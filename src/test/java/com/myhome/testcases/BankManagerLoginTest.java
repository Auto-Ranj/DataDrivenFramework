package com.myhome.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.myhome.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
		//++++++++to handle multiple failures in a test === it will fail here itself below steps in this test is not executed and it will go to next test case they it is not able to find elements so it throws an exception
		//Assert.assertEquals("abc", "xyz");
		
		//instaed of above if u do try catch then after assertion is not printed.... inside catch is printed....and all below steps is executed
		/*
		try {
			
			Assert.assertEquals("abc", "xyz");
			System.out.println("after assertion");
			
		}catch(Throwable t)
		{
			System.out.println("Inside catch");
		}
		*/
		
		//is like soft assertion only - instead of above try catch you can create method like this, same only but the failure is reported and further steps also executed
		verifyEquals("abc", "xyz");
		
		///+++++++++++++++All commented lines are implemented in CustomListeners class++++++++++++++++++////
		//++++++++++++to print screenshotlink in ReportNg this statement shud be used
		//System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		//++++++++++++this log.debug is for application(userdefined)/selenium logs log 4j.properties
		log.debug("Inside Login as Bank Manager Test method");
		
		//driver.findElement(By.cssSelector(or.getProperty("bmlBtn_CSS"))).click();
		
		//++++++++++++instead of above find element code u can use below click method serves the same purpose
		click("bmlBtn_CSS");
		
		
		//++++++++++++instead of log.debug, if below is used then u can see this msg in report
		//Reporter.log("Login Successfully executed!!");
		
		//+++++++++++++you can also add screenshot to the report,,,target to view the image in new tab,,,last reporter is to print screenshot itself
		//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\ranji\\Favorites\\image\\errorr.jpg\">ScreenshotLink</a>");
		//Reporter.log("<br></br>");
		//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\ranji\\Favorites\\image\\errorr.jpg\"><img src=\"C:\\Users\\ranji\\Favorites\\image\\errorr.jpg\" height=300 weight=300></img></a>");
		
		//++++++++++++Putting Validation
		Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCustBtn_CSS"))), "Login not successfull");
		log.debug("Login Successfully executed!!");
		
		//Assert.fail("Login not successful");//to forcefully failing, just to check functionality
		
	}
	
	
}
