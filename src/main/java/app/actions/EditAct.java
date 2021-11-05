package app.actions;

import app.helper.IODatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class EditAct {
    private TreeMap<String, String> dictionary = new TreeMap<>();
    private final Connection connection = IODatabase.connection;

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

    public String convertToHTML (String addWord, String addPron, String addDescription) {
        StringBuilder convertHTML = new StringBuilder();
        convertHTML.append("<h1>" + addWord + "</h1");
        convertHTML.append("<h3><i>" + addPron + "</i></h3>");
        convertHTML.append("<ul>" + addDescription + "</ul>");
        return convertHTML.toString();
    }

    public void editWordInDatabase (String oldWord, String newWord, String addPron, String addDescription) {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (!entry.getKey().equals(oldWord)) {
                System.out.println(oldWord + " is not exist in the Dictionary!");
                return;
            }
        }
        String html = convertToHTML(newWord, addPron, addDescription);
        String sql = "UPDATE av SET description = ?, html = ?, pronounce = ? WHERE word = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addDescription);
            preparedStatement.setString(2, html);
            preparedStatement.setString(3, addPron);
            preparedStatement.setString(4, newWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadDictionaryTree();
    }

    public void reloadDictionaryTree() {
        dictionary = new TreeMap<>();
        insertFromDictionaryDatabase();
    }
}
