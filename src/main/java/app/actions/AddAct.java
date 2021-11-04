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

    public AddAct() {
        insertFromDictionaryDatabase();
        System.out.println("Done insert new word to database!");
    }

    public void insertFromDictionaryDatabase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avDictionary");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dictionary.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveWordToDictionaryDatabase(String foundWord) {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (entry.getKey().equals(foundWord)) {
                System.out.println(foundWord + " is exist in the Dictionary!");
                return;
            }
        }
        String sql = "INSERT INTO avDictionary(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foundWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadDictionaryTree();
    }

    public ArrayList<String> getDictionaryString() {
        ArrayList<String> wordArrays = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word FROM avDictionary GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                wordArrays.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordArrays;
    }

    public ArrayList<String> foundWordFromDictionaryDatabase(String foundWord) {
        PreparedStatement preparedStatement;
        ArrayList<String> words = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM avDictionary WHERE word LIKE '" + foundWord + "%' GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word newWord = new Word(resultSet.getString(1), resultSet.getString(2));
                words.add(newWord.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public Word binarySearchDictionary(String foundWord) {
        ArrayList<Word> arrayWords = new ArrayList<>();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            Word insertWord = new Word(entry.getKey(), entry.getValue());
            arrayWords.add(insertWord);
        }
        int start = 0;
        int end = arrayWords.size() - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            Word word = arrayWords.get(mid);
            String wordString = word.getWord();
            int compare = wordString.compareTo(foundWord);
            if (compare == 0) {
                return word;
            }
            if (compare > 0) {
                end = mid - 1;
            }
            if (compare < 0) {
                start = mid + 1;
            }
        }
        return null;
    }

    public Word searchUseQueryDictionary(String foundWord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avDictionary WHERE word = ? GROUP BY word ");
            preparedStatement.setString(1, foundWord);
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
