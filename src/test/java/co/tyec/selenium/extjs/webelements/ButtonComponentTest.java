package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
	}
	
	@Test
	public void nullTest() {
		assertTrue(null == null);
		assertTrue(!("null".equals(null)));
	}
	
	/**
	 * Method testWindow.
	 */
	@Test
	public void testWindow() {
		Mockito.when(js.executeScript("window.findComponentByText('test','window')")).thenReturn("koko");
		
		final Window window = new Window(driver, ExtJSQueryType.ComponentQuery, "[text='test']");
		assertNotNull(window);
		
		Component comp = window.findComponentIn(ExtJSQueryType.ComponentQuery, "[text='OK']");
		assertNotNull(comp);
	}
	
	/**
	 * Method testWindowMethod.
	 */
	@Test
	public void testWindowMethod() {
		
		Mockito.when(js.executeScript("window.findComponentByText('win_title','window')")).thenReturn("win_id");
		
		Button result = new Button(driver, ExtJSQueryType.ComponentQuery, "[name='win_title']");
		
		assertNotNull(result);
		
		assertEquals("win_id", result.getComponentId());
		assertEquals(true, result.visible());
		assertEquals(false, result.disabled());
		assertEquals(false, result.isDisabled());
		assertEquals("window.Ext.getCmp('win_id')", result.getExpression());
		assertEquals(false, result.hidden());
		assertEquals("//*[@id='win_id']", result.getXPath());
		assertEquals(true, result.waitForVisible());
	}
	
}
