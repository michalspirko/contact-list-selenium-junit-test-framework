package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditContactPage extends BasePage{
    // Fields
    private static final By firstNameSelector = By.id("firstName");
    private static final By emailSelector = By.id("email");
    private static final By submitButtonSelector = By.id("submit");
    private static final By contactDetailsFormSelector = By.id("contactDetails");

    // Constructor
    public EditContactPage(WebDriver driver) {
        super(driver);
    }

    // Method for editing the 'First Name' and 'Last Name' field; returns ContactDetailsPage
    public ContactDetailsPage editNameField(String newFirstName) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameSelector));
        // Custom wait to ensure the input field is populated
        wait.until((WebDriver d) -> {
            String fieldValue = d.findElement(firstNameSelector).getAttribute("value");
            return fieldValue != null && !fieldValue.isEmpty();
        });
        // Waiting for fields to populate
        Thread.sleep(2000);
        driver.findElement(firstNameSelector).click();
        driver.findElement(firstNameSelector).sendKeys(Keys.CONTROL + "a");
        driver.findElement(firstNameSelector).sendKeys(Keys.DELETE);
        driver.findElement(firstNameSelector).clear();
        driver.findElement(firstNameSelector).sendKeys(newFirstName);
        driver.findElement(submitButtonSelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactDetailsFormSelector));
        return new ContactDetailsPage(driver);
    }

    // Method for editing the email field
    public ContactDetailsPage editEmailField(String email) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailSelector));
        // Custom wait to ensure the input field is populated
        wait.until((WebDriver d) -> {
            String fieldValue = d.findElement(firstNameSelector).getAttribute("value");
            return fieldValue != null && !fieldValue.isEmpty();
        });
        // Waiting for input fields to populate
        Thread.sleep(2000);
        driver.findElement(emailSelector).click();
        driver.findElement(emailSelector).sendKeys(Keys.CONTROL + "a");
        driver.findElement(emailSelector).sendKeys(Keys.DELETE);
        driver.findElement(emailSelector).clear();
        driver.findElement(emailSelector).sendKeys(email);
        driver.findElement(submitButtonSelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactDetailsFormSelector));
        return new ContactDetailsPage(driver);
    }
}
