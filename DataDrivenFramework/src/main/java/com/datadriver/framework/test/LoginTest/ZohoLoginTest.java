package com.datadriver.framework.test.LoginTest;

import org.testng.annotations.Test;

import com.datadriven.framework.base.BaseUI;

public class ZohoLoginTest extends BaseUI{

	
	@Test
	public void doZohoLoginTest() {
		
		logger = report.createTest("Zoho Login Test Case");
		invokeBrowser("Chrome");
		openURL("websiteURL");
		elementClick("zohoLoginTextBox_Xpath");
		enterText("zohoUserNameTextBox_Id","User_ID");
		enterText("zhPasswordTB_Id","Psd");
		elementClick("zhNext_Xpath");
		elementClick("zhSignBtn_Xpath");
		
		
	}
}
