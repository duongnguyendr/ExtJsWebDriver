
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends Field
{

    public TextField(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public TextField(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

}
