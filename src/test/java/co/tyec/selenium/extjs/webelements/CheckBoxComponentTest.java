package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * 
 */
public class CheckBoxComponentTest {
	
	WebDriver driver;
	
	JavascriptExecutor js;
	
	/**
	 * Method setUp.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
	}
	
	/**
	 * testCheckBox Method
	 */
	@Test
	public void testCheckBox() {
		final Checkbox chk = Mockito.spy(new Checkbox(driver, ExtJSQueryType.ComponentQuery, "[@text='checkBox_title']"));
		Mockito.doReturn("checkBox_id").when(chk).getComponentId();
		chk.getComponentId();
		
		assertEquals("checkbox id is incorrect", "checkBox_id", chk.getComponentId());
	}
	
}
