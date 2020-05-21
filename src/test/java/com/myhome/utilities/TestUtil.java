package com.myhome.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.myhome.base.TestBase;

public class TestUtil extends TestBase{

	public static String screenShotPath;
	public static String screenShotName;
	
	public static void captureScreenshot() throws IOException {
		
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		//screenShotName="error.jpg";
		//implemented because to have the different files for 2 failures.....if 2 fails were there then the error.jpg was overriden(only last ss was displayed) so...
		
		Date d = new Date();
		screenShotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenShotName));
	}
	
	//here Hashtable is created for each row of data, column names are the key, and cellvalues are values===> table is created with 1st row as keys and 2nd as one row of data.
	@DataProvider(name="dp")
	public Object[][] getData(Method m)
	{
		//String sheetName = "AddCustomerTest";
		//instead of hard coding the sheet name, use the below line
		
		String sheetName = m.getName(); //method m gets u the method name
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][1];//1 because only one argument that is hasttable
		
		Hashtable<String, String> table = null;
		
		for(int rowNum = 2; rowNum<=rows; rowNum++) {
			
			table = new Hashtable<String, String>();
			
			for(int colNum=0; colNum<cols; colNum++) {
				
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				//data[0][0] //column is always 0 because used hastable and it has only 1 argument so
				data[rowNum-2][0]= table;
			}
		}
		
		return data;
		
	}
	
	/*// Hashtable is implemented in above same method
	@DataProvider(name="dp")
	public Object[][] getData(Method m)
	{
		//String sheetName = "AddCustomerTest";
		//instead of hard coding the sheet name, use the below line
		
		String sheetName = m.getName(); //method m gets u the method name
		
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
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		
		String sheetName = "test_suite";
		
		int rows = excel.getRowCount(sheetName);
		
		System.out.println("NO OF ROWS IS "+rows);
		
		for(int rNum=2; rNum<=rows; rNum++) {
			
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			
			if(testCase.equalsIgnoreCase(testName)) {
				
				String runMode = excel.getCellData(sheetName, "Runmode", rNum);
				
				if(runMode.equalsIgnoreCase("Y")) {
					
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		
		return false;
		
	}
}
