package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage{

    // Fields
    private static final By inputFirstNameSelector = By.id("firstName");
    private static final By inputLastNameSelector = By.id("lastName");
    private static final By inputEmailSelector = By.id("email");
    private static final By inputPasswordSelector = By.id("password");
    private static final By buttonSignupSelector = By.id("submit");


    // Constructor
    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    // Method for valid signup
    public MyContactsPage validSignup(String firstName, String lastName, String email, String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputFirstNameSelector));
        driver.findElement(inputFirstNameSelector).sendKeys(firstName);
        driver.findElement(inputLastNameSelector).sendKeys(lastName);
        driver.findElement(inputEmailSelector).sendKeys(email);
        driver.findElement(inputPasswordSelector).sendKeys(password);
        driver.findElement(buttonSignupSelector).click();
        return new MyContactsPage(driver);
    }

    // Method for invalid signup with missing password
    public MyContactsPage missingPasswordSignUp(String firstName, String lastName, String email, String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputFirstNameSelector));
        driver.findElement(inputFirstNameSelector).sendKeys(firstName);
        driver.findElement(inputLastNameSelector).sendKeys(lastName);
        driver.findElement(inputEmailSelector).sendKeys(email);
        driver.findElement(inputPasswordSelector).sendKeys(password == null ? "" : password);
        driver.findElement(buttonSignupSelector).click();
        return new MyContactsPage(driver);
    }
}
