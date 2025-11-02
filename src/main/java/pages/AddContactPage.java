package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddContactPage extends BasePage{
    // Fields
    private static final By firstNameSelector = By.id("firstName");
    private static final By lastNameSelector = By.id("lastName");
    private static final By dateOfBirthSelector = By.id("birthdate");
    private static final By emailSelector = By.id("email");
    private static final By phoneSelector = By.id("phone");
    private static final By street1Selector = By.id("street1");
    private static final By street2Selector = By.id("street2");
    private static final By citySelector = By.id("city");
    private static final By stateProvinceSelector = By.id("stateProvince");
    private static final By postalCodeSelector = By.id("postalCode");
    private static final By countrySelector = By.id("country");
    private static final By submitContactSelector = By.id("submit");
    private static final By tableSelector = By.id("myTable");


    // Constructor
    public AddContactPage(WebDriver driver) {
        super(driver);
    }

    // Method for adding contact with required fields only
    public MyContactsPage addContact(String firstName, String lastName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameSelector));
        driver.findElement(firstNameSelector).sendKeys(firstName == null ? "" : firstName);
        driver.findElement(lastNameSelector).sendKeys(lastName);
        driver.findElement(submitContactSelector).click();
        return new MyContactsPage(driver);
    }

    // Method for adding a contact with required fields and email
    public MyContactsPage addContact(String firstName, String lastName, String email){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameSelector));
        driver.findElement(firstNameSelector).sendKeys(firstName);
        driver.findElement(lastNameSelector).sendKeys(lastName);
        driver.findElement(emailSelector).sendKeys(email);
        driver.findElement(submitContactSelector).click();
        return new MyContactsPage(driver);
    }

    // Method for adding the contact with all fields
    public MyContactsPage addContact(String firstName, String lastName, String dateOfBirth, String email, String phone,
                                     String street1, String street2, String city, String stateProvince,
                                     String postalCode, String country) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameSelector));

        String[] dataInputs = {firstName, lastName, dateOfBirth, email, phone, street1, street2, city, stateProvince,
                postalCode, country};
        By[] selectors = {firstNameSelector, lastNameSelector, dateOfBirthSelector, emailSelector, phoneSelector,
                street1Selector, street2Selector, citySelector, stateProvinceSelector, postalCodeSelector, countrySelector};

        for (int i = 0; i < dataInputs.length; i++) {
            String inputValue = dataInputs[i] == null ? "" : dataInputs[i];  // Handle null directly here
            driver.findElement(selectors[i]).sendKeys(inputValue);
        }

        driver.findElement(submitContactSelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
        return new MyContactsPage(driver);
    }
}