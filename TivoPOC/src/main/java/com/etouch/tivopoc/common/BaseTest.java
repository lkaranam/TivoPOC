package com.etouch.tivopoc.common;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.TestSuiteManager;
import com.etouch.taf.core.config.TestBedConfig;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.driver.DriverBuilder;
import com.etouch.taf.core.exception.DefectException;
import com.etouch.taf.core.exception.MonitorException;
import com.etouch.taf.core.exception.DriverException;
//import com.etouch.taf.core.monitor.gridmonitor.SeleniumMonitor;
import com.etouch.taf.util.FileUtils;
import com.etouch.taf.util.LogUtil;


// TODO: Auto-generated Javadoc
/**
 * Base test that all the test classes should extend.
 * This class initializes {@link TestBedManager} based on configuration file input
 *
 * @author eTouch Systems Corporation
 *
 */

public class BaseTest {

	/** The log. */
	static Log log = LogUtil.getLog(BaseTest.class);

	/** The test bed manager. */
	protected TestBedManager testBedManager = TestBedManager.INSTANCE;
		
	/** The defect_properties_file. */
	protected static String defect_properties_file = null;

	/** The base url. */
	public String BASE_URL = null;
	
	/** The BAS e_ ur l2. */
	public String BASE_URL2 = null;
	
	/** The start time. */
	public long startTime = 0;
    
    /** The method num. */
    public int methodNum = 0;
    
  //required for screen recorder
  	/** The screen recorder. */
      private ScreenRecorder screenRecorder;
  	

    /**
     * Prints the time start.
     *
     * @param method the method
     */
	@BeforeMethod()
	public void printTimeStart(Method method) {
		methodNum = methodNum + 1;
		//log.info("<.<.<.<.<.<.<.<.<.<.< Starting method " + methodNum + "::" + method.getName() + "<.<.<.<.<.<.<.<.<.<.<");
		startTime = System.currentTimeMillis();
	}

	/**
	 * Prints the time taken.
	 *
	 * @param method the method
	 */
	@AfterMethod()
	public void printTimeTaken(Method method) {
		log.info(">.>.>.>.>.>.>.>.>.>.>.>.> Ending method " + methodNum + "::" + method.getName() + ">.>.>.>.>.>.>.>.>.>.>.>.>Time Taken ::" + (System.currentTimeMillis() - startTime) / 1000
		        + " Seconds");
	}
	
	/**
 * Setup config.
 *
 * @param configParameters the config parameters
 * @param browserName the browser name
 * @throws DriverException the driver exception
 * @throws DefectException the defect exception
 */
@BeforeSuite()
	
	private static void setupConfig(ITestContext configParameters,@Optional("FF")String browserName)
			throws DriverException, DefectException {
		
	}

	/**
	 * Load config.
	 *
	 * @throws DriverException the driver exception
	 * @throws DefectException the defect exception
	 */
	/*@BeforeClass(alwaysRun = true)
	public void loadConfig() throws DriverException, DefectException {
		

		//TODO: commented by Sophia Ghins
		//TestBedManagerConfiguration profile = new TestBedManagerConfiguration(configFile);
		//BASE_URL = profile.getBaseURL();
		//BASE_URL2 = profile.getBaseUrl2();

		//BASE_URL = testBedManager.getProfile().getBaseURL();
		//BASE_URL2 = testBedManager.getProfile().getBaseUrl2();
		
	}*/
	
	/**
	 * Tear down.
	 */
	@AfterClass(alwaysRun=true)
	public void tearDown(){
			
		WebDriver driver = (WebDriver) TestBedManager.INSTANCE.getCurrentTestBed().getDriver();		
/*	try {
		driver.wait(15000);
	} catch (Exception e) {
		log.error(e.getMessage());
	}*/
	//driver.close();
	driver.quit();
	
	}

	/**
	 * Construct url.
	 *
	 * @param inputURL the input url
	 * @return the string
	 */
	public String constructURL(String inputURL)
	{
		if(inputURL.contains("<baseurl>"))
			inputURL = inputURL.replace("<baseurl>", BASE_URL);
		else if(inputURL.contains("<baseurl2>"))
			inputURL = inputURL.replace("<baseurl2>", BASE_URL2);

		return inputURL;
	}


	///////// to start and stop video recording  // -- added by sonam
	
	
	 /**
	 * Start recording.
	 */
	public void startRecording() 
       {
           try{                  
            GraphicsConfiguration gc = GraphicsEnvironment
               .getLocalGraphicsEnvironment()
               .getDefaultScreenDevice()
               .getDefaultConfiguration();
 
           	this.screenRecorder = new ScreenRecorder(gc,
           	   new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                    QualityKey, 1.0f,
                    KeyFrameIntervalKey, 15 * 60),
               new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                    FrameRateKey, Rational.valueOf(30)),
               null);
           	this.screenRecorder.start();
        
           }catch(IOException e){
           e.printStackTrace();
           }catch(AWTException e){
        	   e.printStackTrace();  
           }
       }
 
       /**
   	 * Stop recording.
   	 */
   	public void stopRecording() 
       {
    	 try{
    		 this.screenRecorder.stop();
    	 }catch(Exception e){
    		  e.printStackTrace(); 
    	 }
       }

 /////////////////Screenshot //////// -- added by sonam
       
       /**
	 * Gets the screenshot.
	 *
	 * @return the screenshot
	 */
	public void getScreenshot() {
			try{
		       // Thread.sleep(10000);
		        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		        ImageIO.write(image, "jpg", new File("..\\AmazonPOC\\src\\test\\resources\\testdata\\screenshots\\sample2.png"));
		      
       }catch(IOException e){
           e.printStackTrace();
           }catch(AWTException e){
        	   e.printStackTrace();  
           }
       }
	
	
	
}
