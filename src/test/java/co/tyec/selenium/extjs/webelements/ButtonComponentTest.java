package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class ButtonComponentTest {
	
	private WebDriver driver;
	
	private JavascriptExecutor js;
	
	@Test
	public void nullTest() {
		assertTrue(null == null);
		assertTrue(!("null".equals(null)));
	}
	
	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
	}
	
	
}
