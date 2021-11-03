package app.controllers.panes;

import app.dictionary.Word;
import app.helper.AudioFreeTSSAPI;
import app.helper.AudioGoogleAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;

public class DefinitionPaneController {
    private ContainerController state;
    private String word;
    @FXML
    private WebView meaningWebView;
    private WebEngine meaningWebEngine;

    @FXML
    private Label trueBookmarkLabel;
    @FXML
    private Label falseBookmarkLable;

    @FXML
    private Button ukPronounceButton;

    @FXML
    private Button usPronounceButton;

    @FXML
    private Label onlineSearchButton;

    @FXML
    void handleOnlineSearchButton(MouseEvent event) {
        if (event.getSource() == onlineSearchButton) {
            this.state.showOnlineEVPane();
            this.state.getOnlineGoogleSearchEVController().setInputOnlineEVTextArea(word);
        }
    }

    @FXML
    void handlePronounceClick(MouseEvent event) throws JavaLayerException, IOException {
        if (event.getSource() == ukPronounceButton) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            AudioFreeTSSAPI textToSpeak = new AudioFreeTSSAPI(word);
            textToSpeak.play();
        } else if (event.getSource() == usPronounceButton) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(word, "en");
            audio.play(sound);
        }
    }


    @FXML
    void handleClickBookMark(MouseEvent event) {
        Word foundWord = this.state.getDictionaryManagement().binarySearchFavorite(word);
        if (foundWord != null) {
            this.removeBookmark();
            System.out.println("Remove Fav word!");
        } else {
            this.addBookmark();
            System.out.println("Save Fav word!");
        }
        this.loadBookmark();
        this.state.reloadBookmark();
    }

    public void addBookmark() {
        this.state.getDictionaryManagement().saveToFavoriteDatabase(word);
    }

    public void removeBookmark() {
        this.state.getDictionaryManagement().removeFavouriteWordFromDatabase(word);
        this.state.resetBookmark();
    }

    public void loadBookmark() {
        Word foundWord = this.state.getDictionaryManagement().binarySearchFavorite(word);
        trueBookmarkLabel.setVisible(foundWord != null);
    }

    public void reload() {
        this.loadBookmark();
    }

    public void initData(ContainerController state, String word, String meaning) {
        this.state = state;
        this.word = word;
        meaningWebEngine = meaningWebView.getEngine();
        meaningWebEngine.loadContent(meaning);
        meaningWebEngine.setUserStyleSheetLocation(getClass().getResource("webview.css").toString());
        //this.loadBookmark();
    }
}
