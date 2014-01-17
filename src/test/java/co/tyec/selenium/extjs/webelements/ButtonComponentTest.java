package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.outbrain.selenium.util.ExtjsUtils.Xtype;
import com.thoughtworks.selenium.Selenium;

/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class ButtonComponentTest {
	
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

    final WebDriver driver = Mockito.mock(WebDriver.class);
    final JavascriptExecutor js = (JavascriptExecutor)driver;
    
    final ComponentLocatorFactory loc = new ComponentLocatorFactory(driver);
    final ComponentLocator locator = loc.createLocator("test", Xtype.WINDOW);
    Mockito.when(js.executeScript("window.findComponentByText('test','window')")).thenReturn("koko");
    assertTrue("locator has the wrong type", locator instanceof TextOrLableLocator);
    locator.getComponentId();
    final Window window = new Window(locator);
    assertEquals("Window's id is incorrect", "koko", window.getComponentId());
    final ComponentLocator btnLocator = window.findComponentIn("OK", Xtype.BUTTON, loc);
    assertTrue("locator has the wrong type", btnLocator instanceof TextOrLableInComponentLocator);
  }

  /**
   * Method testWindowMethod.
   */
  @Test
  public void testWindowMethod() {

    final Selenium sel = Mockito.mock(Selenium.class);
    final ComponentLocatorFactory loc = new ComponentLocatorFactory(sel);
    Mockito.when(sel.getEval("window.findComponentByText('win_title','window')")).thenReturn("win_id");
    final ComponentLocator locator = loc.createLocator("win_title", Xtype.WINDOW);
    final Window result = new Window(locator);

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
