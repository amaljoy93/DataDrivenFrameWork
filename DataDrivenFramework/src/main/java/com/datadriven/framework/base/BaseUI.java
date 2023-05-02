package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.DateUtil;
import com.datadriven.framework.utils.DateUtils;
import com.datadriven.framework.utils.ExtentReportManager;

public class BaseUI {
	
	
	
	public WebDriver driver;
	public Properties prop;
	
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	
	SoftAssert softAssert = new SoftAssert();
	
	/************Invoke the browser*******************/
	
	
    
	public void invokeBrowser(String browserName) {
	try {
		if(browserName.equalsIgnoreCase("Chrome")) {
			
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//drivers//chromedriver.exe");
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(co);
			} else if(browserName.equalsIgnoreCase("Mozila")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//test//resources//drivers//geckodriver.exe");
			driver = new FirefoxDriver();
		    }
		
	} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
		
		//System.out.println(System.getProperty("user.dir"));
		if(prop==null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir") +"//src//test//resources//ObjectRepository//projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				reportFail(e.getMessage());
				e.printStackTrace();
			}
			
		}
		
	}
	/********Open the Website URL****************/
	public void openURL(String websiteURLKey) {
		try {
			driver.get(prop.getProperty(websiteURLKey));
			reportPass(websiteURLKey +"Identified Sucessfully");
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(e.getMessage());
		}
		
		
	}
	
	/*******To close the Browser Instance****************/
	public void tearDown() {
		driver.close();
		
	}
	
	//To Quit the Browser Instance
	public void quitBrowser() {
		driver.quit();
		
	}
	
	
	//Enter the Text in Text Fields 
	public void enterText(String xpathKey, String data) {
		try {
		getElement(xpathKey).sendKeys(data);
		reportPass(data +"- Entered successfully in locator Element :"+ xpathKey);
		//driver.findElement(By.xpath(prop.getProperty(xpathKey))).sendKeys(data);
		} catch (Exception e) {
			reportFail(e.getMessage());
			
		}
		
	}
	 
	//To Click the Element 
	 public void elementClick(String xpathKey) {
		 try {
		 //driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();
		 getElement(xpathKey).click();
		 reportPass(xpathKey+ ": Element Clicked Sucessfully");
		 }catch(Exception e) {
			reportFail(e.getMessage());
		 }
		 
	 }
	 
	 // To Identify the page WebElement 
	 public WebElement getElement(String locatorKey) {
		 WebElement element = null;
		 
		 
		 try {
		  if(locatorKey.endsWith("_Id")) {
			element = driver.findElement(By.id(prop.getProperty(locatorKey)))  ;
			logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  } else if(locatorKey.endsWith("_Xpath")) {
			  element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			  logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  }else if(locatorKey.endsWith("_CSS")) {
			  element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
			  logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  }else if(locatorKey.endsWith("_LinkText")) {
			  element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
			  logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  }
		  else if(locatorKey.endsWith("_PartialLinkText")) {
			  element = driver.findElement(By.partialLinkText(prop.getProperty(locatorKey)));
			  logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  }else if(locatorKey.endsWith("_Name")) {
			  element = driver.findElement(By.name(prop.getProperty(locatorKey))); 
			  logger.log(Status.INFO,"Locator Identified :"+ locatorKey );
		  }else {
			  reportFail("Failing the Testcase, Invalid Locator"+ locatorKey);
			  
		  }
		  
		  
		 }catch (Exception e){
			 
			 //Fail the TestCase and Report the error
			 reportFail(e.getMessage());
			 e.printStackTrace();
			 
			
			 
			 
		 }
		 
	
	 return element;
	 
	 }
	 
	 /********************Verify Element***********************/
	 public boolean isElementPresent(String locatorKey) {
		 try {
			 if(getElement(locatorKey).isDisplayed()) {
			 reportPass(locatorKey+ "Element is Displayed");
			 return true;
			 }
		 }catch(Exception e) {
			 reportFail(e.getMessage());
		 }
		 return false;
	 }
	 
	 
	 public boolean isElementSelected(String locatorKey) {
		 try {
			 if(getElement(locatorKey).isSelected()) {
			 reportPass(locatorKey+ "Element is Selected");
			 return true;
			 }
		 }catch(Exception e) {
			 reportFail(e.getMessage());
		 }
		 return false;
	 }
		 
		 public boolean isElementEnabled(String locatorKey) {
			 try {
				 if(getElement(locatorKey).isEnabled()) {
				 reportPass(locatorKey+ "Element is Enabled");
				 return true;
				 }
			 }catch(Exception e) {
				 reportFail(e.getMessage());
			 }
			 return false;
		 }
	 
		// Explore All Products
		 public void verifyPageTitle(String pageTitle) {
			try {
				String actualTitle = driver.getTitle();
				logger.log(Status.INFO, "Actual Tittle is : " + actualTitle);
				logger.log(Status.INFO, "Expected Title is : "+ pageTitle);
				Assert.assertEquals(actualTitle, pageTitle);
			}catch(Exception e){
				reportFail(e.getMessage());
			}
			 
		 }
		 
		 
	 
	 /*********Assertion Functions*****************/
	 
	 public  void assertTrue(boolean flag) {
		 
			 softAssert.assertTrue(flag);
	 }
	 public  void assertfalse(boolean flag) {
		 
		 softAssert.assertFalse(flag);
 }
	 
	 public void  assertEquals(String actual, String expected) {
		 
		softAssert.assertEquals(actual,expected);
	 }
	 
	 
	 /*********************Reporting Functions******************/
	 

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
		
		
	}
	
	public void reportPass(String reportString) {
		
		logger.log(Status.PASS, reportString);
		
	}
	
	@AfterMethod
	public void afterTest(){
		softAssert.assertAll();
		
	}

	/*****************Capture Screen shot ******************/
		public void takeScreenShotOnFailure() {
			TakesScreenshot takeScreenShot= (TakesScreenshot) driver;
			File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
			
			File destFile = new File(System.getProperty("user.dir")+"/ScreenShots/"+DateUtils.getTimeStamp()+".png");
			try {
				FileUtils.copyFile(sourceFile, destFile);
				logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"/ScreenShots/"+DateUtils.getTimeStamp()+".png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	 }
	 