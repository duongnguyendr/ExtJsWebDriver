
package co.tyec.selenium.extjswebdriver;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckboxIT extends BaseIT
{

    final static Logger logger = LoggerFactory.getLogger(CheckboxIT.class);

    @Test
    public void selectExtJSCheckBox()
    {
        Checkbox checkbox = new Checkbox(driver, byExtJsComponentQuery.byExtJsComponentQuery("[name='myCheckbox']"));
        Assert.assertNotNull(checkbox.isChecked());

        checkbox.check();
        Assert.assertTrue(checkbox.isChecked());

        checkbox.uncheck();
        Assert.assertFalse(checkbox.isChecked());

        checkbox.check(false);
        Assert.assertFalse(checkbox.isChecked());
    }

}
