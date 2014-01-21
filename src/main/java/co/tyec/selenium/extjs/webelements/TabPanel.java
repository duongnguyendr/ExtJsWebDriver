package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TabPanel represent Ext TabPanel (The Master tab Panel) So if search for specific tab inside a tabPanel the function will return the master tab panel
 */
public class TabPanel extends Component {
	
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
		evalTrue(".collapse()");
		waitForEvalTrue(".collapsed == true");
	}
	
	/**
	 * try to expend the selected tab
	 */
	public void expand() {
		evalTrue(".expand()");
		waitForEvalTrue(".collapsed == false");
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
		return getEval("data.keys").split(",");
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
