package app.dictionary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class FavoriteAct extends DictionaryAct{
    public FavoriteAct() {

    }

    public void importFavoriteFromDatabase() {
        TreeMap<String, String> favorite = new TreeMap<>();
        connectDatabase();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT word, descriptrion FROM avFavorite");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next() == true) {
                favorite.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void addToFavoriteDatabase(String foundWord) {
        connectDatabase();
        String sql = "INSERT INTO avFavorite(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foundWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
}
