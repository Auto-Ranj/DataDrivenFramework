package com.myhome.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.myhome.base.TestBase;
import com.myhome.utilities.TestUtil;

public class OpenAccountTest extends TestBase{

	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data) throws InterruptedException {
		
		//runmodes - Y= Yes //works only inside test method not working in listener onstart method
				if(!TestUtil.isTestRunnable("openAccountTest", excel)) {
					
					throw new SkipException("Skipping the test = "+"openAccountTest".toUpperCase()+" as the Run mode is N");
				}
		
		click("OpenAccount_XPATH");
		select("CustName_CSS", data.get("Customername"));
		select("Currency_CSS", data.get("Customerccy"));
		click("proceedBtn_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue((alert.getText().contains(data.get("AlertText"))));
		//Thread.sleep(3000);
		alert.accept();
	}
	
	
}
