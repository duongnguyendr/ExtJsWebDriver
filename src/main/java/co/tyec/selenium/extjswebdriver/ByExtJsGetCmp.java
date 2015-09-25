
package co.tyec.selenium.extjswebdriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by yorta01 on 9/24/2015.
 */
public class ByExtJsGetCmp extends By
{

    protected final JavascriptExecutor jse;

    private final String query;

    ByExtJsGetCmp(WebDriver webDriver)
    {
        this.jse = (JavascriptExecutor) webDriver;
        this.query = null;
    }

    ByExtJsGetCmp(WebDriver webDriver, String query)
    {
        this.jse = (JavascriptExecutor) webDriver;
        this.query = query;
    }

    ByExtJsGetCmp(JavascriptExecutor jse, String query)
    {
        this.jse = jse;
        this.query = query;
    }

    public ByExtJsGetCmp byExtJsGetCmp(JavascriptExecutor jse, String query)
    {
        return new ByExtJsGetCmp(jse, query);
    }

    public ByExtJsGetCmp byExtJsGetCmp(String query)
    {
        return new ByExtJsGetCmp(this.jse, query);
    }

    private String convertByToJs()
    {
        String js = String.format("return Ext.getCmp(\"%s\").getEl().dom;", query);
        return js;
    }

    @Override
    public WebElement findElement(SearchContext searchContext)
    {
        if (searchContext instanceof WebDriver)
        {
            searchContext = null;
        }
        return (WebElement) jse.executeScript(convertByToJs(), searchContext);
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext)
    {
        if (searchContext instanceof WebDriver)
        {
            searchContext = null;
        }
        Object out = jse.executeScript(convertByToJs(), searchContext);
        List<WebElement> list = new ArrayList<WebElement>();
        list.add((WebElement) out);
        return list;
    }
}
