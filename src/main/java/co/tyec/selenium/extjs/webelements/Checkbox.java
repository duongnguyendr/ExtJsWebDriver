package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor York
 */
public class Checkbox extends Component {
	String setCheckbox = "SExt.prototype.setCheckbox = function (query, value, uuid) {"
			+ "var comp = this.findVisibleComponent(query);"
			+ "var success = comp.setValue(value);"
			+ "writeDataToDiv(success, uuid);"
			+ "}";
	
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
		boolean ret = evalTrue("return extCmp.checked;");
		return ret;
	}
	
	/**
	 * check(true) or check(false) to set checkbox to checked or unchecked.
	 * 
	 * @param enable
	 *            boolean
	 */
	public void check(final boolean enable) {
		if (enable != evalTrue("return extCmp.getValue()")) {
			click();
		}
		execScriptOnExtJsComponent("extCmp.setValue(" + enable + ")");
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