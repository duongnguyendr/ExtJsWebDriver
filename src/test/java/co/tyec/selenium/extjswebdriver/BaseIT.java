
package co.tyec.selenium.extjswebdriver;

import java.io.IOException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseIT
{

    protected static WebDriver driver;

    final static Logger logger = LoggerFactory.getLogger(BaseIT.class);

    private static String htmlTestLocation;

    protected ByExtJsComponentQuery byExtJsComponentQuery;

    private ByExtJsGetCmp byExtJsGetCmp;

    /**
     * This rule states that if there is an exception, log it to logger.
     * Without this, Junit will catch it in it's own XML logs, but it will not be displayed
     * in your personal logs or console.
     * To get the logs in your console, configure log4j to append to the console.
     * 
     * @author tyork
     */
    public class ExceptionLoggingRule implements TestRule
    {

        Logger logger = LoggerFactory.getLogger(ExceptionLoggingRule.class);

        public Statement apply(Statement base, Description description)
        {
            return statement(base);
        }

        private Statement statement(final Statement base)
        {
            return new Statement()
            {

                @Override
                public void evaluate() throws Throwable
                {
                    try
                    {
                        base.evaluate();
                    }
                    catch (Exception e)
                    {
                        logger.error("JUnit Test Threw Exception: ", e);
                        throw e;
                    }
                }
            };
        }
    }

    /**
     * This Chain states that we only want to see the thrown exception for regular test cases.
     * If a Test expects an exception, that exception will NOT be logged.
     * If a Test does NOT expect any exceptions, there will be a logged message.
     */
    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(new ExceptionLoggingRule()).around(ExpectedException.none());

    @BeforeClass
    public static void beforeClassBase() throws IOException
    {
        URL res = BaseIT.class.getResource("ExtJSTest.html");
        htmlTestLocation = BaseIT.class.getResource("ExtJSTest.html").toString();
        logger.info("ExtJSTest Location: " + htmlTestLocation);

        logger.info("Starting Selenium FirefoxDriver");
        FirefoxProfile profile = new FirefoxProfile();
        driver = new FirefoxDriver(profile);
    }

    @AfterClass
    public static void afterclassBase()
    {
        try
        {
            if (driver != null)
            {
                driver.quit();
            }
        }
        catch (Exception e)
        {
            logger.debug("Exception closing driver", e);
        }
    }

    @Before
    public void beforeMethodBase()
    {
        logger.info("Navigating to: " + htmlTestLocation);
        driver.navigate().to(htmlTestLocation);
        byExtJsComponentQuery = new ByExtJsComponentQuery(driver);
        byExtJsGetCmp = new ByExtJsGetCmp(driver);

    }
}