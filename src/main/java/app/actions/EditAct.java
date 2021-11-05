package app.actions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditAct extends AddAct {

    public void editWordInDatabase(String oldWord, String newWord, String addPron, String addDescription) {
        String html = convertToHTML(newWord, addPron, addDescription);
        String sql = "UPDATE av SET description = ?, html = ?, pronounce = ?, word = ? WHERE word = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addDescription);
            preparedStatement.setString(2, html);
            preparedStatement.setString(3, addPron);
            preparedStatement.setString(4, newWord);
            preparedStatement.setString(5, oldWord);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reloadDictionaryTree();
        System.out.println("Edit word success!");
    }

    @Override
    public boolean checkValidWord(String word) {
        if (!dictionary.containsKey(word)) {
            System.out.println(word + " is not exist in Database!");
            return false;
        }
        return true;
    }
}
