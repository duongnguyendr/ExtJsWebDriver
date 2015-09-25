
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 */
public class Grid extends ExtJSComponent
{

    /**
     * Field gridExp.
     */
    String gridExp = "";

    public Grid(WebDriver driver, By by)
    {
        super(driver, by);
        gridExp = getExpression();
    }

    public Grid(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
        gridExp = getExpression();
    }

    /**
     * Click on selected cell (with link inside) by given row and column
     * 
     * @deprecated This function just doesnt look like it works. its too general.
     * @param row
     * @param col
     * @return Grid
     */
    @Deprecated
    public Grid clickOnCell(final int row, final int col)
    {
        WebElement cell = topElement.findElement(By.xpath(String.format(".//*[contains(text(),'%s')]", getCellValue(row, col))));
        cell.click();
        return this;
    }

    /**
     * clicks on a dom object (with the specified CSS class) residing inside a grid cell
     * 
     * @param row
     *            - the row of the cell
     * @param col
     *            - the column of the cell
     * @param cssClass
     *            - the CSS class of the DOM object inside the grid cell, upon which the click should be fired
     * @return Grid - returns the object (this)
     */

    public Grid clickOnItemWithClassInCell(final int row, final int col, final String cssClass)
    {
        final String cellDomExpression = getCellDomObjectExpression(row, col);
        String xpath = getComponentXpath(driver, cellDomExpression);
        xpath += "//*[contains(@class, \"" + cssClass + "\")]";
        driver.findElement(By.xpath(xpath)).click();

        return this;
    }

    /**
     * Method contains.
     * 
     * @param key
     *            String
     * @return boolean
     */
    public boolean contains(final String key)
    {
        final String[] keys = getKeys();
        for (final String key2 : keys)
        {
            if (key.equals(key2))
            {
                return true;
            }

        }

        return false;

    }

    /**
     * Double click on an editable cell by given row and column
     * 
     * @param row
     * @param col
     * @return Grid
     */
    public Grid doubleClickOnEditableCell(final int row, final int col)
    {
        final String cellDomExpression = getCellDomObjectExpression(row, col);
        final String xpath = getComponentXpath(driver, cellDomExpression);

        WebElement cell = driver.findElement(By.xpath(xpath));

        Actions actions = new Actions(driver);
        actions.doubleClick(cell);
        actions.perform();

        return this;
    }

    public static String getComponentXpath(final WebDriver driver, final String element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript(String.format("return window.createXPathFromElement(%s)", element));
    }

    /**
     * If the cell in an editor grid is currently being edited, change its value to the speicified one
     * 
     * @param row
     * @param col
     * @param value
     * @return Grid
     */

    public Grid editCellAndSetValue(final int row, final int col, final String value)
    {

        execScriptClean(String.format("%s.getColumnModel().getCellEditor(%d,%d).setValue('%s')", gridExp, col, row, value));
        return this;
    }

    /**
     * searches for a row with a specific value in a specified column, and returns the row index
     * 
     * @param col
     *            - the column to search over
     * @param requiredValue
     *            - the required value to look for
     * @return int - the index of the column (starting from 0)
     */

    public int findRowIndexByColumnValue(final int col, final String requiredValue)
    {
        final String columnDataIndex = (String) execScriptOnExtJsCmp(String.format("return extCmp.getColumnModel().getDataIndex(%d)", col));
        return Integer.parseInt((String) execScriptOnExtJsCmp(String.format(".getStore().find('%s', '%s')",
                                                                            columnDataIndex,
                                                                            requiredValue)));
    }

    /**
     * return the dom object EXPRESSION of inner cell This would only be translated to the DOM object in the client-side upon selenium.eval
     * 
     * @param row
     * @param col
     * @return String
     */
    public String getCellDomObjectExpression(final int row, final int col)
    {
        return String.format("window.Ext.fly(%s.view.getCell(%d,%d)).dom", gridExp, row, col);
    }

    /**
     * Get the inner html of given cell.
     * 
     * @param row
     *            starts from 0
     * @param col
     *            starts from 0
     * @return String
     */
    public String getCellValue(final int row, final int col)
    {

        final String colExp = String.format("window.Ext.fly(%s.view.getCell(%d,%d)).dom.textContent", gridExp, row, col);

        return (String) execScriptClean(colExp);
    }

    /**
     * return the header of the grid by given column index
     * 
     * @param colIndex
     * @return String
     */
    public String getColumnHeader(final Integer colIndex)
    {

        return (String) execScriptOnExtJsCmp(String.format("return extCmp.getColumnModel().getColumnHeader(%d)", colIndex));
    }

    /**
     * return the ui row count (just what the user see)
     * 
     * @return Long
     */
    public Long getGridRowCount()
    {
        waitToLoad();
        return (Long) execScriptOnExtJsCmp("return extCmp.view.getRows().length");
    }

    /**
     * Method getGridStoreCount.
     * 
     * @return Integer
     */
    public Long getGridStoreCount()
    {
        waitToLoad();
        return (Long) execScriptOnExtJsCmp("extCmp.getStore().data.length");
    }

    /**
     * @return array of id
     */
    public String[] getKeys()
    {
        return ((String) execScriptOnExtJsCmp("return el.getStore().data.keys")).split(",");
    }

    /**
     * return selected row index
     * 
     * @return int
     */
    public int getSelectedIndex()
    {
        final int length = getStoreDataLength();
        for (int i = 0; i < length; i++)
        {
            if (isSelected(i))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * return count of grid data
     * 
     * @return (int) count of grid data
     */
    public int getStoreDataLength()
    {
        Integer length = (Integer) execScriptOnExtJsCmp("return extCmp.getStore().data.length");
        if (length == null)
        {
            length = 0;
        }

        return length;
    }

    /**
     * return True if given Index row is selected
     * 
     * @param index
     * @return boolean
     */
    public boolean isSelected(final int index)
    {
        return execScriptOnExtJsCmpReturnBoolean("return extCmp.getSelectionModel().isSelected(" + index + ")");
    }

    /**
     * Method openContextMenu.
     * 
     * @param rowNumber
     *            int
     * @param colNumber
     *            int
     * @param menuId
     *            String
     * @return Menu
     */
    public Menu openContextMenu(final int rowNumber, final int colNumber, final String menuId)
    {
        final String[] keys = getKeys();
        return openContextMenu(keys[rowNumber], colNumber, menuId);
    }

    /**
     * Method openContextMenu.
     * 
     * @param selectedKey
     *            String
     * @param colNumber
     *            int
     * @param menuId
     *            String
     * @return Menu
     */
    public Menu openContextMenu(final String selectedKey, final int colNumber, final String menuId)
    {
        WebElement el = driver.findElement(By.xpath(getComponentId() + "_" + selectedKey + "_col" + colNumber));

        Actions actions = new Actions(driver);
        actions.contextClick(el);
        actions.perform();
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        return new Menu(driver, byExtJsGetCmp.byExtJsGetCmp(menuId));
    }

    /**
     * Method openContextMenu.
     * 
     * @param selectedKey
     *            String
     * @param menuId
     *            String
     * @return Menu
     */
    public Menu openContextMenu(final String selectedKey, final String menuId)
    {
        return openContextMenu(selectedKey, 1, menuId);
    }

    /**
     * Select ui-row by given row index
     * 
     * @param index
     *            - (int) row Index start from 0
     * @return Grid
     */
    public Grid select(final int index)
    {
        waitToLoad();
        execScriptOnExtJsCmp("extCmp.getSelectionModel().selectRow(" + index + ")");

        return this;
    }

    /**
     * Method select.
     * 
     * @param key
     *            String
     * @return Grid
     */
    public Grid select(final String key)
    {
        // Didn't worked!
        // String eval = getEval( ".getStore().data.indexOfKey('" + key + "')" );

        final String[] keys = getKeys();
        for (int i = 0; i < keys.length; i++)
        {
            if (key.equals(keys[i]))
            {
                select(i);

                return this;
            }

        }

        throw new IllegalArgumentException("Unable to select" + key);

    }

    /**
     * Select row by given Text to search and cell - (where to search)
     * 
     * @param text
     *            - any text
     * @param cellIndex
     *            - search the text in the cell start from 0
     * @return selected (int) index
     */
    public Integer select(final String text, final int cellIndex)
    {
        waitToLoad();
        final int gridCount = getStoreDataLength();
        for (int i = 0; i < gridCount; i++)
        {
            if (getCellValue(i, cellIndex).equals(text))
            {

                select(i);
                return i;
            }
        }
        return -1;

    }

    /**
     * wait until the mask disappear
     */
    public void waitForLoading()
    {
        waitForGridLoadingMaskToDisappear(getComponentId());
    }

}
