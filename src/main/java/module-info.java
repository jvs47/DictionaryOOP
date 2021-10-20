module app.DictionaryFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens app to javafx.fxml;
    exports app;
    exports app.controllers.panes;
    opens app.controllers.panes to javafx.fxml;
}