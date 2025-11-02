package base;

import listeners.CustomTestExecutionExceptionHandler;
import listeners.CustomTestWatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.ConfigLoader;
import utils.WebDriverFactory;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({CustomTestWatcher.class, CustomTestExecutionExceptionHandler.class})
public class BaseTest {
    // Fields
    public WebDriver driver;
    protected LoginPage loginPage;
    protected String browserType;
    protected Boolean isHeadless;
    protected String url;


    // Setup
    @BeforeAll
    void setup() {
        // Get browserType, headless mode and URL from ConfigLoader
        browserType = ConfigLoader.getBrowser();
        isHeadless = ConfigLoader.isHeadless();
        url = ConfigLoader.getTestEnvironmentURL();

        // Create WebDriver based on the fetched configurations
        this.driver = WebDriverFactory.createDriver(browserType, isHeadless);

        //Maximize window
        driver.manage().window().maximize();

        //Step 1 - Open Login page
        loginPage = new LoginPage(driver, url);
        loginPage.open();
    }

    // Teardown
    @AfterAll
    void tearDown() throws IOException, InterruptedException {
        //Quit the driver & end session
        driver.quit();
    }
}

