package baseTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
	    WebDriverManager.chromedriver().setup();

	    ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
	    Map<String, Object> prefs = new HashMap<>();

	    // Auto-allow notifications
	    prefs.put("profile.default_content_setting_values.notifications", 1);  // 1 = Allow

	    options.setExperimentalOption("prefs", prefs);
	    options.addArguments("--disable-popup-blocking"); // Optional

	    driver = new ChromeDriver(options);
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	    driver.get("https://dev-dash.janitri.in/");
	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}

	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	
}
