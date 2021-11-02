package app.controllers.panes;

import app.dictionary.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContainerController implements Initializable {
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    @FXML
    private Button nav_editButton;
    @FXML
    private Button nav_favouriteButton;
    @FXML
    private Button nav_historyButton;
    @FXML
    private Button nav_homeButton;
    @FXML
    private Button nav_onlineButton;
    @FXML
    private Button nav_searchButton;
    @FXML
    private Label categoryLabel;
    @FXML
    private AnchorPane categoryPanel;
    private AnchorPane anchorSearchPane;
    private AnchorPane anchorOnlineSearchEVPane;
    private AnchorPane anchorOnlineSearchVEPane;
    private AnchorPane anchorFavouritePane;
    private AnchorPane anchorHistoryPane;
    private AnchorPane anchorEditPane;
    private AnchorPane anchorHomePane;
    private SearchPaneController searchPaneController;
    private OnlineGoogleSearchController onlineGoogleSearchEVController;
    private OnlineGoogleSearchController onlineGoogleSearchVEController;
    private FavouritePaneController favouritePaneController;
    private HistoryPaneController historyPaneController;
    private EditPaneController editPaneController;
    private HomePaneController homePaneController;
    private HistoryAct historyAct = new HistoryAct();
    private DictionaryAct dictionaryAct = new DictionaryAct();
    private FavoriteAct favoriteAct = new FavoriteAct();

    public void setAnchorEditPane(AnchorPane anchorEditPane) {
        this.anchorEditPane = anchorEditPane;
    }

    public HistoryAct getHistoryAct() {
        return historyAct;
    }

    public void setHistoryAct(HistoryAct historyAct) {
        this.historyAct = historyAct;
    }

    public DictionaryAct getDictionaryAct() {
        return dictionaryAct;
    }

    public void setDictionaryAct(DictionaryAct dictionaryAct) {
        this.dictionaryAct = dictionaryAct;
    }

    public FavoriteAct getFavoriteAct() {
        return favoriteAct;
    }

    public void setFavoriteAct(FavoriteAct favoriteAct) {
        this.favoriteAct = favoriteAct;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homePane.fxml"));
            anchorHomePane = fxmlLoader.load();
            homePaneController = fxmlLoader.getController();
            homePaneController.initData(this);
        } catch (IOException e) {
            System.out.println("Load search_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchPane.fxml"));
            anchorSearchPane = fxmlLoader.load();
            searchPaneController = fxmlLoader.getController();
            searchPaneController.initData(this);
        } catch (IOException e) {
            System.out.println("Load search_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favouritePane.fxml"));
            anchorFavouritePane = fxmlLoader.load();
            favouritePaneController = fxmlLoader.getController();
            favouritePaneController.initData(this);
        } catch (IOException e) {
            System.out.println("Load favourite_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("historyPane.fxml"));
            anchorHistoryPane = fxmlLoader.load();
            historyPaneController = fxmlLoader.getController();
            historyPaneController.initData(this);
        } catch (IOException e) {
            System.out.println("Load history_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editPane.fxml"));
            anchorEditPane = fxmlLoader.load();
            editPaneController = fxmlLoader.getController();
            editPaneController.initData(this);
        } catch (IOException e) {
            System.out.println("Load history_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("onlineGoogleEVPane.fxml"));
            anchorOnlineSearchEVPane = fxmlLoader.load();
            onlineGoogleSearchEVController = fxmlLoader.getController();
            onlineGoogleSearchEVController.initData(this, true);
        } catch (IOException e) {
            System.out.println("Load online_search_EV_pane failed");
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("onlineGoogleVEPane.fxml"));
            anchorOnlineSearchVEPane = fxmlLoader.load();
            onlineGoogleSearchVEController = fxmlLoader.getController();
            onlineGoogleSearchVEController.initData(this, false);
        } catch (IOException e) {
            System.out.println("Load online_search_VE_pane failed");
        }
        this.showHomePane();
    }

    private void setContentPane(AnchorPane anchorPane) {
        this.categoryPanel.getChildren().setAll(anchorPane);
        //.categoryPanel = anchorPane;
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == nav_homeButton) {
            showHomePane();
        } else if (event.getSource() == nav_searchButton) {
            showSearchPane();
        } else if (event.getSource() == nav_editButton) {
            showEditPane();
        } else if (event.getSource() == nav_favouriteButton) {
            showFavouritePane();
        } else if (event.getSource() == nav_historyButton) {
            showHistoryPane();
        } else if (event.getSource() == nav_onlineButton) {
            showOnlineEVPane();
        }
    }

    public void resetStyleNav() {
        nav_homeButton.setStyle(null);
        nav_searchButton.setStyle(null);
        nav_editButton.setStyle(null);
        nav_favouriteButton.setStyle(null);
        nav_historyButton.setStyle(null);
        nav_onlineButton.setStyle(null);
    }

    public void showSearchPane() {
        categoryLabel.setText("DICTIONARY");
        this.setContentPane(anchorSearchPane);
        searchPaneController.initData(this);
        this.resetStyleNav();
        nav_searchButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showHistoryPane() {
        categoryLabel.setText("HISTORY");
        this.setContentPane(anchorHistoryPane);
        historyPaneController.initData(this);
        this.resetStyleNav();
        nav_historyButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showFavouritePane() {
        categoryLabel.setText("FAVOURITE");
        this.setContentPane(anchorFavouritePane);
        favouritePaneController.initData(this);
        this.resetStyleNav();
        nav_favouriteButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showEditPane() {
        categoryLabel.setText("ADJUST");
        this.setContentPane(anchorEditPane);
        editPaneController.initData(this);
        this.resetStyleNav();
        nav_editButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showHomePane() {
        categoryLabel.setText("HOME");
        this.setContentPane(anchorHomePane);
        homePaneController.initData(this);
        this.resetStyleNav();
        nav_homeButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showOnlineEVPane() {
        categoryLabel.setText("Google Translate");
        this.setContentPane(anchorOnlineSearchEVPane);
        onlineGoogleSearchEVController.initData(this, true);
        this.resetStyleNav();
        nav_onlineButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 10px 10px 10px 10px;");
    }

    public void showOnlineVEPane() {
        categoryLabel.setText("Google Translate");
        this.setContentPane(anchorOnlineSearchVEPane);
        onlineGoogleSearchVEController.initData(this, false);
        this.resetStyleNav();
        nav_onlineButton.setStyle("-fx-background-color: #191970; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px; -fx-background-radius: 5px 5px 0px 0px;");
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }
}
