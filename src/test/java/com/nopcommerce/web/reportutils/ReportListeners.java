package com.nopcommerce.web.reportutils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.nopcommerce.web.baseutils.ExtentReporter;
import com.nopcommerce.web.driverbase.BrowserBase;

public class ReportListeners implements ITestListener {

	private ExtentReports extentReport;
	private ExtentTest extentTest;
	private BrowserBase browserBase;
	WebDriver driver;

	@Override
	public void onStart(ITestContext context) {
		try {
			browserBase = new BrowserBase();
			extentReport = ExtentReporter.generateExtentReport();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getName();
		extentTest = extentReport.createTest(testName);
		extentTest.log(Status.INFO, testName + " started executing");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		try {
		
		String testName = result.getName();
		extentTest.log(Status.PASS, MarkupHelper.createLabel(testName + " - Test Case Passed", ExtentColor.GREEN));
		
		  driver = browserBase.driver;
	        String screenshotPath = captureScreenshot(testName);
	        extentTest.pass("Screenshot",
					MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
	
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
		String testName = result.getName();
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		extentTest.log(Status.FAIL, result.getThrowable());

		// Capture screenshot and attach to report
		driver = browserBase.driver;
		String screenshotPath;
		
			screenshotPath = captureScreenshot(testName);
			extentTest.fail("Screenshot",
					MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "/ExtentReports/extentReport.html";
		File extentReportFile = new File(pathOfExtentReport);

//		try {
//			Desktop.getDesktop().browse(extentReportFile.toURI());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public String captureScreenshot(String testName) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(source));
		String screenshot = new String(encoded, StandardCharsets.US_ASCII);
		//String destination = System.getProperty("user.dir") + "/Screenshots/" + testName + ".png";
		//File destinationFile = new File(destination);
		return screenshot;


	}

}

