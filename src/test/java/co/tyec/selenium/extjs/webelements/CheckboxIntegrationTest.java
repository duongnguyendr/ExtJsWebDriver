package co.tyec.selenium.extjs.webelements;

import java.lang.reflect.Method;

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

public class CheckboxIntegrationTest {
	String htmlTestLocation = this.getClass().getResource("ExtJSTest.html").toString();
	
	String locator = "//";
	
	FirefoxProfile profile = new FirefoxProfile();
	
	WebDriver driver;
	
	final static Logger logger = LoggerFactory.getLogger(CheckboxIntegrationTest.class);
	
	public CheckboxIntegrationTest() {
	}
	
	@BeforeClass
	public void beforeClass() {
		logger.info("ExtJSTest Location: " + htmlTestLocation);
	}
	
	@Before
	public void beforeMethod(Method m) {
		if (driver == null) {
			driver = new FirefoxDriver(profile);
			driver.navigate().to(htmlTestLocation);
		}
	}
	
	@AfterClass
	public void afterclass() {
		try {
			driver.close();
		} catch (Exception e) {
			logger.debug("Exception closing driver", e);
		}
	}
	
	@Test
	public void selectExtJSButton() {
		Button button = new Button(driver, ExtJSQueryType.ComponentQuery, "[itemId='pressed']");
		
		// DomElement pressedDiv = page.domElementGet("DIV[@id='pressed']//*[@textContents='pressed: true']", "//");
		WebElement pressedDiv = driver.findElement(By.xpath("//div[@id='pressed']//*[contains(text(), 'pressed: true')]"));
		Assert.assertNotNull(pressedDiv);
		
	}
	
	@Test
	public void selectExtJSComboBox() {
		ComboBox comboBox = new ComboBox(driver, ExtJSQueryType.ComponentQuery, "[name='state']");
		
		// DomElement stateInput = page.domElementGet("INPUT[@name='state']", "//");
		WebElement stateInput = driver.findElement(By.name("state"));
		Assert.assertNotNull(stateInput);
		
		// WebDriver get value
		String selectedState = (String) stateInput.getAttribute("value");
		Assert.assertNotNull(selectedState);
		Assert.assertEquals(selectedState, "Alaska");
		
		// Via framework
		selectedState = comboBox.getValue();
		Assert.assertNotNull(selectedState);
		Assert.assertEquals(selectedState, "Alaska");
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
