package com.etouch.tivopoc.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.etouch.tivopoc.common.CommonPage;
import com.etouch.tivopoc.pages.test.TestTivo;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.core.resources.ObjectType;
import com.etouch.taf.core.resources.ObjectValType;
import com.etouch.taf.core.resources.WaitCondition;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.PropertyReader;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;
import com.etouch.taf.webui.selenium.webelement.*;

public class TivoPages extends CommonPage {
	
	/** The log. */
	static Log log = LogUtil.getLog(TivoPages.class);

	private static WebDriver driver;
	public Properties properties = null;
	final int MAX_WAIT = 30;
	int numOfShows = 0;

	String propFile = ".//src//main//resources//TivoProps.properties";

	public TivoPages(String sbPageUrl, WebPage TivowebPage)
			throws InterruptedException {
		super(sbPageUrl, TivowebPage);

		driver = webPage.getDriver();
		// Implicit wait
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		loadPage();
		Thread.sleep(5000);

		properties = new PropertyReader().getObjectRep(propFile);

	}

	public void signIn(String username, String pwd) throws PageException,
			InterruptedException {
		CommonUtil.sop("starting signIn");

		// provide username
		((TextBox) webPage.findObject(ObjectType.TextBox,
				properties.getProperty("username_ID"), ObjectValType.ID,
				MAX_WAIT, WaitCondition.VISIBLE)).enterText(username);
		Thread.sleep(1000);

		// provide password
		((TextBox) webPage.findObject(ObjectType.TextBox,
				properties.getProperty("password_ID"), ObjectValType.ID,
				MAX_WAIT, WaitCondition.VISIBLE)).enterText(pwd);
		Thread.sleep(1000);

		// click "Login"
		driver.findElement(By.xpath(properties.getProperty("login_XPATH"))).click();
		Thread.sleep(3000);
		
		//validate the title page
		/*String tabTitle = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("whatToWatch_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).getText();
		CommonUtil.sop("assert, the page title: " + tabTitle);		
		SoftAssertor.assertEquals(tabTitle,"What to Watch","signIn-AssertErrorMsg:");*/
	
	}

	// add video streaming to My Shows
	public void addAShow() throws PageException, InterruptedException, AWTException {
		CommonUtil.sop("starting addAShow");
		String errorMsg1 = "";
		//String errorMsg2 = "";
		boolean	videoAddedBtnTextMsg = false;
		
		// hover on 1st show in the list
		WebElement showElement = driver.findElement(By.xpath(properties.getProperty("show_XPATH")));
		Actions action = new Actions(driver);
		action.moveToElement(showElement).click().build().perform();
		Thread.sleep(3000);
				
		/*((Image) webPage.findObject(ObjectType.Image,
				properties.getProperty("1stShow_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.VISIBLE)).hover();
		Thread.sleep(3000);*/

		// click "Info" button
		// driver.findElement(By.xpath(properties.getProperty("infoBtn_XPATH"))).click();
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("infoBtn_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(3000);
			
		
		String showTitleText = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("ShowTitle_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).getText();
		CommonUtil.sop("selected show title: "+showTitleText);
		
		
		try{
		// click "Add this streaming video to my shows"
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("addThisStreamingVideo_XPATH"), 
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(3000);

		// validate the show has been added
		boolean videoAddedTextMsg = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("videoAddedText_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.VISIBLE)).isDisplayed();
		//Thread.sleep(3000);
		CommonUtil.sop("assert1, show added text msg: " + videoAddedTextMsg);

		SoftAssertor.assertTrue(videoAddedTextMsg,"addAShow-AssertErrorMsg1:");
		
		}catch(Exception e){
			errorMsg1 = e.getMessage();
			
			videoAddedBtnTextMsg = driver.findElement(By.xpath(properties.getProperty("videoAddedBtn_XPATH"))).isDisplayed();
			CommonUtil.sop("assert1, show added button text msg: " + videoAddedBtnTextMsg);
			SoftAssertor.assertTrue(videoAddedBtnTextMsg,"addAShow-AssertErrorMsg1:"); 
	
		}
		finally {
				
			if(errorMsg1.length()>0 && videoAddedBtnTextMsg == false )	
			{
				SoftAssertor.addVerificationFailure(errorMsg1);
				log.error(errorMsg1);
			}
			
		
		// click "X" to Close the window
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("closeBtn_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(3000);
		
		/*Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		Thread.sleep(4000);*/
		}
		
		// click "My Shows" tab
		WebElement myShowsTab = driver.findElement(By.linkText(properties.getProperty("myShows_LINKTEXT")));
		action.moveToElement(myShowsTab).click().build().perform();
		Thread.sleep(3000);
		
		
		//validate number of shows in My shows page
		numOfShows = driver.findElements(By.xpath("//*[@id='one-pass-list']/ul/li")).size();
		CommonUtil.sop("assert2,number of shows in My Shows page: "+numOfShows);
		
		SoftAssertor.assertEquals(numOfShows, 2,"addShow-AssertErrorMsg2:");
		
		if(numOfShows == 2) {
		//confirm added show is present in My shows page
		String actualShow = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("addedShowTitle_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).getText();
		CommonUtil.sop("added show in My Shows page: " + actualShow);
		}	
	}

	public void deleteAShow() throws PageException, InterruptedException {
		CommonUtil.sop("starting deleteAShow");

	if(numOfShows == 2) {
		// click "Edit"
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("editBtn_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(3000);
		
		// select the added show
		driver.findElement(By.xpath(properties.getProperty("showCheckBox_XPATH"))).click();
		Thread.sleep(3000);

		// click "Delete"
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("deleteBtn_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(3000);

		// click "Delete streaming video"
		((Button) webPage.findObject(ObjectType.Button,
				properties.getProperty("deleteVideoStream_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).click(); 
		Thread.sleep(3000);
	}	
		//confirm the show is not present in My shows page
		String showTitle = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("currentShowTitle_XPATH"),
				ObjectValType.XPATH, MAX_WAIT, WaitCondition.CLICKABLE)).getText();
		CommonUtil.sop("the current show in My Shows page: " + showTitle);
	
		//validate number of shows in My shows page
		int numOfShows = driver.findElements(By.xpath("//*[@id='one-pass-list']/ul/li")).size();
		CommonUtil.sop("assert,number of shows in My Shows page: "+numOfShows);
				
		SoftAssertor.assertEquals(numOfShows, 1,"deleteAShow-AssertErrorMsg:");
	
	}

	public void searchForAShow(String searchTitle) throws PageException,InterruptedException {
		CommonUtil.sop("starting searchForAShow");

		// click Search box and enter "House of Cards"
		WebElement searchBox = driver.findElement(By.xpath(properties.getProperty("searchBox_XPATH")));
		searchBox.clear();
		searchBox.sendKeys(searchTitle);
		Thread.sleep(3000);
	
		// click submit
		driver.findElement(By.xpath(properties.getProperty("submit_Xpath"))).click();
		Thread.sleep(5000);
		
		// validate the search result show title
		String searchResult = driver.findElement(
				By.xpath(properties.getProperty("searchResultsShow_XPATH"))).getText();
		CommonUtil.sop("assert, search result : "+searchResult);

		SoftAssertor.assertEquals(searchResult,"House of Cards","searchForAShow-AssertErrorMsg:");
	}

	public void signOut() throws PageException, InterruptedException {
		CommonUtil.sop("starting signOut");

		// click "SignOut"
		((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("signOut_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).click();
		Thread.sleep(5000);
		
		//validate on "Login" page
		String loginText = ((Text) webPage.findObject(ObjectType.Text,
				properties.getProperty("loginHeader_XPATH"), ObjectValType.XPATH,
				MAX_WAIT, WaitCondition.CLICKABLE)).getText();
		CommonUtil.sop("assert, Login header in login page: "+loginText);
		
		SoftAssertor.assertEquals(loginText,"Login","signOut-AssertErrorMsg:");
		
	}

}
