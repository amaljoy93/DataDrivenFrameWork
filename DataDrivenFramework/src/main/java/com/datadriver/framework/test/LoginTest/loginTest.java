package com.datadriver.framework.test.LoginTest;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.ExtentReportManager;
import com.datadriven.framework.utils.TestDataProvider;

public class loginTest extends BaseUI {
	
	
	@Test(dataProvider="getTestOneData")
	public void testOne(Hashtable<String,String> dataTable) {
		
		
		logger = report.createTest("Enter UserName And Password in Rediff");
		
		invokeBrowser("Chrome");
		openURL("websiteURL");
		elementClick("singinBtn_Xpath");
		enterText("usernameTextbox_Id", dataTable.get("Col1"));
		enterText("passwordTextbox_CSS", dataTable.get("Col4"));
		
	}
	@AfterTest
	public void endReport() {
		report.flush();
	}
	@DataProvider
	public Object[][] getTestOneData(){
		return TestDataProvider.getTestData("TestData_Testmangement.xlsx", "Feature1", "Test Three");
	}
	
	
	//@Test 
	public void testTwo() {
		logger = report.createTest("Open Rediff and Enter UserName ");
		invokeBrowser("Chrome");
		openURL("websiteURL");
		elementClick("singinBtn_Xpath");
		enterText("usernameTextbox_Xpath", "amallla");
		
		
		
	}
	//@Test(dependsOnMethods = "testTwo")
	public void testThree() {
		invokeBrowser("Chrome");
		openURL("websiteURL");
		elementClick("singinBtn_xpath");
		enterText("usernameTextbox_xpath", "amallla");
		tearDown();
		
	}
	
}
