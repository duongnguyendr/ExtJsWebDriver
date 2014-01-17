package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The class <code>ComponentTest</code> contains tests for the class <code>{@link Component}</code>.
 * 
 */
public class ComponentTest {
	
	WebDriver driver;
	JavascriptExecutor js;
	
	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
	}
	
	/**
	 * Run the Component(Selenium,ComponentLocator) constructor test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testComponent_id() throws Exception {
		final Component result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		
		// add additional test code here
		assertNotNull(result);
		assertEquals("cmp_id", result.getComponentId());
		assertEquals(true, result.visible());
		assertEquals(false, result.disabled());
		assertEquals(false, result.isDisabled());
		assertEquals("window.Ext.getCmp('cmp_id')", result.getExpression());
		assertEquals(false, result.hidden());
		assertEquals("cmp_id", result.getComponentId());
		assertEquals("//*[@id='cmp_id']", result.getXPath());
		assertEquals(true, result.waitForVisible());
	}
	
	/**
	 * Run the void blur() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBlur() throws Exception {
		final Component result =  Mockito.spy(new Component(driver, ExtJSQueryType.GetCmp, "cmp_id"));
		result.blur();
 
		Mockito.verify(result).fireEvent("blur");
	}
	
	/**
	 * Run the boolean cleanEvalTrue(String) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCleanEvalTrue() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id')")).thenReturn("true");
		final boolean result = cmp_result.cleanEvalTrue(cmp_result.getExpression());
		
		assertTrue(result);
	}
	
	/**
	 * Run the boolean cleanEvalTrue(String) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCleanEvalTrue_1() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "null");
		final boolean result = cmp_result.cleanEvalTrue(cmp_result.getExpression());
		assertEquals(false, result);
	}
	
	/**
	 * Run the boolean disabled() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDisabled() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id').disabled")).thenReturn("true");
		assertTrue(cmp_result.disabled());
	}
	
	/**
	 * Run the boolean disabled() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDisabled_1() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		assertEquals(false, cmp_result.disabled());
	}
	
	/**
	 * Run the boolean evalNullComponent(String) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEvalNullComponent() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id')")).thenReturn("null");
		final boolean result = cmp_result.evalNullComponent(cmp_result.getExpression());
		
		assertTrue(result);
	}
	
	/**
	 * Run the boolean evalNullComponent(String) method test. return false
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEvalNullComponent_false() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id')")).thenReturn("cmpid");
		final boolean result = cmp_result.evalNullComponent(cmp_result.getExpression());
		
		assertEquals(false, result);
	}
	
	/**
	 * Run the String getComponentId() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetComponentId() throws Exception {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		final String result = cmp_result.getComponentId();
		assertNotNull(result);
	}
	
	/**
	 * Run the String getComponentId() . Use method type locator
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetComponentId_1() throws Exception {
		Mockito.when(js.executeScript("window.findComponentByText('cmp_title','box')")).thenReturn("cmp_id");
		final Component cmp_result = new Component(driver, ExtJSQueryType.ComponentQuery, "[@text='cmp_title']");
		final String result = cmp_result.getComponentId();
		assertNotNull(result);
		assertEquals("cmp_id", cmp_result.getComponentId());
	}
	
	/**
	 * Run the String getElDom() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetElDom() throws Exception {
		WebElement mockedEl = Mockito.mock(WebElement.class);
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");
		assertNotNull(cmp_result);
		Mockito.when(((JavascriptExecutor)driver).executeScript(Mockito.contains("getEl().dom"))).thenReturn(mockedEl);
		assertEquals(mockedEl, cmp_result.getElDom());
	}
	
	@Test
	public void waitForFinishAjaxRequestTest() {
		final Component cmp_result = new Component(driver, ExtJSQueryType.GetCmp, "cmp_id");		
		assertTrue(cmp_result.waitForFinishAjaxRequest());
	}
}