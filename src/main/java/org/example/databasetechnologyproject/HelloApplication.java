package org.example.databasetechnologyproject;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("logos.png");
        stage.getIcons().add(icon);
        stage.setTitle("RestaurantStats");
        stage.setScene(scene);
        stage.show();

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    }

    public static void main(String[] args) {
        launch();
    }
}