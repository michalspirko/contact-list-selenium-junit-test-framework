package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

// Class to read data from a CSV file
public class CSVReader {

    // Method to fetch data from a CSV file and return as a list of string arrays
    public static List<String[]> fetchDataFromCSV() throws IOException {
        // Fetch CSV file path from ConfigLoader
        String csvPath = ConfigLoader.getCsvFilePath();

        // Check if the CSV path is empty or null
        if (csvPath == null || csvPath.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV path is not defined in the config file.");
        }

        // Convert the CSV file path to a Path object
        Path path = Paths.get(csvPath);

        // Check if the CSV file actually exists at the given path
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("CSV file does not exist at the provided path.");
        }

        // Read all the lines from the CSV file into a list
        List<String> lines = Files.readAllLines(path);

        // Convert the list of lines into a list of string arrays (skipping the first line)
        return lines.stream()
                .skip(1)  // Skip the header line
                .map(line -> line.split(";", -1))  // Split each line by semicolon
                .collect(Collectors.toList());  // Collect into a list
    }
}
