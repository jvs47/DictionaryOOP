package app.dictionary;

import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    private Dictionary dictionary;
    private Connection connection = null;

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        dictionary = dictionary;
    }

    public Dictionary insertFromDatabase() {
        HashMap<String, String> dic = new HashMap<>();
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
                dic.put(newWord.getWord(), newWord.getWordExplain());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dictionary.setDictionary(dic);
        return dictionary;
    }

    public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Number of Words: ");
        int n = scan.nextInt();
        scan.nextLine();
        for(int i = 0; i < n; ++i) {
            dictionary.addWord();
        }
        System.out.println("Finished!");
        return;
    }

    public void showAllWord() {
        HashMap<String, String> dic = dictionary.getDictionary();
        for(HashMap.Entry<String, String> entry : dic.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "\t" + value);
        }
        return;
    }
}