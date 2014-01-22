package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TabPanel represent Ext TabPanel (The Master tab Panel) So if search for specific tab inside a tabPanel the function will return the master tab panel
 */
public class TabPanel extends ExtJSComponent {
	
	public TabPanel(WebDriver driver, ExtJSQueryType queryType, String query, final Integer tabIndex) {
		super(driver, queryType, query);
		this.tabIndex = tabIndex;
	}
	
	/**
	 * Field tabIndex.
	 */
	private int tabIndex;
	
	public TabPanel(WebDriver driver, WebElement topElement, final Integer tabIndex) {
		super(driver, topElement);
		this.tabIndex = tabIndex;
	}
	
	/**
	 * try to collapse the selected tab
	 */
	public void collapse() {
		execScriptOnExtJsCmp("extCmp.collapse()");
		waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == true");
	}
	
	/**
	 * try to expend the selected tab
	 */
	public void expand() {
		execScriptOnExtJsCmp("return extCmp.expand()");
		waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == false");
	}
	
	/**
	 * Set active by given index of wanted panel
	 * 
	 * @param indexPanel
	 */
	public void setActiveTab(final int indexPanel) {
		execScriptClean(String.format("%s.setActiveTab(%d)", getExpression(), indexPanel));
	}
	
	/**
	 * set Active tab after the search
	 */
	public void setAsActiveTab() {
		setActiveTab(tabIndex);
	}
	
	/**
	 * 
	 * 
	 * @return - (int) the current index of selected Tab
	 */
	public Integer getCurentIndexTab() {
		final String activeTabId = (String) execScriptClean(String.format("%s.getActiveTab().getActiveTab().id", getExpression()));
		return Integer.parseInt((String) execScriptClean(String.format("%s.items.indexOf('%s')", getExpression(), activeTabId)));
	}
	
	/**
	 * return All id's of the current parent tabPanel
	 * 
	 * 
	 * @return String[]
	 */
	public String[] getKeys() {
		String keys = String.valueOf(execScriptOnExtJsCmp("return extCmp.data.keys"));
		return keys.split(",");
	}
	
	/**
	 * Method getTabIndex.
	 * 
	 * @return Integer
	 */
	public Integer getTabIndex() {
		return tabIndex;
	}
	
	/**
	 * Method setTabIndex.
	 * 
	 * @param tabIndex
	 *            Integer
	 */
	public void setTabIndex(final Integer tabIndex) {
		this.tabIndex = tabIndex;
	}
	
}
