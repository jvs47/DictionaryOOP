package app.dictionary;

import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    private Dictionary dictionary;
    protected Connection connection = null;
    private Scanner scan = new Scanner(System.in);
    private TreeMap<String, String> history = new TreeMap<>();
    private TreeMap<String, String> favorite = new TreeMap<>();

    public DictionaryManagement() {
        dictionary = new Dictionary();
        connectDatabase();
        insertFromDatabase();
        insertFromHistoryDatabase();
        insertFromFavoriteDatabase();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public TreeMap<String, String> getTreeWord() {
        return dictionary.getDictionary();
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

    public void insertFromDatabase() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM av group by word");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word newWord = new Word(resultSet.getString(1), resultSet.getString(2));
                dictionary.getDictionary().put(newWord.getWord(), newWord.getWordExplain());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFromCommandline() {
        Word newWord = new Word();
        System.out.print("Enter word: ");
        String insertWord = scan.nextLine();
        newWord.setWord(insertWord);
        System.out.print("Enter word meaning: ");
        String insertWordMeaning = scan.nextLine();
        newWord.setWordExplain(insertWordMeaning);
        if (dictionary.addWord(newWord)) {
            dictionary.getDictionary().put(newWord.getWord(), newWord.getWordExplain());
            String sql = "INSERT INTO av(word, description) VALUES(?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newWord.getWord());
                preparedStatement.setString(2, newWord.getWordExplain());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished!");
    }

    public void deleteWord() {
        Word removedWord = new Word();
        System.out.print("Enter word: ");
        String deletedWord = scan.nextLine();
        removedWord.setWord(deletedWord);
        if (dictionary.removeWord(removedWord)) {
            dictionary.getDictionary().remove(removedWord.getWord());
            String sql = "DELETE FROM av WHERE word = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, removedWord.getWord());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished!");
    }

    public void showAllWord() {
        for (Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
            String value = entry.getValue();
            System.out.println("|" + entry + "\t" + "|" + value);
        }
    }

    public void editWordMeaning() {
        System.out.print("Enter word: ");
        String editedWord = scan.nextLine();
        for (Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
            String key = entry.getKey();
            if (key.equals(editedWord)) {
                System.out.print("Edit the meaning of the word: ");
                String editedWordMeaning = scan.nextLine();
                dictionary.getDictionary().computeIfPresent(key, (k, v) -> editedWordMeaning);
                String sql = "UPDATE av SET description = ? WHERE word = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, editedWordMeaning);
                    preparedStatement.setString(2, editedWord);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Invalid Word!");
    }

    public TreeMap<String, String> findWord(String foundWord) {
        TreeMap<String, String> searchedWord = new TreeMap<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM av WHERE word LIKE '" + foundWord + "%'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                searchedWord.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedWord;
    }

    public ArrayList<String> getStringFoundWord(String foundWord) {
        ArrayList<String> results = new ArrayList<>();
        TreeMap<String, String> found = findWord(foundWord);
        for (Map.Entry<String, String> entry : found.entrySet()) {
            results.add(entry.getKey());
        }
        return results;
    }

    public Word binarySearch(String foundWord) {
        ArrayList<Word> arrayWords = dictionary.toArrayWord();
        int start = 0;
        int end = arrayWords.size() - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            Word word = arrayWords.get(mid);
            String currentWord = word.getWord();
            int compare = currentWord.compareTo(foundWord);
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

    // History

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
        System.out.println("Done insert history from database!");
    }

    public void saveWordToHistoryDatabase(String foundWord) {
        for (Map.Entry<String, String> entry : history.entrySet()) {
            if (entry.getKey().equals(foundWord)) {
                System.out.println(foundWord + " is exist in History List!");
                return;
            }
        }
        String sql = "INSERT INTO avHistory(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foundWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory WHERE word = ?");
            preparedStatement.setString(1, foundWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                history.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        ArrayList<Word> arrayWords = new ArrayList<>();
        for (Map.Entry<String, String> entry : history.entrySet()) {
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

    //Favorite

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
        try {
            preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory WHERE word = ?");
            preparedStatement.setString(1, foundWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                history.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadFavouriteTree();
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
        for (Map.Entry<String, String> entry : favorite.entrySet()) {
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

    private void reloadFavouriteTree(){
        favorite = new TreeMap<>();
        insertFromFavoriteDatabase();
    }
}