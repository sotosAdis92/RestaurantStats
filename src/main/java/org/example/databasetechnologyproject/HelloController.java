package org.example.databasetechnologyproject;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    BorderPane scene1;
    @FXML
    Button exitButton;
    @FXML
    Tooltip tooltipexit;
    @FXML
    Button HomeButton;
    @FXML
    WebView webview1;
    WebEngine engine;

    @FXML
    WebView webview2;
    WebEngine engine2;


    @FXML
    ComboBox<String> combo1;
    @FXML
    ComboBox<Integer> combo2;

    @FXML
    ComboBox<Integer> combo5;
    @FXML
    ComboBox<Integer> combo6;

    @FXML
    Label Label11111;

    @FXML
    Label Label12;
    @FXML
    Label Label1;

    @FXML
    Label customer;
    @FXML
    Label table;
    @FXML
    Label order;
    @FXML
    Label mone;
    Stage stage1;
    @FXML
    private ComboBox<Integer> comboA;

    @FXML
    private ComboBox<Integer> comboX;

    @FXML
    private ComboBox<String> comboY;

    @FXML
    private ComboBox<Integer> comboZ;
    @FXML
    private Label la1;

    @FXML
    private Label la2;

    @FXML
    private Label la3;
    @FXML
    Label total;

    @FXML
    Label tsi;

    @FXML
    Label lrt;

    @FXML
    Label per;
    private DialogPane dialog;
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
    static String driverClassName = "org.postgresql.Driver";
    static Dotenv dotenv = Dotenv.load();
    static String url = dotenv.get("DB_URL");
    static String user = dotenv.get("DB_USER");
    static String password = dotenv.get("DB_PASSWORD");
    static Connection dbConnection;

    public void initialize(URL location, ResourceBundle arg1){
        combo2.setVisible(true);
        combo5.setVisible(false);
        combo6.setVisible(false);
        Label11111.setVisible(false);
        Label12.setVisible(false);
        Label1.setVisible(true);


        comboZ.setVisible(true);
        comboX.setVisible(false);
        comboA.setVisible(false);
        la1.setVisible(false);
        la3.setVisible(false);
        la2.setVisible(true);

        int currentYear = java.time.Year.now().getValue();
        combo1.setValue("Yearly");
        comboY.setValue("Yearly");
        combo5.setValue(currentYear);
        combo2.setValue(currentYear);
        comboX.setValue(currentYear);
        comboZ.setValue(currentYear);
        combo6.setValue(1);
        comboA.setValue(1);

        System.out.println(url);
        System.out.println(user);
        System.out.println(password);

        try{
            Class.forName(driverClassName);
        }catch (ClassNotFoundException ex){

        }
        try{
            dbConnection = DriverManager.getConnection(url,user,password);
            System.out.println(dbConnection);
        } catch (SQLException ex){

        }

        int homeScreenId = 1;
        if(homeScreenId == 1){
            HomeButton.setStyle("-fx-font-size: 17px;\n" +
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
        combo1.getItems().add("Yearly");
        combo1.getItems().add("Monthly");
        combo2.setValue(currentYear);

        comboY.getItems().add("Yearly");
        comboY.getItems().add("Monthly");
        comboX.setValue(currentYear);
        try{
            String SelectString = "SELECT * FROM getYears()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                int y = rs.getInt(1);
                combo2.getItems().add(y);
                combo5.getItems().add(y);
                comboZ.getItems().add(y);
                comboX.getItems().add(y);
            }
        } catch (SQLException ex){

        }
        combo6.getItems().add(1);
        combo6.getItems().add(2);
        combo6.getItems().add(3);
        combo6.getItems().add(4);
        combo6.getItems().add(5);
        combo6.getItems().add(6);
        combo6.getItems().add(7);
        combo6.getItems().add(8);
        combo6.getItems().add(9);
        combo6.getItems().add(10);
        combo6.getItems().add(11);
        combo6.getItems().add(12);

        comboA.getItems().add(1);
        comboA.getItems().add(2);
        comboA.getItems().add(3);
        comboA.getItems().add(4);
        comboA.getItems().add(5);
        comboA.getItems().add(6);
        comboA.getItems().add(7);
        comboA.getItems().add(8);
        comboA.getItems().add(9);
        comboA.getItems().add(10);
        comboA.getItems().add(11);
        comboA.getItems().add(12);


        engine = webview1.getEngine();
        engine2 = webview2.getEngine();
        int year = combo2.getValue();
        int year2 = comboZ.getValue();
        if(combo1.getValue().equals("Yearly")){
            loadPage(year);
            Label1.setVisible(true);
            combo1.setVisible(true);
            combo2.setVisible(true);
            combo5.setVisible(false);
            combo6.setVisible(false);
            Label11111.setVisible(false);
            Label12.setVisible(false);
            loadPageOrderYear(year2);
            comboZ.setVisible(true);
            comboX.setVisible(false);
            comboA.setVisible(false);
            la1.setVisible(false);
            la3.setVisible(false);
            la2.setVisible(true);
        }
        else if(combo1.getValue().equals("Monthly")){

            Label1.setVisible(false);
            combo2.setVisible(false);
            combo5.setVisible(true);
            combo6.setVisible(true);
            Label11111.setVisible(true);
            Label12.setVisible(true);
        }

        try{
            String SelectString = "SELECT * FROM getHighestCustomerRating()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String name = rs.getString(1);
                String surname = rs.getString(2);
                customer.setText(name + " " + surname);
            }
        } catch (SQLException ex){

        }

        try{
            String SelectString = "SELECT * FROM mostReservedTable()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                table.setText(String.valueOf(num));
            }
        } catch (SQLException ex){

        }

        try{
            String SelectString = "SELECT * FROM averageOrderValue()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                float num = rs.getFloat(1);
                order.setText(String.valueOf(num) + "€");
            }
        } catch (SQLException ex){

        }

        try {
            String SelectString = "SELECT totalRevenueToday()";
            fillTable = dbConnection.prepareStatement(SelectString);
            ResultSet rs = fillTable.executeQuery();

            if (rs.next()) {
                int num = rs.getInt(1);
                mone.setText(String.valueOf(num) + "€");
                System.out.println("number: " + num);
            } else {
                mone.setText("0");
                System.out.println("No result returned");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try{
            String SelectString = "SELECT * FROM getCountCustomers()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                total.setText(String.valueOf(num));
            }
        } catch (SQLException ex){

        }

        try{
            String SelectString = "SELECT * FROM getBestItem()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String num = rs.getString(2);
                tsi.setText(num);
            }
        } catch (SQLException ex){

        }

        try{
            String SelectString = "SELECT * FROM leastReservedTable()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                int num = rs.getInt(1);
                lrt.setText(String.valueOf(num));
            }
        } catch (SQLException ex){

        }

        try{
            String SelectString = "SELECT * FROM getTopEmployee()";
            fillTable = dbConnection.prepareStatement(SelectString);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();
            while(rs.next()){
                String num = rs.getString(2);
                per.setText(num);
            }
        } catch (SQLException ex){

        }
    }


    public void select(){
        int year = combo2.getValue();
        int year2 = combo5.getValue();
        int month = combo6.getValue();
        if(combo1.getValue().equals("Yearly")){
            loadPage(year);
            Label1.setVisible(true);
            combo2.setVisible(true);
            combo5.setVisible(false);
            combo6.setVisible(false);
            Label11111.setVisible(false);
            Label12.setVisible(false);


        }
        else if(combo1.getValue().equals("Monthly")){
            loadPageMonthly(year2,month);
            Label1.setVisible(false);
            combo2.setVisible(false);
            combo5.setVisible(true);
            combo6.setVisible(true);
            Label11111.setVisible(true);
            Label12.setVisible(true);


        }
    }
    public void select2(){
        int year = combo2.getValue();
        int year2 = combo5.getValue();
        int month = combo6.getValue();
        if(combo1.getValue().equals("Yearly")){
            loadPage(year);
            Label1.setVisible(true);
            combo2.setVisible(true);
            combo5.setVisible(false);
            combo6.setVisible(false);
            Label11111.setVisible(false);
            Label12.setVisible(false);


        }
        else if(combo1.getValue().equals("Monthly")){
            loadPageMonthly(year2,month);
            Label1.setVisible(false);
            combo2.setVisible(false);
            combo5.setVisible(true);
            combo6.setVisible(true);
            Label11111.setVisible(true);
            Label12.setVisible(true);


        }
    }
    public void select3(){
        int year = comboZ.getValue();
        int year2 = comboX.getValue();
        int month = comboA.getValue();
        if(comboY.getValue().equals("Yearly")){
            loadPageOrderYear(year);
            comboZ.setVisible(true);
            comboX.setVisible(false);
            comboA.setVisible(false);
            la1.setVisible(false);
            la3.setVisible(false);
            la2.setVisible(true);
        }else if(comboY.getValue().equals("Monthly")){
            loadPageOrderMonth(year2,month);
            comboZ.setVisible(false);
            comboX.setVisible(true);
            comboA.setVisible(true);
            la1.setVisible(true);
            la3.setVisible(true);
            la2.setVisible(false);
        }
    }
    public void select4(){
        int year = comboZ.getValue();
        int year2 = comboX.getValue();
        int month = comboA.getValue();
        if(comboY.getValue().equals("Yearly")){
            loadPageOrderYear(year);
            comboZ.setVisible(true);
            comboX.setVisible(false);
            comboA.setVisible(false);
            la1.setVisible(false);
            la3.setVisible(false);
            la2.setVisible(true);
        }else if(comboY.getValue().equals("Monthly")){
            loadPageOrderMonth(year2,month);
            comboZ.setVisible(false);
            comboX.setVisible(true);
            comboA.setVisible(true);
            la1.setVisible(true);
            la3.setVisible(true);
            la2.setVisible(false);
        }
    }
    public void loadPageOrderYear(int year){
        int[] nums = new int[12];
        int[] months = new int[12];
        Arrays.fill(nums, 0);
        Arrays.fill(months, 0);

        try {
            String selectString = "SELECT * FROM getYearlyOrders(?)";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.setInt(1, year);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();

            int i = 0;
            while(rs.next() && i<12) {
                int month = rs.getInt(1);
                int num = rs.getInt(2);
                nums[month - 1] = num;
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (engine2 != null) {
            String arrayData = Arrays.toString(nums).replace("[", "").replace("]", "");

            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Monthly Reservations Chart</title>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                    body {
                        margin: 0;
                        padding: 0px;
                        background: white;
                        font-family: Arial, sans-serif;
                    }
                    #chartContainer {
                        width: 600px;
                        height: 300px;
                        background: white;
                        border-radius: 10px;
                        margin-top: 50px;
                        padding: 0px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    }
                    .chart-title {
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        color: #333;
                    }
                </style>
            </head>
            <body>
                <div id="chartContainer">
                    <canvas id="myChart1" width="600px" height="300px"></canvas>
                </div>
                <script>
                    const ctx = document.getElementById('myChart1').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: [
                                'January', 'February', 'March', 'April', 'May', 'June',
                                'July', 'August', 'September', 'October', 'November', 'December'
                            ],
                            datasets: [{
                                label: 'Number of Orders',
                                data: [%s],
                                backgroundColor: [
                                    '#FFB3BA', '#B3E0FF', '#FFF6B3', '#B3FFDA', '#B3D9FF', '#B3C7D9',
                                    '#FFB3D9', '#E6B3FF', '#FFE0B3', '#B3E6D9', '#B3D1E0', '#FFB3B3'
                                ],
                                borderColor: [
                                    '#FF6B6B', '#4ECDC4', '#FFD166', '#06D6A0', '#118AB2', '#073B4C',
                                    '#EF476F', '#7209B7', '#F8961E', '#43AA8B', '#277DA1', '#F94144'
                                ],
                                borderWidth: 2
                            }]
                        },
                        options: {
                            responsive: false,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top',
                                },
                                tooltip: {
                                    enabled: true
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Number of Reservations'
                                    },
                                    ticks: {
                                        stepSize: 1
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Months'
                                    },
                                    ticks: {
                                        autoSkip: false,
                                        maxRotation: 45
                                    }
                                }
                            }
                        }
                    });
                </script>
            </body>
            </html>""".formatted(arrayData);

            System.out.println("HTML Content generated with data: " + arrayData);
            engine2.loadContent(htmlContent);
        } else {
            System.out.println("WebEngine is not initialized");
        }
    }
    public void loadPageOrderMonth(int year, int month){
        int[] dailyData = new int[31];
        Arrays.fill(dailyData, 0);

        try {
            String selectString = "SELECT * FROM getMonthlyOrders(?,?)";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.setInt(1, year);
            fillTable.setInt(2, month);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();

            while(rs.next()) {
                int day = rs.getInt(1);
                int num = rs.getInt(2);
                if (day >= 1 && day <= 31) {
                    dailyData[day - 1] = num;
                }
                System.out.println("Day " + day + ": " + num + " reservations");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (engine2 != null) {
            String arrayData = Arrays.toString(dailyData).replace("[", "").replace("]", "");
            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Monthly Reservations Chart</title>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                    body {
                        margin: 0;
                        padding: 0px;
                        background: white;
                        font-family: Arial, sans-serif;
                    }
                    #chartContainer {
                        width: 600px;
                        height: 300px;
                        background: white;
                        border-radius: 10px;
                        margin-top: 50px;
                        padding: 0px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    }
                    .chart-title {
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        color: #333;
                    }
                </style>
            </head>
            <body>
                <div id="chartContainer">
                    <canvas id="myChart1" width="600px" height="300px"></canvas>
                </div>
                <script>
                    const ctx = document.getElementById('myChart1').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'],
                            datasets: [{
                                label: 'Number of Orders',
                                data: [%s],
                                backgroundColor: [
                                    '#FFB3BA', '#B3E0FF', '#FFF6B3', '#B3FFDA', '#B3D9FF', '#B3C7D9',
                                    '#FFB3D9', '#E6B3FF', '#FFE0B3', '#B3E6D9', '#B3D1E0', '#FFB3B3'
                                ],
                                borderColor: [
                                    '#FF6B6B', '#4ECDC4', '#FFD166', '#06D6A0', '#118AB2', '#073B4C',
                                    '#EF476F', '#7209B7', '#F8961E', '#43AA8B', '#277DA1', '#F94144'
                                ],
                                borderWidth: 2
                            }]
                        },
                        options: {
                            responsive: false,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top',
                                },
                                tooltip: {
                                    enabled: true
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Number of Reservations'
                                    },
                                    ticks: {
                                        stepSize: 1
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Days'
                                    },
                                    ticks: {
                                        autoSkip: false,
                                        maxRotation: 45
                                    }
                                }
                            }
                        }
                    });
                </script>
            </body>
            </html>""".formatted(arrayData);

            System.out.println("HTML Content generated with data: " + arrayData);
            engine2.loadContent(htmlContent);
        } else {
            System.out.println("WebEngine is not initialized");
        }
    }
    public void loadPage(int year){
        int[] nums = new int[12];
        int[] months = new int[12];
        Arrays.fill(nums, 0);
        Arrays.fill(months, 0);
        try {
            String selectString = "SELECT * FROM getYearlyReservations(?)";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.setInt(1, year);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();

            int i = 0;
            while(rs.next() && i<12) {
                int month = rs.getInt(1);
                int num = rs.getInt(2);
                nums[month - 1] = num;
                System.out.println("Month " + (i + 1) + ": " + nums[i]);
                i++;
            }
            System.out.println("Full array: " + Arrays.toString(nums));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (engine != null) {
            String arrayData = Arrays.toString(nums).replace("[", "").replace("]", "");

            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Monthly Reservations Chart</title>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                    body {
                        margin: 0;
                        padding: 0px;
                        background: white;
                        font-family: Arial, sans-serif;
                    }
                    #chartContainer {
                        width: 620px;
                        height: 300px;
                        background: white;
                        border-radius: 10px;
                        margin-top: 50px;
                        padding: 0px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    }
                    .chart-title {
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        color: #333;
                    }
                </style>
            </head>
            <body>
                <div id="chartContainer">
                    <canvas id="myChart1" width="620px" height="300px"></canvas>
                </div>
                <script>
                    const ctx = document.getElementById('myChart1').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: [
                                'January', 'February', 'March', 'April', 'May', 'June',
                                'July', 'August', 'September', 'October', 'November', 'December'
                            ],
                            datasets: [{
                                label: 'Number of Reservations',
                                data: [%s],
                                backgroundColor: [
                                    '#FFB3BA', '#B3E0FF', '#FFF6B3', '#B3FFDA', '#B3D9FF', '#B3C7D9',
                                    '#FFB3D9', '#E6B3FF', '#FFE0B3', '#B3E6D9', '#B3D1E0', '#FFB3B3'
                                ],
                                borderColor: [
                                    '#FF6B6B', '#4ECDC4', '#FFD166', '#06D6A0', '#118AB2', '#073B4C',
                                    '#EF476F', '#7209B7', '#F8961E', '#43AA8B', '#277DA1', '#F94144'
                                ],
                                borderWidth: 2
                            }]
                        },
                        options: {
                            responsive: false,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top',
                                },
                                tooltip: {
                                    enabled: true
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Number of Reservations'
                                    },
                                    ticks: {
                                        stepSize: 1
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Months'
                                    },
                                    ticks: {
                                        autoSkip: false,
                                        maxRotation: 45
                                    }
                                }
                            }
                        }
                    });
                </script>
            </body>
            </html>""".formatted(arrayData);

            System.out.println("HTML Content generated with data: " + arrayData);
            engine.loadContent(htmlContent);
        } else {
            System.out.println("WebEngine is not initialized");
        }
    }
    public void loadPageMonthly(int year,int month){
        int[] dailyData = new int[31];
        Arrays.fill(dailyData, 0);

        try {
            String selectString = "SELECT * FROM getMonthlyReservations(?,?)";
            fillTable = dbConnection.prepareStatement(selectString);
            fillTable.setInt(1, year);
            fillTable.setInt(2, month);
            fillTable.executeQuery();
            ResultSet rs = fillTable.getResultSet();

            while(rs.next()) {
                int day = rs.getInt(1);
                int num = rs.getInt(2);
                if (day >= 1 && day <= 31) {
                    dailyData[day - 1] = num;
                }
                System.out.println("Day " + day + ": " + num + " reservations");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (engine != null) {
            String arrayData = Arrays.toString(dailyData).replace("[", "").replace("]", "");
            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Monthly Reservations Chart</title>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                    body {
                        margin: 0;
                        padding: 0px;
                        background: white;
                        font-family: Arial, sans-serif;
                    }
                    #chartContainer {
                        width: 620px;
                        height: 300px;
                        background: white;
                        border-radius: 10px;
                        margin-top: 50px;
                        padding: 0px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    }
                    .chart-title {
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        color: #333;
                    }
                </style>
            </head>
            <body>
                <div id="chartContainer">
                    <canvas id="myChart1" width="620px" height="300px"></canvas>
                </div>
                <script>
                    const ctx = document.getElementById('myChart1').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'],
                            datasets: [{
                                label: 'Number of Reservations',
                                data: [%s],
                                backgroundColor: [
                                    '#FFB3BA', '#B3E0FF', '#FFF6B3', '#B3FFDA', '#B3D9FF', '#B3C7D9',
                                    '#FFB3D9', '#E6B3FF', '#FFE0B3', '#B3E6D9', '#B3D1E0', '#FFB3B3'
                                ],
                                borderColor: [
                                    '#FF6B6B', '#4ECDC4', '#FFD166', '#06D6A0', '#118AB2', '#073B4C',
                                    '#EF476F', '#7209B7', '#F8961E', '#43AA8B', '#277DA1', '#F94144'
                                ],
                                borderWidth: 2
                            }]
                        },
                        options: {
                            responsive: false,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top',
                                },
                                tooltip: {
                                    enabled: true
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Number of Reservations'
                                    },
                                    ticks: {
                                        stepSize: 1
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Days'
                                    },
                                    ticks: {
                                        autoSkip: false,
                                        maxRotation: 45
                                    }
                                }
                            }
                        }
                    });
                </script>
            </body>
            </html>""".formatted(arrayData);

            System.out.println("HTML Content generated with data: " + arrayData);
            engine.loadContent(htmlContent);
        } else {
            System.out.println("WebEngine is not initialized");
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
            stage = (Stage) scene1.getScene().getWindow();
            System.out.println("You exited the app");
            stage.close();
        }
    }
    public void switchToSceneCustomer(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
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
            ex.printStackTrace();
            System.out.println("Cannot change scenes");
        }
    }
    public void switchToMenuScreen(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menuScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
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
            Parent root = FXMLLoader.load(getClass().getResource("ordersScreen.fxml"));
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
            ex.printStackTrace();
        }
    }
    public void guide(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("guideScreen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex){
            System.out.println("Cannot change scenes");
            ex.printStackTrace();
        }
    }

}