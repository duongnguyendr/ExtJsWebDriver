package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TriggerField extends Component {
	
	public TriggerField(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
		trigger = new Button(driver, queryType, query);
	}

	public TriggerField(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
		trigger = new Button(driver, ExtJSQueryType.GetCmp, getExpression() + ".trigger");
	}

	/**
	 * Field trigger.
	 */
	private Button trigger;
	
	/**
	 * Method clickTrigger.
	 * 
	 * @return TriggerField
	 */
	public TriggerField clickTrigger() {
		trigger.click();
		
		return this;
	}
	
}
