package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.io.IO;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    Stage stage1;
    Scene scene;
    Parent root;
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
    TableColumn<Customer, Integer> ratingColumn;
    @FXML
    ComboBox<String> textfield;

    @FXML
    ComboBox<String> textfield2;

    @FXML
    ComboBox<String> textfield3;

    @FXML
    ComboBox<Object> textfield4;
    @FXML
    Button resetAllButton;
    @FXML
    Label rowResult;



    ObservableList<Customer> customers = FXCollections.observableArrayList();
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

    int i = 0;
    private DialogPane dialog;
    DialogPane dialog3;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        textfield.setValue("all");
        textfield2.setValue("all");
        textfield3.setValue("all");
        textfield4.setValue("all");


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
        } catch (Exception ex) {
            System.out.println("Image not found");
        }
        //Γεμισμα των Combobox
        try{
            textfield.getItems().add("all");
            String selectString = "SELECT * FROM getFn()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                String firstname = rs.getString("getfn");
                textfield.getItems().addAll(firstname);
            }
        } catch (SQLException ex){

        }
        try{
            textfield2.getItems().add("all");
            String selectString = "SELECT * FROM getLn()";
            getLastName = dbConnection.prepareStatement(selectString);
            getLastName.executeQuery();
            ResultSet rs = getLastName.getResultSet();
            while(rs.next()){
                String lastname = rs.getString("getln");
                textfield2.getItems().addAll(lastname);
            }
        } catch (SQLException ex){

        }
        try{
            textfield3.getItems().add("all");
            String selectString = "SELECT * FROM getP()";
            getPhone = dbConnection.prepareStatement(selectString);
            getPhone.executeQuery();
            ResultSet rs = getPhone.getResultSet();
            while(rs.next()){
                String phone = rs.getString("getp");
                textfield3.getItems().addAll(phone);
            }
        } catch (SQLException ex){

        }
        try{
            textfield4.getItems().add("all");
            String selectString = "SELECT * FROM getR();";
            getRating = dbConnection.prepareStatement(selectString);
            getRating.executeQuery();
            ResultSet rs = getRating.getResultSet();
            while(rs.next()){
                int rating = rs.getInt("getr");
                textfield4.getItems().addAll(rating);
            }
        } catch (SQLException ex){

        }
        try{
            String selectString = "SELECT * FROM getTable()";
            String selectString2 = "SELECT COUNT(*) FROM getTable()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String homeAddress = rs.getString(4);
                String phone = rs.getString(5);
                int rating = rs.getInt(6);
                Customer customer1 = new Customer(id,firstName,lastName,homeAddress,phone,rating);
                customers.add(customer1);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }

        customerTable.setOnMouseClicked(event ->{
            if(event.getClickCount()==1 || event.getClickCount()==2){
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            }
        });

        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Customer, String>>() {
            @Override
            public void handle(CellEditEvent<Customer, String> event) {
                Customer customer1 = event.getRowValue();
                customer1.setFirstName(event.getNewValue());
                String firstName = customer1.getFirstName();
                int id = customer1.getId();
                String lastName = null;
                String homeAddress = null;
                String phone = null;
                int rating = customer1.getRating();
                try{
                    String selectString = "SELECT updateCustomer(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, homeAddress);
                    update.setString(5, phone);
                    update.setInt(6, rating);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Customer, String>>() {
            @Override
            public void handle(CellEditEvent<Customer, String> event) {
                Customer customer1 = event.getRowValue();
                customer1.setLastName(event.getNewValue());
                String firstName = null;
                int id = customer1.getId();
                String lastName = customer1.getLastName();
                String homeAddress = null;
                String phone = null;
                int rating = customer1.getRating();
                try{
                    String selectString = "SELECT updateCustomer(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, homeAddress);
                    update.setString(5, phone);
                    update.setInt(6, rating);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        homeAddressColoumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        homeAddressColoumn.setCellFactory(TextFieldTableCell.forTableColumn());
        homeAddressColoumn.setOnEditCommit(new EventHandler<CellEditEvent<Customer, String>>() {
            @Override
            public void handle(CellEditEvent<Customer, String> event) {
                Customer customer1 = event.getRowValue();
                customer1.setHomeAddress(event.getNewValue());
                String firstName = null;
                int id = customer1.getId();
                String lastName = null;
                String homeAddress = customer1.getHomeAddress();
                String phone = null;
                int rating = customer1.getRating();
                try{
                    String selectString = "SELECT updateCustomer(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, homeAddress);
                    update.setString(5, phone);
                    update.setInt(6, rating);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberColumn.setOnEditCommit(new EventHandler<CellEditEvent<Customer, String>>() {
            @Override
            public void handle(CellEditEvent<Customer, String> event) {
                Customer customer1 = event.getRowValue();
                customer1.setNumber(event.getNewValue());
                String firstName = null;
                int id = customer1.getId();
                String lastName = null;
                String homeAddress = null;
                String phone = customer1.getNumber();
                int rating = customer1.getRating();
                try{
                    String selectString = "SELECT updateCustomer(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, homeAddress);
                    update.setString(5, phone);
                    update.setInt(6, rating);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        ratingColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            return new SimpleIntegerProperty(customer.getRating()).asObject();
        });
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ratingColumn.setOnEditCommit(new EventHandler<CellEditEvent<Customer, Integer>>() {
            @Override
            public void handle(CellEditEvent<Customer, Integer> event) {
                Customer customer1 = event.getRowValue();
                customer1.setRating(event.getNewValue());
                String firstName = null;
                int id = customer1.getId();
                String lastName = null;
                String homeAddress = null;
                String phone = null;
                int rating = customer1.getRating();
                try{
                    String selectString = "SELECT updateCustomer(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, homeAddress);
                    update.setString(5, phone);
                    update.setInt(6, rating);
                    update.executeQuery();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });
        customerTable.setItems(customers);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        homeAddressColoumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("homeAddress"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("number"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("rating"));
        customerTable.setItems(customers);

    }



    public void showNotification(int ind){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("✔Operation Successful, Customer Updated at Row: " + ind);
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
        toastStage.setWidth(450);
        toastStage.setHeight(60);
        toastStage.setX(currentStage.getX() + currentStage.getWidth() - 470);
        toastStage.setY(currentStage.getY() + currentStage.getHeight() - 80);

        toastStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
    }

    public void showNotification2(){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        Label label = new Label("✔Operation Successful, Customer Deleted");
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
            stage1 = (Stage) scene1.getScene().getWindow();
            System.out.println("You exited the app");
            stage1.close();
        }
    }
    public void switchToHomeScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToReservationsScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("reservationsScene.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
        }
    }
    public void switchToEmployeesScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("employeesScreen.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToMenuScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("employeesScreen.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToTableScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tablesScene.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToOrdersScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tablesScene.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToAboutScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("aboutScreen.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            customerinsetcontroller.setStage(currentStage);
            customerinsetcontroller.setMainController(this);
            customerinsetcontroller.setTableView(customerTable);
            customerinsetcontroller.setFirstNameColumn(firstNameColumn);
            customerinsetcontroller.setLastNameColumn(lastNameColumn);
            customerinsetcontroller.setHomeAddressColoumn(homeAddressColoumn);
            customerinsetcontroller.setNumberColumn(numberColumn);
            customerinsetcontroller.setRatingColumn(ratingColumn);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
    public void openCustomerAudit(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customerAudit.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Log File");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            refresh();
        } catch (IOException ex){
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
                int rating = rs.getInt(6);
                Customer customer1 = new Customer(id,firstName,lastName,homeAddress,phone,rating);
                customers.add(customer1);
            }
        } catch (SQLException es){

        }
    }

    public void deleteCustomer(ActionEvent event){
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog3 = alert3.getDialogPane();
        alert3.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        Image icon = new Image("logos.png");
        dialog3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alert3.setTitle("Delete Customer");
        dialog3.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete a customer");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog3.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
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
            customerServiceClass.getInstance().triggerRefresh();
            refresh();
            showNotification2();
        }
        else{
            return;
        }
    }

    public void select(ActionEvent event){
        try{
            String firstName = null;
            String lastname = null;
            String address = null;
            String phone = null;
            int rating = 0;
            int id = 0;
            customers.clear();
            String selected = textfield.getSelectionModel().getSelectedItem();
            String selected2 = textfield2.getSelectionModel().getSelectedItem();
            String selected3 = textfield3.getSelectionModel().getSelectedItem();
            Object selected4 = textfield4.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterCustomer(?,?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterCustomer(?,?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected);
            getFname.setString(2, selected2);
            getFname.setString(3, selected3);

            getCountCustomer.setString(1,selected);
            getCountCustomer.setString(2, selected2);
            getCountCustomer.setString(3, selected3);

            if (selected4 instanceof Integer) {
                getFname.setInt(4, (Integer) selected4);
            } else if ("Any Rating".equals(selected4)) {
                getFname.setNull(4, Types.INTEGER);
            } else {
                getFname.setNull(4, Types.INTEGER);
            }

            if (selected4 instanceof Integer) {
                getCountCustomer.setInt(4, (Integer) selected4);
            } else if ("Any Rating".equals(selected4)) {
                getCountCustomer.setNull(4, Types.INTEGER);
            } else {
                getCountCustomer.setNull(4, Types.INTEGER);
            }
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                address = rs.getString(4);
                phone = rs.getString(5);
                rating = rs.getInt(6);
                Customer customer1 = new Customer(id,firstName,lastname,address,phone,rating);
                customers.add(customer1);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.getMessage();
        }
    }
    public void resetAll(ActionEvent evet){
        textfield.setValue("all");
        textfield2.setValue("all");
        textfield3.setValue("all");
        textfield4.setValue("all");
        refresh();
    }
}
