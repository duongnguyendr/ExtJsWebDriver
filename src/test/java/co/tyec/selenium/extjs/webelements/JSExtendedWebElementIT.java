package co.tyec.selenium.extjs.webelements;

import java.io.IOException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSExtendedWebElementIT extends BaseTest {
	static String htmlTestLocation;
	
	static String locator = "//";
	
	static FirefoxProfile profile = new FirefoxProfile();
	
	static WebDriver driver;
	
	final static Logger logger = LoggerFactory.getLogger(JSExtendedWebElementIT.class);
	
	public JSExtendedWebElementIT() {
	}
	
	@SuppressWarnings("unused")
	@BeforeClass
	public static void beforeClass() throws IOException {
		URL res = JSExtendedWebElementIT.class.getResource("ExtJSTest.html");
		htmlTestLocation = JSExtendedWebElementIT.class.getResource("ExtJSTest.html").toString();
		logger.info("ExtJSTest Location: " + htmlTestLocation);
	}
	
	@Before
	public void beforeMethod() {
		if (driver == null) {
			logger.info("Starting Selenium FirefoxDriver");
			driver = new FirefoxDriver(profile);
			logger.info("Navigating to: " + htmlTestLocation);
			driver.navigate().to(htmlTestLocation);
		}
	}
	
	@AfterClass
	public static void afterclass() {
		try {
			if(driver != null){
				driver.quit();
			}
		} catch (Exception e) {
			logger.debug("Exception closing driver", e);
		}
	}
	
	@Test
	public void simpleJSReturnTest() {
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		// No return. This test proves that you need a return.
		Object ret = jsEl.execScriptClean("1;");
		Assert.assertEquals(null, ret);
		
		// With return, no Semicolon. This test proves that you do NOT need a semi colon.
		ret = jsEl.execScriptClean("return \"something\"");
		Assert.assertEquals("something", ret);

		// With return, and semicolon
		ret = jsEl.execScriptClean("return \"something\";");
		Assert.assertEquals("something", ret);	
	}
	
	@Test
	public void longJSReturnTest() {
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		// Long
		Object ret = jsEl.execScriptClean("return 1+1");
		Assert.assertTrue(ret instanceof Long);
		Long retLong = (Long) ret;
		Assert.assertEquals(Long.valueOf("2"), retLong);
	}
	
	@Test(expected=ClassCastException.class)
	public void classCastExceptionForInteger() {
		// You cannot cast an output to Integer.
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		Integer retInt = (Integer) jsEl.execScriptClean("return 1+1;");
	}
	
	@Test(expected=ClassCastException.class)
	public void classCastExceptionForString() {
		// You cannot cast an output to String.
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		String retString = (String) jsEl.execScriptClean("return 1+1;");
	}
	
	@Test
	public void noClassCastExceptionForStringValueOf() {
		// You CAN parse the output with String.valueOf
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		String retString = String.valueOf(jsEl.execScriptClean("return 1+1;"));
	}
	
	@Test
	public void noClassCastExceptionForLongValueOf() {
		// You cannot parse the output with Long.valueOf, but you can convert to string first (but you should just
		// cast to long if it's castable.
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		Long retString = Long.valueOf(String.valueOf(jsEl.execScriptClean("return 1+1;")));
	}
	
	@Test
	public void returnIsNullTest() {
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		Object ret = jsEl.execScriptCleanReturnIsNull("");
		Assert.assertNull(ret);
		
		ret = jsEl.execScriptCleanReturnIsNull("return 1;");
		Assert.assertNotNull(ret);
	}
	
	@Test
	public void returnBooleanTest() {
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		Object ret = null;
		
		// return true
		ret = jsEl.execScriptCleanReturnBoolean("return true;");
		Assert.assertTrue((Boolean) ret);
		
		// return false
		ret = jsEl.execScriptCleanReturnBoolean("return false;");
		Assert.assertFalse((Boolean) ret);
		
		// Integers always return false
		ret = jsEl.execScriptCleanReturnIsNull("return 1;");
		Assert.assertFalse((Boolean) ret);
		
		// Strings always return false
		ret = jsEl.execScriptCleanReturnIsNull("return \"something\";");
		Assert.assertFalse((Boolean) ret);
		
		// Strings always return false, even if the string is "true"
		ret = jsEl.execScriptCleanReturnIsNull("return \"true\";");
		Assert.assertFalse((Boolean) ret);
	}
	
	@Test
	public void queryConstructorTest() {
		// query to find the body tag
		String query = "var arr = document.getElementsByTagName('body'); if(arr.length >=0){ return arr[0]; } else { return arr; }";
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, query);
		Assert.assertNotNull(jsEl);
		
		Object ret = null;
		ret = jsEl.execScriptOnTopLevelElement("return el.tagName");
		Assert.assertEquals("BODY", String.valueOf(ret).toUpperCase()); // firefox does all upper case. i dont care.
	}
	
	@Test
	public void elementConstructorTest() {
		WebElement el = driver.findElement(By.xpath("//body"));
		JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
		Assert.assertNotNull(jsEl);
		
		Object ret = null;
		ret = jsEl.execScriptOnTopLevelElement("return el.tagName");		
		Assert.assertEquals("BODY", String.valueOf(ret).toUpperCase()); // firefox does all upper case. i dont care.
	}
}
