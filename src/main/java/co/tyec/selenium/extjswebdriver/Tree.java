
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Tree extends ExtJSComponent
{

    public Tree(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public Tree(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * Method contains.
     * 
     * @param id
     *            String
     * @return boolean
     */
    public boolean contains(final String id)
    {
        return execScriptOnExtJsCmpReturnBoolean("return extCmp.nodeHash['" + id + "'] != null");
    }

    /**
     * Method getErrorText.
     * 
     * @return String
     */
    public String getErrorText()
    {
        WebElement errorText = driver.findElement(By.xpath(getXPath() + "//div[@class='x-form-invalid-msg']"));
        final String text = errorText.getText();
        return text;
    }

    /**
     * Method getRootNode.
     * 
     * @return TreeNode
     */
    public TreeNode getRootNode()
    {
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        final TreeNode treeNode = new TreeNode(driver, byExtJsGetCmp.byExtJsGetCmp(getComponentId()));
        return treeNode.getRootNode();
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
     * Method select By Id.
     * 
     * @param id
     *            String
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

}
