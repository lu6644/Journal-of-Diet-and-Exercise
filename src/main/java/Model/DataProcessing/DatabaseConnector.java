package Model.DataProcessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // JDBC URL, username and password of MySQL server
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/fitnessjournal?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Abby6!644";

    // Single instance of the class
    private static DatabaseConnector instance;

    // Private constructor to prevent direct instantiation from outside the class
    private DatabaseConnector() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Public method to get the single instance of the class
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnector.class) {
                // Double-checked locking to ensure only one instance is created
                if (instance == null) {
                    instance = new DatabaseConnector();
                }
            }
        }
        return instance;
    }

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
