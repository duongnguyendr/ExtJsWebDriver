package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.tyec.selenium.extjs.webelements.ExtJSQueryType;

/**
 * @author Taylor York
 */
public class Button extends Component {
	
	public Button(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Button(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * return true if the component is disabled
	 * 
	 * 
	 * @return boolean
	 */
	@Override
	public boolean disabled() {
		return evalTrue(".disabled");
	}
	
	/**
	 * Method click and check if is no error after Ajax callback.
	 * 
	 * @throws InterruptedException
	 */
	public void clickAndWaitForAjaxValid() throws InterruptedException {
		waitEvalTrue(".disabled == false");
		click();
		wait(2);
		waitForFinishAjaxRequest();
		waitForDialogFailure();
	}
	
}