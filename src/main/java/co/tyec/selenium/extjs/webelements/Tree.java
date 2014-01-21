package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tree extends Component {
	
	public Tree(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Tree(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	/**
	 * Method getRootNode.
	 * 
	 * @return TreeNode
	 */
	public TreeNode getRootNode() {
		final TreeNode treeNode = new TreeNode(driver, ExtJSQueryType.Custom, getExpression());
		return treeNode.getRootNode();
	}
	
	/**
	 * Method select By Id.
	 * 
	 * @param id
	 *            String
	 * @return TreeNode
	 */
	public TreeNode select(final String id) {
		execScriptClean(".getSelectionModel().select(" //
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
		WebElement errorText = driver.findElement(By.xpath(getXPath() + "//div[@class='x-form-invalid-msg']"));
		final String text = errorText.getText();
		return text;
	}
	
	/**
	 * Method contains.
	 * 
	 * @param id
	 *            String
	 * @return boolean
	 */
	public boolean contains(final String id) {
		final String eval = getEval(".nodeHash['" + id + "'] != null");
		return Boolean.parseBoolean(eval);
	}
	
}
