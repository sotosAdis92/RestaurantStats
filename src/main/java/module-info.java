module org.example.databasetechnologyproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.databasetechnologyproject to javafx.fxml;
    requires java.dotenv;
    requires java.sql;
    requires atlantafx.base;
    exports org.example.databasetechnologyproject;
}