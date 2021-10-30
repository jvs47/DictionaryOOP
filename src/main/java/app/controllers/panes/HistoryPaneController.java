package app.controllers.panes;

import app.dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class HistoryPaneController extends ViewController{
    public Button searchButton;

    public ArrayList<String> toArrayWordHistory() {
        TreeMap<String, String> history = dictionary.getDictionaryHistory();
        ArrayList<String> wordHistoryArrays = new ArrayList<>();
        for(Map.Entry<String, String> entry : history.entrySet()) {
            wordHistoryArrays.add(entry.getKey());
        }
        return wordHistoryArrays;
    }
    @Override
    public void searchAct(String foundWord) {
        ArrayList<String> wordArrays = dictionaryManagement.getStringFoundWord(foundWord);
        search_list_view.getItems().setAll(wordArrays);

        Word word = dictionaryManagement.binarySearch(foundWord);
        if(word != null) {
            definitionPaneController.initData(this.state, word.getWord(), word.getWordExplain());
        }
        return;
    }

    @FXML
    public void handleSearchButtonEvent(ActionEvent actionEvent) {
        if(actionEvent.getSource() == searchButton) {
            String searchText = input_search.getText();
            if(!searchText.isEmpty()) {
                searchAct(searchText);
            } else {
                ArrayList<String> wordArrays = dictionaryManagement.getStringFoundWord(searchText);
                search_list_view.getItems().setAll(wordArrays);
            }
        }
    }

    public void reset() {
        input_search.setText("");
        ArrayList<String> history = toArrayWordHistory();
        search_list_view.getItems().setAll(history);
        definitionPaneController.initData(this.state, "", "");
    }

    public void reload() {
        if(state == null) return;
        String searchText = input_search.getText();
        if(!searchText.isEmpty()) {
            searchAct(searchText);
        } else {
            ArrayList<String> wordArrays = toArrayWordHistory();
            search_list_view.getItems().setAll(wordArrays);
        }

    }
}
