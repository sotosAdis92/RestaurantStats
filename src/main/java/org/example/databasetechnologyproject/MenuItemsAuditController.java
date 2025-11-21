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

public class MenuItemsAuditController implements Initializable {
    @FXML
    private Label rowResult;
    @FXML
    TableColumn<MenuAudti, String> na;

    @FXML
    TableColumn<MenuAudti, String> nfn;

    @FXML
    TableColumn<MenuAudti, Float> nln;

    @FXML
    TableColumn<MenuAudti, String> np;

    @FXML
    TableColumn<MenuAudti, Integer> nr;

    @FXML
    TableColumn<MenuAudti, String> oa;

    @FXML
    TableColumn<MenuAudti, String> ofn;

    @FXML
    TableColumn<MenuAudti, Float> oln;

    @FXML
    TableColumn<MenuAudti, String> op;

    @FXML
    TableColumn<MenuAudti, String> operation;

    @FXML
    TableColumn<MenuAudti, Integer> or;

    @FXML
    TableColumn<MenuAudti, String> time;

    @FXML
    TableColumn<MenuAudti, String> use;
    @FXML
    TableView<MenuAudti> table1;
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
    ObservableList<MenuAudti> customerAud = FXCollections.observableArrayList();
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        MenuItemsServiceClass.getInstance().setRefreshCallback(v -> refresh());
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
            String selectString = "SELECT * FROM getItemsAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getItemsAudit()";
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
                String nfn = rs.getString(5);
                String nln = rs.getString(6);
                float n = rs.getFloat(7);
                float o = rs.getFloat(8);
                String n1 = rs.getString(9);
                String n2 = rs.getString(10);
                String n3 = rs.getString(11);
                String n4 = rs.getString(12);
                MenuAudti ne = new MenuAudti(ope,time,use,id,nfn,nln,n,o,n1,n2,n3,n4);
                customerAud.add(ne);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM getOperationItemsAudit()";
            getaudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getaudit.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                textfield.getItems().addAll(ope);
            }
        } catch (SQLException ex){

        }
        operation.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("operation"));
        time.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("timestamp"));
        use.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("userid"));
        nfn.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("new_name"));
        ofn.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("old_name"));
        nln.setCellValueFactory(new PropertyValueFactory<MenuAudti, Float>("new_price"));
        oln.setCellValueFactory(new PropertyValueFactory<MenuAudti, Float>("old_price"));
        np.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("new_category"));
        op.setCellValueFactory(new PropertyValueFactory<MenuAudti, String>("old_category"));
        nr.setCellValueFactory(new PropertyValueFactory<MenuAudti, Integer>("new_state"));
        or.setCellValueFactory(new PropertyValueFactory<MenuAudti, Integer>("old_state"));
        table1.setItems(customerAud);
    }
    public void refresh(){
        customerAud.clear();
        try{
            String selectString = "SELECT * FROM getItemsAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getItemsAudit()";
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
                String nfn = rs.getString(5);
                String nln = rs.getString(6);
                float n = rs.getFloat(7);
                float o = rs.getFloat(8);
                String n1 = rs.getString(9);
                String n2 = rs.getString(10);
                String n3 = rs.getString(11);
                String n4 = rs.getString(12);
                MenuAudti ne = new MenuAudti(ope,time,use,id,nfn,nln,n,o,n1,n2,n3,n4);
                customerAud.add(ne);
            }
        } catch (SQLException ex){

        }
    }
    public void select(ActionEvent event){
        try{
            customerAud.clear();
            String operation = textfield.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM getFilteredItemsAudit(?)";
            String selectString2 = "SELECT COUNT(*) FROM getFilteredItemsAudit(?)";
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
                String nfn = rs.getString(5);
                String nln = rs.getString(6);
                float n = rs.getFloat(7);
                float o = rs.getFloat(8);
                String n1 = rs.getString(9);
                String n2 = rs.getString(10);
                String n3 = rs.getString(11);
                String n4 = rs.getString(12);
                MenuAudti ne = new MenuAudti(ope,time,use,id,nfn,nln,n,o,n1,n2,n3,n4);
                customerAud.add(ne);
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
