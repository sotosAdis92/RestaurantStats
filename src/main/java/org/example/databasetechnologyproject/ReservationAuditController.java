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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ReservationAuditController implements Initializable {

    @FXML
     Label Title;

    @FXML
     Label filterLabel;

    @FXML
     Label firstLabel;

    @FXML
     TableColumn<ReservationAudit, Integer> nc;

    @FXML
     TableColumn<ReservationAudit, Integer> nt;

    @FXML
     TableColumn<ReservationAudit, Timestamp> ntime;

    @FXML
     TableColumn<ReservationAudit, Integer> oc;

    @FXML
     TableColumn<ReservationAudit, String> operationColumn;

    @FXML
     TableColumn<ReservationAudit, Integer> ot;

    @FXML
     TableColumn<ReservationAudit, Timestamp> otime;

    @FXML
     Label rowsLabel;

    @FXML
     TableColumn<ReservationAudit, Integer> sizen;

    @FXML
     TableColumn<ReservationAudit, Integer> sizeo;

    @FXML
     Label subtitle;

    @FXML
     ComboBox<String> textfield;

    @FXML
     TableColumn<ReservationAudit, Timestamp> timeColumn;

    @FXML
     AnchorPane topLayer;

    @FXML
     TableColumn<ReservationAudit, String> userColumn;
    @FXML
    Label rowResult;
    @FXML
    TableView t;
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
    PreparedStatement getF;
    ObservableList<ReservationAudit> reservationAudit = FXCollections.observableArrayList();

    @Override
    public void initialize(URL y, ResourceBundle resourceBundle) {
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
            String selectString = "SELECT * FROM getReservationAudit()";
            String selectString2 = "SELECT COUNT(*) FROM getReservationAudit()";
            getAudit = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);
            getCountOfTable.executeQuery();
            ResultSet rs = getAudit.executeQuery();
            ResultSet rs2 = getCountOfTable.getResultSet();
            while(rs.next()) {
                String ope = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                int nfn = rs.getInt(5);
                int ofn = rs.getInt(6);
                int nln = rs.getInt(7);
                int oln = rs.getInt(8);
                Timestamp na = rs.getTimestamp(9);
                Timestamp oa = rs.getTimestamp(10);
                int np = rs.getInt(11);
                int op = rs.getInt(12);
                ReservationAudit reserv = new ReservationAudit(ope,time,use,id,nfn,ofn,nln,oln,na,oa,np,op);
                reservationAudit.add(reserv);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        try{
            String selectString = "SELECT * FROM getReservationOp()";
            getaudit = dbConnection.prepareStatement(selectString);
            ResultSet rs = getaudit.executeQuery();
            while(rs.next()){
                String ope = rs.getString(1);
                textfield.getItems().addAll(ope);
            }
        } catch (SQLException ex){

        }
        operationColumn.setCellValueFactory(new PropertyValueFactory<ReservationAudit, String>("operation"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Timestamp>("timestamp"));
        userColumn.setCellValueFactory(new PropertyValueFactory<ReservationAudit, String>("userid"));
        nc.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("newcid"));
        oc.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("oldcid"));
        nt.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("newtid"));
        ot.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("oldtid"));
        ntime.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Timestamp>("newtime"));
        otime.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Timestamp>("oldtime"));
        sizen.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("newps"));
        sizeo.setCellValueFactory(new PropertyValueFactory<ReservationAudit, Integer>("oldps"));
        t.setItems(reservationAudit);
    }
    public void select(ActionEvent ev){
        try{
            reservationAudit.clear();
            String SelectString = "SELECT * FROM getFilteredResAudit(?)";
            String ses = "SELECT COUNT(*) FROM getFilteredResAudit(?)";
            getFiltered = dbConnection.prepareStatement(SelectString);
            getF = dbConnection.prepareStatement(ses);
            String ope = textfield.getValue();
            getFiltered.setString(1, ope);
            getF.setString(1,ope);
            getFiltered.executeQuery();
            getF.executeQuery();
            ResultSet rs = getFiltered.getResultSet();
            ResultSet r2 = getF.getResultSet();
            while(rs.next()){
                String oper = rs.getString(1);
                String time = rs.getString(2);
                String use = rs.getString(3);
                int id = rs.getInt(4);
                int nfn = rs.getInt(5);
                int ofn = rs.getInt(6);
                int nln = rs.getInt(7);
                int oln = rs.getInt(8);
                Timestamp na = rs.getTimestamp(9);
                Timestamp oa = rs.getTimestamp(10);
                int np = rs.getInt(11);
                int op = rs.getInt(12);
                ReservationAudit reserv = new ReservationAudit(oper,time,use,id,nfn,ofn,nln,oln,na,oa,np,op);
                reservationAudit.add(reserv);
            }
            while(r2.next()){
                int result = r2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
    }
}
