
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Window extends BasicForm
{

    public Window(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public Window(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * Method close.
     */
    public void close()
    {
        topElement.findElement(By.xpath(".//div[contains(@class, 'x-tool-close')]")).click();
    }

}
