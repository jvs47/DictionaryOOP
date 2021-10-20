package app.controllers.panes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class OnlineGoogleSearchController {
    private ContainerController stage;
    @FXML
    private Label switchLanguageEVLabel;
    @FXML
    private Label switchLanguageVELabel;

    @FXML
    void onMouseClickSwitchEVLanguage(MouseEvent event) {
        stage.showOnlineVEPane();
    }

    @FXML
    void onMouseClickSwitchVELanguage(MouseEvent event) {
        stage.showOnlineEVPane();
    }

    public void initData(ContainerController stage) {
        this.stage = stage;
    }
}
