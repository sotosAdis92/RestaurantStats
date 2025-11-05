package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    TableColumn<Customer, String> emailColumn;
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
    public void setEmailColumn(TableColumn<Customer, String> emailColumn){
        this.emailColumn = emailColumn;
    }
    public void cancelInput(ActionEvent event){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
    public void Submit(ActionEvent event){
        int id = 0;
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        String homeAddress = textField3.getText();
        String number = textField4.getText();
        String email = textField5.getText();
        try{
            String selectString = "SELECT insertCustomer(?,?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setString(3, homeAddress);
            insert.setString(4, number);
            insert.setString(5, email);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("SqlException");
        }
        Customer customer = new Customer(id, firstName, lastName, homeAddress, number, email);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        customerTable.getItems().add(customer);
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        mainController.refresh();
    }
}
