package app.controllers.panes;

import app.online.GoogleTranslateAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class OnlineGoogleSearchController extends ViewController {
    private ContainerController stage;
    private boolean modeEV;
    @FXML
    private Label switchLanguageEVLabel;
    @FXML
    private Label switchLanguageVELabel;

    @FXML
    private TextArea inputOnlineEVTextArea, inputOnlineVETextArea;

    @FXML
    private TextArea meaningOnlineEVTextArea, meaningOnlineVETextArea;
    @FXML
    void onMouseClickSwitchEVLanguage(MouseEvent event) {
        stage.showOnlineVEPane();
        modeEV = false;
    }

    @FXML
    void onMouseClickSwitchVELanguage(MouseEvent event) {
        stage.showOnlineEVPane();
        modeEV = true;
    }

    public void initData(ContainerController stage, boolean modeEV) {
        this.stage = stage;
        this.modeEV = modeEV;
    }

    @FXML
    void handleOnlineEVInput(KeyEvent event) throws IOException {
        if (event.getSource() == inputOnlineEVTextArea) {
            String input = inputOnlineEVTextArea.getText();
            System.out.println(input);
            if (modeEV) {
                String output = GoogleTranslateAPI.onlineTranslate("en", "vi", input);
                meaningOnlineEVTextArea.setText(output);
            }
        }
    }

    @FXML
    void handleOnlineVEInput(KeyEvent event) throws IOException {
        if (event.getSource() == inputOnlineVETextArea) {
            String input = inputOnlineVETextArea.getText();
            System.out.println(input);
            if (!modeEV) {
                String output = GoogleTranslateAPI.onlineTranslate("en", "vi", input);
                meaningOnlineVETextArea.setText(output);
            }
        }
    }
}
