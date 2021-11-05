package app.actions;

import app.dictionary.Word;
import app.helper.IODatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AddAct {
    private TreeMap<String, String> dictionary = new TreeMap<>();
    private final Connection connection = IODatabase.connection;

    public String convertToHTML (String addWord, String addPron, String addDescription) {
        StringBuilder convertHTML = new StringBuilder();
        convertHTML.append("<h1>" + addWord + "</h1");
        convertHTML.append("<h3><i>" + addPron + "</i></h3>");
        convertHTML.append("<ul>" + addDescription + "</ul>");
        return convertHTML.toString();
    }

    public void addNewWordToDatabase (String addWord, String addPron, String addDescription) {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (entry.getKey().equals(addWord)) {
                System.out.println(addWord + " is exist in the Dictionary!");
                return;
            }
        }
        String html = convertToHTML(addWord, addPron, addDescription);
        String sql = "INSERT INTO av(word, html, description, pronounce) VALUES(?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addWord);
            preparedStatement.setString(2, html);
            preparedStatement.setString(3, addDescription);
            preparedStatement.setString(4, addPron);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadDictionaryTree();
    }

    public void insertFromDictionaryDatabase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM av");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dictionary.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDictionaryString() {
        ArrayList<String> wordArrays = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word FROM av GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                wordArrays.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordArrays;
    }

    public ArrayList<String> foundWordFromDatabase(String newWord) {
        PreparedStatement preparedStatement;
        ArrayList<String> words = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM av WHERE word LIKE '" + newWord + "%' GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word addNewWord = new Word(resultSet.getString(1), resultSet.getString(2));
                words.add(addNewWord.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Word searchUseQueryDictionary(String newWord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM av WHERE word = ? GROUP BY word ");
            preparedStatement.setString(1, newWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() == true) {
                Word word = new Word(resultSet.getString(1), resultSet.getString(2));
                return word;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reloadDictionaryTree() {
        dictionary = new TreeMap<>();
        insertFromDictionaryDatabase();
    }
}
