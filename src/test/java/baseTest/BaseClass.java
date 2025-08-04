package baseTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	 static public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
	    WebDriverManager.chromedriver().setup();

	    ChromeOptions options = new ChromeOptions();
		//options.addArguments("--incognito");
	    Map<String, Object> prefs = new HashMap<>();

	    // Auto-allow notifications
	    prefs.put("profile.default_content_setting_values.notifications", 1);  // 1 = Allow

	    options.setExperimentalOption("prefs", prefs);
	    options.addArguments("--disable-popup-blocking"); // Optional

	    driver = new ChromeDriver(options);
	    driver.manage().window().maximize();
	    driver.manage().deleteAllCookies();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    System.out.println("Chrome browser opened successfully.");
	    
	    driver.get("https://dev-dash.janitri.in/");
	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	    System.out.println("Navigated to URL:https://dev-dash.janitri.in/");
	    //System.out.println("Current URL:"+driver.getCurrentUrl()+" and Title:"+driver.getTitle());
		
	}

	
	@AfterMethod
	public void tearDown()
	{
		if(driver!=null) {
			driver.quit();
			System.out.println("Browser closed successfully.\n");
		}
		
	}
	
	public String getScreenshot(String tname) throws IOException {
		
		if (driver == null)
			System.out.println("driver is null");
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\Screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		//source.renameTo(targetFile);
		FileUtils.copyFile(source, targetFile);
		return targetFilePath;
			
			
	}
	
	
}

