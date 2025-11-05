package org.example.databasetechnologyproject;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException{
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        System.out.println(url);
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(url,user,password);
    }
}
