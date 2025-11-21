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
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class InsertMenuItemController implements Initializable {
    @FXML
    Stage scene1;
    private DialogPane dialog;
    public MenuItemsController mainController;
    @FXML
    TableView<MenuItem> customerTable;
    @FXML
    TableColumn<MenuItem, String> name;
    @FXML
    TableColumn<MenuItem, Float> price;
    @FXML
    TableColumn<MenuItem, String> cat;
    @FXML
    TableColumn<MenuItem, String> st;
    @FXML
    TextField textField1;
    @FXML
    ComboBox<String> combo1;
    @FXML
    ComboBox<String> combo2;
    @FXML
    Spinner<Float> spin;
    PreparedStatement insert;
    @FXML
            Label firstNameError;
    @FXML
    Label firstNameLabel;
    @FXML
    Label lastNameLabel;
    @FXML
    Label addressError;
    @FXML
    Label lastNameError;
    @FXML
    Label homeLabel;
    int i = 0;
    DialogPane dialog3;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        try{
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){

        }
        try{
            dbConnection = DriverManager.getConnection(url,user,password);
            System.out.println(dbConnection);
        } catch (SQLException ex){

        }
        firstNameError.setVisible(false);
        setupFocusListeners();
        SpinnerValueFactory<Float> valueFactory = new SpinnerValueFactory<Float>(){
            @Override
            public void decrement(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value - steps * 1.0f);
                }
            }

            @Override
            public void increment(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value + steps * 1.0f);
                }
            }
        };
        valueFactory.setValue(1.0f);
        spin.setValueFactory(valueFactory);
        float currentValue = spin.getValue();
        lastNameError.setVisible(false);
        addressError.setVisible(false);
        combo1.setValue("Pick any");
        combo2.setValue("Pick any");
        combo1.getItems().add("Main");
        combo1.getItems().add("Side");
        combo1.getItems().add("Desert");
        combo1.getItems().add("Appetizer");
        combo2.getItems().add("Available");
        combo2.getItems().add("Not Available");
    }
    public void setMainController(MenuItemsController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<MenuItem> customerTable){
        this.customerTable = customerTable;
    }
    public void setFirstNameColumn(TableColumn<MenuItem, String> name){
        this.name = name;
    }
    public void setLastNameColumn(TableColumn<MenuItem, Float> price){
        this.price = price;
    }
    public void setHomeAddressColoumn(TableColumn<MenuItem, String> cat){
        this.cat = cat;
    }
    public void setNumberColumn(TableColumn<MenuItem, String> st){
        this.st = st;
    }
    public void setStage(Stage stage) {
        this.scene1 = stage;
    }

    public void Submit(ActionEvent event){
        if(textField1.getText().isEmpty()){
            firstNameInputError();
        }
        if(combo1.getValue() == "Pick any"){
            lastNameInputError();
        }
        if(combo2.getValue() == "Pick any"){
            homeAddressInputError();
        }

        int id = 0;
        String firstName = textField1.getText();
        String category = combo1.getValue();
        String available = combo2.getValue();
        float price = spin.getValue();
        try{
            String selectString = "SELECT insertMenuItem(?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setString(1, firstName);
            insert.setString(2, category);
            insert.setString(3, available);
            insert.setFloat(4, price);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("SqlException");
        }
        MenuItem mi1 = new MenuItem(id, firstName,price, category, available);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        customerTable.getItems().add(mi1);
        textField1.setText("");
        SpinnerValueFactory<Float> valueFactory = new SpinnerValueFactory<Float>(){
            @Override
            public void decrement(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value - steps * 1.0f);
                }
            }

            @Override
            public void increment(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value + steps * 1.0f);
                }
            }
        };
        valueFactory.setValue(1.0f);
        spin.setValueFactory(valueFactory);
        showNotification();
        mainController.refresh();
    }

    public void showNotification(){
        Stage toastStage = new Stage();
        toastStage.initOwner(scene1);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("Operation Successful, Item Inserted ✔");
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
    public void firstNameInputError(){
        firstNameError.setVisible(true);
        firstNameError.setText("First name cannot be empty");
        firstNameError.setStyle("-fx-text-fill: #D0342C");
        firstNameLabel.setStyle("-fx-text-fill: #D0342C");
        textField1.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void lastNameInputError(){
        lastNameError.setVisible(true);
        lastNameError.setText("Last name cannot be empty");
        lastNameError.setStyle("-fx-text-fill: #D0342C");
        lastNameLabel.setStyle("-fx-text-fill: #D0342C");
    }
    public void homeAddressInputError(){
        addressError.setVisible(true);
        addressError.setText("address cannot be empty");
        addressError.setStyle("-fx-text-fill: #D0342C");
        homeLabel.setStyle("-fx-text-fill: #D0342C");
    }
    private void setupFocusListeners() {
        textField1.focusedProperty().addListener((observable, oldValue, newValue) ->{
            firstNameError.setVisible(false);
            if(newValue){
                firstNameLabel.setStyle("-fx-text-fill: #FF5C00");
                textField1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else{
                firstNameLabel.setStyle("");
                textField1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
    }
    public void cancelInput(ActionEvent event){
        textField1.setText("");
        SpinnerValueFactory<Float> valueFactory = new SpinnerValueFactory<Float>(){
            @Override
            public void decrement(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value - steps * 1.0f);
                }
            }

            @Override
            public void increment(int steps) {
                Float value = getValue();
                if (value != null) {
                    setValue(value + steps * 1.0f);
                }
            }
        };
        valueFactory.setValue(1.0f);
        spin.setValueFactory(valueFactory);
    }
}
