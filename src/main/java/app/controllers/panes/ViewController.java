package app.controllers.panes;

import app.dictionary.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    protected ContainerController state;

    @FXML
    protected TextField input_search;

    @FXML
    protected ListView<String> search_list_view;

    @FXML
    protected AnchorPane definitionPane;
    protected DefinitionPaneController definitionPaneController;
    protected ArrayList<String> arrayWords;

    @FXML
    public void handleChangeInputSearch(KeyEvent event) {
        if (event.getSource() == input_search) {
            String searchText = input_search.getText();
            if (!searchText.isEmpty()) {
                searchAct(searchText);
            } else {
                search_list_view.getItems().setAll(arrayWords);
            }
        }
    }

    @FXML
    public void handleEnterInputSearch(ActionEvent event) {
        if (event.getSource() == input_search) {
            System.out.println("Enter search");
        }
    }

    @FXML
    public void handleSelectItemListView(MouseEvent event) {
        String word = search_list_view.getSelectionModel().getSelectedItem();
        if (word == null) {
            return;
        }
        searchAct(word);
        this.state.getDictionaryManagement().saveWordToHistoryDatabase(word);
    }

    public void searchAct(String foundWord) {
        //ArrayList<String> arrayWords = this.state.getDictionaryManagement().getStringFoundWord(foundWord);
        ArrayList<String> arrayWords = this.state.getDictionaryManagement().getStringFoundWord(foundWord);
        search_list_view.getItems().setAll(arrayWords);
        Word word = this.state.getDictionaryManagement().binarySearch(foundWord);
        if (word != null) {
            definitionPaneController.initData(this.state, word.getWord(), word.getWordExplain());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadDefinitionPane("Word", "Explain");
    }

    public void reload() {
        if (state == null) return;
        String searchText = input_search.getText();

        if (!searchText.isEmpty()) {
            searchAct(searchText);
        } else {
            search_list_view.getItems().setAll(arrayWords);
        }
        //definitionPaneController.reload();
    }

    protected void loadDefinitionPane(String word, String explain) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("definitionPane.fxml"));
        VBox definitionVBox;
        try {
            definitionVBox = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Load definition_pane failed");
            return;
        }
        definitionPane.getChildren().addAll(definitionVBox);
        definitionPaneController = fxmlLoader.getController();
        definitionPaneController.initData(this.state, word, explain);
    }

    public void initData(ContainerController state) {
        this.state = state;
        if (definitionPaneController == null) {
            loadDefinitionPane("", "");
        }
        arrayWords = this.state.getDictionaryManagement().getDictionary().toArrayString();
        this.reload();
    }
}
