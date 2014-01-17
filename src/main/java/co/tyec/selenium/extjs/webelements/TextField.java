package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends Field {
	
	public TextField(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public TextField(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
}
