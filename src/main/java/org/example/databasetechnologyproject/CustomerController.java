package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IO;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button CustomersButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    TableView<Customer> customerTable;
    @FXML
    TableColumn<Customer, String> firstNameColumn;
    @FXML
    TableColumn<Customer, String> lastNameColumn;
    @FXML
    TableColumn<Customer, String> homeAddressColoumn;
    @FXML
    TableColumn<Customer, String> numberColumn;
    @FXML
    TableColumn<Customer, String> emailColumn;


    ObservableList<Customer> customers = FXCollections.observableArrayList();
    PreparedStatement fillTable;
    PreparedStatement delete;

    private DialogPane dialog;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
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







        int customerScreenId = 2;
        if (customerScreenId == 2){
            CustomersButton.setStyle("-fx-font-size: 17px;\n" +
                    "    -fx-font-family: \"Lexend Giga\";\n" +
                    "    -fx-background-color: #5CABF5;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-alignment: CENTER_LEFT;");
        }
        try{
            Image image = new Image(getClass().getResourceAsStream("g.png"));
            ImageView imageview = new ImageView(image);
            tooltipexit.setGraphic(imageview);
        } catch (Exception ex){
            System.out.println("Image not found");
        }
        try{
            String selectString = "SELECT * FROM getTable()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String homeAddress = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);
                Customer customer1 = new Customer(id,firstName,lastName,homeAddress,phone,email);
                customers.add(customer1);
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }
        customerTable.setItems(customers);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        homeAddressColoumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("homeAddress"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("number"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        customerTable.setItems(customers);

    }
    public void exitButton(ActionEvent event){
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog = alert2.getDialogPane();
        alert2.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        dialog.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Image icon = new Image("logos.png");
        dialog.getStyleClass().add("dialog2");
        alert2.setTitle("Exit Application");
        alert2.setHeaderText("You are About to Exit The Application");
        alert2.setContentText("Are you sure you wish to exit the Application?");
        Stage alertstage = (Stage) dialog.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert2.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scene1.getScene().getWindow();
            System.out.println("You exited the app");
            stage.close();
        }
    }
    public void switchToHomeScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToReservationsScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("reservationsScene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToEmployeesScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("employeesScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToMenuScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("employeesScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToTableScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tablesScene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToOrdersScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tablesScene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToAboutScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("aboutScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void openCustomerInsertForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customerInsertFormWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            CustomerInsertController customerinsetcontroller = fxmlLoader.getController();
            customerinsetcontroller.setMainController(this);
            customerinsetcontroller.setTableView(customerTable);
            customerinsetcontroller.setFirstNameColumn(firstNameColumn);
            customerinsetcontroller.setLastNameColumn(lastNameColumn);
            customerinsetcontroller.setHomeAddressColoumn(homeAddressColoumn);
            customerinsetcontroller.setNumberColumn(numberColumn);
            customerinsetcontroller.setEmailColumn(emailColumn);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
    public void refresh(){
        try{
            customers.clear();
            String selectString = "SELECT * FROM getTable()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String homeAddress = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);
                Customer customer1 = new Customer(id,firstName,lastName,homeAddress,phone,email);
                customers.add(customer1);
            }
        } catch (SQLException es){

        }
    }
    public void deleteCustomer(ActionEvent event){
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer!=null){
            try{
                int id = selectedCustomer.getId();
                String selectString = "SELECT deleteCustomer(?)";
                delete = dbConnection.prepareStatement(selectString);
                delete.setInt(1, id);
                delete.executeQuery();
            } catch (SQLException exception){
                exception.printStackTrace();
                System.out.println("Database error: " + exception.getMessage());
            }
        }
        TableView.TableViewSelectionModel<Customer> selectionModel = customerTable.getSelectionModel();
        if(selectionModel.isEmpty()){
            System.out.println("Table is empty");
        }
        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        Integer[] selectedItems = new Integer[list.size()];
        selectedItems = list.toArray(selectedItems);
        Arrays.sort(selectedItems);
        for(int i=selectedItems.length - 1;i>=0;i--){
            selectionModel.clearSelection(selectedItems[i].intValue());
            customerTable.getItems().remove(selectedItems[i].intValue());
        }
    }
}
