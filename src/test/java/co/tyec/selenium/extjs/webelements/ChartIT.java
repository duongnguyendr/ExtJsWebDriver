package co.tyec.selenium.extjs.webelements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartIT extends BaseTest {
	static WebDriver driver;
	
	static String htmlTestLocation;
	
	static String locator = "//";
	
	final static Logger logger = LoggerFactory.getLogger(ChartIT.class);
	
	static FirefoxProfile profile = new FirefoxProfile();
	
	@AfterClass
	public static void afterclass() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			logger.debug("Exception closing driver", e);
		}
	}
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		htmlTestLocation = ChartIT.class.getResource("ExtJSTest.html").toString();
		logger.info("ExtJSTest Location: "
				+ htmlTestLocation);
	}
	
	@Before
	public void beforeMethod() {
		if (driver == null) {
			logger.info("Starting Selenium FirefoxDriver");
			driver = new FirefoxDriver(profile);
			logger.info("Navigating to: "
					+ htmlTestLocation);
			driver.navigate().to(htmlTestLocation);
		}
	}
	
	@Test
	public void getChartStoreAsJSON() throws FileNotFoundException, URISyntaxException {
        URL url = ChartIT.class.getResource("ExtJSChart.json");
        String expectedJSON = new java.util.Scanner(new File(url.toURI()),"UTF8").useDelimiter("\\Z").next();

		Chart chart = new Chart(driver, ExtJSQueryType.ComponentQuery, "[itemId='myChart']");
		String chartJSON = chart.getChartAsJSON();
		Assert.assertNotNull(chartJSON);
		Assert.assertEquals(expectedJSON, chartJSON);
	}
	
	@Test
	public void getChartStoreAsCSV() throws FileNotFoundException, URISyntaxException {
        URL url = ChartIT.class.getResource("ExtJSChart.csv");
        String expectedCSV = new java.util.Scanner(new File(url.toURI()),"UTF8").useDelimiter("\\A").next();
        // Fix line endings
        expectedCSV = expectedCSV.replace("\r\n", "\n").replace("\r", "\n").trim();
        
		Chart chart = new Chart(driver, ExtJSQueryType.ComponentQuery, "[itemId='myChart']");
		String chartCSV = chart.getChartAsCSV().trim();
		Assert.assertNotNull(chartCSV);
		Assert.assertEquals(expectedCSV, chartCSV);
	}
		
}
