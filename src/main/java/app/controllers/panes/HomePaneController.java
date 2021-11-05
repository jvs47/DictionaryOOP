package app.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.awt.Desktop.getDesktop;


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
    private Hyperlink cuongknHyperLink;

    @FXML
    private Hyperlink dcanh143HyperLink;

    @FXML
    private Hyperlink jvs47HyperLink;

    @FXML
    private Hyperlink uetHyperLink;

    @FXML
    void handleHyperLinkClick(ActionEvent event) {
        if (event.getSource() == jvs47HyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/jvs47"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == dcanh143HyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/dcanh143"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == cuongknHyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/cuongkn"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == uetHyperLink) {
            try {
                getDesktop().browse(new URI("https://uet.vnu.edu.vn/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

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
