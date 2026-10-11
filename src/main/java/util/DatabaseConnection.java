package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";

    private final String USER = "root";
    private final String PASSWORD = "se250917";

    private DatabaseConnection() {

        try {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("✅ Conexión Exitosa");

        } catch (SQLException e) {

            System.out.println("❌ Error de conexión");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {

        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}