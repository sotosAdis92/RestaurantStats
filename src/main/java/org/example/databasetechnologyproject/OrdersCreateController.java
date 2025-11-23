package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
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
    int idd = 0;
    ObservableList<OrderItem> customers = FXCollections.observableArrayList();
    OrderItem item;
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
            String selectString = "SELECT * FROM getTableIdAndNumber()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(2);
                combo3.getItems().add(num);
            }
            if (!combo3.getItems().isEmpty()) {
                combo3.setValue(combo3.getItems().get(0));
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
                combo2.getItems().add(num + ": " + name);
            }
            if (!combo2.getItems().isEmpty()) {
                combo2.setValue(combo2.getItems().get(0));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
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
        try{
            int table = 0;
            String employee = combo2.getValue();
            String id = employee.replaceAll("\\D.*", "");
            int employeeId = Integer.parseInt(id);
            String selectString1 = "SELECT * FROM getTableIdAndNumber(?)";
            getFirstName = dbConnection.prepareStatement(selectString1);
            getFirstName.setInt(1, combo3.getValue());
            getFirstName.executeQuery();
            ResultSet rs2 = getFirstName.getResultSet();
            while(rs2.next()){
                table = rs2.getInt(1);
                System.out.println("table id: "+table);
            }
            Timestamp date = Timestamp.valueOf(java.time.LocalDateTime.now());
            float totalprice = 0;
            String selectString = "SELECT insertOrder(?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, employeeId);
            insert.setInt(2, table);
            insert.setFloat(3, totalprice);
            insert.setTimestamp(4, date);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                idd = rs.getInt(1);
            }
            System.out.println("1 " + idd);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("insertOrderStep2.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                inserOrderPart2 controller = fxmlLoader.getController();
                controller.setOrderId(idd);

                Stage stage = new Stage();
                stage.setTitle("Insert Form");
                Image icon = new Image("logos.png");
                stage.getIcons().add(icon);
                stage.setScene(new Scene(root));
                stage.show();
                controller.setStage(stage);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                controller.setStage(currentStage);
                controller.setMainController(mainController);
                controller.setTableView(mainController.customerTable);
                controller.setFirstNameColumn(mainController.on);
                controller.setHomeAddressColoumn(mainController.emp);
                controller.setNumberColumn(mainController.ta);
                controller.setRatingColumn(mainController.amm);
                controller.setNew(mainController.da);
                System.out.println("2 "+idd);
            } catch (IOException ex) {
                System.out.println("This window could not load");
                ex.printStackTrace();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
