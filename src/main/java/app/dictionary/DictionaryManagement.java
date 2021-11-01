package app.dictionary;

import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    private Dictionary dictionary;
    protected Connection connection = null;
    private Scanner scan = new Scanner(System.in);

    public DictionaryManagement() {
        dictionary = new Dictionary();
        insertFromDatabase();
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
        connectDatabase();
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
        connectDatabase();
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
        return;
    }

    public void deleteWord() {
        connectDatabase();
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
        connectDatabase();
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
        for(Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
            if(entry.getKey().equals(foundWord)) {
                searchedWord.put(entry.getKey(), entry.getValue());
            }
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
        while(start <= end) {
            int mid = start + (end - start)/2;
            Word word = arrayWords.get(mid);
            String currentWord = word.getWord();
            int compare = currentWord.compareTo(foundWord);
            if(compare == 0) {
                return word;
            }
            if(compare > 0) {
                end = mid - 1;
            }
            if(compare < 0) {
                start = mid + 1;
            }
        }
        return null;
    }
}