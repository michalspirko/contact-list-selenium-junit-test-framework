package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactDetailsPage extends BasePage{
    // Fields
    private static final By returnButtonSelector = By.id("return");
    private static final By deleteButtonSelector = By.id("delete");
    private static final By editButtonSelector = By.id("edit-contact");
    private static final By contactFirstNameSelector = By.id("firstName");
    private static final By contactLastNameSelector = By.id("lastName");
    private static final By contactDateOfBirthSelector = By.id("birthdate");
    private static final By contactEmailSelector = By.id("email");
    private static final By contactPhoneSelector = By.id("phone");
    private static final By contactStreetAddress1Selector = By.id("street1");
    private static final By contactStreetAddress2Selector = By.id("street2");
    private static final By contactCitySelector = By.id("city");
    private static final By contactStateProvinceSelector = By.id("stateProvince");
    private static final By contactPostalCodeSelector = By.id("postalCode");
    private static final By contactCountrySelector = By.id("country");
    private static final By tableSelector = By.id("myTable");
    private static final By inputFieldSelector = By.tagName("input");



    // Constructor
    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Method for getting the contact details' elements text
    public String getElementText(By selector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return driver.findElement(selector).getText();
    }

    // Getters for the contact details elements
    public String getFirstName() {
        return getElementText(contactFirstNameSelector);
    }

    public String getLastName() {
        return getElementText(contactLastNameSelector);
    }

    public String getDateOfBirth() {
        return getElementText(contactDateOfBirthSelector);
    }

    public String getEmail() {
        return getElementText(contactEmailSelector);
    }

    public String getPhone() {
        return getElementText(contactPhoneSelector);
    }

    public String getStreetAddress1() {
        return getElementText(contactStreetAddress1Selector);
    }

    public String getStreetAddress2() {
        return getElementText(contactStreetAddress2Selector);
    }

    public String getCity() {
        return getElementText(contactCitySelector);
    }

    public String getStateProvince() {
        return getElementText(contactStateProvinceSelector);
    }

    public String getPostalCode() {
        return getElementText(contactPostalCodeSelector);
    }

    public String getCountry() {
        return getElementText(contactCountrySelector);
    }

    // Method that checks if the return button is displayed
    public boolean isReturnButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(returnButtonSelector));
            return driver.findElement(returnButtonSelector).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method that checks if the delete button is displayed
    public boolean isDeleteButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonSelector));
            return driver.findElement(deleteButtonSelector).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method that checks if the edit button is displayed
    public boolean isEditButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonSelector));
            return driver.findElement(editButtonSelector).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method for deleting a contact
    public MyContactsPage deleteContact(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonSelector));
        driver.findElement(deleteButtonSelector).click();
        driver.switchTo().alert().accept();
        return new MyContactsPage(driver);
    }

    // Method for canceling contact deletion
    public MyContactsPage cancelContactDeletion(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButtonSelector));
        driver.findElement(deleteButtonSelector).click();
        driver.switchTo().alert().dismiss();
        driver.findElement(returnButtonSelector).click();
        return new MyContactsPage(driver);
    }

    //Method for returning back to the 'My Contacts page'
    public MyContactsPage openMyContactPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(returnButtonSelector));
        driver.findElement(returnButtonSelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
        return new MyContactsPage(driver);
    }

    // method for returning the EditContactPage
    public EditContactPage openEditContactPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonSelector));
        driver.findElement(editButtonSelector).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputFieldSelector));
        return new EditContactPage(driver);
    }
}
