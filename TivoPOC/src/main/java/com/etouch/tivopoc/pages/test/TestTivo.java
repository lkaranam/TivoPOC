package com.etouch.tivopoc.pages.test;

import org.apache.commons.logging.Log;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
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
//@IExcelDataFiles(excelDataFiles = { "file1=testData" }) //not reading testData from devConfig
public class TestTivo extends BaseTest {

	/** The log. */
	static Log log = LogUtil.getLog(TestTivo.class);

	/** The current url. */
	private String currentUrl = "";

	/** The web page. */
	private WebPage webPage;

	private TivoPages tivoPages;

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

		}catch (InterruptedException e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	@Test(priority = 1)
	public void testSignIn() {
		CommonUtil.sop("Starting testSignIn");
	
		String username = "DCtesting1";
		String pwd = "testing123tivo";
		try { 
			tivoPages.signIn(username, pwd);
		
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage()); e.printStackTrace();
			log.error(e.getMessage());
		} 
		finally
		{			
			//logDefect(inputs);
			SoftAssertor.displayErrors();
		}
		
	}
	 
	@Test(priority = 2)
	public void testAddAShow() {
		CommonUtil.sop("Starting testAddAShow");
	
		try {
			// TivoPages tivoPages = new TivoPages(currentUrl,webPage);
			tivoPages.addAShow();

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{			
			//logDefect(inputs);
			SoftAssertor.displayErrors();
		}
	
	}

	@Test(priority = 3)
	public void testDeleteAShow() {
		CommonUtil.sop("Starting testDeleteAShow");

		try {
			tivoPages.deleteAShow();
			
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{		
			//logDefect(inputs);
			SoftAssertor.displayErrors();
		}
		
	}
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 4)
	@ITafExcelDataProviderInputs(excelFile = "file1", excelsheet = "TestTivoPOC_Defects", dataKey = "SearchForAShow")
	public void testSearchForAShow(TestParameters inputs) {
		CommonUtil.sop("Starting testSearchForAShow");

		String showTitle = "House of Cards";
		try {
			tivoPages.searchForAShow(showTitle);

		} catch (Exception e) {
				
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{	
			//if(!isRecordingStopped)
			//	videoRecorder.stopRecording();			
			//logDefect(inputs);
			SoftAssertor.displayErrors();
		}
	}
		
	@Test(priority = 4)
	public void testsignOut(){
		CommonUtil.sop("Starting testsignOut");

		try {
			tivoPages.signOut();
			
		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{	
				
			//logDefect(inputs);
			SoftAssertor.displayErrors();
		}
		
	}
	
	public void logDefect(TestParameters inputs)
	{
		String SEARCH_DEFECT_NAME = inputs.getParamMap().get("SearchDefectName");	
		String SEARCH_TEST_ID =  inputs.getParamMap().get("RallyTestCaseID");
		String DEFECT_SEVERITY =  inputs.getParamMap().get("DefectSeverity");
		String DEFECT_NOTES =  inputs.getParamMap().get("DefectNotes");
		String attachmentPath = ".//src//test//resources//screenshots";
		String errMsg = SoftAssertor.readErrorsForTest();
		
		CommonUtil.sop("Defect Notes is: " + DEFECT_NOTES);
		
		tivoPages.getScreenshot();
		//videoRecorder.createScreenshot(attachmentPath, "error");
		
		tivoPages.logAndCreateADefect(TestBedManager.INSTANCE.getDefect(),DEFECT_PROP,SEARCH_TEST_ID, WORKSPACE_ID, PROJECT_ID,  STORY_ID, SEARCH_DEFECT_NAME, DEFECT_SEVERITY,DEFECT_OWNER,DEFECT_NOTES, errMsg, attachmentPath);
		//defect logging method for jira
		//mainPage.logAndCreateADefect(TestBedManager.INSTANCE.getDefect(),url3, issueUrl, username, password,keys);
	}
	

}
