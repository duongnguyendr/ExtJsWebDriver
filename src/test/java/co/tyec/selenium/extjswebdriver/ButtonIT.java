
package co.tyec.selenium.extjswebdriver;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonIT extends BaseIT
{

    final static Logger logger = LoggerFactory.getLogger(ButtonIT.class);

    @Test
    public void selectExtJSButton()
    {
        Button button = new Button(driver, byExtJsComponentQuery.byExtJsComponentQuery("[itemId='pressed']"));

        button.click();
        // DomElement pressedDiv = page.domElementGet("DIV[@id='pressed']//*[@textContents='pressed: true']", "//");
        WebElement pressedDiv = driver.findElement(By.xpath("//div[@id='pressed']//*[contains(text(), 'pressed: true')]"));
        Assert.assertNotNull(pressedDiv);
    }

}
