module app.DictionaryFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;


    opens app to javafx.fxml;
    exports app;
    exports app.controllers.panes;
    opens app.controllers.panes to javafx.fxml;
}