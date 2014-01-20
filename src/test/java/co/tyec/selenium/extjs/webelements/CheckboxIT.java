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

public class CheckboxIT extends BaseTest {
	static String htmlTestLocation;
	
	static String locator = "//";
	
	static FirefoxProfile profile = new FirefoxProfile();
	
	static WebDriver driver;
	
	final static Logger logger = LoggerFactory.getLogger(CheckboxIT.class);
	
	public CheckboxIT() {
	}
	
	@SuppressWarnings("unused")
	@BeforeClass
	public static void beforeClass() throws IOException {
		URL res = CheckboxIT.class.getResource("ExtJSTest.html");
		htmlTestLocation = CheckboxIT.class.getResource("ExtJSTest.html").toString();
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
	public void selectExtJSButton() {
		Button button = new Button(driver, ExtJSQueryType.ComponentQuery, "[itemId='pressed']");
		button.click();
		// DomElement pressedDiv = page.domElementGet("DIV[@id='pressed']//*[@textContents='pressed: true']", "//");
		WebElement pressedDiv = driver.findElement(By.xpath("//div[@id='pressed']//*[contains(text(), 'pressed: true')]"));
		Assert.assertNotNull(pressedDiv);
		
	}
	
	@Test
	public void selectExtJSComboBox() {
		ComboBox comboBox = new ComboBox(driver, ExtJSQueryType.ComponentQuery, "[name='state']");
		comboBox.setValue("Alaska");
		
		// DomElement stateInput = page.domElementGet("INPUT[@name='state']", "//");
		WebElement stateInput = driver.findElement(By.name("state"));
		Assert.assertNotNull(stateInput);
		
//		// WebDriver get value
//		String selectedState = (String) stateInput.getAttribute("value");
//		Assert.assertNotNull(selectedState);
//		Assert.assertEquals("Alaska", selectedState);
		
		// Via framework
		String selectedState = comboBox.getValue();
		Assert.assertNotNull(selectedState);
		Assert.assertEquals("Alaska", selectedState);
	}
	
	@Test
	public void selectExtJSCheckBox() {
		Checkbox checkbox = new Checkbox(driver, ExtJSQueryType.ComponentQuery, "[name='myCheckbox']");
		Assert.assertNotNull(checkbox.isChecked());
		
		checkbox.check();
		Assert.assertTrue(checkbox.isChecked());
		
		checkbox.uncheck();
		Assert.assertFalse(checkbox.isChecked());
		
		checkbox.check(false);
		Assert.assertFalse(checkbox.isChecked());
	}
	
}
