package app.controllers.panes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePaneController {
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
        } else if (event.getSource() == aboutHomeButton) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aboutPane.fxml"));
            try {
                Scene aboutScene = new Scene(fxmlLoader.load(), 300, 250);
                Stage aboutStage = new Stage();
                aboutStage.getIcons().add(new Image(getClass().getResourceAsStream("About-icon-24.png")));
                aboutStage.setTitle("About!");
                aboutStage.setScene(aboutScene);
                aboutStage.show();
            } catch (IOException e) {
                System.out.println("Load about_pane failed");
            }
        }
    }

    public void initData(ContainerController stage) {
        this.stage = stage;
    }

}
