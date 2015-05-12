package com.etouch.tivopoc.pages.test;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;
import com.etouch.tivopoc.common.BaseTest;
import com.etouch.tivopoc.pages.TivoPages;

// TODO: Auto-generated Javadoc

/**
 * The Class TestTivo.
 */
@IExcelDataFiles(excelDataFiles = { "file1=src//test//resources//testdata//TestTivoPOC_Defects.xls" })
//@IExcelDataFiles(excelDataFiles = { "file1=testData" })
public class TestTivo extends BaseTest {

	/** The log. */
	static Log log = LogUtil.getLog(TestTivo.class);

	/** The current url. */
	private String currentUrl = "";

	/** The web page. */
	private WebPage webPage;
	
	private WebDriver driver;

	private TivoPages tivoPages;
	VideoRecorder videoRecorder = null;

	// required for jira
	/** The url3. */
	String url3 = TestBedManager.INSTANCE.getDefectConfig().getUrl3();

	/** The issue url. */
	String issueUrl = TestBedManager.INSTANCE.getDefectConfig().getIssueUrl();

	/** The username. */
	String username = TestBedManager.INSTANCE.getDefectConfig().getUsername();

	/** The password. */
	String password = TestBedManager.INSTANCE.getDefectConfig().getPassword();

	/** The keys. */
	String keys = TestBedManager.INSTANCE.getDefectConfig().getKeys();

	// required for rally
	/** The Constant DEFECT_PROP. */
	private static final String DEFECT_PROP = null;

	/** The Constant STORY_ID. */
	private static final String STORY_ID = null;

	/** The project id. */
	String PROJECT_ID = TestBedManager.INSTANCE.getDefectConfig()
			.getProjectId();

	/** The defect owner. */
	String DEFECT_OWNER = TestBedManager.INSTANCE.getDefectConfig()
			.getDefectOwner();

	/** The workspace id. */
	String WORKSPACE_ID = TestBedManager.INSTANCE.getDefectConfig()
			.getWorkspaceId();


	/**
	 * Prepare before class.
	 * 
	 * @throws Exception
	 *             the exception
	 */

	@BeforeClass(alwaysRun = true)
	public void setUpBeforeClass() {

		try {
			webPage = new WebPage();
			currentUrl = "http://www.rcn2go.com/tve/start";
			tivoPages = new TivoPages(currentUrl, webPage);
			//videoRecorder = new VideoRecorder("/Users/malathikosaraju/Documents/etouchWorkspace/TivoPOC/src/test/resources/videos/");
			
			//changes for running in jenkins
			//reading video file path from Chrome job and Firefox job
			driver = webPage.getDriver();
			if(driver.toString().startsWith("ChromeDriver:")) {
				videoRecorder = new VideoRecorder("/Users/rpandey/workspace/TivoPOC-Chrome/TivoPOC/src/test/resources/videos/");
			}else if (driver.toString().startsWith("FirefoxDriver:")) {
				videoRecorder = new VideoRecorder("/Users/rpandey/workspace/TivoPOC-Firefox/TivoPOC/src/test/resources/videos/");
			}
			
			
		}catch (InterruptedException e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 1)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "SignIn")
	public void testSignIn(TestParameters inputs) {
		CommonUtil.sop("Starting testSignIn");
		
		String username = "DCtesting1";
		String pwd = "testing123tivo";
		
		try { 
			videoRecorder.startRecording();
			CommonUtil.sop("Recording feature on");

			tivoPages.signIn(username, pwd);
			
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error(e.getMessage());
		}
		finally
		{		
			String errMsg = SoftAssertor.readErrorsForTest();
			if(errMsg!=null && errMsg=="") {
				videoRecorder.stopRecording();
				videoRecorder.deleteRecording();
				CommonUtil.sop("Stopped Recording at the end");
			}else {
				logDefect(inputs,errMsg);
			}
			SoftAssertor.displayErrors();
		}
		
	}
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 2)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "AddAShow")		 
	public void testAddAShow(TestParameters inputs) {
		CommonUtil.sop("Starting testAddAShow");
	
		try {
			videoRecorder.startRecording();
			CommonUtil.sop("Recording feature on");
			
			tivoPages.addAShow();

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error(e.getMessage());
		}
		finally
		{		
			String errMsg = SoftAssertor.readErrorsForTest();
			if( errMsg!=null && errMsg=="") {
				videoRecorder.stopRecording();
				videoRecorder.deleteRecording();
				CommonUtil.sop("Stopped Recording at the end");
			}else {
				logDefect(inputs,errMsg);
			}
			SoftAssertor.displayErrors();
		}
	
	}
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 3)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "DeleteAShow")
	public void testDeleteAShow(TestParameters inputs) {
		CommonUtil.sop("Starting testDeleteAShow");

		try {
			videoRecorder.startRecording();
			CommonUtil.sop("Recording feature on");
			
			tivoPages.deleteAShow();
			
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error(e.getMessage());
		}
		finally
		{		
			String errMsg = SoftAssertor.readErrorsForTest();
			if( errMsg!=null && errMsg=="") {
				videoRecorder.stopRecording();
				videoRecorder.deleteRecording();
				CommonUtil.sop("Stopped Recording at the end");
			}else {
				logDefect(inputs,errMsg);
			}
			SoftAssertor.displayErrors();
		}
		
	}
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 4)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "SearchForAShow")
	public void testSearchForAShow(TestParameters inputs) {
		CommonUtil.sop("Starting testSearchForAShow");

		String showTitle = "House of Cards";
		try {
			videoRecorder.startRecording();
			CommonUtil.sop("Recording feature on");
			
			tivoPages.searchForAShow(showTitle);

		} catch (Exception e) {
				
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error(e.getMessage());
		}
		finally
		{	
			String errMsg = SoftAssertor.readErrorsForTest();
			if( errMsg!=null && errMsg=="") {
				videoRecorder.stopRecording();
				videoRecorder.deleteRecording();
				CommonUtil.sop("Stopped Recording at the end");
			}else {
				
				logDefect(inputs,errMsg);
			}
			SoftAssertor.displayErrors();	
		}
	}
		
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 5)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "SignOut")
	public void testsignOut(TestParameters inputs){
		CommonUtil.sop("Starting testsignOut");

		try {
			videoRecorder.startRecording();
			CommonUtil.sop("Recording feature on");
			
			tivoPages.signOut();
			
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error(e.getMessage());
		}
		finally
		{		
			String errMsg = SoftAssertor.readErrorsForTest();
			if( errMsg!=null && errMsg=="") {
				videoRecorder.stopRecording();
				videoRecorder.deleteRecording();
				CommonUtil.sop("Stopped Recording at the end");
				
			}else {
				logDefect(inputs,errMsg);
			}
			SoftAssertor.displayErrors();
		}
		
	}
	
	public void logDefect(TestParameters inputs, String errorMsg)
	{
		String SEARCH_DEFECT_NAME = inputs.getParamMap().get("SearchDefectName");	
		String SEARCH_TEST_ID =  inputs.getParamMap().get("RallyTestCaseID");
		String DEFECT_SEVERITY =  inputs.getParamMap().get("DefectSeverity");
		String DEFECT_NOTES =  inputs.getParamMap().get("DefectNotes") + " : " + "Video Link: "+ videoRecorder.getVideoLink();
		String attachmentPath = ".//src//test//resources//screenshots";
		
			videoRecorder.stopRecording();
			CommonUtil.sop("Stopped Recording at the end");
			tivoPages.getScreenshot();
			tivoPages.logAndCreateADefect(TestBedManager.INSTANCE.getDefect(),DEFECT_PROP,SEARCH_TEST_ID, WORKSPACE_ID, PROJECT_ID,  STORY_ID, SEARCH_DEFECT_NAME, DEFECT_SEVERITY,DEFECT_OWNER,DEFECT_NOTES, errorMsg, attachmentPath);
		
	}
	

}
