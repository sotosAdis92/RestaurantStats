package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class inserOrderPart2 implements Initializable {
    private DialogPane dialog;
    public OrdersController mainController;
    @FXML
    TableView<Order> customerTable;
    @FXML
    TableColumn<Order, Integer> on;
    @FXML
    TableColumn<Order, String> cu;
    @FXML
    TableColumn<Order, String> emp;
    @FXML
    TableColumn<Order, Integer> ta;
    @FXML
    TableColumn<Order, Float> amm;
    @FXML
    TableColumn<Order, Timestamp> da;
    @FXML
    TextField textField1;

    @FXML
    Stage scene1;

    @FXML
    TextField textField2;

    @FXML
    ComboBox<Integer> textField3;

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
    Label addressError;
    @FXML
    Label emailError;
    @FXML
    Label firstNameError;
    @FXML
    Label lastNameError;
    @FXML
    Label phoneError;
    Label phoneLabel;
    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane center2;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private ComboBox<String> combo2;

    @FXML
    private ComboBox<Integer> combo3;

    @FXML
    private ComboBox<String> combo4;

    @FXML
    private ComboBox<String> combo5;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button e;


    @FXML
    private Button insert1;

    @FXML
    private Button insertCustomerButton;

    @FXML
    private TableColumn<OrderItem, Object> price;

    @FXML
    private TableColumn<OrderItem, String> pro;

    @FXML
    private TableColumn<OrderItem, Object> quant;

    @FXML
    private Label rowsLabel;

    @FXML
    private Spinner<Integer> spin;

    @FXML
    private TableView<OrderItem> table1;

    @FXML
    private Label title;

    @FXML
    private Label undertitle;
    PreparedStatement insert;
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
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    ObservableList<OrderItem> customers = FXCollections.observableArrayList();
    OrderItem item;
    @Override
    public void initialize(URL f, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spin.setValueFactory(valueFactory);
        combo5.setValue("Nigger");
        try{
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){

        }
        try{
            dbConnection = DriverManager.getConnection(url,user,password);
            System.out.println(dbConnection);
        } catch (SQLException ex){

        }

        try{
            String selectString = "SELECT * FROM getItemsNameAndId()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                String name =  rs.getString(2);
                combo5.getItems().add(num + ": " + name);
            }
            if (!combo5.getItems().isEmpty()) {
                combo5.setValue(combo5.getItems().get(0));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        table1.setItems(customers);
        pro.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemid"));
        quant.setCellValueFactory(new PropertyValueFactory<OrderItem, Object>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<OrderItem, Object>("price"));
        table1.setItems(customers);
    }


    public void setMainController(OrdersController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Order> customerTable){
        this.customerTable = customerTable;
    }
    public void setFirstNameColumn(TableColumn<Order, Integer> firstNameColumn){
        this.on = firstNameColumn;
    }
    public void setLastNameColumn(TableColumn<Order, String> lastNameColumn){
        this.cu= lastNameColumn;
    }
    public void setHomeAddressColoumn(TableColumn<Order, String> homeAddressColoumn){
        this.emp = homeAddressColoumn;
    }
    public void setNumberColumn(TableColumn<Order, Integer> numberColumn){
        this.ta = numberColumn;
    }
    public void setRatingColumn(TableColumn<Order, Float> ratingColumn){
        this.amm = ratingColumn;
    }
    public void setNew(TableColumn<Order, Timestamp> ratingColumn){
        this.da = ratingColumn;
    }
    public void setStage(Stage stage) {
        this.scene1 = stage;
    }
    public void Submit(ActionEvent event){

    }
    public void create(ActionEvent event){}
    public void cancel(ActionEvent event){}
    public void addProduct(ActionEvent event){
        int qu = spin.getValue();
        String name = combo5.getValue();
        String names;
        int qua=0;
        float prices=0;
        int item=0;
        String idString = name.replaceAll(":.*", "");
        int id = Integer.parseInt(idString.trim());

        try{
            String selectString = "SELECT * FROM getItemPrices(?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, id);
            insert.setInt(2, qu);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                item = rs.getInt(1);
                names =rs.getString(2);
                qua = rs.getInt(3);
                prices = rs.getFloat(4);
            }
            OrderItem od = new OrderItem(item,qua,prices);
            customers.add(od);
        } catch (SQLException ex){

        }
    }
    public void refresh(){

    }
    public void removeProduct(ActionEvent event){}
}
