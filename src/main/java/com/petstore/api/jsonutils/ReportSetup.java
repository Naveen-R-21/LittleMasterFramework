package com.petstore.api.jsonutils;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.nopcommerce.web.baseutils.ExtentReporter;
import com.petstore.api.jsonutils.ExtentReportManager;

public class ReportSetup implements ITestListener {
	
	
	 public static ExtentReports extentReports;
	 public static ThreadLocal  <ExtentTest> extentTest = new ThreadLocal<>();
	
	
	 public void onStart(ITestResult result) {
		 
		 String fileName = ExtentReportManager.getReportNameWithTimeStamp();
		 
		 String reportFilePath = System.getProperty("user.dir") + "/Reports/" + fileName ;
		 
		 extentReports = ExtentReportManager.createInstance(fileName, "Petstore API Report", "API Automation");
		   
		  }
	 
	 public void onFinish(ITestResult result) {
		  
		 if (extentReports != null)

			 extentReports.flush();
	  }
	 
	 public void onTestStart(ITestResult result) {
		 
		 
		ExtentTest test = extentReports.createTest("Test Name" + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		 
		 extentTest.set(test);
			
		 
	 }

}
