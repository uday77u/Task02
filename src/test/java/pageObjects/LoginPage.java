/*
 * Page Object Class (LoginPage.java) should include:
Locators for:
User ID input
Password input
Login button
Password visibility toggle (eye icon)
Methods:
testLoginButtonDisabledWhenFieldAreEmpty()
testPasswordMaskedbutton()
testInvalidLoginShowErrorMsg()
 */

package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage  extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
//----------------------------------------Locators--------------------------------------------------------------
	@FindBy(css = ("input[name='email']"))
	private WebElement userIDInput; 
	
	@FindBy(css = ("input[name='password']"))
	private WebElement passwordInput; 
	
	@FindBy(css = ("button[type='submit']"))
	private WebElement LoginButton; 
	
	@FindBy(css = ("img[class='passowrd-visible']"))
	private WebElement PasswordVisibilityToggle; 
	
	@FindBy(xpath = "//p[text()='Invalid Credentials']")
	private WebElement errorMessageInvalidCredentials;
	
	public WebElement getErrorElement() {
		return errorMessageInvalidCredentials;
	}
//----------------------------------------Methods---------------------------------------------------------------
	
	
	public void setUserIDInput(String userID) {
		userIDInput.clear();
		userIDInput.sendKeys(userID);
	}
	
	public void setPasswordInput(String userID) {
		passwordInput.clear();
		passwordInput.sendKeys(userID);
	}
	
	public void clickLoginButton() {
		//--In case of not able to click on the button solutions--
		
		//soln1
		//LoginButton.click();
		
		//slon2
		//LoginButton.submit();
		
		//soln3
		//Actions act=new Actions(driver);
		//act.click(LoginButton).perform();
		
		//soln4
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeAsyncScript("arguments[0].click();", LoginButton);
	}
	
	public boolean LoginButtonEnabled() {
		return LoginButton.isEnabled();
	}

	public boolean displayedErrorMessageInvalidCredentials() {
		return errorMessageInvalidCredentials.isDisplayed();
	}
	
	public String getErrorMessageInvalidCredentials() {
		return errorMessageInvalidCredentials.getText();
	}
	
	public void clickPasswordVisibilityToggle() {
		PasswordVisibilityToggle.click();
	}
	
	public boolean displayedPassword() {
		return passwordInput.getAttribute("type").equalsIgnoreCase("text");
		
	}
	public boolean areLoginElementsVisible() {
        return userIDInput.isDisplayed()
                && passwordInput.isDisplayed()
                && LoginButton.isDisplayed()
                && PasswordVisibilityToggle.isDisplayed();
    }
}
