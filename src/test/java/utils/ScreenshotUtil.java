package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String testName) {
        try {

            // Get directory from ConfigLoader
            String directory = ConfigLoader.getScreenshotDirectory();

            // Create the directory if it doesn't exist
            Path dirPath = Paths.get(directory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Save the screenshot
            Path dest = Paths.get(directory, testName + ".png");
            Files.copy(source.toPath(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
