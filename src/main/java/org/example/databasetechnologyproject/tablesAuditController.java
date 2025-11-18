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

public class tablesAuditController implements Initializable {
    @FXML
    TableColumn<TablesAudit, String> na;

    @FXML
    TableColumn<TablesAudit, Integer> nfn;

    @FXML
    TableColumn<TablesAudit, String> nln;

    @FXML
    TableColumn<TablesAudit, String> np;

    @FXML
    TableColumn<TablesAudit, Integer> nr;

    @FXML
    TableColumn<TablesAudit, Integer> oa;

    @FXML
    TableColumn<TablesAudit, Integer> ofn;

    @FXML
    TableColumn<TablesAudit, String> oln;

    @FXML
    TableColumn<TablesAudit, String> op;

    @FXML
    TableColumn<TablesAudit, String> operation;

    @FXML
    TableColumn<TablesAudit, Integer> or;

    @FXML
    TableColumn<TablesAudit, String> time;

    @FXML
    TableColumn<TablesAudit, String> use;
    @FXML
    TableView<TablesAudit> table1;
    @FXML
    ComboBox<String> textfield;
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
    ObservableList<TablesAudit> customerAud = FXCollections.observableArrayList();
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        TablesServiceClass.getInstance().setRefreshCallback(v -> refresh());
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
            textfield.getItems().addAll("all");
            String selectString = "SELECT * FROM getTablesAudti()";
            String selectString2 = "SELECT COUNT(*) FROM getTablesAudti()";
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
                int nfn = rs.getInt(5);
                int ofn = rs.getInt(6);
                int oa = rs.getInt(7);
                int nr = rs.getInt(8);
                String np = rs.getString(9);
                String nln = rs.getString(10);
                TablesAudit ad = new TablesAudit(ope,time,use,id,nfn,ofn,oa,nr,np,nln);
                customerAud.add(ad);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM getTablesAuditOpe()";
            getaudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getaudit.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                textfield.getItems().addAll(ope);
            }
        } catch (SQLException ex){

        }
        operation.setCellValueFactory(new PropertyValueFactory<TablesAudit, String>("operation"));
        time.setCellValueFactory(new PropertyValueFactory<TablesAudit, String>("timestamp"));
        use.setCellValueFactory(new PropertyValueFactory<TablesAudit, String>("userid"));
        nfn.setCellValueFactory(new PropertyValueFactory<TablesAudit, Integer>("new_tablenumber"));
        ofn.setCellValueFactory(new PropertyValueFactory<TablesAudit, Integer>("old_tablenumber"));
        nln.setCellValueFactory(new PropertyValueFactory<TablesAudit, String>("old_status"));
        oa.setCellValueFactory(new PropertyValueFactory<TablesAudit, Integer>("new_capacity"));
        np.setCellValueFactory(new PropertyValueFactory<TablesAudit, String>("new_status"));
        nr.setCellValueFactory(new PropertyValueFactory<TablesAudit, Integer>("old_capacity"));
        table1.setItems(customerAud);
    }

    public void refresh(){
        customerAud.clear();
        try{
            textfield.getItems().addAll("all");
            String selectString = "SELECT * FROM getTablesAudti()";
            String selectString2 = "SELECT COUNT(*) FROM getTablesAudti()";
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
                int nfn = rs.getInt(5);
                int ofn = rs.getInt(6);
                int oa = rs.getInt(7);
                int nr = rs.getInt(8);
                String np = rs.getString(9);
                String nln = rs.getString(10);
                TablesAudit ad = new TablesAudit(ope,time,use,id,nfn,ofn,oa,nr,np,nln);
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
            String operation = textfield.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM getTfilter(?)";
            String selectString2 = "SELECT COUNT(*) FROM getTfilter(?)";
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
                int nfn = rs.getInt(5);
                int ofn = rs.getInt(6);
                int oa = rs.getInt(7);
                int nr = rs.getInt(8);
                String np = rs.getString(9);
                String nln = rs.getString(10);
                TablesAudit ad = new TablesAudit(ope,time,use,id,nfn,ofn,oa,nr,np,nln);
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
