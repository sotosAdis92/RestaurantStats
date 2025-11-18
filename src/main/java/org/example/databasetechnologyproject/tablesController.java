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
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class tablesController implements Initializable {
    Stage stage1;
    Scene scene;
    Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button TablesButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    ComboBox<Object> textfield;
    @FXML
    ComboBox<Object> textfield2;
    @FXML
    TableView<Tables> customerTable;
    @FXML
     TableColumn<Tables, String> status;
    @FXML
     TableColumn<Tables, Integer> tnumber;
    @FXML
     TableColumn<Tables, Integer> capacity;
    PreparedStatement update;
    @FXML
    ComboBox<String> textfield3;
    @FXML
    CheckBox c1;
    @FXML
    CheckBox c2;
    @FXML
    CheckBox c3;
    @FXML
    Label rowResult;

    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    PreparedStatement getFirstName;
    PreparedStatement getCountOfTable;
    PreparedStatement delete;
    static Connection dbConnection;
    PreparedStatement fillTable;
    PreparedStatement getLastName;
    PreparedStatement getPhone;
    PreparedStatement getRating;
    PreparedStatement getFname;
    PreparedStatement getCountCustomer;
    ObservableList<Tables> tabl = FXCollections.observableArrayList();
    int i = 0;
    private DialogPane dialog;
    DialogPane dialog3;
    @Override
    public void initialize(URL d, ResourceBundle resourceBundle) {
        textfield.setValue("all");
        textfield2.setValue("all");
        textfield3.setValue("all");
        textfield.setDisable(false);
        textfield2.setDisable(true);
        textfield3.setDisable(true);
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
        int customerScreenId = 6;
        if (customerScreenId == 6){
            TablesButton.setStyle("-fx-font-size: 17px;\n" +
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
            textfield.getItems().add("all");
            String selectString = "SELECT * FROM getTableNumbers()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                textfield.getItems().add(num);

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            textfield2.getItems().add("all");
            String selectString = "SELECT * FROM getTableCapacities()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                textfield2.getItems().add(num);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            textfield3.getItems().add("all");
            String selectString = "SELECT * FROM getStatus()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                String num = rs.getString(1);
                textfield3.getItems().add(num);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            String selectString = "SELECT * FROM fillTablesTable()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int id = rs.getInt(1);
                int tnum = rs.getInt(2);
                int c = rs.getInt(3);
                String state = rs.getString(4);
                Tables ta1 = new Tables(id,tnum,c,state);
                tabl.add(ta1);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        tnumber.setCellValueFactory(cellData -> {
            Tables customer = cellData.getValue();
            return new SimpleIntegerProperty(customer.getTnumber()).asObject();
        });
        tnumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tnumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Tables, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Tables, Integer> event) {
                Tables customer1 = event.getRowValue();
                customer1.setTnumber(event.getNewValue());
                int id = customer1.getTid();
                String status = customer1.getStatus();
                int ca = customer1.getCapacity();
                int num = customer1.getTnumber();
                try{
                    String selectString = "SELECT updateTables(?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setInt(2,num);
                    update.setInt(3, ca);
                    update.setString(4, status);
                    update.executeQuery();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                TablesServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });


        capacity.setCellValueFactory(cellData -> {
            Tables customer = cellData.getValue();
            return new SimpleIntegerProperty(customer.getCapacity()).asObject();
        });
        capacity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capacity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Tables, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Tables, Integer> event) {
                Tables customer1 = event.getRowValue();
                customer1.setCapacity(event.getNewValue());
                int id = customer1.getTid();
                String status = customer1.getStatus();
                int ca = customer1.getCapacity();
                int num = customer1.getTnumber();
                try{
                    String selectString = "SELECT updateTables(?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setInt(2,num);
                    update.setInt(3, ca);
                    update.setString(4, status);
                    update.executeQuery();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                TablesServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Tables, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Tables, String> event) {
                Tables customer1 = event.getRowValue();
                customer1.setStatus(event.getNewValue());
                int id = customer1.getTid();
                String status = customer1.getStatus();
                int ca = customer1.getCapacity();
                int num = customer1.getTnumber();
                try{
                    String selectString = "SELECT updateTables(?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setInt(2,num);
                    update.setInt(3, ca);
                    update.setString(4, status);
                    update.executeQuery();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index ->{
                    i = index + 1;
                });
                refresh();
                TablesServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });



        customerTable.setItems(tabl);
        tnumber.setCellValueFactory(new PropertyValueFactory<Tables, Integer>("tnumber"));
        capacity.setCellValueFactory(new PropertyValueFactory<Tables, Integer>("capacity"));
        status.setCellValueFactory(new PropertyValueFactory<Tables, String>("status"));
        customerTable.setItems(tabl);
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




    public void switchToSceneCustomer(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
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
            ex.printStackTrace();
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToMenuScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".fxml"));
            stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToTableScene(ActionEvent event){

    }
    public void switchToOrdersScene(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".fxml"));
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
    public void select(){

    }
    public void openTableInsertForm(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tableInsertForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            tablesInsertController customerinsetcontroller = fxmlLoader.getController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            customerinsetcontroller.setStage(currentStage);
            customerinsetcontroller.setMainController(this);
            customerinsetcontroller.setTableView(customerTable);
            customerinsetcontroller.setFirstNameColumn(status);
            customerinsetcontroller.setLastNameColumn(tnumber);
            customerinsetcontroller.setHomeAddressColoumn(capacity);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
    public void refresh(){
        try{
            tabl.clear();
            String selectString = "SELECT * FROM fillTablesTable()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int id = rs.getInt(1);
                int tnum = rs.getInt(2);
                int c = rs.getInt(3);
                String state = rs.getString(4);
                Tables ta1 = new Tables(id,tnum,c,state);
                tabl.add(ta1);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void deleteTable(ActionEvent event){
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
            Tables selectedItem = customerTable.getSelectionModel().getSelectedItem();
            try{
                int id = selectedItem.getTid();
                String selectString = "SELECT deleteTables(?)";
                delete = dbConnection.prepareStatement(selectString);
                delete.setInt(1, id);
                delete.executeQuery();
            } catch (SQLException exception){
                exception.printStackTrace();
                System.out.println("Database error: " + exception.getMessage());
            }
            TableView.TableViewSelectionModel<Tables> selectionModel = customerTable.getSelectionModel();
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
            TablesServiceClass.getInstance().triggerRefresh();
            refresh();
            showNotification2();
        }
        else{
            return;
        }
    }
    public void openTablesAudit(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tablesAudit.fxml"));
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

    public void executeAllFilters(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield.getSelectionModel().getSelectedItem();
            Object selected1 = textfield2.getSelectionModel().getSelectedItem();
            String selected2 = textfield3.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByNumberAndStatusandCapacity(?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByNumberAndStatusandCapacity(?,?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            if (selected instanceof Integer) {
                getFname.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(1, Types.INTEGER);
            } else {
                getFname.setNull(1, Types.INTEGER);
            }

            if (selected1 instanceof Integer) {
                getFname.setInt(3, (Integer) selected1);
            } else if ("all".equals(selected1)) {
                getFname.setNull(3, Types.INTEGER);
            } else {
                getFname.setNull(3, Types.INTEGER);
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

            if (selected1 instanceof Integer) {
                getCountCustomer.setInt(3, (Integer) selected1);
            } else if ("all".equals(selected1)) {
                getCountCustomer.setNull(3, Types.INTEGER);
            } else {
                getCountCustomer.setNull(3, Types.INTEGER);
            }

            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executeNameAndPositionFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield2.getSelectionModel().getSelectedItem();
            Object selected2 = textfield.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterCapacityAndNumber(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterCapacityAndNumber(?,?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            if (selected instanceof Integer) {
                getFname.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(1, Types.INTEGER);
            } else {
                getFname.setNull(1, Types.INTEGER);
            }

            if (selected2 instanceof Integer) {
                getFname.setInt(2, (Integer) selected);
            } else if ("all".equals(selected)) {
                getFname.setNull(2, Types.INTEGER);
            } else {
                getFname.setNull(2, Types.INTEGER);
            }

            if (selected instanceof Integer) {
                getCountCustomer.setInt(1, (Integer) selected);
            } else if ("all".equals(selected)) {
                getCountCustomer.setNull(1, Types.INTEGER);
            } else {
                getCountCustomer.setNull(1, Types.INTEGER);
            }

            if (selected2 instanceof Integer) {
                getCountCustomer.setInt(2, (Integer) selected);
            } else if ("all".equals(selected2)) {
                getCountCustomer.setNull(2, Types.INTEGER);
            } else {
                getCountCustomer.setNull(2, Types.INTEGER);
            }

            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executePositionAndEmailFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield2.getSelectionModel().getSelectedItem();
            String selected2 = textfield3.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterCapacityAndStatus(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterCapacityAndStatus(?,?)";
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
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executeNameAndEmailFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield.getSelectionModel().getSelectedItem();
            String selected2 = textfield3.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByNumberAndStatus(?,?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByNumberAndStatus(?,?)";
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
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executeNameFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByTableNumber(?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByTableNumber(?)";
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
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executePositionFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            Object selected = textfield2.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByTableCapacity(?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByTableCapacity(?)";
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
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void executeEmailFilter(){
        try{
            int firstName = 0;
            int lastname = 0;
            String postion = null;
            int id = 0;
            tabl.clear();
            String selected = textfield3.getSelectionModel().getSelectedItem();
            String selectString = "SELECT * FROM filterByStatus(?)";
            String selectString2 = "SELECT COUNT(*) FROM filterByStatus(?)";
            getCountCustomer = dbConnection.prepareStatement(selectString2);
            getFname = dbConnection.prepareStatement(selectString);
            getFname.setString(1, selected);
            getCountCustomer.setString(1,selected);
            ResultSet rs = getFname.executeQuery();
            ResultSet rs2 = getCountCustomer.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
                firstName = rs.getInt(2);
                lastname = rs.getInt(3);
                postion = rs.getString(4);
                Tables emp = new Tables(id,firstName,lastname,postion);
                tabl.add(emp);
            }
            while(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void select(ActionEvent event){
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
    public void reset(ActionEvent evet){
        textfield.setValue("all");
        textfield2.setValue("all");
        textfield3.setValue("all");
        textfield3.setDisable(true);
        textfield2.setDisable(true);
        textfield.setDisable(false);
        c1.setSelected(true);
        c2.setSelected(false);
        c3.setSelected(false);
        refresh();
    }

    public void check(ActionEvent event){
        if(c1.isSelected()){
            textfield3.setDisable(true);
            textfield.setDisable(false);
            textfield2.setDisable(true);
        }
        if(c2.isSelected()){
            textfield3.setDisable(true);
            textfield.setDisable(true);
            textfield2.setDisable(false);
        }
        if(c3.isSelected()){
            textfield3.setDisable(false);
            textfield.setDisable(true);
            textfield2.setDisable(true);
        }
        if(c1.isSelected() && c2.isSelected()){
            textfield3.setDisable(true);
            textfield.setDisable(false);
            textfield2.setDisable(false);
        }
        if(c1.isSelected() && c3.isSelected()){
            textfield3.setDisable(false);
            textfield.setDisable(false);
            textfield2.setDisable(true);
        }
        if(c2.isSelected() && c3.isSelected()){
            textfield3.setDisable(false);
            textfield.setDisable(true);
            textfield2.setDisable(false);
        }
        if(c1.isSelected() && c2.isSelected() && c3.isSelected()){
            textfield3.setDisable(false);
            textfield.setDisable(false);
            textfield2.setDisable(false);
        }
        if(!c1.isSelected() && !c2.isSelected() && !c3.isSelected()){
            textfield3.setDisable(true);
            textfield.setDisable(true);
            textfield2.setDisable(true);
        }
    }
}
