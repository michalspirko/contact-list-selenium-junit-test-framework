package utils;

import java.io.File;

// Utility class for generating HTML test reports
public class HTMLReportUtil {

    // Method to generate the start of the HTML report
    public static String startHtml(String title) {
        // Returns the starting HTML tags with the provided title
        return "<html><head><title>" + title + "</title></head><body>";
    }

    // Method to generate the end of the HTML report
    public static String endHtml() {
        // Returns the closing HTML tags
        return "</body></html>";
    }

    // Method to add an entry for a single test to the HTML report
    public static String addTestEntry(String className, String packageName, String startTime, String endTime,
                                      String duration, String status) {

        // Fetch the screenshot directory path from ConfigLoader
        String imagePath = ConfigLoader.getScreenshotDirectory();
        File imgDir = new File(imagePath);
        // Filter the files to only include relevant screenshots
        File[] files = imgDir.listFiles((dir, name) -> name.contains(startTime) && name.endsWith(".png"));

        // Initialize the image HTML tag
        String imageTag = "";
        if (files != null && files.length > 0) {
            // Create an HTML img tag with the first matching screenshot file
            imageTag = "<img src='" + "screenshots" + "/" + files[0].getName() + "' alt='Test Screenshot' width=1000px>";
        }

        // Return the HTML entry for the test
        return "<div><h2>Test Name: " + className + "</h2>" +
                "<p>Test Suite: " + packageName + "</p>"+
                "<p>Start Time: " + startTime + "</p>" +
                "<p>End Time: " + endTime + "</p>" +
                "<p>Duration: " + duration + "</p>" +
                "<p>Status: " + status + "</p>" +
                imageTag +
                "</div><hr>";
    }

    // Method to add a summary section to the HTML report
    public static String addSummary(int total, int passed, int failed, int aborted) {
        // Calculate success rate as percentage
        double successRate = ((double) passed / total) * 100;

        // Return the HTML summary section
        return "<div><h2>Summary</h2>" +
                "<p>Total Tests: " + total + "</p>" +
                "<p>Tests Passed: " + passed + "</p>" +
                "<p>Tests Failed: " + failed + "</p>" +
                "<p>Tests Aborted: " + aborted + "</p>" +
                "<p>Success Rate: " + successRate + "%</p>" +
                "</div>" + endHtml();
    }
}
