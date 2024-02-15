package com.nopcommerce.web.testcases;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import com.nopcommerce.web.driverbase.BrowserBase;
import com.nopcommerce.web.pages.RegisterPage;

public class RegisterTest extends BrowserBase {
	private RegisterPage registerPage;

	@BeforeClass
	public void setUpRegisterPage(ITestContext context) throws IOException {
		Faker faker = new Faker();
		registerPage = new RegisterPage(getDriver(context), faker);
	}
	@Test(priority = 1)
	public void registerWithoutUserData() {

		registerPage.registerUserWithoutData() ;

	}

	@Test(priority = 2)
	public void registerNewUser() throws Exception {

		registerPage.registerNewUser() ;

	}

}


