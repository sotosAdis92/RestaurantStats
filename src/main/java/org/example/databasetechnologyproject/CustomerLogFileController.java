package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerLogFileController implements Initializable {
    @FXML
     TableColumn<CustomerAudit, String> na;

    @FXML
     TableColumn<CustomerAudit, String> nfn;

    @FXML
     TableColumn<CustomerAudit, String> nln;

    @FXML
     TableColumn<CustomerAudit, String> np;

    @FXML
     TableColumn<CustomerAudit, Integer> nr;

    @FXML
     TableColumn<CustomerAudit, String> oa;

    @FXML
     TableColumn<CustomerAudit, String> ofn;

    @FXML
     TableColumn<CustomerAudit, String> oln;

    @FXML
     TableColumn<CustomerAudit, String> op;

    @FXML
     TableColumn<CustomerAudit, String> operation;

    @FXML
     TableColumn<CustomerAudit, Integer> or;

    @FXML
     TableColumn<CustomerAudit, String> time;

    @FXML
     TableColumn<CustomerAudit, String> use;
    @FXML
     TableView<CustomerAudit> table1;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    PreparedStatement getAudit;
    ObservableList<CustomerAudit> customerAud = FXCollections.observableArrayList();
    @Override
    public void initialize(URL y, ResourceBundle resourceBundle) {
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);
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
            String selectString = "SELECT * FROM getCustomerAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getAudit.executeQuery();
            while(rs.next()){
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
                CustomerAudit ad = new CustomerAudit(ope,time,use,id,nfn,ofn,nln,oln,na,oa,np,op,nr,or);
                customerAud.add(ad);
            }
        } catch (SQLException ex){

        }
        operation.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("operation"));
        time.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("timestamp"));
        use.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("userid"));
        nfn.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("new_first_name"));
        ofn.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("old_first_name"));
        nln.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("new_last_name"));
        oln.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("old_last_name"));
        na.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("new_address"));
        oa.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("old_address"));
        np.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("new_phone"));
        op.setCellValueFactory(new PropertyValueFactory<CustomerAudit, String>("old_phone"));
        nr.setCellValueFactory(new PropertyValueFactory<CustomerAudit, Integer>("new_rating"));
        or.setCellValueFactory(new PropertyValueFactory<CustomerAudit, Integer>("old_rating"));
        table1.setItems(customerAud);
    }
}
