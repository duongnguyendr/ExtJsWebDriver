package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor York
 */
public class Button extends ExtJSComponent {
	String clickButton = "SExt.prototype.clickButton = function (query, uuid) {"
			+ "var comp = this.findVisibleComponent(query);"
			+ "var success = comp.btnEl.dom.click();"
			+ "var writeDataToDiv(success, uuid);"
			+ "}";
	
	public Button(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Button(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * Method click and check if is no error after Ajax callback.
	 * 
	 * @throws InterruptedException
	 */
	public void clickAndWaitForAjaxValid() throws InterruptedException {
		waitForExecScriptToReturnTrue(".disabled == false");
		click();
		wait(2);
		waitForFinishAjaxRequest();
		waitForDialogFailure();
	}
	
	/**
	 * return true if the component is disabled
	 * 
	 * @return boolean
	 */
	@Override
	public boolean disabled() {
		return (Boolean) execScriptOnExtJsCmp("return extCmp.disabled");
	}
	
}