package org.example.databasetechnologyproject;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private DialogPane dialog;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    BorderPane scene1;
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

        stage.setOnCloseRequest(event ->{
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            dialog = alert2.getDialogPane();
            alert2.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
            dialog.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Image icon2 = new Image("logos.png");
            dialog.getStyleClass().add("dialog2");
            alert2.setTitle("Exit Application");
            alert2.setHeaderText("You are About to Exit The Application");
            alert2.setContentText("Are you sure you wish to exit the Application?");
            Stage alertstage = (Stage) dialog.getScene().getWindow();
            alertstage.getIcons().add(icon2);
            if (alert2.showAndWait().get() == ButtonType.OK) {
                stage.close();
            }else{
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}