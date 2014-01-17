package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.tyec.selenium.extjs.webelements.ExtJSQueryType;

/**
 * @author Taylor York
 */
public class Checkbox extends Component {
	
	public Checkbox(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	public Checkbox(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	/**
	 * @return boolean
	 */
	public boolean isChecked() {
		boolean ret = evalTrue("return el.checked;");
		return ret;
	}
	
	/**
	 * check(true) or check(false) to set checkbox to checked or unchecked.
	 * 
	 * @param enable
	 *            boolean
	 */
	public void check(final boolean enable) {
		if (enable != evalTrue("return el.getValue()")) {
			click();
		}
		execScriptOnExtJsComponent("el.setValue(" + enable + ")");
	}
	
	/**
	 * check() sets checkbox to checked
	 * 
	 */
	public void check() {
		check(true);
	}
	
	/**
	 * uncheck() sets checkbox to unchecked
	 * 
	 */
	public void uncheck() {
		check(false);
	}
	
}