package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.PauseTransition;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.databasetechnologyproject.CustomerController.dbConnection;
import static org.example.databasetechnologyproject.HelloController.driverClassName;

public class ReservationInsertController implements Initializable {
    private DialogPane dialog;
    public ReservationsController mainController;
    @FXML
    TableView<Reservation> reservationTable;
    @FXML
    TextField textField1;
    @FXML
    PreparedStatement insert;
    @FXML
    TableColumn<Reservation, String> customerColumn;
    @FXML
    TableColumn<Reservation, Integer> tableColumn;
    @FXML
    TableColumn<Reservation, String> timeColumn;
    @FXML
    TableColumn<Reservation, Integer> partySizeColumn;
    @FXML
    Stage scene1;
    @FXML
    private ComboBox<String> text1;
    @FXML
    DatePicker h;
    @FXML
    Label firstNameError;
    @FXML
    Label lastNameError;
    @FXML
    Label addressError;
    @FXML
    Label phoneError;
    @FXML
    Label emailError;
    @FXML
    Label firstNameLabel;
    @FXML
    Label lastNameLabel;
    @FXML
    Label homeLabel;
    @FXML
    Label d;
    @FXML
    Label phoneLabel;
    @FXML
    private ComboBox<Integer> text2;

    @FXML
    private ComboBox<String> text3;
    PreparedStatement getCustomer;
    PreparedStatement getTabelNumb;
    PreparedStatement setReservation;


    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        text1.setValue("Pick a customer from the database");
        text3.setValue("12:00");
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
            String selectString = "SELECT * FROM getCustomerNameAndId()";
            getCustomer = dbConnection.prepareStatement(selectString);
            getCustomer.executeQuery();
            ResultSet rs = getCustomer.getResultSet();
            while(rs.next()){
                String name = rs.getString(1);
                String lname = rs.getString(2);
                int id = rs.getInt(3);
                text1.getItems().add(name + "\t" + lname + "\t, ID: " + id);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            Integer firstTableNumber = null;
            String selectString = "SELECT * FROM getTableNumber()";
            getTabelNumb = dbConnection.prepareStatement(selectString);
            getTabelNumb.executeQuery();
            ResultSet rs = getTabelNumb.getResultSet();
            while(rs.next()){
                int number = rs.getInt(1);
                text2.getItems().add(number);
                if (firstTableNumber == null) {
                    firstTableNumber = number;
                }
                if (firstTableNumber != null) {
                    text2.setValue(firstTableNumber);
                } else {
                    text2.setValue(0);
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();;
        }
        text3.getItems().add("12:00:00");
        text3.getItems().add("13:00:00");
        text3.getItems().add("14:00:00");
        text3.getItems().add("15:00:00");
        text3.getItems().add("16:00:00");
        text3.getItems().add("17:00:00");
        text3.getItems().add("18:00:00");
        text3.getItems().add("19:00:00");
        text3.getItems().add("20:00:00");
        text3.getItems().add("21:00:00");
        text3.getItems().add("22:00:00");
        text3.getItems().add("23:00:00");
        text3.getItems().add("00:00:00");
        setupFocusListeners();
    }
    public void setMainController(ReservationsController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView<Reservation> reservationTable){
        this.reservationTable = reservationTable;
    }
    public void setCustomerColumn(TableColumn<Reservation, String> customerColumn){
        this.customerColumn = customerColumn;
    }
    public void setTableColumn(TableColumn<Reservation, Integer> tableColumn){
        this.tableColumn = tableColumn;
    }
    public void setTimeColumn(TableColumn<Reservation, String> timeColumn){
        this.timeColumn = timeColumn;
    }
    public void partySizeColumn(TableColumn<Reservation, Integer> partySizeColumn){
        this.partySizeColumn = partySizeColumn;
    }
    public void setStage(Stage stage) {
        this.scene1 = stage;
    }
    public void cancelInput(ActionEvent event){
        textField1.setText("");
        text1.setValue("Pick a customer from the database");
        text2.setValue(0);
        text3.setValue("12:00");
    }
    private void setupFocusListeners() {
        text1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            firstNameError.setVisible(false);
            if (newValue) {
                firstNameLabel.setStyle("-fx-text-fill: #FF5C00");
                text1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else {
                firstNameLabel.setStyle("");
                text1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        text2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            lastNameError.setVisible(false);
            if (newValue) {
                lastNameLabel.setStyle("-fx-text-fill: #FF5C00");
                text2.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else {
                lastNameLabel.setStyle("");
                text2.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        text3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            addressError.setVisible(false);
            if (newValue) {
                homeLabel.setStyle("-fx-text-fill: #FF5C00");
                text3.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else {
                homeLabel.setStyle("");
                text3.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        h.focusedProperty().addListener((observable, oldValue, newValue) -> {
            phoneError.setVisible(false);
            if (newValue) {
                d.setStyle("-fx-text-fill: #FF5C00");
                h.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else {
                d.setStyle("");
                h.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
        textField1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            emailError.setVisible(false);
            if (newValue) {
                phoneLabel.setStyle("-fx-text-fill: #FF5C00");
                textField1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #FF5C00;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            } else {
                phoneLabel.setStyle("");
                textField1.setStyle("-fx-background-color: transparent;\n" +
                        "    -fx-border-width: 0 0 1 0;\n" +
                        "    -fx-border-color: #bdc3c7;\n" +
                        "    -fx-padding: 8 0 8 0;\n" +
                        "    -fx-background-radius: 0;\n" +
                        "    -fx-border-radius: 0;");
            }
        });
    }
    public void firstNameInputError(){
        firstNameError.setVisible(true);
        firstNameError.setText("cannot be empty");
        firstNameError.setStyle("-fx-text-fill: #D0342C");
        firstNameLabel.setStyle("-fx-text-fill: #D0342C");
        text1.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void lastNameInputError(){
        lastNameError.setVisible(true);
        lastNameError.setText("cannot be empty");
        lastNameError.setStyle("-fx-text-fill: #D0342C");
        lastNameLabel.setStyle("-fx-text-fill: #D0342C");
        text2.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void homeAddressInputError(){
        addressError.setVisible(true);
        addressError.setText("annot be empty");
        addressError.setStyle("-fx-text-fill: #D0342C");
        homeLabel.setStyle("-fx-text-fill: #D0342C");
        text3.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void phoneNumberInputError(){
        phoneError.setVisible(true);
        phoneError.setText("cannot be empty");
        phoneError.setStyle("-fx-text-fill: #D0342C");
        phoneLabel.setStyle("-fx-text-fill: #D0342C");
        h.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void emailInputError(){
        emailError.setVisible(true);
        emailError.setText("cannot be empty");
        emailError.setStyle("-fx-text-fill: #D0342C");
        d.setStyle("-fx-text-fill: #D0342C");
        textField1.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-width: 0 0 1 0;\n" +
                "    -fx-border-color: #D0342C;\n" +
                "    -fx-padding: 8 0 8 0;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-border-radius: 0;");
    }
    public void showNotification(){
        Stage toastStage = new Stage();
        toastStage.initOwner(scene1);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label("Operation Successful, Reservation Inserted ✔");
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
    public void sumbit(ActionEvent event){
        if(textField1.getText().isEmpty()){
            firstNameInputError();
        }
        if(text1.getValue() == "Pick a customer from the database"){
            lastNameInputError();
        }
        if(text2.getValue() == 0){
            homeAddressInputError();
        }
        if(text3.getValue() == "12:00"){
            phoneNumberInputError();
        }
        if(textField1.getText().isEmpty()){
            emailInputError();
        }
        int resid = 0;
        int id = 0;
        String customer = text1.getValue();

        String digitsOnly = customer.replaceAll("\\D", "");

        id = Integer.parseInt(digitsOnly);

        int tableNumber = text2.getSelectionModel().getSelectedItem();

        String time = text3.getSelectionModel().getSelectedItem();

        String text = textField1.getText();

        int party_size = Integer.parseInt(text);

        LocalDate date = h.getValue();

        LocalTime t = LocalTime.parse(text3.getValue());

        LocalDateTime dateTime = LocalDateTime.of(date,t);

        Timestamp timestamp = Timestamp.valueOf(dateTime);

        int tableId = 0;
        try {
            String query = "SELECT * FROM getIdFromTableNumber(?)";
            PreparedStatement stmt = dbConnection.prepareStatement(query);
            stmt.setInt(1, tableNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tableId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try{
            String selectString = "SELECT insertReserve(?,?,?,?)";
            setReservation = dbConnection.prepareStatement(selectString);
            setReservation.setInt(1, id);
            setReservation.setInt(2, tableId);
            setReservation.setTimestamp(3, timestamp);
            setReservation.setInt(4, party_size);
            ResultSet rs = setReservation.executeQuery();
            while(rs.next()){
                resid = rs.getInt(1);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        Reservation reserve = new Reservation(resid,id,tableNumber,timestamp,party_size);
        reservationTable.getItems().add(reserve);
        textField1.setText("");
        text1.setValue("Pick a customer from the database");
        text2.setValue(0);
        text3.setValue("12:00");
        showNotification();
        ReservationServiceClass.getInstance().triggerRefresh();
        mainController.refresh();
    }



}
