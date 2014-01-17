package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Panel extends Component {
	
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
		evalTrue(".collapse()");
		waitForEvalTrue(".collapsed == true");
	}
	
	/**
	 * Method expand.
	 */
	public void expand() {
		evalTrue(".expand()");
		waitForEvalTrue(".collapsed == false");
	}
}
