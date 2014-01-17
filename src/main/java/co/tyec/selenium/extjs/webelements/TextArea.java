package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextArea extends Field {
	
	public TextArea(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public TextArea(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
}
