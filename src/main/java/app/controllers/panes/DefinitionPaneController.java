package app.controllers.panes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

public class DefinitionPaneController {
    @FXML
    private WebView meaningWebView;

    @FXML
    private Label trueBookmarkLabel;

    @FXML
    private Label ukPronounceLabel;

    @FXML
    private Label usPronounceLabel;
    private ContainerController state;

    @FXML
    void handleClickBookMark(MouseEvent event) {

    }
    public void initData(ContainerController state, String pronunciation, String meaning) {
        this.state = state;
        ukPronounceLabel.setText(pronunciation);
        usPronounceLabel.setText(pronunciation);
    }
}
