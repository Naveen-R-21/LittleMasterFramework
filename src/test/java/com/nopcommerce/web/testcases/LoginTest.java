package com.nopcommerce.web.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.nopcommerce.web.driverbase.BrowserBase;
import com.nopcommerce.web.pages.LoginPage;

public class LoginTest extends BrowserBase {

	private LoginPage loginPage;

	@BeforeClass
	public void setUpRegisterPage(ITestContext context) throws IOException {
		loginPage = new LoginPage(getDriver(context));
		System.out.println("Properties loaded in testcase file");
	}

	@Test(priority = 1, groups="Sanity")
	public void verifyLoginWithoutCredentials() {

		loginPage.clickingLoginWithoutCredentials() ;

	}

	@Test(priority = 2)
	public void verifyLoginWithoutPassword() {

		loginPage.clickingLoginWithoutPassword();
		Assert.fail();

	}

	@Test(priority = 3)
	public void verifyLogin() {

		loginPage.enteringValidLoginCredentials();


	}

}

