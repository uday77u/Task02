/*
 * Methods:
testLoginButtonDisabledWhenFieldAreEmpty()
testPasswordMaskedbutton()
testInvalidLoginShowErrorMsg()

 * Test Scenarios to Automate (without valid credentials):
Attempt login with blank fields and verify UI behavior
Enter any random credentials and click login – capture and print any error message shown
Validate password masking/unmasking toggle
Validate presence of page elements (e.g., title, input fields, eye icon)
 */

package testCases;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseTest.BaseClass;
import baseTest.GeneralScript;
import pageObjects.LoginPage;

public class TC001_LoginAccount extends BaseClass {

//-------------------------Attempt login with blank fields and verify UI behavior-----------------------------------------	
/* ---Depend on Requirements,decide  which case should be consider--- 
case1: Login button is disable for empty credentials.
case2: Login button is enable for empty credentials -check error message.*/
	
	//cas1:
	@Test(priority = 1 )
	public void testLoginButtonDisabledWhenFieldAreEmptyCase1() {
		LoginPage Login=new LoginPage(driver);
		Login.setUserIDInput("");
		Login.setPasswordInput("");
		assertFalse(Login.LoginButtonEnabled(), "Login credentials are empty, still Login button is enabled");
		Reporter.log("Login button is correctly disabled when credentials are empty", true);

	}
	
	//case2:
	@Test(priority = 2 )
	public void testErrorMessageWhenFieldAreEmptyAndLoginButtonEnabledCase2() throws InterruptedException {
		LoginPage Login=new LoginPage(driver);
		Login.setUserIDInput("");
		Login.setPasswordInput("");
		Login.clickLoginButton();
		assertTrue(Login.displayedErrorMessageInvalidCredentials(),"Error Message-'InvalidCredentials' is not displayed");
		Reporter.log("Error Message-'InvalidCredentials' is displayed", true);
	}

	
//------------------------------------	Enter any random credentials and click login – capture and print any error message shown---------------------------
	@Test(priority = 3)
	public void testInvalidLoginShowErrorMsg()  {
		
		GeneralScript gs=new GeneralScript();    //Used to get the random data for credential fields.
		String email = gs.getRandomEmail();
		String password = gs.getRandomPassword();
		Reporter.log("Attempting login with Email: " + email + ", Password: " + password,true);

		LoginPage Login=new LoginPage(driver);
		Login.setUserIDInput(gs.getRandomEmail());
		Login.setPasswordInput(gs.getRandomPassword());
		Login.clickLoginButton();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(Login.getErrorElement()));

		assertTrue(Login.displayedErrorMessageInvalidCredentials(),"Error Message-'InvalidCredentials' is not displayed");
		String errorMessage=Login.getErrorMessageInvalidCredentials();
		System.out.println("Captured error Message:"+errorMessage);
		Reporter.log("Error Message:"+errorMessage+"is displayed", true);
		
	}
	
	
	
	
//--------------------------------------------Validate password masking/unmasking toggle--------------------------------	
	@Test(priority = 4)
	public void testPasswordMaskedbutton() {
		GeneralScript gs=new GeneralScript();    //Used to get the random data for credential fields.
		LoginPage Login=new LoginPage(driver);
		Login.setPasswordInput(gs.getRandomPassword());
		
		SoftAssert sa=new SoftAssert();
		sa.assertFalse(Login.displayedPassword(),"Password is initially masked,but Password is unmasked (visible)");
		Reporter.log("Password is initially masked",true);
		
		Login.clickPasswordVisibilityToggle();
		sa.assertTrue(Login.displayedPassword(),"click on Togle,password should be unmasked,but Password is masked (visible)");
		Reporter.log("click on Toggle,password is unmasked",true);
		sa.assertAll();
		
	}
	
	
//-----------------------------------------------Validate presence of page elements (e.g., title, input fields, eye icon------------------------	
	@Test(priority = 5)
	public void testUIElementsPresence() {
		LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.areLoginElementsVisible(), "All login elements should be visible.");
        Reporter.log("All Login elements are visible: \nuserIDInput\npasswordInput\nLoginButton\nPasswordVisibilityToggle ",true);
	} 
	
}
