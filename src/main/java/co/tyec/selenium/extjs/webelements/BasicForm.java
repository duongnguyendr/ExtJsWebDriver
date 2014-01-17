package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.tyec.selenium.extjs.webelements.ExtJSQueryType;


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

}
