module app.fxtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
    exports app.controllers.panes;
    opens app.controllers.panes to javafx.fxml;
}