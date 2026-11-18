package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/rsm?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection connection;

    public static void connect() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            if (connection != null)
            {

            }
            else {
                System.out.println("Connection Failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {
                connect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

}