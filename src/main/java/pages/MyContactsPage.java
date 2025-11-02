package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MyContactsPage extends BasePage{
    // Fields
    private static final By logoutSelector = By.id("logout");
    private static final By addNewContactSelector = By.id("add-contact");
    private static final By contactInTheListSelector = By.tagName("td");
    private static final By tableSelector = By.id("myTable");
    private static final By tableDataSelector = By.tagName("td");
    private static final By tableRowSelector = By.tagName("tr");
    private static final int CONTACT_ID_INDEX = 0;
    private static final int FULL_NAME_INDEX = 1;



    // Constructor
    public MyContactsPage(WebDriver driver) {
        super(driver);
    }

    // Method that checks whether the logout button is displayed
    public boolean isLogoutButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutSelector));
            return driver.findElement(logoutSelector).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method that checks if the 'Add a New Contact' button is displayed
    public boolean isAddNewContactButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(addNewContactSelector));
            return driver.findElement(addNewContactSelector).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method for getting the full name of a newly added contact
    public String getFullNameText(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
            List<WebElement> tdTags = driver.findElements(contactInTheListSelector);
            return tdTags.get(FULL_NAME_INDEX).getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    // Method for checking whether a contact is present in the 'My Contact' table
    public boolean isContactDisplayedInTable() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
            List<WebElement> tdTags = driver.findElements(contactInTheListSelector);
            return tdTags.size() >= 2 && tdTags.get(FULL_NAME_INDEX).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Method for getting the first contact ID for cleanup
    public String getFirstContactID(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
        List<WebElement> tdTags = driver.findElements(contactInTheListSelector);
        return tdTags.get(CONTACT_ID_INDEX).getAttribute("textContent");
    }

    // Method that looks through the <td> tags and compares the text with the method parameter
    public boolean isTextDisplayedInAnyTd(String textToFind) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
        List<WebElement> tdElements = driver.findElements(contactInTheListSelector);
        for (WebElement td : tdElements) {
            String tdText = td.getText().trim();
            if (textToFind.equals(tdText)) {
                return true;
            }
        }
        return false;
    }

    // Method that counts table rows
    public int countTableRows() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(tableDataSelector));
            List<WebElement> rows = driver.findElements(tableRowSelector);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    // Method for returning the AddContactPage
    public AddContactPage openAddContact(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addNewContactSelector));
        driver.findElement(addNewContactSelector).click();
        return new AddContactPage(driver);
    }

    // Method for returning the ContactDetails page
    public ContactDetailsPage openContactDetails(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableSelector));
        WebElement element = driver.findElement(contactInTheListSelector);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
        return new ContactDetailsPage(driver);
    }

    // Method for returning the LoginPage
    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutSelector));
        driver.findElement(logoutSelector).click();
        return new LoginPage(driver);
    }
}