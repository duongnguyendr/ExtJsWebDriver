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

public class ComboBox extends Component {
	String setComboBox = "SExt.prototype.setComboBox = function (query, item, uuid) {"
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
	
	/**
	 * @param elementContainer
	 *            - locator of either parent element which wraps text input and drop down button or text input
	 */
	public ComboBox(WebDriver driver, WebElement elementContainer) {
		super(driver, elementContainer);
	}
	
	public ComboBox(WebDriver driver, ExtJSQueryType queryType, String query){
		super(driver, queryType, query);
	}
	
	private static final By BOUND_LIST_LOCATOR = By.cssSelector("li.x-boundlist-item");
	
	private WebElement input;
	
	private String listDynId = null;
	
	private static final By TEXT_INPUT_LOCATOR = By.cssSelector("input.x-form-field.x-form-text");
	
	protected String getListDynId() {
		return listDynId;
	}
	
	/**
	 * sets id of generated list with combobox options
	 */
	protected void setListDynId() {
		listDynId = (String) js.executeScript(TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION + " extCmp.expand(); return extCmp.listKeyNav.boundList.id;",
				getTextInput());
	}
	
	/**
	 * sends arrow key to text box
	 */
	public void retrieveOptions() {
		sendKeys(Keys.ARROW_DOWN);
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
	
	private Boolean isDirty() {
		return (Boolean) js.executeScript(TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION + " return extCmp.isDirty();", getTextInput());
	}
	
	private void collapseDropDown() {
		js.executeScript(TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION + " extCmp.collapse();", getTextInput());
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
	 * @return list of available option elements
	 */
	private List<WebElement> getOptionElements() {
		return getListContainer().findElements(BOUND_LIST_LOCATOR);
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
	
	public void clear() {
		getTextInput().clear();
	}
	
	public String getAttribute(String arg0) {
		return getTextInput().getAttribute(arg0);
	}
	
	public void sendKeys(CharSequence... arg0) {
		getTextInput().sendKeys(arg0);
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
		evalTrue("extCmp.setValue( '" + value + "' )");
		execScriptOnExtJsComponent("extCmp.fireEvent( 'select', extCmp, extCmp.store.getById('" + value + "'), extCmp.store.indexOfId('" + value + "') )");
		blur();
		return value;
	}
	
	/**
	 * 
	 * @param value
	 * @param fieldName
	 */
	public void setValue(final String value, final String fieldName) {
		//focus();
		final Integer index = findInStore(fieldName, value);
		execScriptOnExtJsComponent("extCmp.setValue(extCmp.store.getAt(" + index + ").get(extCmp.valueField) )");
		execScriptOnExtJsComponent("extCmp.fireEvent( 'select', extCmp, extCmp.store.getAt(" + index + "), " + index + " )");
		//blur();
	}
	
	/**
	 * Finds the index of the first matching Record in this store by a specific field value
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public int findInStore(final String fieldName, final String value) {
		return (Integer) execScriptOnExtJsComponent(String.format("return extCmp.store.find('%s','%s');", fieldName, value));
	}
	
	/**
	 * Method select.
	 * 
	 * @param i
	 *            int
	 */
	public void select(final int i) {
		//focus();
		execScriptOnExtJsComponent("extCmp.setValue(extCmp.store.getAt(" + i + ").get(extCmp.valueField) )");
		execScriptOnExtJsComponent("extCmp.fireEvent( 'select', extCmp, extCmp.store.getAt(" + i + "), " + i + " )");
		//blur();
	}
	
	/**
	 * Method getCount.
	 * 
	 * @return Integer
	 */
	public Integer getCount() {
		final String eval = (String) execScriptOnExtJsComponent("return extCmp.store.getCount();");
		if (eval == null || "null".equals(eval)) {
			return null;
		}
		
		return Integer.valueOf(eval);
		
	}
	
	/**
	 * return the selected value inner component
	 * 
	 * @return String
	 */
	public String getRawValue() {
		return (String) execScriptOnExtJsComponent(String.format("return extCmp.getRawValue();"));
	}
	
	/**
	 * Returns the currently selected field value or empty string if no value is set.
	 * 
	 * @return String
	 */
	public String getValue() {
		return (String) execScriptOnExtJsComponent(String.format("return extCmp.getValue();"));
	}
	
	/**
	 * Resets the current field value to the originally-loaded value and clears any validation messages.
	 * 
	 * @return String
	 */
	public Boolean reset() {
		return (Boolean) execScriptOnExtJsComponent(String.format("return extCmp.reset();"));
	}
}