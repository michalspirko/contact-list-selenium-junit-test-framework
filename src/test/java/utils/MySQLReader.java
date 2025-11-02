package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Class responsible for reading data from a MySQL database
public class MySQLReader {

    // Method to connect to MySQL database
    private static Connection connect() throws SQLException {
        try {
            // Attempt to load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Throw runtime exception if MySQL JDBC driver is not found
            throw new RuntimeException("MySQL JDBC driver not found!", e);
        }

        // Create MySQL connection URL
        String url = "jdbc:mysql://" + ConfigLoader.getDbHost() + ":" + ConfigLoader.getDbPort() + "/" +
                ConfigLoader.getDbName() + "?serverTimezone=UTC"; // Specifying the server timezone
        String user = ConfigLoader.getDbUser();

        // Fetch password from environment variables
        String password = System.getenv("DB_PASSWORD");

        // Get and return MySQL connection
        return DriverManager.getConnection(url, user, password);
    }

    // Method to fetch data from MySQL database
    public List<Object[]> fetchDataFromMySQL() throws SQLException {
        // Initialize list to store result rows
        List<Object[]> data = new ArrayList<>();

        // Get table name from configuration
        String tableName = ConfigLoader.getTableName();

        // Try-with-resources for auto-closing SQL objects
        try (Connection conn = connect();  // Connecting to database
             Statement stmt = conn.createStatement();  // Create statement object
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {  // Execute query

            // Determine number of columns in ResultSet
            int columnCount = rs.getMetaData().getColumnCount();

            // Iterate through each row in ResultSet
            while (rs.next()) {
                // Initialize array to hold row data
                Object[] row = new Object[columnCount];

                // Fetch each column's value
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }

                // Add row array to list
                data.add(row);
            }
        }

        // Return list of rows as object arrays
        return data;
    }
}
