
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextArea extends Field
{

    public TextArea(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public TextArea(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }
}
