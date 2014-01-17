/***
 * 
 * @author Asaf
 *
 */

package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TreeGrid extends Component {
	
	public TreeGrid(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
		// TODO Auto-generated constructor stub
	}
	
	public TreeGrid(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * return the root-node of the tree
	 * 
	 * @return - node
	 */
	public TreeNode getRootNode() {
		final TreeNode treeNode = new TreeNode(driver, ExtJSQueryType.Custom, getExpression());
		return treeNode.getRootNode();
	}
	
	/**
	 * select node by given string
	 * 
	 * @param name
	 * 
	 * 
	 * @return TreeNode
	 */
	public TreeNode selectNodeByName(final String name) {
		final TreeNode treeNode = new TreeNode(driver, ExtJSQueryType.Custom, getExpression());
		treeNode.getRootNode().findNodeGridChild(name);
		
		return treeNode.getSelectedNode();
	}
	
	/**
	 * Select the ui-node by given nodeId
	 * 
	 * @param id
	 *            - nodeId
	 * 
	 * 
	 * @return TreeNode
	 */
	public TreeNode select(final String id) {
		getCleanEval(".getSelectionModel().select(" //
				+ getExpression() + ".nodeHash['" + id + "']" + //
				")");
		final TreeNode treeNode = new TreeNode(driver, ExtJSQueryType.Custom, getExpression());
		
		return treeNode.getSelectedNode();
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
	 * Method getErrorText.
	 * 
	 * @return String
	 */
	public String getErrorText() {
		WebElement el = driver.findElement(By.xpath(getXPath() + "//div[@class='x-form-invalid-msg']"));
		final String text = el.getText();
		return text;
	}
	
	/**
	 * (search in node attribute) return True if found any match
	 * 
	 * @param id
	 *            - nodeId
	 * 
	 * 
	 * @return boolean
	 */
	public boolean contains(final String id) {
		final String eval = getEval(".nodeHash['" + id + "'] != null");
		return Boolean.parseBoolean(eval);
	}
	
	/**
	 * return the selected Treenode
	 * 
	 * @return TreeNode
	 */
	public TreeNode getSelectedNode() {
		final TreeNode node = new TreeNode(driver, ExtJSQueryType.Custom, getExpression());
		return node.getSelectedNode();
	}
	
	/**
	 * Method whaitForloading.
	 */
	public void waitForloading() {
		waitForTreeLoadingMask("loading-mask");
	}
}
