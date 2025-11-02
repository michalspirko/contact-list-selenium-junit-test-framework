package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    // Fields
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final By h1Heading = By.tagName("h1");
    private static final By errorSelector = By.id("error");

    // Constructors
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Method for refreshing the page
    public void refreshPage(){
        driver.navigate().refresh();
    }

    // Method for getting the current URL
    public String getURL(String expectedUrl) {
        try {
            wait.until(webDriver -> webDriver.getCurrentUrl().equals(expectedUrl));
        } catch (TimeoutException e) {
            // Just proceed to return the current URL
        }
        return driver.getCurrentUrl();
    }

    // Method for getting the title of the page
    public String getTitle(String expectedTitle) {
        try {
            wait.until(webDriver -> webDriver.getTitle().equals(expectedTitle));
        } catch (TimeoutException e) {
            // Just proceed to return the current title
        }
        return driver.getTitle();
    }

    // Method for getting the h1 heading of the page
    public String getHeading(String expectedHeadingText) {
        try {
            wait.until(webDriver -> {
                        return webDriver.findElement(h1Heading).getText().equals(expectedHeadingText);
                    });
        } catch (TimeoutException e) {
            // Just proceed to return the current h1 heading text
        }
        return driver.findElement(h1Heading).getText();
    }

    // Method for getting the error text
    public String getErrorText(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorSelector));
            return driver.findElement(errorSelector).getText();
        } catch (TimeoutException e) {
            return null;
        }
    }
}

