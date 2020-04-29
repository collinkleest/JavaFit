package com.javafit.View;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
public class DashboardView {

    private final Scene scene;
    private boolean gainMuscle;
    private boolean gainStrength;
    private boolean loseWeight;

    private void start() {
        Stage primaryStage = new Stage();
        primaryStage.setScene(this.scene);
        primaryStage.setTitle("JavaFit Dashboard");
        primaryStage.show();
    }

    public DashboardView() {
        gainMuscle = false;
        gainStrength = false;
        loseWeight = false;

        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(10);
        gP.setVgap(10);
        gP.setPadding(new Insets(25, 25, 25, 25));
        this.scene = new Scene(gP, 1000, 600);

        Text scenetitle = new Text("Welcome to JavaFit");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.getStyleClass().setAll("strong", "h1");
        gP.add(scenetitle, 0, 0, 2, 1);

        Label currentWeight = new Label("Current Weight:");
        currentWeight.getStyleClass().setAll("strong", "lead");
        gP.add(currentWeight, 0, 1);

        TextField weightDisplay = new TextField();
        gP.add(weightDisplay, 1, 1);
        weightDisplay.setEditable(false);

        Button changeWeight = new Button("Update");
        changeWeight.setPrefHeight(40);
        changeWeight.setDefaultButton(true);
        changeWeight.setPrefWidth(100);
        changeWeight.setAlignment(Pos.CENTER);
        changeWeight.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(changeWeight, 2, 1);
        changeWeight.setOnAction((ActionEvent event) -> {
            System.out.println("I do nothing");
        });

        Label suggestedCalorie = new Label("Suggested Caloric Intake:");
        suggestedCalorie.getStyleClass().setAll("strong", "lead");
        gP.add(suggestedCalorie, 0, 2);

        TextField calorieDisplay = new TextField();
        gP.add(calorieDisplay, 1, 2);
        calorieDisplay.setEditable(false);

        Label goals = new Label("Goals: ");
        goals.getStyleClass().setAll("strong", "lead");
        gP.add(goals, 0, 3);

        TextField gainMuscleField = new TextField("Gain Muscle?: " + gainMuscle);
        gP.add(gainMuscleField, 1, 3);
        gainMuscleField.setEditable(false);

        TextField gainStrengthField = new TextField("Gain Strength?: " + gainStrength);
        gP.add(gainStrengthField, 1, 4);
        gainStrengthField.setEditable(false);

        TextField loseWeightField = new TextField("Lose Weight?: " + loseWeight);
        gP.add(loseWeightField, 1, 5);
        loseWeightField.setEditable(false);

        Button openBMICalc = new Button("Open BMI Calculator");
        openBMICalc.setPrefHeight(40);
        openBMICalc.setDefaultButton(true);
        openBMICalc.setPrefWidth(200);
        openBMICalc.setAlignment(Pos.CENTER);
        openBMICalc.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(openBMICalc, 0, 6);
        openBMICalc.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) openBMICalc.getScene().getWindow();
            stage.close();
            
            BMICalculatorView bmiCalcView = new BMICalculatorView();
        });

        Button openRoutines = new Button("Open Routine Manager");
        openRoutines.setPrefHeight(40);
        openRoutines.setDefaultButton(true);
        openRoutines.setPrefWidth(200);
        openRoutines.setAlignment(Pos.CENTER);
        openRoutines.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(openRoutines, 1, 6);
        openRoutines.setOnAction((ActionEvent event) -> {
            System.out.println("I do nothing");
        });

        Button openReport = new Button("Generate Report");
        openReport.setPrefHeight(40);
        openReport.setDefaultButton(true);
        openReport.setPrefWidth(200);
        openReport.setAlignment(Pos.CENTER);
        openReport.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(openReport, 2, 6);
        openReport.setOnAction((ActionEvent event) -> {
            System.out.println("I do nothing");
        });

        this.scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        this.start();
    }
}
