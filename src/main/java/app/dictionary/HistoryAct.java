package app.dictionary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class HistoryAct extends DictionaryManagement {
    TreeMap<String, String> history;

    public HistoryAct() {
        history = new TreeMap<>();
        insertFromHistoryDatabase();
    }

//    public void insertFromHistoryDatabase() {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next() == true) {
//                history.put(resultSet.getString(1), resultSet.getString(2));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Done insert history from database!");
//    }
//
//    public void saveWordToHistoryDatabase(String foundWord) {
//        String sql = "INSERT INTO avHistory(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, foundWord);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ArrayList<String> getHistoryString() {
//        ArrayList<String> wordArrays = new ArrayList<>();
//        for(Map.Entry<String, String> entry : history.entrySet()) {
//            wordArrays.add(entry.getKey());
//        }
//        return wordArrays;
//    }
//
//    public ArrayList<String> foundWordFromHistoryDatabase(String foundWord){
//        PreparedStatement preparedStatement;
//        ArrayList<String> words = new ArrayList<>();
//        try {
//            preparedStatement = connection.prepareStatement("SELECT word, html FROM avHistory WHERE word LIKE '" + foundWord + "%';");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Word newWord = new Word(resultSet.getString(1), resultSet.getString(2));
//                words.add(newWord.getWord());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return words;
//    }
//
//    public Word binarySearchHistory(String foundWord) {
//        ArrayList<Word> arrayWords = new ArrayList<>();
//        for(Map.Entry<String, String> entry : history.entrySet()) {
//            Word insertWord = new Word(entry.getKey(), entry.getValue());
//            arrayWords.add(insertWord);
//        }
//        int start = 0;
//        int end = arrayWords.size() - 1;
//        while(start <= end) {
//            int mid = start + (end - start)/2;
//            Word word = arrayWords.get(mid);
//            String wordString = word.getWord();
//            int compare = wordString.compareTo(foundWord);
//            if(compare == 0) {
//                return word;
//            }
//            if(compare > 0) {
//                end = mid - 1;
//            }
//            if(compare < 0) {
//                start = mid + 1;
//            }
//        }
//        return null;
//    }
}
