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

public class orderAuditController implements Initializable {
    @FXML
    TableColumn<OrderAudit, String> op;

    @FXML
    TableColumn<OrderAudit, String> t;

    @FXML
    TableColumn<OrderAudit, String> u;

    @FXML
    TableColumn<OrderAudit, String> ne;

    @FXML
    TableColumn<OrderAudit, Integer> nt;

    @FXML
    TableColumn<OrderAudit, Integer> ot;

    @FXML
    TableColumn<OrderAudit, Float> nto;

    @FXML
    TableColumn<OrderAudit, Float> oto;

    @FXML
    TableColumn<OrderAudit, String> nd;

    @FXML
    TableColumn<OrderAudit, String> od;
    @FXML
    TableColumn<OrderAudit, String> oe;
    @FXML
    TableView<OrderAudit> table1;
    @FXML
    ComboBox<String> combo;
    @FXML
    Label rowResult;

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
    ObservableList<OrderAudit> customerAud = FXCollections.observableArrayList();
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        combo.setValue("all");
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
            String selectString = "SELECT * FROM getOrdersAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getOrdersAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getCountOfTable.executeQuery();
            ResultSet rs = getAudit.executeQuery();
            ResultSet rs2 = getCountOfTable.getResultSet();
            while(rs.next()){
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                String newemp = rs.getString(6);
                String oldemp = rs.getString(5);
                int newta = rs.getInt(8);
                int oldta = rs.getInt(7);
                float newto = rs.getFloat(10);
                float oldto = rs.getFloat(9);
                Timestamp nDate = rs.getTimestamp(12);
                Timestamp oDate = rs.getTimestamp(11);
                OrderAudit ad = new OrderAudit(ope,time,use,id,newemp,oldemp,newta,oldta,newto,oldto,nDate,oDate);
                customerAud.add(ad);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
        try{
            combo.getItems().add("all");
            String selectString = "SELECT * FROM getOrderAuditOp()";
            getaudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getaudit.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                combo.getItems().addAll(ope);
            }
        } catch (SQLException ex){

        }
        op.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("operation"));
        t.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("time"));
        u.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("user"));
        ne.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("newemp"));
        oe.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("oldemp"));
        nt.setCellValueFactory(new PropertyValueFactory<OrderAudit, Integer>("newta"));
        ot.setCellValueFactory(new PropertyValueFactory<OrderAudit, Integer>("oldta"));
        nto.setCellValueFactory(new PropertyValueFactory<OrderAudit, Float>("newto"));
        oto.setCellValueFactory(new PropertyValueFactory<OrderAudit, Float>("oldto"));
        nd.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("nDate"));
        od.setCellValueFactory(new PropertyValueFactory<OrderAudit, String>("oDate"));
        table1.setItems(customerAud);
    }

    public void refresh(){
        customerAud.clear();
        try{
            String selectString = "SELECT * FROM getOrdersAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getOrdersAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getCountOfTable.executeQuery();
            ResultSet rs = getAudit.executeQuery();
            ResultSet rs2 = getCountOfTable.getResultSet();
            while(rs.next()){
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                String newemp = rs.getString(6);
                String oldemp = rs.getString(5);
                int newta = rs.getInt(8);
                int oldta = rs.getInt(7);
                float newto = rs.getFloat(10);
                float oldto = rs.getFloat(9);
                Timestamp nDate = rs.getTimestamp(12);
                Timestamp oDate = rs.getTimestamp(11);
                OrderAudit ad = new OrderAudit(ope,time,use,id,newemp,oldemp,newta,oldta,newto,oldto,nDate,oDate);
                customerAud.add(ad);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
    }

    public void select(ActionEvent event){
        try{
            customerAud.clear();
            String operation = combo.getSelectionModel().getSelectedItem();
            System.out.println(combo.getValue());
            String selectString = "SELECT * FROM getOrderByOperation(?)";
            String selectString2 = "SELECT COUNT(*) FROM getOrderByOperation(?)";
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getFiltered = dbConnection.prepareStatement(selectString);
            getFiltered.setString(1, operation);
            getCountOfTable.setString(1, operation);
            ResultSet rs = getFiltered.executeQuery();
            ResultSet rs2 = getCountOfTable.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                String newemp = rs.getString(6);
                String oldemp = rs.getString(5);
                int newta = rs.getInt(8);
                int oldta = rs.getInt(7);
                float newto = rs.getFloat(10);
                float oldto = rs.getFloat(9);
                Timestamp nDate = rs.getTimestamp(12);
                Timestamp oDate = rs.getTimestamp(11);
                OrderAudit ad = new OrderAudit(ope,time,use,id,newemp,oldemp,newta,oldta,newto,oldto,nDate,oDate);
                customerAud.add(ad);
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
