package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import co.tyec.selenium.extjs.webelements.ExtJSQueryType;

/**
 * @author Taylor
 */
public class Field extends Component {
	
	public Field(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Field(WebDriver driver, WebElement topLevel) {
		super(driver, topLevel);
	}
	
	/**
	 * Method resetValue.
	 */
	public void resetValue() {
		evalTrue("el.setValue( '' )");
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
	 * Sets a data value into the field and validates it. To set the value directly without validation
	 * 
	 * @param value
	 * 
	 * @return String
	 */
	public String setValue(final String value) {
		return (String) execScriptOnExtJsComponent(String.format("return el.setValue('%s')", value));
	}
	
	/**
	 * Resets the current field value to the originally-loaded value and clears any validation messages
	 */
	public void reset() {
		execScriptOnExtJsComponent("el.reset()");
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
	 * return the value of component
	 * 
	 * @return String - theValue
	 */
	public String getValue() {
		return (String) execScriptOnExtJsComponent("return el.getValue()");
	}
	
	/**
	 * Returns the raw data value which may or may not be a valid, defined value.Returns the normalized data for date field
	 * 
	 * @return String - theValue
	 */
	public String getRawValue() {
		return (String) execScriptOnExtJsComponent("return el.getRawValue()");
	}
}
