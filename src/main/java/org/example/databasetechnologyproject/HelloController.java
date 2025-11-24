package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button exitButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    Button HomeButton;
    @FXML
    WebView webview1;
    WebEngine engine;

    @FXML
    WebView webView2;
    WebEngine engine2;
    private DialogPane dialog;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;

    public void initialize(URL location, ResourceBundle arg1){
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);
        try{
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){

        }
        try{
            dbConnection = DriverManager.getConnection(url,user,password);
            System.out.println(dbConnection);
        } catch (SQLException ex){

        }

        int homeScreenId = 1;
        if(homeScreenId == 1){
            HomeButton.setStyle("-fx-font-size: 17px;\n" +
                    "    -fx-font-family: \"Lexend Giga\";\n" +
                    "    -fx-background-color: #5CABF5;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-alignment: CENTER_LEFT;");
        }
        try{
            Image image = new Image(getClass().getResourceAsStream("g.png"));
            ImageView imageview = new ImageView(image);
            tooltipexit.setGraphic(imageview);
        } catch (Exception ex){
            System.out.println("Image not found");
        }
        engine = webview1.getEngine();
        engine2 = webView2.getEngine();
        loadPage();
        loadPage2();
    }

    public void loadPage(){
        if (engine != null) {
            String htmlContent = """
                    <!DOCTYPE html>
                      <html>
                      <head>
                          <meta charset="UTF-8">
                          <title>Monthly Reservations Chart</title>
                          <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                          <style>
                              body {\s
                                  margin: 0;\s
                                  padding: 0px;\s
                                  background: white;\s
                                  font-family: Arial, sans-serif;
                              }
                              #chartContainer {
                                  width: 630px;
                                  height: 390px;
                                  background: white;
                                  border-radius: 10px;
                                  margin-top: 50px;
                                  padding: 0px;
                                  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                              }
                              .chart-title {
                                  text-align: center;
                                  font-size: 24px;
                                  font-weight: bold;
                                  margin-bottom: 20px;
                                  color: #333;
                              }
                          </style>
                      </head>
                      <body>
                        
                          <div id="chartContainer">
                              <canvas id="myChart1" width="630px" height="390px"></canvas>
                          </div>
                          <script>
                              // Simple immediate execution
                              const ctx = document.getElementById('myChart1').getContext('2d');
                              const myChart = new Chart(ctx, {
                                  type: 'bar',
                                  data: {
                                      labels: [
                                          'January', 'February', 'March', 'April', 'May', 'June',
                                          'July', 'August', 'September', 'October', 'November', 'December'
                                      ],
                                      datasets: [{
                                          label: 'Number of Reservations',
                                          data: [8, 12, 15, 7, 18, 9, 11, 14, 16, 6, 13, 10],
                                          backgroundColor: [
                                              '#FFB3BA', '#B3E0FF', '#FFF6B3', '#B3FFDA', '#B3D9FF', '#B3C7D9',
                                              '#FFB3D9', '#E6B3FF', '#FFE0B3', '#B3E6D9', '#B3D1E0', '#FFB3B3'
                                          ],
                                          borderColor: [
                                              '#FF6B6B', '#4ECDC4', '#FFD166', '#06D6A0', '#118AB2', '#073B4C',
                                              '#EF476F', '#7209B7', '#F8961E', '#43AA8B', '#277DA1', '#F94144'
                                          ],
                                          borderWidth: 2
                                      }]
                                  },
                                  options: {
                                      responsive: false,
                                      maintainAspectRatio: false,
                                      plugins: {
                                          legend: {
                                              display: true,
                                              position: 'top',
                                          },
                                          tooltip: {
                                              enabled: true
                                          }
                                      },
                                      scales: {
                                          y: {
                                              beginAtZero: true,
                                              title: {
                                                  display: true,
                                                  text: 'Number of Reservations'
                                              },
                                              ticks: {
                                                  stepSize: 1
                                              }
                                          },
                                          x: {
                                              title: {
                                                  display: true,
                                                  text: 'Months'
                                              },
                                              ticks: {
                                                  autoSkip: false,
                                                  maxRotation: 45
                                              }
                                          }
                                      }
                                  }
                              });
                              console.log('Full year chart created!');
                          </script>
                      </body>
                      </html>""";
            engine.loadContent(htmlContent);

        } else {
            System.out.println("WebEngine is not initialized");
        }
    }


    public void loadPage2(){
        if (engine2 != null) {
            String htmlContent = """
                    """;
            engine2.loadContent(htmlContent);

        } else {
            System.out.println("WebEngine is not initialized");
        }
    }

    public void exitButton(ActionEvent event){
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog = alert2.getDialogPane();
        alert2.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        dialog.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Image icon = new Image("logos.png");
        dialog.getStyleClass().add("dialog2");
        alert2.setTitle("Exit Application");
        alert2.setHeaderText("You are About to Exit The Application");
        alert2.setContentText("Are you sure you wish to exit the Application?");
        Stage alertstage = (Stage) dialog.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert2.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scene1.getScene().getWindow();
            System.out.println("You exited the app");
            stage.close();
        }
    }
    public void switchToSceneCustomer(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
        }
    }
    public void switchToReservationsScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("reservationsScene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToEmployeesScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("employeesScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToMenuScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menuScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
        }
    }
    public void switchToTableScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tablesScene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToOrdersScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ordersScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToAboutScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("aboutScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }

}