
package co.tyec.selenium.extjswebdriver;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSExtendedWebElementIT extends BaseIT
{

    final static Logger logger = LoggerFactory.getLogger(JSExtendedWebElementIT.class);

    @Test(expected = ClassCastException.class)
    public void classCastExceptionForInteger()
    {
        // You cannot cast an output to Integer.
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        Integer retInt = (Integer) jsEl.execScriptClean("return 1+1;");
    }

    @Test(expected = ClassCastException.class)
    public void classCastExceptionForString()
    {
        // You cannot cast an output to String.
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        String retString = (String) jsEl.execScriptClean("return 1+1;");
    }

    @Test
    public void elementConstructorTest()
    {
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        Assert.assertNotNull(jsEl);

        Object ret = null;
        ret = jsEl.execScriptOnTopLevelElement("return el.tagName");
        Assert.assertEquals("BODY", String.valueOf(ret).toUpperCase()); // firefox does all upper case. i dont care.
    }

    @Test
    public void longJSReturnTest()
    {
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        // Long
        Object ret = jsEl.execScriptClean("return 1+1");
        Assert.assertTrue(ret instanceof Long);
        Long retLong = (Long) ret;
        Assert.assertEquals(Long.valueOf("2"), retLong);
    }

    @Test
    public void noClassCastExceptionForLongValueOf()
    {
        // You cannot parse the output with Long.valueOf, but you can convert to string first (but you should just
        // cast to long if it's castable.
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        Long retString = Long.valueOf(String.valueOf(jsEl.execScriptClean("return 1+1;")));
    }

    @Test
    public void noClassCastExceptionForStringValueOf()
    {
        // You CAN parse the output with String.valueOf
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        String retString = String.valueOf(jsEl.execScriptClean("return 1+1;"));
    }

    @Test
    public void queryConstructorTest()
    {
        // query to find the body tag
        String query = "var arr = document.getElementsByTagName('body'); if(arr.length >=0){ return arr[0]; } else { return arr; }";
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, query);
        Assert.assertNotNull(jsEl);

        Object ret = null;
        ret = jsEl.execScriptOnTopLevelElement("return el.tagName");
        Assert.assertEquals("BODY", String.valueOf(ret).toUpperCase()); // firefox does all upper case. i dont care.
    }

    @Test
    public void returnBooleanTest()
    {
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        Object ret = null;

        // return true
        ret = jsEl.execScriptCleanReturnBoolean("return true;");
        Assert.assertTrue((Boolean) ret);

        // return false
        ret = jsEl.execScriptCleanReturnBoolean("return false;");
        Assert.assertFalse((Boolean) ret);

        // Integers always return false
        ret = jsEl.execScriptCleanReturnIsNull("return 1;");
        Assert.assertFalse((Boolean) ret);

        // Strings always return false
        ret = jsEl.execScriptCleanReturnIsNull("return \"something\";");
        Assert.assertFalse((Boolean) ret);

        // Strings always return false, even if the string is "true"
        ret = jsEl.execScriptCleanReturnIsNull("return \"true\";");
        Assert.assertFalse((Boolean) ret);
    }

    @Test
    public void returnIsNullTest()
    {
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        Boolean ret = jsEl.execScriptCleanReturnIsNull("");
        Assert.assertTrue(ret);

        ret = jsEl.execScriptCleanReturnIsNull("return 1;");
        Assert.assertFalse(ret);
    }

    @Test
    public void simpleJSReturnTest()
    {
        WebElement el = driver.findElement(By.xpath("//body"));
        JSExtendedWebElement jsEl = new JSExtendedWebElement(driver, el);
        // No return. This test proves that you need a return.
        Object ret = jsEl.execScriptClean("1;");
        Assert.assertEquals(null, ret);

        // With return, no Semicolon. This test proves that you do NOT need a semi colon.
        ret = jsEl.execScriptClean("return \"something\"");
        Assert.assertEquals("something", ret);

        // With return, and semicolon
        ret = jsEl.execScriptClean("return \"something\";");
        Assert.assertEquals("something", ret);
    }
}
