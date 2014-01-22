package co.tyec.selenium.extjs.webelements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComboBox extends ExtJSComponent {
	final static Logger logger = LoggerFactory.getLogger(ComboBox.class);
	
	private static final By BOUND_LIST_LOCATOR = By.cssSelector("li.x-boundlist-item");
	
	static final String setComboBox = "SExt.prototype.setComboBox = function (query, item, uuid) {"
			+ "var success = false;"
			+ "var comp = this.findVisibleComponent(query);"
			+ "var store = comp.getStore();"
			+ "var index = store.find(comp.displayField, item);"
			+ "if ( index != -1 ) {"
			+ "	comp.setValue(store.getAt(index));"
			+ "	success = comp.fireEvent('select', comp, [store.getAt(index)]);"
			+ "}"
			+ "writeDataToDiv(success, uuid);"
			+ "}";
	
	private static final By TEXT_INPUT_LOCATOR = By.cssSelector("input.x-form-field.x-form-text");
	
	private WebElement input;
	
	private String listDynId = null;
	
	public ComboBox(WebDriver driver, ExtJSQueryType queryType, String query){
		super(driver, queryType, query);
	}
	
	/**
	 * @param elementContainer
	 *            - locator of either parent element which wraps text input and drop down button or text input
	 */
	public ComboBox(WebDriver driver, WebElement elementContainer) {
		super(driver, elementContainer);
		
	}
	
	/**
	 * @param optionToChoose
	 *            - option to choose and additional keys to send
	 */
	public void chooseOption(CharSequence... optionToChoose) {
		chooseOption(optionToChoose[0].toString());
		for (int i = 1; i < optionToChoose.length; i++) {
			getTextInput().sendKeys(optionToChoose[i]);
		}
	}
	
	/**
	 * chooses option if one is present
	 * 
	 * @param optionToChoose
	 *            - partial text to find among options
	 */
	public void chooseOption(final String optionToChoose) {
		clear();
		if (getListDynId() == null) {
			setListDynId();
		}
		List<WebElement> optionList = getOptionElements();
		if (optionList.size() == 0) {
			sendKeys(optionToChoose);
			new WebDriverWait(driver, 2, 200).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(final WebDriver webDriver) {
					return isDirty();
				}
			});
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + getListDynId() + " li.x-boundlist-item")));
		
		for (int i = 0; i < optionList.size(); i++) {
			String actualOption;
			try {
				actualOption = optionList.get(i).getText().toLowerCase();
			} catch (StaleElementReferenceException e) {
				optionList = getOptionElements();
				i--;
				continue;
			}
			if (actualOption.contains(optionToChoose.toLowerCase())) {
				optionList.get(i).click();
				collapseDropDown();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(getListDynId())));
				break;
			}
		}
	}
	
	public void clear() {
		getTextInput().clear();
	}
	
	private void collapseDropDown() {
		js.executeScript(SCRIPT_TOP_ELEMENT_TO_EXT_JS_CMP + " extCmp.collapse();", getTextInput());
	}
	
	/**
	 * Finds the index of the first matching Record in this store by a specific field value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public int findInStore(final String fieldName, final String value) {
		return (Integer) execScriptOnExtJsCmp(String.format("return extCmp.store.find('%s','%s');", fieldName, value));
	}
	
	public String getAttribute(String arg0) {
		return getTextInput().getAttribute(arg0);
	}
	
	/**
	 * Method getCount.
	 * 
	 * @return Integer
	 */
	public Integer getCount() {
		final String eval = (String) execScriptOnExtJsCmp("return extCmp.store.getCount();");
		if (eval == null || "null".equals(eval)) {
			return null;
		}
		
		return Integer.valueOf(eval);
		
	}
	
	/**
	 * @return web element by dynamic id of reloaded list
	 */
	private WebElement getListContainer() {
		if (getListDynId() == null) {
			setListDynId();
		}
		return driver.findElement(By.id(getListDynId()));
	}
	
	protected String getListDynId() {
		return listDynId;
	}
	
	/**
	 * @return list of available option elements
	 */
	private List<WebElement> getOptionElements() {
		return getListContainer().findElements(BOUND_LIST_LOCATOR);
	}
	
	/**
	 * @return list of available options
	 */
	public List<String> getOptions() {
		retrieveOptions();
		List<String> optionStrList = new ArrayList<String>();
		List<WebElement> optionList = getOptionElements();
		for (WebElement option : optionList) {
			optionStrList.add(option.getText().trim().toLowerCase());
		}
		retrieveOptions();
		return optionStrList;
	}
	
	/**
	 * return the selected value inner component
	 * 
	 * @return String
	 */
	public String getRawValue() {
		return (String) execScriptOnExtJsCmp(String.format("return extCmp.getRawValue();"));
	}
	
	private WebElement getTextInput() {
		if (input == null) {
			if (!topElement.getTagName().equals(HTML.Tag.INPUT.toString())) {
				input = topElement.findElement(TEXT_INPUT_LOCATOR);
			} else {
				input = topElement;
			}
		}
		return input;
	}
	
	/**
	 * Returns the currently selected field value or empty string if no value is set.
	 * 
	 * @return String
	 */
	public String getValue() {
		return (String) execScriptOnExtJsCmp(String.format("return extCmp.getValue();"));
	}

	private Boolean isDirty() {
		return (Boolean) js.executeScript(SCRIPT_TOP_ELEMENT_TO_EXT_JS_CMP + " return extCmp.isDirty();", getTextInput());
	}
	
	/**
	 * Resets the current field value to the originally-loaded value and clears any validation messages.
	 * 
	 * @return String
	 */
	public Boolean reset() {
		return (Boolean) execScriptOnExtJsCmp(String.format("return extCmp.reset();"));
	}
	
	/**
	 * sends arrow key to text box
	 */
	public void retrieveOptions() {
		sendKeys(Keys.ARROW_DOWN);
	}
	
	/**
	 * Method select.
	 * 
	 * @param i
	 *            int
	 */
	public void select(final int i) {
		//focus();
		execScriptOnExtJsCmp("extCmp.setValue(extCmp.store.getAt(" + i + ").get(extCmp.valueField) )");
		execScriptOnExtJsCmp("extCmp.fireEvent( 'select', extCmp, extCmp.store.getAt(" + i + "), " + i + " )");
		//blur();
	}
	
	public void sendKeys(CharSequence... arg0) {
		getTextInput().sendKeys(arg0);
	}
	
	/**
	 * sets id of generated list with combobox options
	 * Could the text input web element be a TextField component instead?
	 */
	protected void setListDynId() {
		listDynId = (String) execScriptOnElement(SCRIPT_TOP_ELEMENT_TO_EXT_JS_CMP 
				+ " extCmp.expand(); return extCmp.listKeyNav.boundList.id;", getTextInput());
	}
	
	/**
	 * Method setValue.
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	public String setValue(final String value) {
		focus();
		execScriptOnExtJsCmp("extCmp.setValue( '" + value + "' )");
		execScriptOnExtJsCmp("extCmp.fireEvent( 'select', extCmp, extCmp.store.getById('" + value + "'), extCmp.store.indexOfId('" + value + "') )");
		blur();
		return value;
	}
	
	/**
	 * 
	 * @param value
	 * @param fieldName
	 */
	public void setValue(final String value, final String fieldName) {
		focus();
		final Integer index = findInStore(fieldName, value);
		execScriptOnExtJsCmp("extCmp.setValue(extCmp.store.getAt(" + index + ").get(extCmp.valueField) )");
		execScriptOnExtJsCmp("extCmp.fireEvent( 'select', extCmp, extCmp.store.getAt(" + index + "), " + index + " )");
		blur();
	}
}