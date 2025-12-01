package org.example.databasetechnologyproject;

import atlantafx.base.theme.PrimerLight;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.application.Application;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {
    Stage stage1;
    Scene scene;
    Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button EmployeesButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    Label rowResult;
    @FXML
     TableView<Employee> customerTable;
    @FXML
     TableColumn<Employee, String> emailColumn;

    @FXML
     Button exitButton;

    @FXML
     Label filterLabel;

    @FXML
     AnchorPane filterPane;

    @FXML
     AnchorPane filterPane2;

    @FXML
     TableColumn<Employee, String> firstNameColumn;

    @FXML
     AnchorPane homePageCenter;

    @FXML
     Label homeTitle;

    @FXML
     Button insertCustomerButton;

    @FXML
     TableColumn<Employee, String> lastNameColumn;

    @FXML
     AnchorPane menuSideBar;

    @FXML
     TableColumn<Employee, Integer> numberColumn;

    @FXML
     Button openLogCustomer;

    @FXML
    TableColumn<Employee, String> posColoumn;

    @FXML
     Button resertAllButton;

    @FXML
     Label rowsLabel;

    @FXML
     Button settingsButton;
    @FXML
     Label utilityLabel;
    @FXML
            CheckBox c1;
    @FXML
    CheckBox c2;
    @FXML
    CheckBox c3;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private ComboBox<String> combo2;

    @FXML
    private ComboBox<Object> combo3;

    @FXML
    private ComboBox<String> combo4;

    @FXML
    private ComboBox<String> combo5;
    int i = 0;
    private DialogPane dialog;
    DialogPane dialog3;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;

    ObservableList<Employee> empl = FXCollections.observableArrayList();
    DiscordRichPresence rich;
    DiscordEventHandlers handlers;
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
    @Override
    public void initialize(URL l, ResourceBundle resourceBundle) {
        combo1.setValue("all");
        combo2.setValue("all");
        combo3.setValue("all");
        combo4.setValue("all");
        combo5.setValue("all");
        combo3.setDisable(true);
        combo4.setDisable(true);
        combo1.setDisable(false);
        combo2.setDisable(false);
        combo5.setDisable(true);
        c1.setSelected(true);
        c2.setSelected(false);
        c3.setSelected(false);
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
        int empid = 4;
        if (empid == 4){
            EmployeesButton.setStyle("-fx-font-size: 17px;\n" +
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
        try{
            combo1.getItems().add("all");
            String selectString = "SELECT * FROM getFnameEmp()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String phone = rs.getString(1);
                combo1.getItems().addAll(phone);
            }
        }catch (SQLException ex){

        }
        try{
            combo2.getItems().add("all");
            String selectString = "SELECT * FROM getLnameEmp()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String phone = rs.getString(1);
                combo2.getItems().addAll(phone);
            }
        }catch (SQLException ex){

        }
        try{
            combo4.getItems().add("all");
            String selectString = "SELECT * FROM getposEmp()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String phone = rs.getString(1);
                combo4.getItems().addAll(phone);
            }
        }catch (SQLException ex){

        }
        try{
            combo5.getItems().add("all");
            String selectString = "SELECT * FROM getEmailEmp()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String phone = rs.getString(1);
                combo5.getItems().addAll(phone);
            }
        }catch (SQLException ex){

        }
        try{
            combo3.getItems().add("all");
            String selectString = "SELECT * FROM getSalEmp()";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                int phone = rs.getInt(1);
                combo3.getItems().addAll(phone);
            }
        }catch (SQLException ex){

        }
        int result = 0;
        try{
            String selectString = "SELECT * FROM getEmployees()";
            String selectString2 = "SELECT COUNT(*) FROM getEmployees();";
            fillTable = dbConnection.prepareStatement(selectString);
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            fillTable.executeQuery();
            getCountCustomer.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountCustomer.getResultSet();
            while(rs.next()){
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String pos = rs.getString(4);
                int sal = rs.getInt(5);
                String email = rs.getString(6);
                Employee emp1 = new Employee(id,fname,lname,pos,sal,email);
                empl.add(emp1);
            }
            while(rs2.next()){
                result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);

        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> event) {
                Employee customer1 = event.getRowValue();
                customer1.setFirstName(event.getNewValue());
                String firstName = customer1.getFirstName();
                int id = customer1.getEmpid();
                String lastName = null;
                String pos = null;
                String email = null;
                int salary = customer1.getSalary();
                try{
                    String selectString = "SELECT updateEmployee(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, pos);
                    update.setString(5, email);
                    update.setInt(6, salary);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                EmployeeServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> event) {
                Employee customer1 = event.getRowValue();
                customer1.setLastName(event.getNewValue());
                String firstName = null;
                int id = customer1.getEmpid();
                String lastName = customer1.getLastName();
                String pos = null;
                String email = null;
                int salary = customer1.getSalary();
                try{
                    String selectString = "SELECT updateEmployee(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, pos);
                    update.setString(5, email);
                    update.setInt(6, salary);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                EmployeeServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });
        ObservableList<String> positions = FXCollections.observableArrayList(
                "Manager", "Waiter", "Chef", "Cashier", "Host", "Bartender", "Cleaner"
        );
        posColoumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        posColoumn.setCellFactory(ComboBoxTableCell.forTableColumn(positions));
        posColoumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> event) {
                Employee customer1 = event.getRowValue();
                customer1.setPosition(event.getNewValue());
                String firstName = null;
                int id = customer1.getEmpid();
                String lastName = null;
                String pos = customer1.getPosition();
                String email = null;
                int salary = customer1.getSalary();
                try{
                    String selectString = "SELECT updateEmployee(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, pos);
                    update.setString(5, email);
                    update.setInt(6, salary);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                EmployeeServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> event) {
                Employee customer1 = event.getRowValue();
                customer1.setEmail(event.getNewValue());
                String firstName = null;
                int id = customer1.getEmpid();
                String lastName = null;
                String pos = null;
                String email = customer1.getEmail();
                int salary = customer1.getSalary();
                try{
                    String selectString = "SELECT updateEmployee(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, pos);
                    update.setString(5, email);
                    update.setInt(6, salary);
                    update.executeQuery();

                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                EmployeeServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        numberColumn.setCellValueFactory(cellData -> {
            Employee customer = cellData.getValue();
            return new SimpleIntegerProperty(customer.getSalary()).asObject();
        });
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, Integer> event) {
                Employee customer1 = event.getRowValue();
                customer1.setSalary(event.getNewValue());
                String firstName = null;
                int id = customer1.getEmpid();
                String lastName = null;
                String pos = null;
                String email = null;
                int salary = customer1.getSalary();
                try{
                    String selectString = "SELECT updateEmployee(?,?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, lastName);
                    update.setString(4, pos);
                    update.setString(5, email);
                    update.setInt(6, salary);
                    update.executeQuery();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                EmployeeServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });


        customerTable.setItems(empl);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        posColoumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("salary"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        customerTable.setItems(empl);
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
    public void guide(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("guideScreen.fxml"));
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
    public void switchToCustomerScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("menuScreen.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("ordersScreen.fxml"));
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
    public void refresh(){
        int result = 0;
        try{
            empl.clear();
            String selectString = "SELECT * FROM getEmployees()";
            String selectString2 = "SELECT COUNT(*) FROM getEmployees();";
            fillTable = dbConnection.prepareStatement(selectString);
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            fillTable.executeQuery();
            getCountCustomer.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountCustomer.getResultSet();
            while(rs.next()){
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String pos = rs.getString(4);
                int sal = rs.getInt(5);
                String email = rs.getString(6);
                Employee emp1 = new Employee(id,fname,lname,pos,sal,email);
                empl.add(emp1);
            }
            while(rs2.next()){
                result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException es){

        }
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void showNotification2(){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        Label label = new Label("✔Operation Successful, Employee Deleted");
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
    public void deteleEmployee(ActionEvent event){
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog3 = alert3.getDialogPane();
        alert3.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        Image icon = new Image("logos.png");
        dialog3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alert3.setTitle("Delete Employee");
        dialog3.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete a Employee");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog3.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
            Employee emp1 = customerTable.getSelectionModel().getSelectedItem();
            try{
                int id = emp1.getEmpid();
                String selectString = "SELECT deleteEmployee(?)";
                delete = dbConnection.prepareStatement(selectString);
                delete.setInt(1,id);
                delete.executeQuery();
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Database error: " + ex.getMessage());
            }
            TableView.TableViewSelectionModel<Employee> selectionModel = customerTable.getSelectionModel();
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
    public void openEmployeeInsertForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeInsertForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            EmployeeInsertController employeeInsertcontroller = fxmlLoader.getController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            employeeInsertcontroller.setStage(currentStage);
            employeeInsertcontroller.setMainController(this);
            employeeInsertcontroller.setTableView(customerTable);
            employeeInsertcontroller.setFirstNameColumn(firstNameColumn);
            employeeInsertcontroller.setLastNameColumn(lastNameColumn);
            employeeInsertcontroller.setHomeAddressColoumn(posColoumn);
            employeeInsertcontroller.setNumberColumn(emailColumn);
            employeeInsertcontroller.setRatingColumn(numberColumn);

        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
    public void openEmployeeAudit(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeAudit.fxml"));
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
    public void check(ActionEvent event){
        if(c1.isSelected()){
            combo3.setDisable(true);
            combo4.setDisable( true);
            combo1.setDisable(false);
            combo2.setDisable(false);
            combo5.setDisable(true);
        }
        if(c2.isSelected()){
            combo3.setDisable(false);
            combo4.setDisable(false);
            combo1.setDisable(true);
            combo2.setDisable(true);
            combo5.setDisable(true);
        }
        if(c3.isSelected()){
            combo3.setDisable(true);
            combo4.setDisable(true);
            combo1.setDisable(true);
            combo2.setDisable(true);
            combo5.setDisable(false);
        }
        if(c1.isSelected() && c2.isSelected()){
            combo3.setDisable(false);
            combo4.setDisable(false);
            combo1.setDisable(false);
            combo2.setDisable(false);
            combo5.setDisable(true);
        }
        if(c1.isSelected() && c3.isSelected()){
            combo3.setDisable(true);
            combo4.setDisable(true);
            combo1.setDisable(false);
            combo2.setDisable(false);
            combo5.setDisable(false);
        }
        if(c2.isSelected() && c3.isSelected()){
            combo3.setDisable(false);
            combo4.setDisable(false);
            combo1.setDisable(true);
            combo2.setDisable(true);
            combo5.setDisable(false);
        }
        if(c1.isSelected() && c2.isSelected() && c3.isSelected()){
            combo3.setDisable(false);
            combo4.setDisable(false);
            combo1.setDisable(false);
            combo2.setDisable(false);
            combo5.setDisable(false);
        }
        if(!c1.isSelected() && !c2.isSelected() && !c3.isSelected()){
            combo3.setDisable(true);
            combo4.setDisable(true);
            combo1.setDisable(true);
            combo2.setDisable(true);
            combo5.setDisable(true);
        }
    }
    public void executeAllFilters(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            String sel1 = combo1.getSelectionModel().getSelectedItem();
            String sel2 = combo2.getSelectionModel().getSelectedItem();
            Object selected = combo3.getSelectionModel().getSelectedItem();
            String selected2 = combo4.getSelectionModel().getSelectedItem();
            String selected3 = combo5.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM allTheFilters(?,?,?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM allTheFilters(?,?,?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            if (selected instanceof Integer) {
                getFname.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(1, Types.INTEGER);
            } else {
                getFname.setNull(1, Types.INTEGER);
            }
            getFname.setString(2, selected2);
            getFname.setString(3, selected3);
            getFname.setString(4, sel1);
            getFname.setString(5, sel2);

            if (selected instanceof Integer) {
                getCountCustomer.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getCountCustomer.setNull(1, Types.INTEGER);
            } else {
                getCountCustomer.setNull(1, Types.INTEGER);
            }
            getCountCustomer.setString(2, selected2);
            getCountCustomer.setString(3, selected3);
            getCountCustomer.setString(4, sel1);
            getCountCustomer.setString(5, sel2);

            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void executeNameAndPositionFilter(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            String selected = combo1.getSelectionModel().getSelectedItem();
            String selected2 = combo2.getSelectionModel().getSelectedItem();
            Object selected3 = combo3.getSelectionModel().getSelectedItem();
            String selected4 = combo4.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByNameAndPosition(?,?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByNameAndPosition(?,?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected);
            getFname.setString(2, selected2);
            if (selected3 instanceof Integer) {
                getFname.setInt(3, (Integer) selected3);
            } else if ("all".equals(selected3)) {
                getFname.setNull(3, Types.INTEGER);
            } else {
                getFname.setNull(3, Types.INTEGER);
            }
            getFname.setString(4, selected4);

            getCountCustomer.setString(1, selected);
            getCountCustomer.setString(2, selected2);
            if (selected3 instanceof Integer) {
                getCountCustomer.setInt(3, (Integer) selected3);
            } else if ("all".equals(selected3)) {
                getCountCustomer.setNull(3, Types.INTEGER);
            } else {
                getCountCustomer.setNull(3, Types.INTEGER);
            }
            getCountCustomer.setString(4, selected4);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void executeNameAndEmailFilter(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            String selected = combo1.getSelectionModel().getSelectedItem();
            String selected2 = combo2.getSelectionModel().getSelectedItem();
            String selected3 = combo5.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByNameAndMail(?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByNameAndMail(?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected);
            getFname.setString(2, selected2);
            getFname.setString(3, selected3);

            getCountCustomer.setString(1, selected);
            getCountCustomer.setString(2, selected2);
            getCountCustomer.setString(3, selected3);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void executePositionAndEmailFilter(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            Object selected = combo3.getSelectionModel().getSelectedItem();
            String selected2 = combo4.getSelectionModel().getSelectedItem();
            String selected3 = combo5.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByPositonAndMail(?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByPositonAndMail(?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            if (selected instanceof Integer) {
                getFname.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(1, Types.INTEGER);
            } else {
                getFname.setNull(1, Types.INTEGER);
            }

            if (selected instanceof Integer) {
                getCountCustomer.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getCountCustomer.setNull(1, Types.INTEGER);
            } else {
                getCountCustomer.setNull(1, Types.INTEGER);
            }
            getFname.setString(2, selected2);
            getFname.setString(3, selected3);

            getCountCustomer.setString(2, selected2);
            getCountCustomer.setString(3, selected3);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public void executeNameFilter(){
        int result = 0;
        try{
            String firstName;
            String lastname;
            String postion;
            String email;
            int salary;
            int id ;
            empl.clear();
            String selected = combo1.getSelectionModel().getSelectedItem();
            String selected2 = combo2.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByEmpName(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByEmpName(?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected);
            getFname.setString(2, selected2);
            getCountCustomer.setString(1,selected);
            getCountCustomer.setString(2,selected2);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void executePositionFilter(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            Object selected = combo3.getSelectionModel().getSelectedItem();
            String selected2 = combo4.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByPosition(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByPosition(?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            if (selected instanceof Integer) {
                getFname.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(1, Types.INTEGER);
            } else {
                getFname.setNull(1, Types.INTEGER);
            }
            getFname.setString(2, selected2);

            if (selected instanceof Integer) {
                getCountCustomer.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getCountCustomer.setNull(1, Types.INTEGER);
            } else {
                getCountCustomer.setNull(1, Types.INTEGER);
            }
            getCountCustomer.setString(2, selected2);

            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                 result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }
    public void executeEmailFilter(){
        int result = 0;
        try{
            String firstName = null;
            String lastname = null;
            String postion = null;
            String email = null;
            int salary = 0;
            int id = 0;
            empl.clear();
            String selected2 = combo5.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByEmail(?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByEmail(?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected2);

            getCountCustomer = dbConnection.prepareStatement(selectString);
            getCountCustomer.setString(1, selected2);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getString(2);
                lastname = rs.getString(3);
                postion = rs.getString(4);
                email = rs.getString(6);
                salary = rs.getInt(5);
                Employee emp = new Employee(id,firstName,lastname,postion,salary,email);
                empl.add(emp);
            }
            while(rs2.next()){
                result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("1421645118569451541",handlers,true);
        rich = new DiscordRichPresence.Builder("Rows Returned: " + result).setDetails("Viewing Employees").build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public void select2(ActionEvent event){
        boolean c1sel = c1.isSelected();
        boolean c2sel = c2.isSelected();
        boolean c3sel = c3.isSelected();
        if (c1sel && c2sel && c3sel) {
            executeAllFilters();
        } else if (c1sel && c2sel) {
            executeNameAndPositionFilter();
        } else if (c1sel && c3sel) {
            executeNameAndEmailFilter();
        } else if (c2sel && c3sel) {
            executePositionAndEmailFilter();
        } else if (c1sel) {
            executeNameFilter();
        } else if (c2sel) {
            executePositionFilter();
        } else if (c3sel) {
            executeEmailFilter();
        } else {
            return;
        }
    }
    public void resetAll(ActionEvent evet){
        combo1.setValue("all");
        combo2.setValue("all");
        combo3.setValue("all");
        combo4.setValue("all");
        combo5.setValue("all");
        combo3.setDisable(true);
        combo4.setDisable(true);
        combo1.setDisable(false);
        combo2.setDisable(false);
        combo5.setDisable(true);
        c1.setSelected(true);
        c2.setSelected(false);
        c3.setSelected(false);
        refresh();
    }
}
