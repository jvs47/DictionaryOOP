package app.controllers.panes;

import app.dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class SearchPaneController extends ViewController{

    public Button searchButton;

    public void selectAct(String foundWord) {
        ArrayList<String> arrayWords = this.dictionaryManagement.getStringFoundWord(foundWord);
        search_list_view.getItems().setAll(arrayWords);
        Word word = dictionaryManagement.binarySearch(foundWord);
        if(word != null) {
            definitionPaneController.initData(this.state, word.getWord(), word.getWordExplain());
        }
    }

    @Override
    @FXML
    public void handleSelectItemListView(MouseEvent event) {
        String foundWord = search_list_view.getSelectionModel().getSelectedItem();
        if(foundWord == null) {
            return;
        }
        input_search.setText(foundWord);
        selectAct(foundWord);
    }

    @FXML
    public void handleSearchButtonEvent(ActionEvent actionEvent) {
        if(actionEvent.getSource() == searchButton) {
            String foundWord = input_search.getText();
            searchAct(foundWord);
        }
        return;
    }
}
