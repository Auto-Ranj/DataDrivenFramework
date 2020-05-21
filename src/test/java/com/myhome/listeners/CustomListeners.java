package com.myhome.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.myhome.base.TestBase;
import com.myhome.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {
		
		//for extent reports, before the test starts we need to tell the report to start the test case
		test = rep.startTest(result.getName().toUpperCase());
		
		
		
	}

	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS!"); //getName()-gives particular test name		
		rep.endTest(test); //once one particular test is executed you need to end that report as well		
		rep.flush(); //flush is mandatory otherwise report is not gonna print
		
	}

	public void onTestFailure(ITestResult result) {
		
		//ReportNG report
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Reporter.log("Click to see screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+">ScreenshotLink</a>");
		Reporter.log("<br></br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenShotName+"><img src="+TestUtil.screenShotName+" height=200 weight=200></img></a>");
		
		//Extent Reports
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception = "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenShotName)); //to print screenshot on failure
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult result) {
		
		//if this is not done then that run mode is not gonna execute as expected
		
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped the test as the Run Mode was N");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
