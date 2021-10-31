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
    private Label pronounceLabel;

    @FXML
    private Label word;

    private ContainerController state;

    @FXML
    void handleClickBookMark(MouseEvent event) {

    }
    public void initData(ContainerController state, String word, String meaning) {
        this.state = state;
        this.word.setText(word);
    }
}
