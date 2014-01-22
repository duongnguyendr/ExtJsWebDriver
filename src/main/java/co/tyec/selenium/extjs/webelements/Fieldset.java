package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class Fieldset extends ExtJSComponent {
	
	public Fieldset(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}

	public Fieldset(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}

	/**
	 * Field checkbox.
	 */
	private Checkbox checkbox;
	
	
	
	/**
	 * Method check.
	 * 
	 * @param enable
	 *            boolean
	 * 
	 * @return Fieldset
	 */
	public Fieldset check(final boolean enable) {
		checkbox.check(enable);
		
		return this;
	}
	
}
