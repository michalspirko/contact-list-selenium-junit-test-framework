package listeners;

import base.BaseTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.WebDriver;
import utils.ConfigLoader;
import utils.ScreenshotUtil;

// Class implementing TestExecutionExceptionHandler to handle test exceptions
public class CustomTestExecutionExceptionHandler implements TestExecutionExceptionHandler {

    // Override method to handle exceptions during test execution
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        // Check if config allows for screenshot capture
        if (ConfigLoader.shouldCaptureScreenshot()) {

            // Get the name of the test class for screenshot labeling
            String testName = context.getRequiredTestClass().getName();

            // Retrieve the test instance and cast it to BaseTest
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest) {

                // Extract the WebDriver object from the BaseTest instance
                WebDriver driver = ((BaseTest) testInstance).driver;

                // Take and save the screenshot
                ScreenshotUtil.takeScreenshot(driver, testName + "_" + CustomTestWatcher.getStartTime());
            }
        }

        // Re-throw the original exception to ensure it's handled by JUnit
        throw throwable;
    }
}
