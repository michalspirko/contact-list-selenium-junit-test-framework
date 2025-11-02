package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    // Creates a WebDriver instance based on browser type and headless mode
    public static WebDriver createDriver(String browserType, Boolean isHeadless) {

        WebDriver driver;

        // Determine the browser type and initialize corresponding driver
        switch (browserType.toLowerCase()) {
            case "chrome":
                // Setup ChromeDriver
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Configure headless mode for Chrome, if requested
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }

                // Initialize ChromeDriver
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                // Setup FirefoxDriver
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                // Configure headless mode for Firefox, if requested
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }

                // Initialize FirefoxDriver
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                // Setup EdgeDriver
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();

                // Configure headless mode for Edge, if requested
                if (isHeadless) {
                    edgeOptions.setHeadless(true);
                }

                // Initialize EdgeDriver
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                // Throw exception for unsupported browser types
                throw new RuntimeException("Unsupported browser: " + browserType);
        }

        // Return the initialized WebDriver
        return driver;
    }
}
