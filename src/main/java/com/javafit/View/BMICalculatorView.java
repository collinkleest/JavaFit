package com.javafit.View;

import com.javafit.Controller.BMICalculatorController;
import com.javafit.Controller.DashController;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bson.Document;

/**
 *
 * @author Connell Boyce and Collin Kleest
 */
public class BMICalculatorView {

    private final Scene scene;
    private MongoClient mongoClient;
    private MongoDatabase usersDB;
    private Document userObj;
    private String weightString;
    private String heightString;
    private BMICalculatorController bmiController;

    /**
     * Starts the stage
     */
    private void start() {
        Stage primaryStage = new Stage();
        primaryStage.setScene(this.scene);
        primaryStage.setTitle("JavaFit BMI Calculator");
        primaryStage.show();
    }

    /**
     * Constructor
     * @param userName current username
     */
    public BMICalculatorView(String userName) {
        //Initializations;
        bmiController = new BMICalculatorController(this);
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(10);
        gP.setVgap(10);
        gP.setPadding(new Insets(25, 25, 25, 25));
        this.initializeMongoConnection();
        this.scene = new Scene(gP, 300, 300);
        this.queryWeight(userName);
        this.queryHeight(userName);
        

        //Initial Style Setup
        Text scenetitle = new Text("BMI Calculator");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.getStyleClass().setAll("strong", "h1");
        gP.add(scenetitle, 0, 0, 2, 1);

        //Current Weight Label
        Label currentWeight = new Label("Your Weight:");
        currentWeight.getStyleClass().setAll("strong", "lead");
        gP.add(currentWeight, 0, 1);

        //Current Weight Display Field
        TextField weightDisplay = new TextField(weightString);
        gP.add(weightDisplay, 1, 1);

        //Current Height Label
        Label currentHeight = new Label("Your Height:");
        currentHeight.getStyleClass().setAll("strong", "lead");
        gP.add(currentHeight, 0, 2);

        //Current Height Field
        TextField heightDisplay = new TextField(heightString);
        gP.add(heightDisplay, 1, 2);

        //Calculate Button Setup and Event Handler
        Button calculate = new Button("Calculate");
        calculate.setPrefHeight(40);
        calculate.setDefaultButton(true);
        calculate.setPrefWidth(100);
        calculate.setAlignment(Pos.CENTER);
        calculate.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(calculate, 0, 3);
        calculate.setOnAction((ActionEvent event) -> {
            try {
                Double.parseDouble(heightDisplay.getText());
                Double.parseDouble(weightDisplay.getText());
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Your BMI");
                alert.setHeaderText("Your BMI is:");

                double calculatedBMI = bmiController.calculateBMI(heightDisplay.getText(), weightDisplay.getText());

                alert.setContentText(calculatedBMI + "");
                alert.initOwner(gP.getScene().getWindow());
                alert.show();
            } catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid");
                alert.setHeaderText("Invalid");
                alert.setContentText("Enter numbers. Height should be (feet.inches)");
                alert.initOwner(gP.getScene().getWindow());
                alert.show();
            }
        });

        //Go Back Button Setup and Event Handler
        Button openDashboard = new Button("Go Back");
        openDashboard.setPrefHeight(40);
        openDashboard.setDefaultButton(true);
        openDashboard.setPrefWidth(100);
        openDashboard.setAlignment(Pos.CENTER);
        openDashboard.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(openDashboard, 1, 3);
        openDashboard.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) openDashboard.getScene().getWindow();
            stage.close();
            this.closeMongoConnection();
            try {
                DashController dashboardView = new DashController(userName);
            } catch (IOException ex) {
                Logger.getLogger(BMICalculatorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Bootstrap CSS
        this.scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //Open window
        this.start();
    }

    private void queryWeight(String userName) {
        FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", "Boyce"));
        
        this.userObj = iterable.first();
        this.weightString = "" + this.userObj.get("weight");
    }
    
    private void queryHeight(String userName) {
        FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", "Boyce"));
        
        this.userObj = iterable.first();
        this.heightString = "" + this.userObj.get("height");
    }

    private void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.usersDB = mongoClient.getDatabase("USERS");
    }

    public void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }
}
