
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class Fieldset extends ExtJSComponent
{

    /**
     * Field checkbox.
     */
    private Checkbox checkbox;

    public Fieldset(WebDriver driver, By by)
    {
        super(driver, by);
    }

    public Fieldset(WebDriver driver, WebElement topElement)
    {
        super(driver, topElement);
    }

    /**
     * Method check.
     * 
     * @param enable
     *            boolean
     * @return Fieldset
     */
    public Fieldset check(final boolean enable)
    {
        checkbox.check(enable);

        return this;
    }

}
