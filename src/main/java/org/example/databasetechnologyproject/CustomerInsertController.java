package org.example.databasetechnologyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
