package app.dictionary;

import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    private Dictionary dictionary;
    private Connection connection = null;
    private Scanner scan = new Scanner(System.in);

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        dictionary = dictionary;
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
            if(connection != null) {
                System.err.println("connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void insertFromDatabase() {
        connectDatabase();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT word, description FROM av");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() == true) {
                Word newWord = new Word(resultSet.getString(1), resultSet.getString(2));
                dictionary.getDictionary().put(newWord.getWord(), newWord.getWordExplain());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void insertFromCommandline() {
        connectDatabase();
        System.out.print("Number of Words: ");
        int n = scan.nextInt();
        scan.nextLine();
        for(int i = 0; i < n; ++i) {
            Word newWord = new Word();
            System.out.print("Enter word: ");
            String insertWord = scan.nextLine();
            newWord.setWord(insertWord);
            System.out.print("Enter word meaning: ");
            String insertWordMeaning = scan.nextLine();
            newWord.setWordExplain(insertWordMeaning);
            if(dictionary.addWord(newWord)) {
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
        }
        System.out.println("Finished!");
        return;
    }

    public void deleteWord() {
        connectDatabase();
        Word removedWord = new Word();
        System.out.print("Enter word: ");
        String deletedWord = scan.nextLine();
        if(dictionary.removeWord(removedWord)) {
            dictionary.getDictionary().remove(removedWord);
            String sql = "DELETE FROM av WHERE discription = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, removedWord.getWordExplain());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished!");
        return;
    }

    public void showAllWord() {
        for(Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
            String value = entry.getValue();
            System.out.println("|" + entry + "\t" + "|" + value);
        }
        return;
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
                return;
            }
        }
        System.out.println("Invalid Word!");
        return;
    }

    public void findWord() {
        System.out.println("Enter word: ");
        String foundWord = scan.nextLine();
        for(Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
            String containedChar = String.valueOf(entry);
            if(containedChar.contains(foundWord)) {
                System.out.println("|" + entry.getKey() + "\t" + "|" + entry.getValue());
            }
        }
        return;
    }
}