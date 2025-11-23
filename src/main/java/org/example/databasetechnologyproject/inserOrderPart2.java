package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class inserOrderPart2 implements Initializable {
    DialogPane dialog3;
    int ordId;
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
    @FXML
            Label priceTotal;
    @FXML
            Label tableLabel;
    @FXML
            Label employeeName;
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
    float total = 0;
    ObservableList<OrderItem> customers = FXCollections.observableArrayList();
    public void setOrderId(int ordId){
        this.ordId = ordId;
        loadOrderDetails();
    }
    private void loadOrderDetails() {
        try{
            String selectString = "SELECT * FROM getEmpTablPrice(?)";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setInt(1, ordId);
            System.out.println("3 "+ordId);
            ResultSet rs = getFname.executeQuery();
            if(rs.next()){
                String name = rs.getString(1);
                int table = rs.getInt(2);
                float price = rs.getFloat(3);
                priceTotal.setText(String.valueOf(price));
                employeeName.setText(name);
                tableLabel.setText(String.valueOf(table));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
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
        try{
            String selectString = "SELECT * FROM getEmpTablPrice(?)";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setInt(1, ordId);
            System.out.println("3 "+ordId);
            ResultSet rs = getFname.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                int table = rs.getInt(2);
                float price = rs.getFloat(3);
                priceTotal.setText(String.valueOf(price));
                employeeName.setText(name);
                tableLabel.setText(String.valueOf(table));
            }
        } catch (SQLException ex){

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
    public void create(ActionEvent event){
        int id=0;
        String name = null;
        int t = 0;
        float total = 0;
        Timestamp time = null;
        try{
            String selectString = "SELECT * FROM getTotalPriceOrderItems(?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, ordId);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                total = rs.getFloat(1);
            }
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM updateOrder(?,?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, ordId);
            insert.setFloat(2, total);
            ResultSet rs = insert.executeQuery();
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM getOrderById(?)";
            insert = dbConnection.prepareStatement(selectString);
            insert.setInt(1, ordId);
            ResultSet rs = insert.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                name = rs.getString(2);
                t = rs.getInt(3);
                total = rs.getFloat(4);
                time = rs.getTimestamp(5);
            }
        } catch (SQLException ex){

        }
        Order od = new Order(id,name,t,total,time);
        customerTable.getItems().add(od);
        showNotification();
        mainController.refresh();
    }
    public void cancel(ActionEvent event){

    }
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
            System.out.println(prices);
            customers.add(od);
        } catch (SQLException ex){

        }
        try{
            String selectString2 = "SELECT insertOderItem(?,?,?,?)";
            insert = dbConnection.prepareStatement(selectString2);
            insert.setInt(1, ordId);
            insert.setInt(2, id);
            insert.setInt(3, qua);
            insert.setFloat(4, prices);
            ResultSet rs = insert.executeQuery();
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM getEmpTablPrice(?)";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setInt(1, ordId);
            System.out.println("3 "+ordId);
            ResultSet rs = getFname.executeQuery();
            while(rs.next()){
                String namess = rs.getString(1);
                int table = rs.getInt(2);
                float price = rs.getFloat(3);
                priceTotal.setText(String.valueOf(price));
                employeeName.setText(namess);
                tableLabel.setText(String.valueOf(table));
            }
        } catch (SQLException ex){

        }
    }
    public void refresh(){
        try{
            String selectString = "SELECT * FROM getEmpTablPrice(?)";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setInt(1, ordId);
            System.out.println("3 "+ordId);
            ResultSet rs = getFname.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                int table = rs.getInt(2);
                float price = rs.getFloat(3);
                priceTotal.setText(String.valueOf(price));
                employeeName.setText(name);
                tableLabel.setText(String.valueOf(table));
            }
        } catch (SQLException ex){

        }
    }
    public void removeProduct(ActionEvent event){
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog3 = alert3.getDialogPane();
        alert3.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        Image icon = new Image("logos.png");
        dialog3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alert3.setTitle("Delete Order Item");
        dialog3.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete a Order Item");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog3.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
            OrderItem selectedItem = table1.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                try{
                    int id = selectedItem.getOrderitemid();
                    System.out.println("id: "+id);
                    String selectString = "SELECT deleteOrderItem(?)";
                    delete = dbConnection.prepareStatement(selectString);
                    delete.setInt(1, id);
                    delete.executeQuery();
                } catch (SQLException exception){
                    exception.printStackTrace();
                    System.out.println("Database error: " + exception.getMessage());
                }
            }
            TableView.TableViewSelectionModel<OrderItem> selectionModel = table1.getSelectionModel();
            if(selectionModel.isEmpty()){
                System.out.println("Table is empty");
            }
            ObservableList<Integer> list = selectionModel.getSelectedIndices();
            Integer[] selectedItems = new Integer[list.size()];
            selectedItems = list.toArray(selectedItems);
            Arrays.sort(selectedItems);
            for(int i=selectedItems.length - 1;i>=0;i--){
                selectionModel.clearSelection(selectedItems[i].intValue());
                table1.getItems().remove(selectedItems[i].intValue());
            }
            customerServiceClass.getInstance().triggerRefresh();
            refresh();
            showNotification2();
        }
        else{
            return;
        }
    }
    public void showNotification2(){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        Label label = new Label("✔Operation Successful, Order Item Deleted");
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
        toastStage.setX(currentStage.getX() + currentStage.getWidth() - 400);
        toastStage.setY(currentStage.getY() + currentStage.getHeight() - 80);
        toastStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
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
