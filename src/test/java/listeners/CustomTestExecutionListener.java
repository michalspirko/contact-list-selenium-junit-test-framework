package listeners;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Custom class implementing the TestExecutionListener interface
public class CustomTestExecutionListener implements TestExecutionListener {

    // Static variable to hold the filename of the custom report
    public static String fileName;

    // Executed when the test plan execution starts
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        // Creating a date-time formatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        // Getting the current date-time
        LocalDateTime now = LocalDateTime.now();

        // Creating a custom report filename based on the current date-time
        fileName = "custom_report_" + dtf.format(now) + ".html";
    }

    // Executed when the test plan execution is finished
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        // Instantiate CustomTestWatcher to close the report
        CustomTestWatcher customTestWatcher = new CustomTestWatcher();

        // Closing the custom test report
        customTestWatcher.closeReport();
    }
}
