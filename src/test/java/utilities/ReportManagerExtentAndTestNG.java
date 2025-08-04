package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportManagerExtentAndTestNG implements ITestListener{
	//Implementation in future for Report generation using the extent report
	public ExtentSparkReporter SparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	//generate report name based on time stamp
			String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			String repName="Test-Report"+timeStamp+".html";
			
	//Implementing TestNG Report
	int i=0;
	@Override
	public void onStart(ITestContext context) {
		Reporter.log("******* Test Case execution started ******",true);
		
		//generate report name based on time stamp
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName="Test-Report"+timeStamp+".html";
		
		SparkReporter=new ExtentSparkReporter("./Reports/"+repName);
				SparkReporter.config().setDocumentTitle("Janitri Test Report");
				SparkReporter.config().setTheme(Theme.DARK);
	
		extent=new ExtentReports();
		extent.attachReporter(SparkReporter);
		extent.setSystemInfo("Application:", "Janitri");
		extent.setSystemInfo("Module:", "Janitri-User Module");
		extent.setSystemInfo("User name:", System.getProperty("user.name"));
		extent.setSystemInfo("Environment:", "QA");
		
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		i++;
		Reporter.log("Test Case:"+i+".\n"+result.getMethod().getMethodName()+" started executing...",true);
		test = extent.createTest(result.getMethod().getMethodName());
		//test = extent.createTest(result.getTestClass().getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName()+" got passed.",true);
		
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName()+" got failed.",true);
		
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		String imgPath = null;
		try {
			imgPath = new baseTest.BaseClass().getScreenshot(result.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.addScreenCaptureFromPath(imgPath);
		Reporter.log("--Taken screenshot:"+result.getName(),true);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName()+" got skipped.",true);
		
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
	}

	

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		Reporter.log("******* Test Case execution finished ******",true);
		
		/*//To open report on desktop..
				String pathOfExtentReport = System.getProperty("user.dir")+"\\Reports\\"+repName;
				File extentReport = new File(pathOfExtentReport);
				
				try {
					Desktop.getDesktop().browse(extentReport.toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}*/
	}

	
	

}