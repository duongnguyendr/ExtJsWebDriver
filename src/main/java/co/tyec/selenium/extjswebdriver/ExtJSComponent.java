
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class ExtJSComponent extends JSExtendedWebElement
{

    static final private String FUNCTION_findVisibleComponentElement = "ExtJsWebDriver.findVisibleComponentElement = function (query) {"//
                    + "  var queryResultArray = (window.frames[0] &&  window.frames[0].Ext) ? window.frames[0].Ext.ComponentQuery.query(query) : Ext.ComponentQuery.query(query);"//
                    + "  var single = null;" //
                    + "  Ext.Array.every(queryResultArray, function(comp) {" //
                    + "	     if (comp != null && comp.isVisible(true)){" //
                    + "	       single = comp;"// 
                    + "	     }"//
                    + "	     return (single != null);" /* return false will stop looping through array */
                    + "	   });" //
                    + "  var el = (single != null ? single.getEl().dom : null);"//
                    + "  return el;"//
                    + "}";

    final static Logger logger = LoggerFactory.getLogger(ExtJSComponent.class);

    /**
     * This is meant to be a general way to convert a specific id to the general id ( Ex. td[@id="combobox-1009-bodyEl"] to comboBox-1009)
     */
    protected static final String SCRIPT_TOP_ELEMENT_TO_EXT_JS_CMP = "var id = el.id"
                    + ".replace(/-\\w*El$/,'')" /* -inputEl, -bodyEl, -anythingEl */
                    + ".replace('-triggerWrap','');" //
                    + "var extCmp = Ext.getCmp(id);";

    static private final String SCRIPT_TOP_ELEMENT_TO_EXT_JS_ID = SCRIPT_TOP_ELEMENT_TO_EXT_JS_CMP + ";return extCmp.getId();"; // should this be .id?

    public ExtJSComponent(WebDriver driver, By by)
    {
        super(driver, by);
    }

    protected String extJsCmpId = null;

    final String FUNCTION_getXPathTo = "ExtJsWebDriver.getXPathTo = function getXPathTo(element) {" //
                    + "    if (element.id!=='')"//
                    + "        return 'id(\"'+element.id+'\")';"//
                    + "    if (element===document.body)" //
                    + "        return element.tagName;"//
                    + "    var ix= 0;" //
                    + "    var siblings= element.parentNode.childNodes;" //
                    + "    for (var i= 0; i<siblings.length; i++) {"//
                    + "        var sibling= siblings[i];"//
                    + "        if (sibling===element)"//
                    + "            return ExtJsWebDriver.getXPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']';"//
                    + "        if (sibling.nodeType===1 && sibling.tagName===element.tagName)"// 
                    + "            ix++;"// 
                    + "    }" //
                    + "}";

    /**
     * @param driver
     * @param topElement
     *            - locator of either parent topElement which wraps text input and drop down button or text input
     */
    public ExtJSComponent(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * Method blur.
     * 
     * @deprecated
     */
    public void blur()
    {
        fireEvent("blur");
    }

    /**
     * This is used to run javascript scrits on the ExtJS ExtJSComponent. For Example, execScriptOnExtJsComponent("return extCmp.getValue()"); will run the
     * JavaScript method getValue on the ExtJS component object.
     * 
     * @param jsCode
     *            Javscript code to execute.
     * @return
     */
    public Object execScriptOnExtJsCmp(String jsCode)
    {
        String finalScript = String.format("var extCmp = Ext.getCmp('%s'); %s;", getComponentId(), jsCode);
        return execScriptClean(finalScript);
    }

    public Boolean execScriptOnExtJsCmpReturnBoolean(String jsCode)
    {
        String finalScript = String.format("var extCmp = Ext.getCmp('%s'); %s;", getComponentId(), jsCode);
        return execScriptCleanReturnBoolean(finalScript);
    }

    /**
     * Method evalNullComponent.
     * 
     * @param jsCode
     *            String
     * @return boolean
     */
    public boolean execScriptOnExtJSCmpReturnIsNull(final String jsCode)
    {
        String finalScript = String.format("var extCmp = Ext.getCmp('%s'); %s;", getComponentId(), jsCode);
        try
        {
            Object out = execScriptOnExtJsCmp(finalScript);
            return out == null || "null".equals(out);
        }
        catch (final Exception e)
        {
            return false;
        }
    }

    public String execScriptOnExtJsCmpReturnString(String jsCode)
    {
        String finalScript = String.format("var extCmp = Ext.getCmp('%s'); %s;", getComponentId(), jsCode);
        return String.valueOf(execScriptCleanReturnBoolean(finalScript));
    }

    @Deprecated
    void fireEvent(String event)
    {
        JavascriptLibrary lib = new JavascriptLibrary();
        lib.callEmbeddedSelenium(driver, "triggerEvent", topElement, event);
    }

    /**
     * Method focus.
     * 
     * @deprecated remove all event methods
     */
    @Deprecated
    public void focus()
    {
        fireEvent("focus");
    }

    /**
     * return the componentId
     * 
     * @return String
     */
    public String getComponentId()
    {
        if (extJsCmpId == null)
        {
            // well then we better have the WebElement!
            if (topElement == null)
                throw new RuntimeException("Neither extJsCmpId or topElement has been set");
            extJsCmpId = (String) execScriptOnTopLevelElement(SCRIPT_TOP_ELEMENT_TO_EXT_JS_ID);
        }
        return extJsCmpId;
    }

    /**
     * Returns the Ext.Element which encapsulates this ExtJSComponent.
     * 
     * @return String
     */
    public WebElement getElDom()
    {
        return (WebElement) execScriptOnExtJsCmp("return extCmp.getEl().dom");
    }

    /**
     * Returns the absolute expression that resolves this proxy's Ext component.
     * 
     * @return String
     */
    public String getExpression()
    {
        return String.format("window.Ext.getCmp('%s')", getComponentId());
    }

    /**
     * Returns an XPath to the Ext component, which contains the ID provided by getId()
     * 
     * @return String
     */
    public String getXPath()
    {
        return String.format("%s return getPathTo(%s", FUNCTION_getXPathTo, topElement);
    }

    /**
     * Gets the xtype for this component as registered with Ext.ComponentMgr ------------
     * 
     * @return String
     */
    public String getXType()
    {
        return (String) execScriptOnExtJsCmp("return el.getXType()");
    }

    /**
     * Returns this ExtJSComponent's xtype --hierarchy-- as a slash-delimited string ------------
     * 
     * @return List<String>
     */
    public String getXTypes()
    {
        return (String) execScriptOnExtJsCmp("return el.getXTypes()");
    }

    /**
     * Method isHidden.
     * 
     * @return boolean
     */
    public boolean isHidden()
    {
        return execScriptOnExtJsCmpReturnBoolean("return extCmp == null") || execScriptOnExtJsCmpReturnBoolean("return extCmp.isHidden");
    }

    /**
     * return true if the component is Disabled
     * 
     * @return boolean
     */
    public boolean isDisabled()
    {
        return (Boolean) execScriptOnExtJsCmp("return extCmp.disabled");
    }

    /**
     * Method isVisible.
     * 
     * @return boolean
     */
    public boolean isVisible()
    {
        return !isHidden();
    }

    /**
     * Method waitFor msg dialog when has an error/failure .
     */
    public void waitForDialogFailure()
    {
        js.executeScript("window.Ext.Msg.getDialog()");

        try
        {
            waitForExecScriptToReturnTrue("window.Ext.Msg.isVisible() == false");
        }
        catch (final RuntimeException e)
        {
            throw e;
        }
    }

    /**
     * Runs a script on an ext JS componenet. If it returns false, it waits, then tries again.
     * Up to timeoutSecond.
     * 
     * @param expr
     *            String
     */
    protected void waitForExecScriptOnExtJSCmpToReturnTrue(final String expr)
    {
        final String fullExpr = getExpression() + expr;
        waitForExecScriptToReturnTrue(fullExpr);
    }

    /**
     * Method what until all ajax requests are finshed .
     * 
     * @return boolean return true if there is no ajax request on AIRR
     */
    @Override
    protected boolean waitForFinishAjaxRequest()
    {
        // DO NOT use helper methods. go straight to js
        long start = System.currentTimeMillis();
        long end = start + timeOutInSeconds * 1000;
        boolean ret = false;
        while (System.currentTimeMillis() <= end)
        {
            try
            {
                if (Boolean.FALSE.equals(js.executeScript("return Ext.Ajax.isLoading()")))
                {
                    ret = true;
                    break; // should not run the sleep below.
                }
            }
            catch (final Exception e)
            {
                // ignore
            }
            try
            {
                Thread.sleep(sleepInMillis);
            }
            catch (final InterruptedException e)
            {
                // ignore
            }
        }
        return ret;
    }

    /**
     * Wait until the Grid Loading Mask is not isVisible any more
     * 
     * @param componentId
     *            String
     * @return boolean
     */
    protected boolean waitForGridLoadingMaskToDisappear(final String componentId)
    {
        final boolean success = waitForExecScriptToReturnTrue(String.format("Ext.getCmp('%s').getEl().isMasked()", componentId));

        return success;
    }

    /**
     * Wait For Tree Loading Mask To Disappear.
     * 
     * @param panelMaskId
     *            String
     * @return boolean
     */
    protected boolean waitForTreeLoadingMaskToDisappear(final String panelMaskId)
    {
        final boolean success = waitForExecScriptToReturnTrue(String.format("!window.Ext.fly('%s').isVisible()", panelMaskId));
        return success;
    }

    /**
     * Wait for component. until the component is enable again.
     */
    public void waitToLoad()
    {
        waitForExecScriptOnExtJSCmpToReturnTrue("extCmp.disabled != true");
    }

}