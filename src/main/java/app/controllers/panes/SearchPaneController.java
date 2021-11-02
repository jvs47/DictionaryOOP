package app.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SearchPaneController extends ViewController{

    public Button searchButton;

    @FXML
    public void handleSearchButtonEvent(ActionEvent actionEvent) {
        if(actionEvent.getSource() == searchButton) {
            System.out.println("Click search!");
        }
    }
}
