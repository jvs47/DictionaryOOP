package app.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IODatabase {
    public static Connection connection;

    public IODatabase() {
        connectDatabase();
    }

    public void connectDatabase() {
        //Install jdbc
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Connect database
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dictionary_database.db");
            if (connection != null) {
                System.err.println("connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
