package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class OrdersCreateController implements Initializable {
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
    @Override
    public void initialize(URL f, ResourceBundle resourceBundle) {
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
            String selectString = "SELECT * FROM getTableNumbers()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                combo3.getItems().add(num);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        try{
            String selectString = "SELECT * FROM getEmployeesIdAndNames()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                String name =  rs.getString(2);
                combo2.getItems().add(num + name);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        try{
            combo4.getItems().add("all");
            String selectString = "SELECT * FROM getItemCategory()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                String num = rs.getString(1);
                combo4.getItems().add(num);

            }
        } catch (SQLException ex){

        }
        if(combo4.getValue().equals("all")){
            functionSelectAll();
        }
        else if(combo4.getValue().equals("Main") || combo4.getValue().equals("Side") || combo4.getValue().equals("Desert") || combo4.getValue().equals("Appetizer")){
            String c = combo4.getValue();
            getItemsByCategory(c);
        }

    }
    public void functionSelectAll(){
        try{
            String selectString = "SELECT * FROM getMenuItemNames()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                String num = rs.getString(1);
                combo5.getItems().add(num);
            }
        } catch (SQLException ex){

        }
    }
    public void getItemsByCategory(String c){
        try{
            String selectString = "SELECT * FROM filterItemsByCategory(?)";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, c);
            ResultSet rs = getFname.executeQuery();
            while(rs.next()){
                String num = rs.getString(1);
                combo5.getItems().add(num);
            }
        } catch (SQLException ex){

        }
    }
    public void Select(ActionEvent event){
        if(combo4.getValue().equals("all")){
            functionSelectAll();
        }
        else if(combo4.getValue().equals("Main") || combo4.getValue().equals("Side") || combo4.getValue().equals("Desert") || combo4.getValue().equals("Appetizer")){
            String c = combo4.getValue();
            getItemsByCategory(c);
        }
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
        if(textField1.getText().isEmpty()){
            //firstNameInputError();
        }
        if(textField2.getText().isEmpty()){
            //lastNameInputError();
        }
        if(textField5.getText().isEmpty()){
           // emailInputError();
        }
        try{
            int id =0;
            String employee = combo2.getValue();
            int table = combo3.getValue();
            int qu = spin.getValue();
            Timestamp date = Timestamp.valueOf(java.time.LocalDateTime.now());
            float totalprice = 0;
            String selectString = "SELECT insertOrder(?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);

            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }

            String selectString2 = "SELECT insertOrderItem(?,?,?)";
            insert = dbConnection.prepareStatement(selectString2);
            insert.setInt(1,id);
            insert.setInt(2, );
            insert.setInt(3, qu);
            ResultSet rs2 = insert.executeQuery();
            while(rs2.next()){

            }
        } catch (SQLException ex){

        }

    }
    public void showNotification(){
        Stage toastStage = new Stage();
        toastStage.initOwner(scene1);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("Operation Successful, Order Inserted ✔");
        label.setStyle("-fx-font-size: 17px;\n" +
                "-fx-text-fill: #001D00;\n" +
                "-fx-background-color: #dcfce7;\n" +
                "-fx-background-radius: 10px;\n" +
                "-fx-border-color: #22c55e;\n" +
                "-fx-border-width: 2px;\n" +
                "-fx-border-radius: 10px;\n" +
                "-fx-text-alignment: center;\n" +
                "-fx-padding: 10px 15px;");

        Scene scene = new Scene(label);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.setWidth(370);
        toastStage.setHeight(60);
        toastStage.setX(scene1.getX() + scene1.getWidth() - 400);
        toastStage.setY(scene1.getY() + scene1.getHeight() - 80);

        toastStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
    }
}
