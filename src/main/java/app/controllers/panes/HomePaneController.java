package app.controllers.panes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomePaneController{
    @FXML
    private Label aboutHomeButton;

    @FXML
    private Label favouriteHomeButton;

    @FXML
    private Label onlineSearchHomeButton;

    @FXML
    private Label searchHomeButton;
    private ContainerController stage;

    @FXML
    void handleHomeButtonClick(MouseEvent event) {
        if (event.getSource() == searchHomeButton) {
            stage.showSearchPane();
        } else if (event.getSource() == favouriteHomeButton) {
            stage.showFavouritePane();
        } else if (event.getSource() == onlineSearchHomeButton) {
            stage.showOnlineEVPane();
        }
    }

    public void initData(ContainerController stage) {
        this.stage = stage;
    }
}
