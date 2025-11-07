package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class CustomerInsertController implements Initializable {
    public CustomerController mainController;
    @FXML
    TableView<Customer> customerTable;
    @FXML
    TableColumn<Customer, String> firstNameColumn;
    @FXML
    TableColumn<Customer, String> lastNameColumn;
    @FXML
    TableColumn<Customer, String> homeAddressColoumn;
    @FXML
    TableColumn<Customer, String> numberColumn;
    @FXML
    TableColumn<Customer, Integer> ratingColumn;
    @FXML
    TextField textField1;

    @FXML
    TextField textField2;

    @FXML
    TextField textField3;

    @FXML
    TextField textField4;

    @FXML
    TextField textField5;

    @FXML
    Label emailLabel;

    @FXML
    Label firstNameLabel;

    @FXML
    Label homeLabel;

    @FXML
    Label lastNameLabel;
    @FXML
     Label addressError;
    @FXML
     Label emailError;
    @FXML
     Label firstNameError;
    @FXML
     Label lastNameError;
    @FXML
     Label phoneError;


    @FXML
    Label phoneLabel;
    PreparedStatement insert;
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
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
        textField5.focusedProperty().addListener((observable, oldValue, newValue) ->{
            emailError.setVisible(false);
            if(newValue){
                emailLabel.setStyle("-fx-text-fill: #FF5C00");
                textField5.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else{
                emailLabel.setStyle("");
                textField5.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
    }
    public void setMainController(CustomerController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Customer> customerTable){
        this.customerTable = customerTable;
    }
    public void setFirstNameColumn(TableColumn<Customer, String> firstNameColumn){
        this.firstNameColumn = firstNameColumn;
    }
    public void setLastNameColumn(TableColumn<Customer, String> lastNameColumn){
        this.lastNameColumn = lastNameColumn;
    }
    public void setHomeAddressColoumn(TableColumn<Customer, String> homeAddressColoumn){
        this.homeAddressColoumn = homeAddressColoumn;
    }
    public void setNumberColumn(TableColumn<Customer, String> numberColumn){
        this.numberColumn = numberColumn;
    }
    public void setRatingColumn(TableColumn<Customer, Integer> ratingColumn){
        this.ratingColumn = ratingColumn;
    }
    public void showNotification(){

    }
    public void cancelInput(ActionEvent event){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
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
    public void emailInputError(){
        emailError.setVisible(true);
        emailError.setText("Rating cannot be empty");
        emailError.setStyle("-fx-text-fill: #D0342C");
        emailLabel.setStyle("-fx-text-fill: #D0342C");
        textField5.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
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
        if(textField5.getText().isEmpty()){
            emailInputError();
        }
        int id = 0;
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        String homeAddress = textField3.getText();
        String number = textField4.getText();
        int rating = 0;
        try{
            try{
                rating = Integer.parseInt(textField5.getText().trim());
            } catch (NumberFormatException e){
                return;
            }
            String selectString = "SELECT insertCustomer(?,?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setString(3, homeAddress);
            insert.setString(4, number);
            insert.setInt(5, rating);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("SqlException");
        }
        Customer customer = new Customer(id, firstName, lastName, homeAddress, number, rating);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        customerTable.getItems().add(customer);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        showNotification();
        mainController.refresh();
    }
}
