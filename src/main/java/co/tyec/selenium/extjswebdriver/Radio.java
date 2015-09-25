
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Radio extends ExtJSComponent
{

    public Radio(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public Radio(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * @return boolean
     */
    public boolean isChecked()
    {
        return execScriptOnExtJsCmpReturnBoolean("return extCmp.checked");
    }

}