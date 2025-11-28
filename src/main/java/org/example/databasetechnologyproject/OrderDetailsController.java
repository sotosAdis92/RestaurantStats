package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {
    @FXML
    private Label Label11;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane center1000;

    @FXML
    private AnchorPane center190;

    @FXML
    private AnchorPane center200;

    @FXML
    private AnchorPane center500;

    @FXML
    private Label customer;

    @FXML
    private TableColumn<OrderDetails, String> item;

    @FXML
    private Label mone;

    @FXML
    private Label order;

    @FXML
    private TableColumn<OrderDetails, Integer> q;

    @FXML
    private Label r;

    @FXML
    private TableColumn<OrderDetails, Float> t;

    @FXML
    private TableView<OrderDetails> ta;

    @FXML
    private Label table;
    ObservableList<OrderDetails> orders = FXCollections.observableArrayList();
    PreparedStatement fillTable;
    PreparedStatement delete;
    PreparedStatement update;
    PreparedStatement getFirstName;
    PreparedStatement getLastName;
    PreparedStatement getPhone;
    PreparedStatement getRating;
    PreparedStatement getFname;
    PreparedStatement getCountCustomer;
    PreparedStatement getCountOfTable;
    PreparedStatement selector;
    int i = 0;
    private DialogPane dialog;
    DialogPane dialog3;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    int orderid;
    public void setOrderId(int orderid){
        this.orderid = orderid;
        loadOrderDetails();
    }
    @Override
    public void initialize(URL k, ResourceBundle resourceBundle) {
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully");
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(orderid);
    }
    private void loadOrderDetails() {
        System.out.println("Loading details for order: " + orderid);
        try{
            String selectString = "SELECT * FROM getOrderDet(?)";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.setInt(1, orderid);
            ResultSet rs = getFirstName.executeQuery();
            while(rs.next()){
                Object num = rs.getObject(1);
                Object time = rs.getObject(2);
                Object number = rs.getObject(3);
                Object emp = rs.getObject(4);
                mone.setText(String.valueOf(num));
                order.setText(String.valueOf(time));
                table.setText(String.valueOf(number));
                customer.setText(String.valueOf(emp));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        try{

                String selectString = "SELECT * FROM getOrderIt(?)";
                getFirstName = dbConnection.prepareStatement(selectString);
                getFirstName.setInt(1 ,orderid);
                getFirstName.executeQuery();
                ResultSet rs = getFirstName.getResultSet();
                while(rs.next()){
                    String name = rs.getString(1);
                    int q = rs.getInt(2);
                    float p = rs.getFloat(3);
                    OrderDetails or1 = new OrderDetails(name,q,p);
                    orders.add(or1);
                }


            ta.setItems(orders);

            item.setCellValueFactory(new PropertyValueFactory<OrderDetails, String>("name"));
            q.setCellValueFactory(new PropertyValueFactory<OrderDetails, Integer>("quantity"));
            t.setCellValueFactory(new PropertyValueFactory<OrderDetails, Float>("price"));
            ta.setItems(orders);
        } catch (SQLException ex){

        }
    }

}
