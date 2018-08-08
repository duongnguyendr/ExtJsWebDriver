
package co.tyec.selenium.extjswebdriver;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//import Project.FrameWork.common.Generic;

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
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
//        ChromeOptions options = setOptionsForChrome();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://movieworld.enovision.net/");
//        js = (JavascriptExecutor) driver;
        byExtJsComponentQuery = new ByExtJsComponentQuery(driver);
        Thread.sleep(10000);
//        logger.info("***** Opened browser *****");
		
//		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
//		js = (JavascriptExecutor) driver;
//		// Global Mocks
//		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
//        byExtJsComponentQuery = new ByExtJsComponentQuery(driver);
	}
	
	@Test
	public void basicTest() {
//        final Button button = Mockito.spy(new Button(driver, byExtJsComponentQuery.byExtJsComponentQuery("[@text='pressed']")));
//		Mockito.doReturn("_id").when(button).getComponentId();
//		String id = button.getComponentId();
//		assertEquals("id is incorrect", "_id", button.getComponentId());
		System.out.println("Button: ");
		System.out.println("Driver: " + driver);
		System.out.println("Js: " + js);
		ByExtJsComponentQuery qr = byExtJsComponentQuery.byExtJsComponentQuery("button[text='Who worked with who']");
		System.out.println("qrrrrrrr : " + qr);
		Button button = new Button(driver, byExtJsComponentQuery.byExtJsComponentQuery("button[text='Who worked with who']"));
		System.out.println("Button: " + button);
		String id = button.getComponentId();
		button.click();
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
