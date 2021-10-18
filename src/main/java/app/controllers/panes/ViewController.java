package app.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private ContainerController state;
    @FXML
    private TextField input_search;

    @FXML
    private ListView<?> search_list_view;

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
    public void initData(ContainerController state){
        this.state = state;
        this.reload();
    }
}
