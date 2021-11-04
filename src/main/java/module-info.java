module app.DictionaryFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires json.simple;
    requires freetts;
    requires jlayer;


    opens app to javafx.fxml;
    exports app;
    exports app.controllers.panes;
    exports app.actions;
    opens app.controllers.panes to javafx.fxml;
}