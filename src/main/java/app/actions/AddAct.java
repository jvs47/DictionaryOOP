package app.actions;

import app.helper.IODatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class AddAct {
    protected TreeMap<String, String> dictionary = new TreeMap<>();
    protected final Connection connection = IODatabase.connection;

    public String convertToHTML(String addWord, String addPron, String addDescription) {
        StringBuilder convertHTML = new StringBuilder();
        convertHTML.append("<h1>").append(addWord).append("</h1>");
        convertHTML.append("<h3><i>/").append(addPron).append("/</i></h3>");
        convertHTML.append("<h2>").append(addDescription).append("</h2>");
        return convertHTML.toString();
    }

    public void addNewWordToDatabase(String addWord, String addPron, String addDescription) {
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
        System.out.println("Add word success!");
    }

    public boolean checkValidWord(String word){
        if (dictionary.containsKey(word)) {
            System.out.println(word + " is exist in Database!");
            return false;
        }
        return true;
    }

    public void loadWordsFromDatabase() {
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

    public void reloadDictionaryTree() {
        dictionary = new TreeMap<>();
        loadWordsFromDatabase();
    }

    public TreeMap<String, String> getDictionary() {
        return dictionary;
    }
}
