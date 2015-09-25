
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TriggerField extends ExtJSComponent
{

    /**
     * Field trigger.
     */
    private Button trigger;

    public TriggerField(WebDriver driver, By by)
    {
        super(driver, by);
        trigger = new Button(driver, by);
    }

    public TriggerField(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
        ByExtJsGetCmp byExtJsGetCmp = new ByExtJsGetCmp(driver);
        // this seems wrong
        trigger = new Button(driver, byExtJsGetCmp.byExtJsGetCmp(getExpression() + ".trigger"));
    }

    /**
     * Method clickTrigger.
     * 
     * @return TriggerField
     */
    public TriggerField clickTrigger()
    {
        trigger.click();

        return this;
    }

}
