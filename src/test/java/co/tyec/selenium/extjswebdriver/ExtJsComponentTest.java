package co.tyec.selenium.extjswebdriver;

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
 * The class <code>ExtJsComponentTest</code> contains tests for the class <code>{@link ExtJSComponent}</code>.
 */
public class ExtJsComponentTest
{

	WebDriver driver;
	JavascriptExecutor js;

	ByExtJsGetCmp byExtJsGetCmp;

	ByExtJsGetCmp cmpIdBy;

	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(js.executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn(Boolean.FALSE);
		byExtJsGetCmp = new ByExtJsGetCmp(driver);

		WebElement mockedEl = Mockito.mock(WebElement.class);
		cmpIdBy = byExtJsGetCmp.byExtJsGetCmp("cmp_id");
		Mockito.when(cmpIdBy.findElement(driver)).thenReturn(mockedEl);
	}

	/**
     * Run the void blur() method test.
     *
     * @throws Exception
     */
	@Test
	public void testBlur() throws Exception {
		final ExtJSComponent result = Mockito.spy(new ExtJSComponent(driver, cmpIdBy));
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
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id')")).thenReturn("true");
		final boolean result = cmp_result.execScriptCleanReturnBoolean(cmp_result.getExpression());
		
		assertTrue(result);
	}
	
	/**
	 * Run the boolean cleanEvalTrue(String) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCleanEvalTrue_1() throws Exception {
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy); // byExtJsGetCmp.byExtJsGetCmp("null"));
		final boolean result = cmp_result.execScriptCleanReturnBoolean(cmp_result.getExpression());
		assertEquals(false, result);
	}
	
	/**
	 * Run the ExtJSComponent(Selenium,ComponentLocator) constructor test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testComponent_id() throws Exception {
		final ExtJSComponent result = new ExtJSComponent(driver, cmpIdBy);
        Mockito.when(js.executeScript(Mockito.contains("isHidden"))).thenReturn(Boolean.FALSE);
		Mockito.when(js.executeScript(Mockito.contains("disabled"))).thenReturn(Boolean.FALSE);
		Mockito.when(js.executeScript(Mockito.contains("return extCmp == null"))).thenReturn(Boolean.FALSE);
		
		// add additional test code here
		assertNotNull(result);
		assertEquals("cmp_id", result.getComponentId());
        assertEquals(true, result.isVisible());
		assertEquals(false, result.isDisabled());
		assertEquals("window.Ext.getCmp('cmp_id')", result.getExpression());
        assertEquals(false, result.isHidden());
		assertEquals("cmp_id", result.getComponentId());
	}
	
	/**
	 * Run the boolean disabled() method test.
	 * 
	 * @throws Exception
	 */
	@Test
    public void testIsDisabled() throws Exception
    {
		WebElement mockedEl = Mockito.mock(WebElement.class);
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		Mockito.when(js.executeScript(Mockito.contains("disabled"), Mockito.anyVararg())).thenReturn(Boolean.TRUE);
        assertTrue(cmp_result.isDisabled());
	}
	
	/**
	 * Run the boolean evalNullComponent(String) method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEvalNullComponent() throws Exception {
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		Mockito.when(js.executeScript("window.Ext.getCmp('cmp_id')")).thenReturn("null");
		final boolean result = cmp_result.execScriptOnExtJSCmpReturnIsNull(cmp_result.getExpression());
		
		assertTrue(result);
	}
	
	/**
	 * Run the boolean evalNullComponent(String) method test. return false
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEvalNullComponent_false() throws Exception {
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		Mockito.when(js.executeScript(Mockito.contains("var extCmp = Ext.getCmp"))).thenReturn("something");
		final boolean result = cmp_result.execScriptOnExtJSCmpReturnIsNull(cmp_result.getExpression());
		
		assertEquals(false, result);
	}
	
	/**
	 * Run the String getComponentId() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetComponentId() throws Exception {
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		final String result = cmp_result.getComponentId();
		assertNotNull(result);
	}
	
	/**
	 * Run the String getElDom() method test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetElDom() throws Exception {
		WebElement mockedEl = Mockito.mock(WebElement.class);
		ByExtJsGetCmp by = cmpIdBy;
		Mockito.when(by.findElement(driver)).thenReturn(mockedEl);

		final ExtJSComponent cmp_result = new ExtJSComponent(driver, by);
		assertNotNull(cmp_result);
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("getEl().dom"))).thenReturn(mockedEl);
		assertEquals(mockedEl, cmp_result.getElDom());
	}
	
	@Test
	public void waitForFinishAjaxRequestTest() {
		final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
		assertTrue(cmp_result.waitForFinishAjaxRequest());
	}
}