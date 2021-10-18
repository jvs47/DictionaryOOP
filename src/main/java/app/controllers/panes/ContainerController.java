package app.controllers.panes;

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

    private SearchPaneController searchPaneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("searchPane.fxml"));
            anchorSearchPane = fxmlLoader.load();
            searchPaneController = fxmlLoader.getController();
            searchPaneController.initData(this);
        } catch (IOException e){
            System.out.println("Load search_pane failed");
        }
        //this.showSearchPanel();
        this.showHomePane();
    }

    private void setContentPane(AnchorPane anchorPane){
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
            categoryLabel.setText("EDIT");
        } else if (event.getSource() == nav_favouriteButton) {
            categoryLabel.setText("FAVOURITE");
        } else if (event.getSource() == nav_historyButton) {
            categoryLabel.setText("HISTORY");
        } else if (event.getSource() == nav_onlineButton) {
            categoryLabel.setText("Google Translate");
        }
    }

    public void resetStyleNav() {
        nav_homeButton.setStyle(null);
        nav_searchButton.setStyle(null);
        nav_editButton.setStyle(null);
        nav_favouriteButton.setStyle(null);
        nav_onlineButton.setStyle(null);
    }

    public void showSearchPane(){
        categoryLabel.setText("SEARCH");
        this.setContentPane(anchorSearchPane);
        searchPaneController.initData(this);
        this.resetStyleNav();
        nav_searchButton.setStyle("-fx-background-color: #1D4698; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px;");
    }

    public void showHomePane(){
        categoryLabel.setText("DICTIONARY");
        this.setContentPane(anchorSearchPane);
        this.resetStyleNav();
        nav_homeButton.setStyle("-fx-background-color: #1D4698; -fx-border-style: hidden hidden solid hidden;-fx-border-width: 2px;-fx-border-color: #FEC400; -fx-border-radius: 5px 5px 0px 0px;");
    }
}
