
package co.tyec.selenium.extjswebdriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>ExtJsComponentTest</code> contains tests for the class <code>{@link ExtJSComponent}</code>.
 */
public class ExtJsComponentIT extends BaseIT
{

    ByExtJsGetCmp cmpIdBy;

    @Before
    public void beforeClass()
    {
        cmpIdBy = byExtJsGetCmp.byExtJsGetCmp("combobox-1009");
    }

    /**
     * Run the boolean cleanEvalTrue(String) method test.
     * 
     * @throws Exception
     */
    @Test
    public void testExecScriptCleanReturnBoolean() throws Exception
    {
        final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
        final boolean result = cmp_result.execScriptCleanReturnBoolean("return true;");

        assertTrue(result);
    }

    /**
     * Run the ExtJSComponent(Selenium,ComponentLocator) constructor test.
     * 
     * @throws Exception
     */
    @Test
    public void testComponent_id() throws Exception
    {
        final ExtJSComponent result = new ExtJSComponent(driver, cmpIdBy);

        // add additional test code here
        assertNotNull(result);
        assertEquals("cmp_id", result.getComponentId());
        assertEquals(true, result.isVisible());
        assertEquals(false, result.isDisabled());
        assertEquals("window.Ext.getCmp('cmp_id')", result.getExpression());
        assertEquals(false, result.isHidden());
        assertEquals("cmp_id", result.getComponentId());
    }

    /**
     * Run the boolean disabled() method test.
     * 
     * @throws Exception
     */
    @Test
    public void testisDisabled() throws Exception
    {
        final ExtJSComponent cmp_result = new ExtJSComponent(driver,
                                                             byExtJsComponentQuery.byExtJsComponentQuery("[itemId='disabledButton']"));
        assertTrue(cmp_result.isDisabled());
    }

    /**
     * Run the String getComponentId() method test.
     * 
     * @throws Exception
     */
    @Test
    public void testGetComponentId() throws Exception
    {
        final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
        final String result = cmp_result.getComponentId();
        assertEquals("combobox-1009", result);
    }

    /**
     * Run the String getElDom() method test.
     * 
     * @throws Exception
     */
    @Test
    public void testGetElDom() throws Exception
    {
        ByExtJsGetCmp by = cmpIdBy;

        final ExtJSComponent cmp_result = new ExtJSComponent(driver, by);
        assertNotNull(cmp_result);
        assertEquals(cmp_result.topElement, cmp_result.getElDom());
    }

    @Test
    public void waitForFinishAjaxRequestTest()
    {
        final ExtJSComponent cmp_result = new ExtJSComponent(driver, cmpIdBy);
        assertTrue(cmp_result.waitForFinishAjaxRequest());
    }
}