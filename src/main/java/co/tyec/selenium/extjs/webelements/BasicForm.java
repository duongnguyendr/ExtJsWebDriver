package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor york
 */
public class BasicForm extends Component {
	
	public BasicForm(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public BasicForm(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	public Component findComponentIn(ExtJSQueryType queryType, String query) {
		return new Component(driver, queryType, query);
	}
}
