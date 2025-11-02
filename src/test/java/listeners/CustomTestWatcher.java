package listeners;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import utils.ConfigLoader;
import utils.HTMLReportUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static listeners.CustomTestExecutionListener.fileName;

// Implementing TestWatcher and BeforeEachCallback for test life cycle events
public class CustomTestWatcher implements TestWatcher, BeforeEachCallback {
    // Instance variables
    private static LocalDateTime startTime;  // Start time for each test
    private LocalDateTime endTime;  // End time for each test

    private static String startTimeString;
    private String endTimeString;
    private StringBuilder reportContent = new StringBuilder(HTMLReportUtil.startHtml("Test Report"));
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    private static int abortedTests = 0;

    // Capture start time before each test
    @Override
    public void beforeEach(ExtensionContext context) {
        startTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        startTimeString = dtf.format(startTime);
    }

    // Handle testDisabled event
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        addTestReportEntry(context, "Disabled");
    }

    // Handle testSuccessful event
    @Override
    public void testSuccessful(ExtensionContext context) {
        addTestReportEntry(context, "Passed");
    }

    // Handle testAborted event
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        addTestReportEntry(context, "Aborted");
    }

    // Handle testFailed event
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        addTestReportEntry(context, "Failed");
    }

    // Utility function to update report based on test status
    private void addTestReportEntry(ExtensionContext context, String status) {
        if (!ConfigLoader.shouldGenerateHtmlReport()) {
            return;  // Skip report if disabled in config
        }

        totalTests++;
        switch (status) {
            case "Passed":
                passedTests++;
                break;
            case "Failed":
                failedTests++;
                break;
            case "Aborted":
                abortedTests++;
                break;
        }

        // Setup directories and time formatting
        File directory = new File(ConfigLoader.getReportDirectory());
        if (!directory.exists()) {
            directory.mkdir();
        }

        endTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        endTimeString = dtf.format(endTime);

        // Capture class and package names
        String packageName = context.getRequiredTestClass().getPackage().getName();
        String fullClassName = context.getRequiredTestClass().getName();
        String[] parts = fullClassName.split("\\.");
        String className = parts[parts.length - 1];

        // Calculate test duration
        String duration = String.valueOf(java.time.Duration.between(startTime, endTime));

        // Generate and append the test report
        String testEntry = HTMLReportUtil.addTestEntry(className, packageName, startTimeString,
                endTimeString, duration, status);
        reportContent.append(testEntry);

        // Write to the report file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                ConfigLoader.getReportDirectory() + fileName, true))) {
            writer.write(testEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close the report and write the summary
    public void closeReport() {
        if (!ConfigLoader.shouldGenerateHtmlReport()) {
            return;
        }

        String summary = HTMLReportUtil.addSummary(totalTests, passedTests, failedTests, abortedTests);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                ConfigLoader.getReportDirectory() + fileName, true))) {
            writer.write(summary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for start time
    public static String getStartTime() {
        return startTimeString;
    }
}
