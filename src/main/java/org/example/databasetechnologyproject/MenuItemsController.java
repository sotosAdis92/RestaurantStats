package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MenuItemsController implements Initializable {
    Stage stage1;
    Scene scene;
    Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button MenuButton;
    @FXML
    TableView<MenuItem> customerTable;
    @FXML
    TableColumn<MenuItem, String> name;
    @FXML
    TableColumn<MenuItem, Float> price;
    @FXML
    TableColumn<MenuItem, String> cat;
    @FXML
    TableColumn<MenuItem, String> st;
    @FXML
    Tooltip tooltipexit;
    @FXML
    Label rowResult;
    ObservableList<MenuItem> items = FXCollections.observableArrayList();
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
    public void initialize(URL n, ResourceBundle resourceBundle) {
        try {
            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully");
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            ex.printStackTrace();
        }


        int menuScreenId = 5;
        if (menuScreenId == 5){
            MenuButton.setStyle("-fx-font-size: 17px;\n" +
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
            String selectString = "SELECT * FROM getMenuItems()";
            String selectString2 = "SELECT COUNT(*) FROM getMenuItems()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String cid = rs.getString(2);
                Float empid = rs.getFloat(3);
                String taid = rs.getString(4);
                String state = rs.getString(5);
                MenuItem item = new MenuItem(id,cid,empid,taid,state);
                items.add(item);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MenuItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, String> event) {
                MenuItem customer1 = event.getRowValue();
                customer1.setItemname(event.getNewValue());
                String firstName = customer1.getItemname();
                int id = customer1.getItemid();
                String cat = customer1.getCategory();
                String av = customer1.getState();
                float price = customer1.getPrice();
                try{
                    String selectString = "SELECT updateMenuItem(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, cat);
                    update.setString(4, av);
                    update.setFloat(5, price);
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

        cat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        cat.setCellFactory(TextFieldTableCell.forTableColumn());
        cat.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MenuItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, String> event) {
                MenuItem customer1 = event.getRowValue();
                customer1.setCategory(event.getNewValue());
                String firstName = customer1.getItemname();
                int id = customer1.getItemid();
                String cat = customer1.getCategory();
                String av = customer1.getState();
                float price = customer1.getPrice();
                try{
                    String selectString = "SELECT updateMenuItem(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, cat);
                    update.setString(4, av);
                    update.setFloat(5, price);
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

        st.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getClass().getName()));
        st.setCellFactory(TextFieldTableCell.forTableColumn());
        st.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MenuItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, String> event) {
                MenuItem customer1 = event.getRowValue();
                customer1.setState(event.getNewValue());
                String firstName = customer1.getItemname();
                int id = customer1.getItemid();
                String cat = customer1.getCategory();
                String av = customer1.getState();
                float price = customer1.getPrice();
                try{
                    String selectString = "SELECT updateMenuItem(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1,id);
                    update.setString(2,firstName);
                    update.setString(3, cat);
                    update.setString(4, av);
                    update.setFloat(5, price);
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


        price.setCellValueFactory(cellData -> {
            MenuItem menuItem = cellData.getValue();
            return new SimpleObjectProperty<>(menuItem.getPrice());
        });

        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter() {
            @Override
            public String toString(Float object) {
                if (object == null) {
                    return "";
                }
                return String.format("%.2f", object);
            }

            @Override
            public Float fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return 0.0f;
                }
                try {
                    return Float.parseFloat(string);
                } catch (NumberFormatException e) {
                    return 0.0f;
                }
            }
        }));

        price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MenuItem, Float>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MenuItem, Float> event) {
                MenuItem menuItem = event.getRowValue();
                menuItem.setPrice(event.getNewValue());

                String itemName = menuItem.getItemname();
                int id = menuItem.getItemid();
                String category = menuItem.getCategory();
                String state = menuItem.getState();
                float price = menuItem.getPrice();

                try {
                    String selectString = "SELECT updateMenuItem(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(selectString);
                    update.setInt(1, id);
                    update.setString(2, itemName);
                    update.setString(3, category);
                    update.setString(4, state);
                    update.setFloat(5, price);
                    update.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                ObservableList<Integer> selectedItem = customerTable.getSelectionModel().getSelectedIndices();
                selectedItem.forEach(index -> {
                    i = index + 1;
                });
                refresh();
                customerServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        customerTable.setItems(items);

        name.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("itemname"));
        price.setCellValueFactory(new PropertyValueFactory<MenuItem, Float>("price"));
        cat.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("category"));
        st.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("state"));
    }
    public void showNotification(int ind){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("✔Operation Successful, Item Updated at Row: " + ind);
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
            items.clear();
            String selectString = "SELECT * FROM getMenuItems()";
            String selectString2 = "SELECT COUNT(*) FROM getMenuItems()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();

            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                int id = rs.getInt(1);
                String cid = rs.getString(2);
                Float empid = rs.getFloat(3);
                String taid = rs.getString(4);
                String state = rs.getString(5);
                MenuItem item = new MenuItem(id,cid,empid,taid,state);
                items.add(item);
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
        alert3.setTitle("Delete Menu Item");
        dialog3.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete an Menu Item");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog3.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
            MenuItem selectedOrder = customerTable.getSelectionModel().getSelectedItem();
            if(selectedOrder!=null){
                try{
                    int id = selectedOrder.getItemid();
                    String selectString = "SELECT deleteMenuItem(?)";
                    delete = dbConnection.prepareStatement(selectString);
                    delete.setInt(1, id);
                    delete.executeQuery();
                } catch (SQLException exception){
                    exception.printStackTrace();
                    System.out.println("Database error: " + exception.getMessage());
                }
            }
            TableView.TableViewSelectionModel<MenuItem> selectionModel = customerTable.getSelectionModel();
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
        Label label = new Label("✔Operation Successful, Item Deleted");
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuAudit.fxml"));
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

    public void openMenuInsert(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuItemInsert.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            InsertMenuItemController od = fxmlLoader.getController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            od.setStage(currentStage);
            od.setMainController(this);
            od.setTableView(customerTable);
            od.setFirstNameColumn(name);
            od.setLastNameColumn(price);
            od.setHomeAddressColoumn(cat);
            od.setNumberColumn(st);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
}
