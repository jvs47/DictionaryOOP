package app.dictionary;

import java.sql.*;
import java.util.Hashtable;

public class DictionaryManagement {

    Connection connection = null;

    public void insertFromCommandline() {
        Hashtable<Integer, Word> dictionary = new Hashtable<>();
        Dictionary dic = new Dictionary();

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
                Word newWord = new Word(resultSet.getString(2), resultSet.getString(4));
                int id = resultSet.getInt(1);
                dictionary.put(id, newWord);
            }
            dic.setDictionary(dictionary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
