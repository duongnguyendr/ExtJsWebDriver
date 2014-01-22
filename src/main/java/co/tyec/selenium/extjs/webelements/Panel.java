package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Panel extends ExtJSComponent {
	
	public Panel(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Panel(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * Method collapse.
	 */
	public void collapse() {
		execScriptOnExtJsCmp("extCmp.collapse()");
		waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == true");
	}
	
	/**
	 * Method expand.
	 */
	public void expand() {
		execScriptOnExtJsCmp("extCmp.expand()");
		waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == false");
	}
}
