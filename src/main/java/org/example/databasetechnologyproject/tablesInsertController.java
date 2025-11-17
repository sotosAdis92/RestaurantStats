package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;

public class tablesInsertController implements Initializable {
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    private DialogPane dialog;
    public tablesController mainController;
    PreparedStatement insert;
    @FXML
    Stage scene1;
    @FXML
    TableView<Tables> customerTable;
    @FXML
    TableColumn<Tables, String> status;
    @FXML
    TableColumn<Tables, Integer> tnumber;
    @FXML
    TableColumn<Tables, Integer> capacity;
    @FXML
    Spinner<Integer> spin1;
    @FXML
    Spinner<Integer> spin2;
    @FXML
            ComboBox<String> textfield1;
    PreparedStatement getFirstName;
    PreparedStatement getCountOfTable;
    PreparedStatement delete;
    static Connection dbConnection;
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        try {
            Class.forName(driverClassName);
            System.out.println("JDBC driver loaded successfully");
        } catch (ClassNotFoundException ex) {
            System.err.println("Failed to load JDBC driver: " + ex.getMessage());
            ex.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully");
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            ex.printStackTrace();
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory.setValue(1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory2.setValue(1);
        spin1.setValueFactory(valueFactory);
        spin2.setValueFactory(valueFactory2);
        int currentValue = spin1.getValue();
        int currentValue2 = spin2.getValue();
        textfield1.setValue("all");
    }

    public void setMainController(tablesController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Tables> customerTable){
        this.customerTable = customerTable;
    }
    public void setFirstNameColumn(TableColumn<Tables, String> status){
        this.status = status;
    }
    public void setLastNameColumn(TableColumn<Tables, Integer> tnumber){
        this.tnumber = tnumber;
    }
    public void setHomeAddressColoumn(TableColumn<Tables, Integer> capacity){
        this.capacity = capacity;
    }
    public void setStage(Stage stage) {
        this.scene1 = stage;
    }
    public void showNotification(){
        Stage toastStage = new Stage();
        toastStage.initOwner(scene1);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("Operation Successful, Customer Inserted ✔");
        label.setStyle("-fx-font-size: 17px;\n" +
                "-fx-text-fill: #001D00;\n" +
                "-fx-background-color: #dcfce7;\n" +
                "-fx-background-radius: 10px;\n" +
                "-fx-border-color: #22c55e;\n" +
                "-fx-border-width: 2px;\n" +
                "-fx-border-radius: 10px;\n" +
                "-fx-text-alignment: center;\n" +
                "-fx-padding: 10px 15px;");

        Scene scene = new Scene(label);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.setWidth(370);
        toastStage.setHeight(60);
        toastStage.setX(scene1.getX() + scene1.getWidth() - 400);
        toastStage.setY(scene1.getY() + scene1.getHeight() - 80);

        toastStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
    }
    public void cancelInput(ActionEvent event){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory.setValue(1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory2.setValue(1);
        spin1.setValueFactory(valueFactory);
        spin2.setValueFactory(valueFactory2);
        int currentValue = spin1.getValue();
        int currentValue2 = spin2.getValue();
        textfield1.setValue("all");
    }
    public void submit(ActionEvent event){
        int id=0;
        String status = textfield1.getValue();
        int number = spin1.getValue();
        int capacity = spin2.getValue();
        try{
            String selectString = "SELECT insertTable(?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, number);
            insert.setInt(2, capacity);
            insert.setString(3, status);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        Tables ta = new Tables(id,number,capacity,status);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory.setValue(1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory2.setValue(1);
        spin1.setValueFactory(valueFactory);
        spin2.setValueFactory(valueFactory2);
        int currentValue = spin1.getValue();
        int currentValue2 = spin2.getValue();
        textfield1.setValue("all");
        customerTable.getItems().add(ta);
        showNotification();
        mainController.refresh();
    }
}
