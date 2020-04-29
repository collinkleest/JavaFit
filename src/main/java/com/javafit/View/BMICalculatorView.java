package com.javafit.View;

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

/**
 *
 * @author Connell Boyce and Collin Kleest
 */
public class BMICalculatorView {
    private Scene scene;
    
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
     */
    public BMICalculatorView() {
        //Initializations
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(10);
        gP.setVgap(10);
        gP.setPadding(new Insets(25, 25, 25, 25));
        this.scene = new Scene(gP, 300, 300);
        
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
        TextField weightDisplay = new TextField();
        gP.add(weightDisplay, 1, 1);
        weightDisplay.setEditable(false);
        
        //Current Height Label
        Label currentHeight = new Label("Your Height:");
        currentHeight.getStyleClass().setAll("strong", "lead");
        gP.add(currentHeight, 0, 2);

        //Current Height Field
        TextField heightDisplay = new TextField();
        gP.add(heightDisplay, 1, 2);
        heightDisplay.setEditable(false);
        
        //Calculate Button Setup and Event Handler
        Button calculate = new Button("Calculate");
        calculate.setPrefHeight(40);
        calculate.setDefaultButton(true);
        calculate.setPrefWidth(100);
        calculate.setAlignment(Pos.CENTER);
        calculate.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(calculate, 0, 3);
        calculate.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Your BMI");
            alert.setHeaderText("Your BMI is:");
            alert.setContentText("(bmi will go here)");
            alert.initOwner(gP.getScene().getWindow());
            alert.show();
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
            
            DashboardView dashboardView = new DashboardView();
        });
        
        //Bootstrap CSS
        this.scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //Open window
        this.start();
    }
}
