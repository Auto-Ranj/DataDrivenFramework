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

public class AddCustomerTest extends TestBase{

	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		
		
		if(!data.get("runmode").equals("Y")) { //runmode is the column name
			
			//will not execute the 2nd row data as its run mode is N
			throw new SkipException("Skipping the test case as the run mode for data set is No");
		}
		
		//driver.findElement(By.cssSelector(or.getProperty("addCustBtn_CSS"))).click();
		//driver.findElement(By.cssSelector(or.getProperty("firstName_CSS"))).sendKeys(firstName);
		//driver.findElement(By.cssSelector(or.getProperty("lastName_CSS"))).sendKeys(lastName);
		//driver.findElement(By.cssSelector(or.getProperty("postCode_CSS"))).sendKeys(postCode);
		//driver.findElement(By.xpath(or.getProperty("addCust_XPATH"))).click();
		
		//above code can be replaced with the below 
		click("addCustBtn_CSS");
		type("firstName_CSS", data.get("firstname"));
		type("lastName_CSS", data.get("lastname"));
		type("postCode_CSS", data.get("postcode"));
		click("addCust_CSS");
		
		//log.debug("Customer added successfully");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue((alert.getText().contains(data.get("alertText"))));
		//Thread.sleep(3000);
		alert.accept();
		//log.debug("Accepted Alert box");
		
		//Assert.fail("Customer not added successfully");//forcefully failing
		
		
	}
	
	/*///no. of parameter = no of columns in the excel, so instead of using all columns as arguments u can use hashtable see above code
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alertText, String runmode) throws InterruptedException {
		
		
		if(!runmode.equals("Y")) {
			
			//will not execute the 2nd row data.
			throw new SkipException("Skipping the test case as the run mode for data set is No");
		}
		
		//driver.findElement(By.cssSelector(or.getProperty("addCustBtn_CSS"))).click();
		//driver.findElement(By.cssSelector(or.getProperty("firstName_CSS"))).sendKeys(firstName);
		//driver.findElement(By.cssSelector(or.getProperty("lastName_CSS"))).sendKeys(lastName);
		//driver.findElement(By.cssSelector(or.getProperty("postCode_CSS"))).sendKeys(postCode);
		//driver.findElement(By.xpath(or.getProperty("addCust_XPATH"))).click();
		
		//above code can be replaced with the below 
		click("addCustBtn_CSS");
		type("firstName_CSS", firstName);
		type("lastName_CSS", lastName);
		type("postCode_CSS", postCode);
		click("addCust_XPATH");
		
		//log.debug("Customer added successfully");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue((alert.getText().contains(alertText)));
		//Thread.sleep(3000);
		alert.accept();
		//log.debug("Accepted Alert box");
		
		//Assert.fail("Customer not added successfully");//forcefully failing
		
		
	}
	*/
	
	/*   //implemented in utility class
	@DataProvider
	public Object[][] getData()
	{
		String sheetName = "AddCustomerTest";
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][cols];
		
		for(int rowNum = 2; rowNum<=rows; rowNum++) {
			
			for(int colNum=0; colNum<cols; colNum++) {
				
				//data[0][0]
				data[rowNum-2][colNum]=excel.getCellData(sheetName, colNum, rowNum);
			}
		}
			
		
		return data;
		
	} 
	*/
	
}
