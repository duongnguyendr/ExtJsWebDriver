package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor York
 */
public class Checkbox extends ExtJSComponent {
	String setCheckbox = "SExt.prototype.setCheckbox = function (query, value, uuid) {"
			+ "var comp = this.findVisibleComponent(query);"
			+ "var success = comp.setValue(value);"
			+ "writeDataToDiv(success, uuid);"
			+ "}";
	
	public Checkbox(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Checkbox(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * check() sets checkbox to checked
	 */
	public void check() {
		check(true);
	}
	
	/**
	 * check(true) or check(false) to set checkbox to checked or unchecked.
	 * 
	 * @param enable
	 *            boolean
	 */
	public void check(final boolean enable) {
		if (enable != (Boolean) execScriptOnExtJsCmp("return extCmp.getValue()")) {
			click();
		}
		execScriptOnExtJsCmp("extCmp.setValue("
				+ enable
				+ ")");
	}
	
	/**
	 * @return boolean
	 */
	public boolean isChecked() {
		boolean ret = (Boolean) execScriptOnExtJsCmp("return extCmp.checked;");
		return ret;
	}
	
	/**
	 * uncheck() sets checkbox to unchecked
	 */
	public void uncheck() {
		check(false);
	}
	
}