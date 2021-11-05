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

public class FavoriteAct {
    private final Connection connection = IODatabase.connection;
    private TreeMap<String, String> favorite = new TreeMap<>();

    public FavoriteAct() {
        insertFromFavoriteDatabase();
        System.out.println("Done insert from favorite database!");
    }

    public void insertFromFavoriteDatabase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avFavorite");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                favorite.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveToFavoriteDatabase(String foundWord) {
        for (Map.Entry<String, String> entry : favorite.entrySet()) {
            if (entry.getKey().equals(foundWord)) {
                System.out.println(foundWord + " is exist on Favorite List!");
                return;
            }
        }
        String sql = "INSERT INTO avFavorite(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foundWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadFavouriteTree();
        System.out.println(foundWord + " is added to favorite!");
    }

    public ArrayList<String> listWordFromFavoriteDatabase(String foundWord) {
        PreparedStatement preparedStatement;
        ArrayList<String> words = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM avFavorite WHERE word LIKE '" + foundWord + "%' GROUP BY word;");
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

    public Word binarySearchFavorite(String foundWord) {
        ArrayList<Word> arrayWords = new ArrayList<>();
        TreeMap<String, String> fav = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM avFavorite");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fav.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : fav.entrySet()) {
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

    public Word searchUseQueryFavorite(String foundWord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avFavorite WHERE word = ? GROUP BY word ");
            preparedStatement.setString(1, foundWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new Word(resultSet.getString(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getFavoriteString() {
        ArrayList<String> wordArrays = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word FROM avFavorite GROUP BY word;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                wordArrays.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordArrays;
    }

    public void removeFavouriteWordFromDatabase(String word) {
        String sql = "DELETE FROM avFavorite WHERE word = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadFavouriteTree();
    }

    public void showAllFavorite() {
        TreeMap<String, String> fav = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM avFavorite");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fav.put(resultSet.getString(1), resultSet.getString(2));
            }
            for(Map.Entry<String, String> entry : fav.entrySet()) {
                System.out.println("|" + entry.getKey() + "    " + "|" + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(fav.isEmpty()) {
            System.out.println("Favorite is empty!");
        }
    }

    private void reloadFavouriteTree() {
        favorite = new TreeMap<>();
        insertFromFavoriteDatabase();
    }
}
