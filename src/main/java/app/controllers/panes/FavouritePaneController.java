package app.controllers.panes;

import app.dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class FavouritePaneController extends ViewController {
    public Button searchButton;
    private ContainerController state;
    @FXML
    public void handleSearchButtonEvent(ActionEvent actionEvent) {
        System.out.println("Click search!");
    }


    @Override
    public void searchAct(String foundWord) {
        ArrayList<String> wordArrays = this.state.getDictionaryManagement().listWordFromFavoriteDatabase(foundWord);
        search_list_view.getItems().setAll(wordArrays);
        Word word = this.state.getDictionaryManagement().binarySearchFavorite(foundWord);
        if(word != null) {
            definitionPaneController.initData(this.state, word.getWord(), word.getWordExplain());
        }
    }

    @Override
    public void handleSelectItemListView(MouseEvent event) {
        String word = search_list_view.getSelectionModel().getSelectedItem();
        if(word == null)
            return;
        searchAct(word);
    }

    @Override
    public void initData(ContainerController state){
        this.state = state;
        if(definitionPaneController == null){
            loadDefinitionPane("","");
        }
        arrayWords = this.state.getDictionaryManagement().getFavoriteString();
        this.reload();
    }

    @Override
    public void reload() {
        if(this.state == null) {
            return;
        }
        String searchText = input_search.getText();
        if(!searchText.isEmpty()) {
            searchAct(searchText);
        } else {
            arrayWords = this.state.getDictionaryManagement().getFavoriteString();
            search_list_view.getItems().setAll(arrayWords);
        }
    }
}
