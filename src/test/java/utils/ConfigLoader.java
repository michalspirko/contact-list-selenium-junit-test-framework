package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    // Load properties from the test-config.properties file during class initialization
    static {
        try {
            String absolutePath = "src/test/resources/test-config.properties";
            FileInputStream stream = new FileInputStream(absolutePath);
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test-config.properties file.", e);
        }
    }

    // Helper method to get property from Maven argument or fallback to property file
    private static String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    // Helper method to get Boolean property
    private static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    // Helper method to get Integer property
    private static int getIntegerProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    // Get the type of web browser to use for tests.
    public static String getBrowser() {
        return getProperty("browser");
    }

    // Check if the browser should run in headless mode
    public static boolean isHeadless() {
        return getBooleanProperty("headless");
    }

    // Check if screenshots should be captured during tests
    public static boolean shouldCaptureScreenshot() {
        return getBooleanProperty("capture_screenshot");
    }

    // Get the directory where screenshots should be saved
    public static String getScreenshotDirectory() {
        return getProperty("screenshot_directory");
    }

    // Check if an HTML report should be generated after tests
    public static boolean shouldGenerateHtmlReport() {
        return getBooleanProperty("generate_html_report");
    }

    // Get the directory where test reports should be saved
    public static String getReportDirectory() {
        return getProperty("report_directory");
    }

    // Get the base URL for the test environment
    public static String getTestEnvironmentURL() {
        return getProperty("test.env.url");
    }

    // Get the type of data source used for tests
    public static String getDataSource() {
        return getProperty("data.source");
    }

    // Get the file path for the CSV data source
    public static String getCsvFilePath() {
        return getProperty("csv.file.path");
    }

    // Get the name of the database
    public static String getDbName() {
        return getProperty("db.name");
    }

    // Get the name of the table in the database
    public static String getTableName() {
        return getProperty("db.table");
    }

    // Get the username for database access
    public static String getDbUser() {
        return getProperty("db.user");
    }

    // Get the host name of the database
    public static String getDbHost() {
        return getProperty("db.host");
    }

    // Get the port number for the database
    public static int getDbPort() {
        return getIntegerProperty("db.port");
    }
}
