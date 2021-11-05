package app.dictionary;

import app.helper.IODatabase;

import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    protected Dictionary dictionary = new Dictionary();
    private Scanner scan = new Scanner(System.in);
    private final Connection connection = IODatabase.connection;

    public DictionaryManagement() {
        insertFromDatabase();
        System.out.println("Done insert from language database!");
    }

    public Dictionary getDictionary() {
        return dictionary;
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
            System.out.println("Finished!");
        }
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
            System.out.println("Finished!");
        }
    }

    public void showAllWord() {
        TreeMap<String, String> dic = new TreeMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM av GROUP BY word");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() == true) {
                dic.put(resultSet.getString(1), resultSet.getString(2));
            }
            for(Map.Entry<String, String> entry : dic.entrySet()) {
                System.out.println("|" + entry.getKey() + "    " + "|" + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    return;
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
            preparedStatement = connection.prepareStatement("SELECT word, html FROM av WHERE word LIKE '" + foundWord + "%' group by word");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                searchedWord.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchedWord;
    }

    public Word binarySearch(String foundWord) {
        ArrayList<Word> arrayWords = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT word, description FROM av group by word");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word newWord = new Word(resultSet.getString(1), resultSet.getString(2));
                arrayWords.add(newWord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Word searchUseQuery(String foundWord) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM av WHERE word = ? GROUP BY word ");
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

    public void deleteWordFromGUI(String removedWord) {
        String sql = "DELETE FROM av WHERE word = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, removedWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}