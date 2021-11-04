package app.controllers.panes;

import app.helper.AudioGoogleAPI;
import app.online.GoogleTransAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;

public class OnlineGoogleSearchController {
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
    private Label listenEN, listenVN;

    @FXML
    void onMouseClickSwitchEVLanguage(MouseEvent event) {
        if (event.getSource() == switchLanguageEVLabel) {
            stage.showOnlineVEPane();
            modeEV = false;
        }
    }

    @FXML
    void onMouseClickSwitchVELanguage(MouseEvent event) {
        if (event.getSource() == switchLanguageVELabel) {
            stage.showOnlineEVPane();
            modeEV = true;
        }
    }

    @FXML
    void handleListenEVClick(MouseEvent event) throws IOException, JavaLayerException {
        if (event.getSource() == listenEN) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(inputOnlineEVTextArea.getText(), "en");
            audio.play(sound);
        } else if (event.getSource() == listenVN) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(meaningOnlineEVTextArea.getText(), "vi");
            audio.play(sound);
        }
    }

    @FXML
    void handleListenVEClick(MouseEvent event) throws IOException, JavaLayerException {
        if (event.getSource() == listenEN) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(inputOnlineVETextArea.getText(), "vi");
            audio.play(sound);
        } else if (event.getSource() == listenVN) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(meaningOnlineVETextArea.getText(), "en");
            audio.play(sound);
        }
    }

    public void initData(ContainerController stage, boolean modeEV) {
        this.stage = stage;
        this.modeEV = modeEV;
    }

    @FXML
    void handleOnlineEVInput(KeyEvent event) throws IOException {
        if (event.getSource() == inputOnlineEVTextArea) {
            String input = inputOnlineEVTextArea.getText();
            if (modeEV) {
                String output = GoogleTransAPI.translate(input, GoogleTransAPI.LANGUAGE.ENGLISH, GoogleTransAPI.LANGUAGE.VIETNAMESE);
                meaningOnlineEVTextArea.setText(output);
            }
        }
    }

    @FXML
    void handleOnlineVEInput(KeyEvent event) throws IOException {
        if (event.getSource() == inputOnlineVETextArea) {
            String input = inputOnlineVETextArea.getText();
            if (!modeEV) {
                String output = GoogleTransAPI.translate(input, GoogleTransAPI.LANGUAGE.VIETNAMESE, GoogleTransAPI.LANGUAGE.ENGLISH);
                meaningOnlineVETextArea.setText(output);
            }
        }
    }

    public void setInputOnlineEVTextArea(String text) {
        this.inputOnlineEVTextArea.setText(text);
    }
}
