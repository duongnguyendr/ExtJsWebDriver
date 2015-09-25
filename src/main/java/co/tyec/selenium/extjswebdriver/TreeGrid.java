/***
 * 
 * @author Asaf
 *
 */

package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TreeGrid extends ExtJSComponent
{

    public TreeGrid(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public TreeGrid(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * (search in node attribute) return True if found any match
     * 
     * @param id
     *            - nodeId
     * @return boolean
     */
    public boolean contains(final String id)
    {
        return execScriptCleanReturnBoolean("return extCmp.nodeHash['" + id + "'] != null");
    }

    /**
     * Method getErrorText.
     * 
     * @return String
     */
    public String getErrorText()
    {
        WebElement el = driver.findElement(By.xpath(getXPath() + "//div[@class='x-form-invalid-msg']"));
        final String text = el.getText();
        return text;
    }

    /**
     * return the root-node of the tree
     * 
     * @return - node
     */
    public TreeNode getRootNode()
    {
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        final TreeNode treeNode = new TreeNode(driver, byExtJsGetCmp.byExtJsGetCmp(getComponentId()));
        return treeNode.getRootNode();
    }

    /**
     * return the selected Treenode
     * 
     * @return TreeNode
     */
    public TreeNode getSelectedNode()
    {
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        final TreeNode node = new TreeNode(driver, byExtJsGetCmp.byExtJsGetCmp(getComponentId()));
        return node.getSelectedNode();
    }

    /**
     * Method hasErrorText.
     * 
     * @param err
     *            String
     * @return boolean
     */
    public boolean hasErrorText(final String err)
    {
        final String text = getErrorText();

        return err.equals(text);
    }

    /**
     * Select the ui-node by given nodeId
     * 
     * @param id
     *            - nodeId
     * @return TreeNode
     */
    public TreeNode select(final String id)
    {
        execScriptClean(".getSelectionModel().select(" //
                        + getExpression() + ".nodeHash['" + id + "']" + //
                        ")");
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        final TreeNode treeNode = new TreeNode(driver, byExtJsGetCmp.byExtJsGetCmp(getComponentId()));

        return treeNode.getSelectedNode();
    }

    /**
     * select node by given string
     * 
     * @param name
     * @return TreeNode
     */
    public TreeNode selectNodeByName(final String name)
    {
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        final TreeNode treeNode = new TreeNode(driver, byExtJsGetCmp.byExtJsGetCmp(getComponentId()));
        treeNode.getRootNode().findNodeGridChild(name);

        return treeNode.getSelectedNode();
    }

    /**
     * Method whaitForloading.
     */
    public void waitForloading()
    {
        waitForTreeLoadingMaskToDisappear("loading-mask");
    }
}
