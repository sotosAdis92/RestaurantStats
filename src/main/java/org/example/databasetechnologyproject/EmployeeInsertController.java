package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class EmployeeInsertController implements Initializable {
    public EmployeesController mainController;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    @FXML
    TableView<Employee> customerTable;
    @FXML
    TableColumn<Employee, String> firstNameColumn;
    @FXML
    TableColumn<Employee, String> lastNameColumn;
    @FXML
    TableColumn<Employee, String> homeAddressColoumn;
    @FXML
    TableColumn<Employee, String> numberColumn;
    @FXML
    TableColumn<Employee, Integer> ratingColumn;
    static Connection dbConnection;
    PreparedStatement insert;
    PreparedStatement fillTable;
    PreparedStatement delete;
    PreparedStatement update;
    PreparedStatement getFirstName;
    PreparedStatement getLastName;
    PreparedStatement getPhone;
    PreparedStatement getRating;
    PreparedStatement getFname;
    PreparedStatement getCountCustomer;
    PreparedStatement getCountOfTable;
    @FXML
    Stage scene1;
    @FXML
     Button CancelButton;

    @FXML
     Label addressError;

    @FXML
     AnchorPane center;

    @FXML
     AnchorPane center2;

    @FXML
     Label emailLabel;

    @FXML
     Label firstNameError;

    @FXML
     Label firstNameLabel;

    @FXML
     Label homeLabel;

    @FXML
     Label lastNameError;

    @FXML
     Label lastNameLabel;

    @FXML
     Label phoneError;

    @FXML
     Label phoneLabel;

    @FXML
     Button submitButton;

    @FXML
     TextField textField1;

    @FXML
     TextField textField2;

    @FXML
     TextField textField3;

    @FXML
     TextField textField4;

    @FXML
     Label title;

    @FXML
     Label undertitle;
    @FXML
    private ComboBox<String> combo;
    @Override

    public void initialize(URL gf, ResourceBundle resourceBundle) {
        setupFocusListeners();
        setupFocusListeners();
        try{
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){

        }
        try{
            dbConnection = DriverManager.getConnection(url,user,password);
            System.out.println(dbConnection);
        } catch (SQLException ex){

        }
        combo.setValue("Cook");
        combo.getItems().add("Cook");
        combo.getItems().add("Waiter");
        combo.getItems().add("Cashier");
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
        textField2.focusedProperty().addListener((observable, oldValue, newValue) ->{
            lastNameError.setVisible(false);
            if(newValue){
                lastNameLabel.setStyle("-fx-text-fill: #FF5C00");
                textField2.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else{
                lastNameLabel.setStyle("");
                textField2.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        textField3.focusedProperty().addListener((observable, oldValue, newValue) ->{
            addressError.setVisible(false);
            if(newValue){
                homeLabel.setStyle("-fx-text-fill: #FF5C00");
                textField3.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else{
                homeLabel.setStyle("");
                textField3.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        textField4.focusedProperty().addListener((observable, oldValue, newValue) ->{
            phoneError.setVisible(false);
            if(newValue){
                phoneLabel.setStyle("-fx-text-fill: #FF5C00");
                textField4.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else{
                phoneLabel.setStyle("");
                textField4.setStyle("-fx-background-color: transparent;\n" +
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
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
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
        textField2.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void homeAddressInputError(){
        addressError.setVisible(true);
        addressError.setText("address cannot be empty");
        addressError.setStyle("-fx-text-fill: #D0342C");
        homeLabel.setStyle("-fx-text-fill: #D0342C");
        textField3.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void phoneNumberInputError(){
        phoneError.setVisible(true);
        phoneError.setText("Phone Number cannot be empty");
        phoneError.setStyle("-fx-text-fill: #D0342C");
        phoneLabel.setStyle("-fx-text-fill: #D0342C");
        textField4.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void setMainController(EmployeesController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Employee> customerTable){
        this.customerTable = customerTable;
    }
    public void setFirstNameColumn(TableColumn<Employee, String> firstNameColumn){
        this.firstNameColumn = firstNameColumn;
    }
    public void setLastNameColumn(TableColumn<Employee, String> lastNameColumn){
        this.lastNameColumn = lastNameColumn;
    }
    public void setHomeAddressColoumn(TableColumn<Employee, String> homeAddressColoumn){
        this.homeAddressColoumn = homeAddressColoumn;
    }
    public void setNumberColumn(TableColumn<Employee, String> numberColumn){
        this.numberColumn = numberColumn;
    }
    public void setRatingColumn(TableColumn<Employee, Integer> ratingColumn){
        this.ratingColumn = ratingColumn;
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
    public void Submit(ActionEvent event){
        if(textField1.getText().isEmpty()){
            firstNameInputError();
        }
        if(textField2.getText().isEmpty()){
            lastNameInputError();
        }
        if(textField3.getText().isEmpty()){
            homeAddressInputError();
        }
        if(textField4.getText().isEmpty()){
            phoneNumberInputError();
        }
        int id = 0;
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        String email = textField4.getText();
        String position = combo.getValue();
        int salary = Integer.parseInt(textField3.getText());
        try{
            String selectString = "SELECT insertEmployee(?,?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setString(3, position);
            insert.setString(4, email);
            insert.setInt(5, salary);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("SqlException");
        }
        Employee emp = new Employee(id,firstName,lastName,position,salary,email);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        customerTable.getItems().add(emp);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        EmployeeServiceClass.getInstance().triggerRefresh();
        showNotification();
        mainController.refresh();
    }
}
