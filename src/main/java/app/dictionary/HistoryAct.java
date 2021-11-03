package app.dictionary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class HistoryAct extends DictionaryAct {

    public HistoryAct() {

    }

//    public void importFromDatabase() {
//        TreeMap<String, String> history = new TreeMap<>();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, description FROM avHistory");
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
//    public void addToHistoryDatabase(String foundWord) {;
//        String sql = "INSERT INTO avHistory(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, foundWord);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public TreeMap<String, String> addToHistoryString(String foundWord) {
//        TreeMap<String, String> history = new TreeMap<>();
//        String sql = "SELECT word, description FROM av";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next() == true) {
//                history.put(resultSet.getString(1), resultSet.getString(2));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return history;
//    }
//
//    public ArrayList<String> toArrayString() {
//        TreeMap<String, String> words = this.getTreeWord();
//        ArrayList<String> wordArrays = new ArrayList<>();
//        for (Map.Entry<String, String> entry : words.entrySet()) {
//            wordArrays.add(entry.getKey());
//        }
//        return wordArrays;
//    }
}
