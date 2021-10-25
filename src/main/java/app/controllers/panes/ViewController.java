package app.controllers.panes;

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
import java.util.ResourceBundle;

public class ViewController implements Initializable {
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

    }

    @FXML
    void handleEnterInputSearch(ActionEvent event) {

    }

    @FXML
    void handleSelectItemListView(MouseEvent event) {

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
