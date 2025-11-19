package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    PreparedStatement insert;
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
        int id = 0;
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        int homeAddress = textField3.getValue();
        Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
        float number = 0;
        System.out.println("SQL Timestamp: " + sqlTimestamp);
        int rating = 0;
        try{
            try{
                rating = Integer.parseInt(textField5.getText().trim());
            } catch (NumberFormatException e){
                return;
            }
            String selectString = "SELECT insertOrder(?,?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setInt(3, homeAddress);
            insert.setFloat(4, number);
            insert.setInt(5, rating);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("SqlException");
        }
        Order or2 = new Order(id,firstName,lastName,homeAddress,number,sqlTimestamp);
        if(customerTable.getItems() == null){
            customerTable.setItems(FXCollections.observableArrayList());
        }
        customerTable.getItems().add(or2);
        textField1.setText("");
        textField2.setText("");
        textField3.setValue(0);
        textField5.setText("");
        customerServiceClass.getInstance().triggerRefresh();
        showNotification();
        mainController.refresh();
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
