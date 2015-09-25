
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Taylor york
 */
public class BasicForm extends ExtJSComponent
{

    public BasicForm(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public BasicForm(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    public ExtJSComponent findComponentIn(By by)
    {
        return new ExtJSComponent(driver, by);
    }
}
