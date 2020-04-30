package com.javafit.View;

import com.javafit.Controller.DashController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
public class ReportView {
    private Scene scene;
    
    /**
     * Starts the display
     */
    private void start() {
        Stage primaryStage = new Stage();
        primaryStage.setScene(this.scene);
        primaryStage.setTitle("JavaFit Progress Report");
        primaryStage.show();
    }
    
    /**
     * Constructor
     * @param userName current username
     */
    public ReportView(String userName) {
        //Initializations
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(10);
        gP.setVgap(10);
        gP.setPadding(new Insets(25, 25, 25, 25));
        this.scene = new Scene(gP, 750, 600);
        
        //Initializing further visual components
        Text scenetitle = new Text("Your Progress Report");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.getStyleClass().setAll("strong", "h1");
        gP.add(scenetitle, 0, 0, 2, 1);
        
        //Current Weight Label
        Label currentWeight = new Label("Current Weight:");
        currentWeight.getStyleClass().setAll("strong", "lead");
        gP.add(currentWeight, 0, 1);
        
        //Current Weight Field
        TextField currentWeightDisplay = new TextField();
        gP.add(currentWeightDisplay, 1, 1);
        currentWeightDisplay.setEditable(false);
        currentWeightDisplay.setPrefWidth(150);  
        
        //Change Weight Button
        Button changeWeightButton = new Button("Change");
        changeWeightButton.setPrefHeight(40);
        changeWeightButton.setDefaultButton(true);
        changeWeightButton.setPrefWidth(100);
        changeWeightButton.setAlignment(Pos.CENTER);
        changeWeightButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(changeWeightButton, 2, 1);
        changeWeightButton.setOnAction((ActionEvent event) -> {
            //Code here
        });
        
        //Weight Lost Label
        Label weightLost = new Label("Total Weight Lost:");
        weightLost.getStyleClass().setAll("strong", "lead");
        gP.add(weightLost, 0, 2);

        //Weight Lost Field
        TextField weightDisplay = new TextField();
        gP.add(weightDisplay, 1, 2);
        weightDisplay.setEditable(false);
        weightDisplay.setPrefWidth(150);
        
        //Weight Remaining Label
        Label weightToGo = new Label("Weight To Go:");
        weightToGo.getStyleClass().setAll("strong", "lead");
        gP.add(weightToGo, 0, 3);

        //Weight Remaining Field
        TextField weightToGoDisplay = new TextField();
        gP.add(weightToGoDisplay, 1, 3);
        weightToGoDisplay.setEditable(false);
        weightToGoDisplay.setPrefWidth(150);   
        
        //Creating Pie Chart
        PieChart.Data data[] = new PieChart.Data[2];
        String keyTerms[] = {"Completed", "To Go"};        
        int iAmTemporary[] = {20, 30};        
        for (int i = 0; i < 2; i++) { 
            data[i] = new PieChart.Data(keyTerms[i], iAmTemporary[i]); 
        } 
        PieChart pie_chart = new PieChart(FXCollections.observableArrayList(data)); 
        pie_chart.setPrefSize(300, 300);
        gP.add(pie_chart, 0, 4, 2, 1);
        
        //Go Back Button and Event Handler
        Button goBackButton = new Button("Go Back");
        goBackButton.setPrefHeight(40);
        goBackButton.setDefaultButton(true);
        goBackButton.setPrefWidth(100);
        goBackButton.setAlignment(Pos.CENTER);
        goBackButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(goBackButton, 0, 5);
        goBackButton.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.close();
            
            try {
                DashController dashboardView = new DashController(userName);
            } catch (IOException ex) {
                Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Add CSS StyleSheet
        this.scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //Start the view
        this.start();
    }
}
