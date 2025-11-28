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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    Stage stage1;
    Scene scene;
    Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button OrdersButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    TableView<Order> customerTable;
    @FXML
    TableColumn<Order, Integer> on;
    @FXML
    TableColumn<Order, String> emp;
    @FXML
    TableColumn<Order, Integer> ta;
    @FXML
    TableColumn<Order, Float> amm;
    @FXML
    TableColumn<Order, Timestamp> da;
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
    @FXML
            CheckBox c1;
    @FXML
            CheckBox c2;
    @FXML
            ComboBox<Object> combo1;
    @FXML
     DatePicker date1;

    @FXML
     DatePicker date2;
    ObservableList<Order> orders = FXCollections.observableArrayList();
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
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        date1.setValue(LocalDate.now());
        date2.setValue(LocalDate.now());
        c1.setSelected(true);
        c2.setSelected(false);
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully");
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            ex.printStackTrace();
        }


        int orderScreenId = 6;
        if (orderScreenId == 6){
            OrdersButton.setStyle("-fx-font-size: 17px;\n" +
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
            String selectString = "SELECT * FROM getOrdersTable()";
            String selectString2 = "SELECT COUNT(*) FROM getOrdersTable()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String empid = rs.getString(2);
                int taid = rs.getInt(3);
                float amm = rs.getFloat(4);
                Timestamp da = rs.getTimestamp(5);
                Order or1 = new Order(id,empid,taid,amm,da);
                orders.add(or1);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }
        try{
            combo1.getItems().add("all");
            String selectString = "SELECT * FROM getTableNumberFromOrders()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                Object num = rs.getObject(1);
                combo1.getItems().add(num);

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        customerTable.setItems(orders);

        on.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderid"));
        emp.setCellValueFactory(new PropertyValueFactory<Order, String>("employeeid"));
        ta.setCellValueFactory(new PropertyValueFactory<Order, Integer>("tableid"));
        amm.setCellValueFactory(new PropertyValueFactory<Order, Float>("total"));
        da.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("date"));
        customerTable.setItems(orders);
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
    public void switchToCustomersScreen(ActionEvent event){
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
    public void refresh(){
        try{
            orders.clear();
            String selectString = "SELECT * FROM getOrdersTable()";
            String selectString2 = "SELECT COUNT(*) FROM getOrdersTable()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String empid = rs.getString(2);
                int taid = rs.getInt(3);
                float amm = rs.getFloat(4);
                Timestamp da = rs.getTimestamp(5);
                Order or1 = new Order(id,empid,taid,amm,da);
                orders.add(or1);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }
    }
    public void deleteOrder(ActionEvent event){
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog3 = alert3.getDialogPane();
        alert3.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        Image icon = new Image("logos.png");
        dialog3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alert3.setTitle("Delete Order");
        dialog3.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete an order");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog3.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
            Order selectedOrder = customerTable.getSelectionModel().getSelectedItem();
            if(selectedOrder!=null){
                try{
                    int id = selectedOrder.getOrderid();
                    String selectString = "SELECT deleteOrder(?)";
                    delete = dbConnection.prepareStatement(selectString);
                    delete.setInt(1, id);
                    delete.executeQuery();
                } catch (SQLException exception){
                    exception.printStackTrace();
                    System.out.println("Database error: " + exception.getMessage());
                }
            }
            TableView.TableViewSelectionModel<Order> selectionModel = customerTable.getSelectionModel();
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
    public void openOrderAudit(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderAudit.fxml"));
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

    public void openCustomerInsertForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderCreationForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            OrdersCreateController od = fxmlLoader.getController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            od.setStage(currentStage);
            od.setMainController(this);
            od.setTableView(customerTable);
            od.setFirstNameColumn(on);
            od.setHomeAddressColoumn(emp);
            od.setNumberColumn(ta);
            od.setRatingColumn(amm);
            od.setNew(da);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }

    public void check(ActionEvent event){
        if(c1.isSelected()){
            date1.setDisable(true);
            combo1.setDisable(false);
            date2.setDisable(true);
        }
        if(c2.isSelected()){
            date1.setDisable(false);
            combo1.setDisable(true);
            date2.setDisable(false);
        }
        if(c1.isSelected() && c2.isSelected()){
            date1.setDisable(false);
            combo1.setDisable(false);
            date2.setDisable(false);
        }
        if(!c1.isSelected() && !c2.isSelected()){
            date1.setDisable(true);
            combo1.setDisable(true);
            date2.setDisable(true);
        }
    }

    public void select(ActionEvent event){
        boolean c1sel = c1.isSelected();
        boolean c2sel = c2.isSelected();
        if (c1sel && c2sel) {
            executeAllFilters();
        } else if (c1sel) {
            executeNameFilter();
        } else if (c2sel) {
            executePositionFilter();
        }  else {
            return;
        }
    }
    public void executePositionFilter(){
        try{
            LocalDate date1s = date1.getValue();
            LocalDate date2s = date2.getValue();

            System.out.println("Date 1: " + date1s);
            System.out.println("Date 2: " + date2s);


            Timestamp t1 = Timestamp.valueOf(date1s.atStartOfDay());
            Timestamp t2 = Timestamp.valueOf(date2s.atTime(23, 59, 59));

            System.out.println("Timestamp 1: " + t1);
            System.out.println("Timestamp 2: " + t2);

            String selectString = "SELECT * FROM filterOrdersByDate(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterOrdersByDate(?,?)";

            selector = dbConnection.prepareStatement(selectString);
            getRating = dbConnection.prepareStatement(selectString2);
            selector.setTimestamp(1, t1);
            selector.setTimestamp(2, t2);
            getRating.setTimestamp(1, t1);
            getRating.setTimestamp(2, t2);

            ResultSet rs = selector.executeQuery();
            ResultSet rs2 = getRating.executeQuery();
            orders.clear();
            while(rs.next()){
                int id = rs.getInt(1);
                String empid = rs.getString(2);
                int taid = rs.getInt(3);
                float amm = rs.getFloat(4);
                Timestamp da = rs.getTimestamp(5);
                Order or1 = new Order(id,empid,taid,amm,da);
                orders.add(or1);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }

    }
    public void executeNameFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            orders.clear();
            Object selected = combo1.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterOrderByTable(?)";
            String selectString2 = "SELECT COUNT(*) FROM filterOrderByTable(?)";
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

            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                String empid = rs.getString(2);
                int taid = rs.getInt(3);
                float amm = rs.getFloat(4);
                Timestamp da = rs.getTimestamp(5);
                Order or1 = new Order(id,empid,taid,amm,da);
                orders.add(or1);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executeAllFilters(){
        try{
            orders.clear();
            LocalDate date1s = date1.getValue();
            LocalDate date2s = date2.getValue();
            Object selected = combo1.getSelectionModel().getSelectedItem();

            System.out.println("Date 1: " + date1s);
            System.out.println("Date 2: " + date2s);


            Timestamp t1 = Timestamp.valueOf(date1s.atStartOfDay());
            Timestamp t2 = Timestamp.valueOf(date2s.atTime(23, 59, 59));

            System.out.println("Timestamp 1: " + t1);
            System.out.println("Timestamp 2: " + t2);

            String selectString = "SELECT * FROM allOrderFilters(?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM allOrderFilters(?,?,?)";

            selector = dbConnection.prepareStatement(selectString);
            getRating = dbConnection.prepareStatement(selectString2);
            selector.setTimestamp(2, t1);
            selector.setTimestamp(3, t2);
            getRating.setTimestamp(2, t1);
            getRating.setTimestamp(3, t2);
            if (selected instanceof Integer) {
                selector.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                selector.setNull(1, Types.INTEGER);
            } else {
                selector.setNull(1, Types.INTEGER);
            }

            if (selected instanceof Integer) {
                getRating.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getRating.setNull(1, Types.INTEGER);
            } else {
                getRating.setNull(1, Types.INTEGER);
            }

            ResultSet rs = selector.executeQuery();
            ResultSet rs2 = getRating.executeQuery();
            orders.clear();
            while(rs.next()){
                int id = rs.getInt(1);
                String empid = rs.getString(2);
                int taid = rs.getInt(3);
                float amm = rs.getFloat(4);
                Timestamp da = rs.getTimestamp(5);
                Order or1 = new Order(id,empid,taid,amm,da);
                orders.add(or1);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){

        }
    }
    public void resetFilters() {
        try {
            c1.setSelected(true);
            c2.setSelected(false);
            combo1.getSelectionModel().clearSelection();
            combo1.setValue(null);
            date1.setValue(LocalDate.now());
            date2.setValue(LocalDate.now());
            date1.setDisable(true);
            date2.setDisable(true);
            combo1.setDisable(false);
            refresh();

            System.out.println("Filters reset successfully");

        } catch (Exception ex) {
            System.out.println("Error resetting filters: " + ex.getMessage());
            ex.printStackTrace();
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
    public void openOrderDetails(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderDetails.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            Order selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            int customerId = selectedCustomer.getOrderid();
            OrderDetailsController od = fxmlLoader.getController();
            od.setOrderId(customerId);
            System.out.println("Scene1"+customerId);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
