package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmployeeAuditController implements Initializable {
    @FXML
    private Label rowResult;
    @FXML
    TableColumn<EmployeeAudit, String> na;

    @FXML
    TableColumn<EmployeeAudit, String> nfn;

    @FXML
    TableColumn<EmployeeAudit, String> nln;

    @FXML
    TableColumn<EmployeeAudit, String> np;

    @FXML
    TableColumn<EmployeeAudit, Integer> nr;

    @FXML
    TableColumn<EmployeeAudit, String> oa;

    @FXML
    TableColumn<EmployeeAudit, String> ofn;

    @FXML
    TableColumn<EmployeeAudit, String> oln;

    @FXML
    TableColumn<EmployeeAudit, String> op;

    @FXML
    TableColumn<EmployeeAudit, String> operation;

    @FXML
    TableColumn<EmployeeAudit, Integer> or;

    @FXML
    TableColumn<EmployeeAudit, String> time;

    @FXML
    TableColumn<EmployeeAudit, String> use;
    @FXML
    TableView<EmployeeAudit> table1;
    @FXML
    ComboBox<String> textfield;

    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    PreparedStatement getAudit;
    PreparedStatement getFiltered;
    PreparedStatement getaudit;
    PreparedStatement getCountOfTable;
    ObservableList<EmployeeAudit> empaud = FXCollections.observableArrayList();
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        EmployeeServiceClass.getInstance().setRefreshCallback(v -> refresh());
        textfield.setValue("all");
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
        try{
            textfield.getItems().add("all");
            String selectString = "SELECT * FROM getOperationEmployee()";
            getaudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getaudit.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                textfield.getItems().addAll(ope);
            }
        } catch (SQLException ex){

        }
        try {
            String selectString = "SELECT * FROM getEmployeeAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getEmployeeAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getCountOfTable.executeQuery();
            ResultSet rs = getAudit.executeQuery();
            ResultSet rs2 = getCountOfTable.getResultSet();
            while (rs.next()) {
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                String nfn = rs.getString(5);
                String ofn = rs.getString(6);
                String nln = rs.getString(7);
                String oln = rs.getString(8);
                String na = rs.getString(9);
                String oa = rs.getString(10);
                String np = rs.getString(11);
                String op = rs.getString(12);
                int nr = rs.getInt(13);
                int or = rs.getInt(14);
                EmployeeAudit ad = new EmployeeAudit(ope, time, use, id, nfn, ofn, nln, oln, na, oa, np, op, nr, or);
                empaud.add(ad);
            }
            if (rs2.next()) {
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){

        }
        operation.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("operation"));
        time.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("timestamp"));
        use.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("userid"));
        nfn.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("new_first_name"));
        ofn.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("old_first_name"));
        nln.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("new_last_name"));
        oln.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("old_last_name"));
        na.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("new_email"));
        oa.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("old_email"));
        np.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("new_pos"));
        op.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, String>("old_pos"));
        nr.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, Integer>("new_salary"));
        or.setCellValueFactory(new PropertyValueFactory<EmployeeAudit, Integer>("old_salary"));
        table1.setItems(empaud);
    }
    public void refresh(){
        empaud.clear();
        try {
            String selectString = "SELECT * FROM getEmployeeAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getEmployeeAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getCountOfTable.executeQuery();
            ResultSet rs = getAudit.executeQuery();
            ResultSet rs2 = getCountOfTable.getResultSet();
            while (rs.next()) {
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                String nfn = rs.getString(5);
                String ofn = rs.getString(6);
                String nln = rs.getString(7);
                String oln = rs.getString(8);
                String na = rs.getString(9);
                String oa = rs.getString(10);
                String np = rs.getString(11);
                String op = rs.getString(12);
                int nr = rs.getInt(13);
                int or = rs.getInt(14);
                EmployeeAudit ad = new EmployeeAudit(ope, time, use, id, nfn, ofn, nln, oln, na, oa, np, op, nr, or);
                empaud.add(ad);
            }
            if (rs2.next()) {
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){

        }
    }
    public void select(ActionEvent event){
        try{
            empaud.clear();
            String operation = textfield.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM getFilteredAuditEmployee(?)";
            String selectString2 = "SELECT COUNT(*) FROM getFilteredAuditEmployee(?)";
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getFiltered = dbConnection.prepareStatement(selectString);
            getFiltered.setString(1, operation);
            getCountOfTable.setString(1, operation);
            ResultSet rs = getFiltered.executeQuery();
            ResultSet rs2 = getCountOfTable.executeQuery();
            while(rs.next()){
                String op = rs.getString(1);
                String time = rs.getString(2);
                String user = rs.getString(3);
                int id = rs.getInt(4);
                String nfn = rs.getString(5);
                String ofn = rs.getString(6);
                String nln = rs.getString(7);
                String oln = rs.getString(8);
                String na = rs.getString(9);
                String oa = rs.getString(10);
                String np = rs.getString(11);
                String ope = rs.getString(12);
                int nr = rs.getInt(13);
                int or = rs.getInt(14);
                EmployeeAudit ca = new EmployeeAudit(op,time,user,id,nfn,ofn,nln,oln,na,oa,np,ope,nr,or);
                empaud.add(ca);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
