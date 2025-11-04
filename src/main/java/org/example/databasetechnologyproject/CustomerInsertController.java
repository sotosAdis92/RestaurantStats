package org.example.databasetechnologyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(textField1.isFocused()){
            firstNameLabel.setStyle("-fx-background-color: #FF5C00");
        }
        else if(textField2.isFocused()){
            lastNameLabel.setStyle("-fx-background-color: #FF5C00");
        }
        else if(textField3.isFocused()){
            homeLabel.setStyle("-fx-background-color: #FF5C00");
        }
        else if(textField4.isFocused()){
            phoneLabel.setStyle("-fx-background-color: #FF5C00");
        }
        else if(textField5.isFocused()){
            emailLabel.setStyle("-fx-background-color: #FF5C00");
        }
    }
    public void setMainController(CustomerController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Customer> tableView){
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

    }
    public void Submit(ActionEvent event){

    }
}
