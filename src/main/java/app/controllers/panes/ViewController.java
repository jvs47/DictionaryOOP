package app.controllers.panes;

import app.dictionary.*;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ViewController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement(dictionary);
    private ContainerController state;

    @FXML
    private TextField input_search;

    @FXML
    private ListView<?> search_list_view;

    @FXML
    private AnchorPane definitionPane;
    private DefinitionPaneController definitionPaneController;

    @FXML
    void handleChangeInputSearch(KeyEvent event) {
        if(event.getSource() == input_search) {
            String searchText = input_search.getText();
            if(!searchText.isEmpty()) {

            }
        }
        return;
    }

    @FXML
    void handleEnterInputSearch(ActionEvent event) {
        if(event.getSource() == input_search) {
            System.out.println("Enter search");
        }
        return;
    }

    @FXML
    void handleSelectItemListView(MouseEvent event) {
        String word = (String) search_list_view.getSelectionModel().getSelectedItem();
        if(word == null) {
            return;
        }
        input_search.setText(word);

    }

    public void start() {
        dictionaryManagement.insertFromDatabase();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void reload(){
        if(state == null) return;
        String searchText = input_search.getText();

        search_list_view.getItems().clear();

        //controllerViewWord.reload();

    }

    protected void loadDefinitionPane(String pronunciation, String meaning){
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
        definitionPaneController.initData(this.state, pronunciation, meaning);
    }

    public void initData(ContainerController state){
        this.state = state;
        if(definitionPaneController == null){
            loadDefinitionPane("","");
        }
        this.reload();
    }
}
