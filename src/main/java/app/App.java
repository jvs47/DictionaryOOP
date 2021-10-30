package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false"); // smooth font
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("container.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 485);
        scene.getStylesheets().add(App.class.getResource("application.css").toExternalForm()); // load global font
        stage.getIcons().add(new Image(App.class.getResourceAsStream("logo.jpg"))); // set icon
        stage.setTitle("UET Dict Box");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}