
package co.tyec.selenium.extjswebdriver;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class ButtonTest
{
	
	private WebDriver driver;
	
	private JavascriptExecutor js;

    private ByExtJsComponentQuery byExtJsComponentQuery;
	
	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
        byExtJsComponentQuery = new ByExtJsComponentQuery(driver);
	}
	
	@Test
	public void basicTest() {
        final Button button = Mockito.spy(new Button(driver, byExtJsComponentQuery.byExtJsComponentQuery("[@text='pressed']")));
		Mockito.doReturn("_id").when(button).getComponentId();
		String id = button.getComponentId();
		assertEquals("id is incorrect", "_id", button.getComponentId());
	}
	
}
