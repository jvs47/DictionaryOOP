package app.controllers.panes;

import app.dictionary.Word;
import app.online.AudioGoogleAPI;
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
    private Label deleteWordButton;

    @FXML
    void handleDeleteWordClick(MouseEvent event) {
        if (event.getSource() == deleteWordButton) {
            this.state.getDictionaryAct().deleteWordFromGUI(word);
            System.out.println("Word deleted!");
            this.state.reset();
        }
    }

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
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(word, "en-UK");
            audio.play(sound);
        } else if (event.getSource() == usPronounceButton) {
            AudioGoogleAPI audio = AudioGoogleAPI.getInstance();
            InputStream sound = audio.getAudio(word, "en-US");
            audio.play(sound);
        }
    }


    @FXML
    void handleClickBookMark(MouseEvent event) {
        Word foundWord = this.state.getFavoriteAct().binarySearchFavorite(word);
        if (foundWord != null) {
            this.removeBookmark();
            System.out.println("Remove Fav word!");
        } else {
            this.addBookmark();
            System.out.println("Save Fav word!");
        }
        this.loadBookmark();
        this.state.reloadFavourite();
    }

    public void addBookmark() {
        this.state.getFavoriteAct().saveToFavoriteDatabase(word);
    }

    public void removeBookmark() {
        this.state.getFavoriteAct().removeFavouriteWordFromDatabase(word);
        this.state.resetFavourite();
    }

    public void loadBookmark() {
        Word foundWord = this.state.getFavoriteAct().binarySearchFavorite(word);
        trueBookmarkLabel.setVisible(foundWord != null);
    }

    public void reload() {
        this.loadBookmark();
    }

    public void reset() {
        this.initData(this.state, "", "");
    }

    public void initData(ContainerController state, String word, String meaning) {
        this.state = state;
        this.word = word;
        meaningWebEngine = meaningWebView.getEngine();
        meaningWebEngine.loadContent(meaning);
        meaningWebEngine.setUserStyleSheetLocation(getClass().getResource("webview.css").toString());
        this.loadBookmark();
    }
}
