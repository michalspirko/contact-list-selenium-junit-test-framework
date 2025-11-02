package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage{
    // Fields
    private static String url;
    private static final By inputUsernameSelector = By.id("email");
    private static final By inputPasswordSelector = By.id("password");
    private static final By buttonLoginSelector = By.id("submit");
    private static final By buttonSignupSelector = By.id("signup");


    // Constructors
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    public LoginPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    // Method for opening the app at LoginPage
    public void open() {
        driver.get(url);
    }

    // Method for navigating back when logged out
    public LoginPage navigateBack() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLoginSelector));
        driver.navigate().back();
        driver.navigate().back();
        return new LoginPage(driver);
    }

    // Method for login/opening the MyContactsPage
    public MyContactsPage login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLoginSelector));
        driver.findElement(inputUsernameSelector).sendKeys(username);
        driver.findElement(inputPasswordSelector).sendKeys(password);
        driver.findElement(buttonLoginSelector).click();
        return new MyContactsPage(driver);
    }

    // Method for opening the SignUp page
    public SignUpPage openSignupPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSignupSelector));
        driver.findElement(buttonSignupSelector).click();
        return new SignUpPage(driver);
    }
}