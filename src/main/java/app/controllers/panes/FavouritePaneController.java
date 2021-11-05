package app.controllers.panes;

import app.dictionary.Word;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class FavouritePaneController extends ViewController {

    @Override
    public void searchAct(String foundWord) {
        ArrayList<String> wordArrays = this.state.getFavoriteAct().listWordFromFavoriteDatabase(foundWord);
        search_list_view.getItems().setAll(wordArrays);
        Word word = this.state.getFavoriteAct().searchUseQueryFavorite(foundWord);
        if (word != null) {
            definitionPaneController.initData(this.state, word.getWord(), word.getWordExplain());
        }
    }

    @Override
    public void handleSelectItemListView(MouseEvent event) {
        String word = search_list_view.getSelectionModel().getSelectedItem();
        if (word == null)
            return;
        searchAct(word);
    }


    @Override
    public void reload() {
        if (this.state == null) {
            return;
        }
        String searchText = input_search.getText();
        if (!searchText.isEmpty()) {
            searchAct(searchText);
        } else {
            arrayWords = this.state.getFavoriteAct().getFavoriteString();
            search_list_view.getItems().setAll(arrayWords);
        }
        definitionPaneController.reload();
    }
}
