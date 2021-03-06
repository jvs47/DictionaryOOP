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

public class HistoryAct {
    private final Connection connection = IODatabase.connection;
    private TreeMap<String, String> history = new TreeMap<>();

    public HistoryAct() {
        insertFromHistoryDatabase();
        System.out.println("Done insert history from database!");
    }

    public void insertFromHistoryDatabase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                history.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showHistoryWord() {
        TreeMap<String, String> his = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM avHistory");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                his.put(resultSet.getString(1), resultSet.getString(2));
            }
            for (Map.Entry<String, String> entry : his.entrySet()) {
                System.out.println("|" + entry.getKey() + "    " + "|" + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkWordInHistoryDatabase(String foundWord) {
        foundWord = foundWord.trim();
        for (Map.Entry<String, String> entry : history.entrySet()) {
            if (entry.getKey().equals(foundWord)) {
                System.out.println(foundWord + " is exist in History List!");
                return;
            }
        }
    }

    public void saveWordToHistoryDatabase(String foundWord) {
        foundWord = foundWord.trim();
        String sql = "INSERT INTO avHistory(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foundWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadHistoryTree();
    }

    public ArrayList<String> getHistoryString() {
        ArrayList<String> wordArrays = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word FROM avHistory GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                wordArrays.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordArrays;
    }

    public ArrayList<String> foundWordFromHistoryDatabase(String foundWord) {
        foundWord = foundWord.trim();
        PreparedStatement preparedStatement;
        ArrayList<String> words = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory WHERE word LIKE '" + foundWord + "%' GROUP BY word;");
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

    public Word binarySearchHistory(String foundWord) {
        foundWord = foundWord.trim();
        ArrayList<Word> arrayWords = new ArrayList<>();
        TreeMap<String, String> his = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM avHistory");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                his.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : his.entrySet()) {
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

    public void deleteAllHistory() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM avHistory");
            preparedStatement.executeUpdate();
            System.out.println("History is empty!");
            reloadHistoryTree();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Word searchUseQueryHistory(String foundWord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory WHERE word = ? GROUP BY word ");
            preparedStatement.setString(1, foundWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Word(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reloadHistoryTree() {
        history = new TreeMap<>();
        insertFromHistoryDatabase();
    }

}
