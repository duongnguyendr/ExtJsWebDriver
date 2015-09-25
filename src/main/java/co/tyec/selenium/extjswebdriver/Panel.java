
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Panel extends ExtJSComponent
{

    public Panel(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public Panel(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * Method collapse.
     */
    public void collapse()
    {
        execScriptOnExtJsCmp("extCmp.collapse()");
        waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == true");
    }

    /**
     * Method expand.
     */
    public void expand()
    {
        execScriptOnExtJsCmp("extCmp.expand()");
        waitForExecScriptOnExtJSCmpToReturnTrue("return extCmp.collapsed == false");
    }
}
