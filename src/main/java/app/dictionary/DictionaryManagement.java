package app.dictionary;

import java.sql.*;
import java.util.HashMap;

public class DictionaryManagement {

    private static Connection connection = null;
    private static HashMap<String, String> dictionary = new HashMap<>();
    public static void insertFromCommandline() {
        //Install jdbc
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Connect database
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dictionary_database.db");
            if(connection != null) {
                System.err.println("connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Get data
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT id, word, description FROM av");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next() == true) {
                Word newWord = new Word(resultSet.getString(2), resultSet.getString(3));
                dictionary.put(newWord.getWord(), newWord.getWordExplain());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
