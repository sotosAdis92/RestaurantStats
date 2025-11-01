package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    Button exitButton;
    @FXML
    Tooltip tooltipexit;
    static Connection dbConnection = null;
    static String driverClassName = "org.postgresql.Driver";
    public void initialize(URL location, ResourceBundle arg1){
        try{
            Image image = new Image(getClass().getResourceAsStream("g.png"));
            ImageView imageview = new ImageView(image);
            tooltipexit.setGraphic(imageview);
        } catch (Exception ex){
            System.out.println("Image not found");
        }
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException ex){
            System.out.println("Class not found");
        }
        try{
            dbConnection = DatabaseConnection.getConnection();
        } catch (SQLException ex){
            System.out.println("Connection to database failed");
        }
    }
}