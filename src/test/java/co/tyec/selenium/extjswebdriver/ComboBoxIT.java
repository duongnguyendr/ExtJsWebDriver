
package co.tyec.selenium.extjswebdriver;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComboBoxIT extends BaseIT
{

    final static Logger logger = LoggerFactory.getLogger(ComboBoxIT.class);

    @Test
    public void selectExtJSComboBox()
    {
        ComboBox comboBox = new ComboBox(driver, byExtJsComponentQuery.byExtJsComponentQuery("[name='state']"));
        comboBox.setValue("Alaska");

        // DomElement stateInput = page.domElementGet("INPUT[@name='state']", "//");
        WebElement stateInput = driver.findElement(By.name("state"));
        Assert.assertNotNull(stateInput);

        // // WebDriver get value
        // String selectedState = (String) stateInput.getAttribute("value");
        // Assert.assertNotNull(selectedState);
        // Assert.assertEquals("Alaska", selectedState);

        // Via framework
        String selectedState = comboBox.getValue();
        Assert.assertNotNull(selectedState);
        Assert.assertEquals("Alaska", selectedState);
    }

}
