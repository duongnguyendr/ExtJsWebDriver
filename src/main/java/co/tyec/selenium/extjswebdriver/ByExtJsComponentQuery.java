
package co.tyec.selenium.extjswebdriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by yorta01 on 9/24/2015.
 */
public class ByExtJsComponentQuery extends By
{

    protected JavascriptExecutor jse;

    static final private String FUNCTION_findVisibleComponentElement = "ExtJsWebDriver.findVisibleComponentElement = function (query) {"
                    + "  var queryResultArray = (window.frames[0] &&  window.frames[0].Ext) ? window.frames[0].Ext.ComponentQuery.query(query) : Ext.ComponentQuery.query(query);"
                    + "  var single = null;" + "  Ext.Array.every(queryResultArray, function(comp) {"
                    + "	     if (comp != null && comp.isVisible(true)){" + "	       single = comp;" + "	     }"
                    + "	     return (single != null);" /* return false will stop looping through array */
                    + "	   });" + "  var el = (single != null ? single.getEl().dom : null);" + "  return el;" + "}";

    static final protected String FUNCTION_DEFINE_EXTJSWEBDRIVER = "if(typeof ExtJsWebDriver === \"undefined\") {ExtJsWebDriver = function(){}; ExtJsWebDriver.log = function(arg){ if(console && console.log) console.log(arg)} };";

    private String query;

    ByExtJsComponentQuery(WebDriver webDriver)
    {
        this.jse = (JavascriptExecutor) webDriver;
        this.query = null;
    }

    ByExtJsComponentQuery(WebDriver webDriver, String query)
    {
        this.jse = (JavascriptExecutor) webDriver;
        this.query = query;
    }

    ByExtJsComponentQuery(JavascriptExecutor jse, String query)
    {
        this.jse = jse;
        this.query = query;
    }

    public ByExtJsComponentQuery byExtJsComponentQuery(String query)
    {
        return new ByExtJsComponentQuery(this.jse, query);
    }

    private String convertByToJs()
    {
        String js = String.format("%s; %s; return ExtJsWebDriver.findVisibleComponentElement(\"%s\");",
                                  FUNCTION_DEFINE_EXTJSWEBDRIVER,
                                  FUNCTION_findVisibleComponentElement,
                                  query);
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
        return (List<WebElement>) jse.executeScript(convertByToJs(), searchContext);
    }
}
