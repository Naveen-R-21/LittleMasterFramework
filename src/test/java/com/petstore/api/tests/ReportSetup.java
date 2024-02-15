package com.petstore.api.tests;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.petstore.api.jsonutils.ExtentReportManager;

public class ReportSetup implements ITestListener {
	
	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	static {
		String fileName = ExtentReportManager.getReportNameWithTimeStamp();
		String reportFilePath = System.getProperty("user.dir") + "/API-Reports/" + fileName;
		extentReports = ExtentReportManager.createInstance(reportFilePath, "Petstore API Report", "API Automation Report");
	}
	

	public void onFinish(ITestResult result) {
		if (extentReports != null) {
			extentReports.flush();
		}
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReports.createTest("Test Name: " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.set(test);
	}
}
