package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Class to handle reading from a PostgreSQL database
public class PostgreSQLReader {

    // Method to establish and return a database connection
    private static Connection connect() throws SQLException {
        // Constructing the database URL from ConfigLoader
        String url = "jdbc:postgresql://" + ConfigLoader.getDbHost() + ":" + ConfigLoader.getDbPort() + "/" + ConfigLoader.getDbName();

        // Fetching database username from ConfigLoader
        String user = ConfigLoader.getDbUser();

        // Fetching database password from environment variables
        String password = System.getenv("DB_PASSWORD");

        // Establishing and returning the database connection
        return DriverManager.getConnection(url, user, password);
    }

    // Method to fetch data from a PostgreSQL database and return it as a list of object arrays
    public List<Object[]> fetchDataFromPostgreSQL() throws SQLException {
        // Initializing an empty list to hold the data rows
        List<Object[]> data = new ArrayList<>();

        // Fetching the table name from ConfigLoader
        String tableName = ConfigLoader.getTableName();

        // Try-with-resources block to automatically close resources
        try (
                Connection conn = connect(); // Establishing database connection
                Statement stmt = conn.createStatement(); // Creating a statement object
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName) // Executing SQL query
        ) {
            // Getting the number of columns in the result set
            int columnCount = rs.getMetaData().getColumnCount();

            // Looping through each row in the result set
            while (rs.next()) {
                // Initializing an object array to hold a single data row
                Object[] row = new Object[columnCount];

                // Looping through each column to fetch its value
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }

                // Adding the row to the list
                data.add(row);
            }
        }

        // Returning the fetched data
        return data;
    }
}
