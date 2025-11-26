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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {
    @FXML
    private Button AboutButton;

    @FXML
    private Button CustomersButton;

    @FXML
    private Button EmployeesButton;

    @FXML
    private Button HomeButton;

    @FXML
    private Label Label1;

    @FXML
    private Button MenuButton;

    @FXML
    private Button OrdersButton;

    @FXML
    private TableView<Reservation> ReservationTable;

    @FXML
    private Button ReservationsButton;

    @FXML
    private Button TablesButton;

    @FXML
    private TableColumn<Reservation, Integer> customerColumn;

    @FXML
    private ImageView customerView;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label filterLabel;

    @FXML
    private AnchorPane homePageCenter;

    @FXML
    private Label homeTitle;

    @FXML
    private Button insertReservationButton;

    @FXML
    private AnchorPane menuSideBar;

    @FXML
    private Button openLogCustomer;

    @FXML
    private TableColumn<Reservation, Integer> partySizeColumn;

    @FXML
    private Button resertAllButton;

    @FXML
    private Label rowResult;

    @FXML
    private Label rowsLabel;

    @FXML
    private BorderPane scene1;

    @FXML
    private Button settingsButton;

    @FXML
    private AnchorPane stage;

    @FXML
    private TableColumn<Reservation, Integer> tableColumn;

    @FXML
    private TableColumn<Reservation, String> timeColumn;

    @FXML
    private Tooltip tooltipexit;
    @FXML
    private ComboBox<Object> select1;

    @FXML
    private ComboBox<Object> select2;

    @FXML
    private ComboBox<String> select3;

    @FXML
    private DatePicker select4;

    @FXML
    private DatePicker select5;
    @FXML
            Label la;
    @FXML
            Label la2;
    int i = 0;
    ObservableList<Reservation> Reservations = FXCollections.observableArrayList();
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
    Stage stage1;
    Scene scene;
    Parent root;
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;
    private DialogPane dialog;
    @Override
    public void initialize(URL y, ResourceBundle resourceBundle) {
        boolean firstTimeFlag = true;
        select1.setValue("Any");
        select2.setValue("Any");
        select3.setValue("12:00:00");
        select4.setValue(null);
        select5.setValue(null);
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
        select3.getItems().add("12:00:00");
        select3.getItems().add("13:00:00");
        select3.getItems().add("14:00:00");
        select3.getItems().add("15:00:00");
        select3.getItems().add("16:00:00");
        select3.getItems().add("17:00:00");
        select3.getItems().add("18:00:00");
        select3.getItems().add("19:00:00");
        select3.getItems().add("20:00:00");
        select3.getItems().add("21:00:00");
        select3.getItems().add("22:00:00");
        select3.getItems().add("23:00:00");
        select3.getItems().add("00:00:00");

        int reservations = 3;
        if (reservations == 3){
            ReservationsButton.setStyle("-fx-font-size: 17px;\n" +
                    "    -fx-font-family: \"Lexend Giga\";\n" +
                    "    -fx-background-color: #5CABF5;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-alignment: CENTER_LEFT;");
        }
        try{
            String selectString = "SELECT * FROM getReserves()";
            String selectString2 = "SELECT COUNT(*) FROM getReserves()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();


            while(rs.next()){
                int id = rs.getInt(1);
                int cid = rs.getInt(2);
                int tid = rs.getInt(3);
                Timestamp d = rs.getTimestamp(4);
                int ps = rs.getInt(5);
                Reservation reserve = new Reservation(id,cid,tid,d,ps);
                Reservations.add(reserve);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        }catch (SQLException ex){
            System.out.println("SQL error");
        }
        try{
            select1.getItems().add("Any");
            String selectString = "SELECT * FROM getTa()";
            getFirstName = dbConnection.prepareStatement(selectString);
            getFirstName.executeQuery();
            ResultSet rs = getFirstName.getResultSet();
            while(rs.next()){
                int t = rs.getInt(1);
                select1.getItems().add(t);
            }
        } catch (SQLException ex){

        }
        try{
            select2.getItems().add("Any");
            String selectString = "SELECT * FROM getSize()";
            getFname = dbConnection.prepareStatement(selectString);
            getFname.executeQuery();
            ResultSet rs = getFname.getResultSet();
            while(rs.next()){
                int t = rs.getInt(1);
                select2.getItems().add(t);
            }
        } catch (SQLException ex){

        }

        customerColumn.setCellValueFactory(cellData -> {
            Reservation r = cellData.getValue();
            return new SimpleIntegerProperty(r.getCustomerid()).asObject();
        });
        customerColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        customerColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, Integer> event) {
                Reservation rev = event.getRowValue();
                rev.setCustomerid(event.getNewValue());

                int cid = rev.getCustomerid();
                int tid = rev.getTableid();
                Timestamp time = rev.getReservationtime();
                int ps = rev.getParty_size();
                int rid = rev.getReservationid();

                try{
                    String updateString = "SELECT updateReservatio(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(updateString);
                    update.setInt(1, rid);
                    update.setInt(2, cid);
                    update.setTimestamp(3, time);
                    update.setInt(4, tid);
                    update.setInt(5, ps);
                    update.executeQuery();

                } catch (SQLException e){
                    e.printStackTrace();
                }

                int selectedIndex = ReservationTable.getSelectionModel().getSelectedIndex();
                i = selectedIndex + 1;
                refresh();
                ReservationServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });
        tableColumn.setCellValueFactory(cellData -> {
            Reservation r = cellData.getValue();
            return new SimpleIntegerProperty(r.getTableid()).asObject();
        });
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, Integer> event) {
                Reservation rev = event.getRowValue();
                rev.setTableid(event.getNewValue());

                int cid = rev.getCustomerid();
                int tid = rev.getTableid();
                Timestamp time = rev.getReservationtime();
                int ps = rev.getParty_size();
                int rid = rev.getReservationid();

                try{
                    String updateString = "SELECT updateReservatio(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(updateString);
                    update.setInt(1, rid);
                    update.setInt(2, cid);
                    update.setTimestamp(3, time);
                    update.setInt(4, tid);
                    update.setInt(5, ps);
                    update.executeQuery();

                } catch (SQLException e){
                    e.printStackTrace();
                }

                int selectedIndex = ReservationTable.getSelectionModel().getSelectedIndex();
                i = selectedIndex + 1;
                refresh();
                ReservationServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        partySizeColumn.setCellValueFactory(cellData -> {
            Reservation r = cellData.getValue();
            return new SimpleIntegerProperty(r.getParty_size()).asObject();
        });
        partySizeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        partySizeColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, Integer> event) {
                Reservation rev = event.getRowValue();
                rev.setParty_size(event.getNewValue());

                int cid = rev.getCustomerid();
                int tid = rev.getTableid();
                Timestamp time = rev.getReservationtime();
                int ps = rev.getParty_size();
                int rid = rev.getReservationid();


                try{
                    String updateString = "SELECT updateReservatio(?,?,?,?,?)";
                    update = dbConnection.prepareStatement(updateString);
                    update.setInt(1, rid);
                    update.setInt(2, cid);
                    update.setTimestamp(3, time);
                    update.setInt(4, tid);
                    update.setInt(5, ps);
                    update.executeQuery();
                } catch (SQLException e){
                    e.printStackTrace();
                }

                int selectedIndex = ReservationTable.getSelectionModel().getSelectedIndex();
                i = selectedIndex + 1;
                refresh();
                ReservationServiceClass.getInstance().triggerRefresh();
                showNotification(i);
            }
        });

        ReservationTable.setItems(Reservations);

        customerColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("customerid"));
        tableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("tableid"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("reservationtime"));
        partySizeColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("party_size"));
        ReservationTable.setItems(Reservations);
    }

    public void showNotification(int ind){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("✔Operation Successful, Reservation Updated at Row: " + ind);
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
    public void openReservationInsertForm(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reservationInsertForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Insert Form");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            ReservationInsertController rc = fxmlLoader.getController();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            rc.setStage(currentStage);
            rc.setMainController(this);
            rc.setTableView(ReservationTable);
            rc.setCustomerColumn(customerColumn);
            rc.setTableColumn(tableColumn);
            rc.setTimeColumn(timeColumn);
            rc.partySizeColumn(partySizeColumn);
        } catch (IOException ex) {
            System.out.println("This window could not load");
            ex.printStackTrace();
        }
    }
    public void refresh(){
        try{
            Reservations.clear();
            String selectString = "SELECT * FROM getReserves()";
            String selectString2 = "SELECT COUNT(*) FROM getReserves()";

            fillTable = dbConnection.prepareStatement(selectString);
            getCountOfTable = dbConnection.prepareStatement(selectString2);

            getCountOfTable.executeQuery();
            fillTable.executeQuery();

            ResultSet rs = fillTable.getResultSet();
            ResultSet rs2 = getCountOfTable.getResultSet();


            while(rs.next()){
                int id = rs.getInt(1);
                int cid = rs.getInt(2);
                int tid = rs.getInt(3);
                Timestamp d = rs.getTimestamp(4);
                int ps = rs.getInt(5);
                Reservation reserve = new Reservation(id,cid,tid,d,ps);
                Reservations.add(reserve);
            }
            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }
        } catch (SQLException es){

        }
    }
    public void showNotification2(){
        Stage toastStage = new Stage();
        Stage currentStage = (Stage) scene1.getScene().getWindow();
        toastStage.initOwner(currentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        Label label = new Label("✔Operation Successful, Reservation Deleted");
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
    public void deleteReservation(ActionEvent event){
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog = alert3.getDialogPane();
        alert3.setGraphic(new ImageView(this.getClass().getResource("kk.png").toString()));
        Image icon = new Image("logos.png");
        dialog.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        alert3.setTitle("Delete Reservation");
        dialog.getStyleClass().add("dialog2");
        alert3.setHeaderText("You are about to delete a reservation");
        alert3.setContentText("This Action is permanent, do you wish to continue?");
        Stage alertstage = (Stage) dialog.getScene().getWindow();
        alertstage.getIcons().add(icon);
        if (alert3.showAndWait().get() == ButtonType.OK) {
            Reservation sr = ReservationTable.getSelectionModel().getSelectedItem();
            if(sr!=null){
                try{
                    int id = sr.getReservationid();
                    String selectString = "SELECT deleteReserve(?)";
                    delete = dbConnection.prepareStatement(selectString);
                    delete.setInt(1, id);
                    delete.executeQuery();
                } catch (SQLException exception){
                    exception.printStackTrace();
                    System.out.println("Database error: " + exception.getMessage());
                }
            }
            TableView.TableViewSelectionModel<Reservation> selectionModel = ReservationTable.getSelectionModel();
            if(selectionModel.isEmpty()){
                System.out.println("Table is empty");
            }
            ObservableList<Integer> list = selectionModel.getSelectedIndices();
            Integer[] selectedItems = new Integer[list.size()];
            selectedItems = list.toArray(selectedItems);
            Arrays.sort(selectedItems);
            for(int i=selectedItems.length - 1;i>=0;i--){
                selectionModel.clearSelection(selectedItems[i].intValue());
                ReservationTable.getItems().remove(selectedItems[i].intValue());
            }
            ReservationServiceClass.getInstance().triggerRefresh();
            refresh();
            showNotification2();
        }
        else{
            return;
        }
    }
    public void openReservationLog(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reservationAudit.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Log File");
            Image icon = new Image("logos.png");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
            refresh();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void select(ActionEvent event){
        try{
            Reservations.clear();

            LocalDate date1 = select4.getValue();
            LocalDate date2 = select5.getValue();
            LocalTime t = null;


            try {
                if (select3.getValue() != null) {
                    t = LocalTime.parse(select3.getValue());
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Failed to parse time: " + e.getMessage());

            }



            Object tableValue = select1.getValue();
            Object partySizeValue = select2.getValue();



            String selectString = "SELECT * FROM getFilteredRes(?,?,?,?)";
            String selectString2 = "SELECT COUNT(*) FROM getFilteredRes(?,?,?,?)";



            getRating = dbConnection.prepareStatement(selectString2);
            selector = dbConnection.prepareStatement(selectString);


            if (tableValue instanceof Integer) {
                selector.setInt(1, (Integer) tableValue);
                getRating.setInt(1, (Integer) tableValue);
            } else {
                selector.setNull(1, Types.INTEGER);
                getRating.setNull(1, Types.INTEGER);
            }


            if (partySizeValue instanceof Integer) {
                System.out.println("DEBUG: Setting party = " + partySizeValue);
                selector.setInt(2, (Integer) partySizeValue);
                getRating.setInt(2, (Integer) partySizeValue);
            } else {
                System.out.println("DEBUG: Setting party = NULL");
                selector.setNull(2, Types.INTEGER);
                getRating.setNull(2, Types.INTEGER);
            }


            if (date1 != null && date2 != null && t != null) {

                LocalDateTime dateTimeStart = LocalDateTime.of(date1, LocalTime.MIN);
                LocalDateTime dateTimeEnd = LocalDateTime.of(date2, LocalTime.of(23, 59, 59));

                Timestamp timestampStart = Timestamp.valueOf(dateTimeStart);
                Timestamp timestampEnd = Timestamp.valueOf(dateTimeEnd);


                selector.setTimestamp(3, timestampStart);
                selector.setTimestamp(4, timestampEnd);
                getRating.setTimestamp(3, timestampStart);
                getRating.setTimestamp(4, timestampEnd);
            } else if (date1 != null && date2 != null && t == null) {

                LocalDateTime dateTimeStart = LocalDateTime.of(date1, LocalTime.MIN);
                LocalDateTime dateTimeEnd = LocalDateTime.of(date2, LocalTime.of(23, 59, 59));

                Timestamp timestampStart = Timestamp.valueOf(dateTimeStart);
                Timestamp timestampEnd = Timestamp.valueOf(dateTimeEnd);


                selector.setTimestamp(3, timestampStart);
                selector.setTimestamp(4, timestampEnd);
                getRating.setTimestamp(3, timestampStart);
                getRating.setTimestamp(4, timestampEnd);
            } else {
                selector.setNull(3, Types.TIMESTAMP);
                selector.setNull(4, Types.TIMESTAMP);
                getRating.setNull(3, Types.TIMESTAMP);
                getRating.setNull(4, Types.TIMESTAMP);
            }


            ResultSet rs = selector.executeQuery();
            ResultSet rs2 = getRating.executeQuery();

            int rowCount = 0;
            while(rs.next()){
                rowCount++;
                int id = rs.getInt(1);
                int cid = rs.getInt(2);
                int tid = rs.getInt(3);
                Timestamp rv = rs.getTimestamp(4);
                int pt = rs.getInt(5);
                Reservation r = new Reservation(id,cid,tid,rv,pt);
                Reservations.add(r);

            }


            if(rs2.next()){
                int result = rs2.getInt(1);
                rowResult.setText(String.valueOf(result));
            }


        } catch (SQLException ex){

            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void resetAll(ActionEvent event){
        select1.setValue("Any");
        select2.setValue("Any");
        select3.setValue("12:00:00");
        select4.setValue(null);
        select5.setValue(null);
        refresh();
    }
    private boolean validateDatesForQuery(LocalDate fromDate, LocalDate toDate) {
        resetDateValidationStyles();

        if (fromDate == null || toDate == null) {
            showDateError("Please select both dates");
            return false;
        }

        if (toDate.isBefore(fromDate)) {
            showDateError("End date cannot be before start date");
            return false;
        }

        if (fromDate.isBefore(LocalDate.now())) {
            showDateError("Start date cannot be in the past");
            return false;
        }

        if (toDate.isBefore(LocalDate.now())) {
            showDateError("End date cannot be in the past");
            return false;
        }

        resetDateValidationStyles();
        return true;
    }
    private void showDateError(String message) {
        la.setStyle("-fx-text-fill: red; -fx-opacity: 1;");
        la2.setStyle("-fx-text-fill: red; -fx-opacity: 1;");
    }
    private void resetDateValidationStyles() {
        la.setStyle("-fx-text-fill: transparent;");
        la2.setStyle("-fx-text-fill: transparent;");
    }

}
