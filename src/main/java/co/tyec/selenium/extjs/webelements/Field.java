package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor
 */
public class Field extends ExtJSComponent {
	
	public Field(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Field(WebDriver driver, WebElement topLevel) {
		super(driver, topLevel);
	}
	
	/**
	 * Method getErrorText.
	 * 
	 * @return String
	 */
	public String getErrorText() {
		return topElement.findElement(By.xpath("../..//div[@class='x-form-invalid-msg']")).getText();
	}
	
	/**
	 * Returns the raw data value which may or may not be a valid, defined value.Returns the normalized data for date field
	 * 
	 * @return String - theValue
	 */
	public String getRawValue() {
		return (String) execScriptOnExtJsCmp("return el.getRawValue()");
	}
	
	/**
	 * return the value of component
	 * 
	 * @return String - theValue
	 */
	public String getValue() {
		return (String) execScriptOnExtJsCmp("return el.getValue()");
	}
	
	/**
	 * Method hasErrorText.
	 * 
	 * @param err
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean hasErrorText(final String err) {
		final String text = getErrorText();
		
		return err.equals(text);
	}
	
	/**
	 * Resets the current field value to the originally-loaded value and clears any validation messages
	 */
	public void reset() {
		execScriptOnExtJsCmp("el.reset()");
	}
	
	/**
	 * Method resetValue.
	 */
	public void resetValue() {
		execScriptOnExtJsCmp("extCmp.setValue( '' )");
	}
	
	/**
	 * Method type.
	 * 
	 * @param text
	 *            String
	 */
	public void sendKeys(final String text) {
		waitToLoad();
		
		focus();
		topElement.sendKeys(text);
		blur();
	}
	
	/**
	 * Sets a data value into the field and validates it. To set the value directly without validation
	 * 
	 * @param value
	 * 
	 * @return String
	 */
	public String setValue(final String value) {
		return (String) execScriptOnExtJsCmp(String.format("return el.setValue('%s')", value));
	}
}
