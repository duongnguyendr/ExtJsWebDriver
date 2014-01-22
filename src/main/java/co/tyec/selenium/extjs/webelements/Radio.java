package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Radio extends ExtJSComponent {
	public Radio(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Radio(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * 
	 * 
	 * @return boolean
	 */
	public boolean isChecked() {
		return execScriptOnExtJsCmpReturnBoolean("return extCmp.checked");
	}
	
}