package com.javafit.View;

import com.javafit.Controller.DashController;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

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
import org.bson.Document;

/**
 *
 * @author Connell Boyce and Collin Kleest
 */
public class ReportView {

    private Scene scene;
    private String userName;
    private MongoClient mongoClient;
    private MongoDatabase usersDB;
    private Document userObj;

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
     *
     * @param userName current username
     */
    public ReportView(String userName) {
        this.userName = userName;
        //Initializations
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.CENTER);
        gP.setHgap(10);
        gP.setVgap(10);
        gP.setPadding(new Insets(25, 25, 25, 25));
        this.scene = new Scene(gP, 750, 600);

        this.initializeMongoConnection();
        this.getUserObj(userName);
        this.closeMongoConnection();

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
        currentWeightDisplay.setEditable(true);
        currentWeightDisplay.setPrefWidth(150);
        this.initializeMongoConnection();
        if (this.userObj.get("currentWeight") == null) {
            currentWeightDisplay.setText(this.userObj.get("weight").toString());
        } else {
            currentWeightDisplay.setText(this.userObj.get("currentWeight").toString());
        }

        //Change Weight Button
        Button changeWeightButton = new Button("Change");
        changeWeightButton.setPrefHeight(40);
        changeWeightButton.setDefaultButton(true);
        changeWeightButton.setPrefWidth(100);
        changeWeightButton.setAlignment(Pos.CENTER);
        changeWeightButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(changeWeightButton, 2, 1);
        changeWeightButton.setOnAction((ActionEvent event) -> {
            this.initializeMongoConnection();
            BasicDBObject queryObj = new BasicDBObject();
            queryObj.put("username", this.userName);

            BasicDBObject newDoc = new BasicDBObject();

            if (!currentWeightDisplay.getText().isEmpty()) {
                newDoc.put("currentWeight", currentWeightDisplay.getText());
            }

            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDoc);

            this.usersDB.getCollection("USERS").updateOne(queryObj, updateObject);
            this.closeMongoConnection();
        });

        //Current Weight Goal Label
        Label targetWeight = new Label("Target Weight:");
        targetWeight.getStyleClass().setAll("strong", "lead");
        gP.add(targetWeight, 0, 2);

        //Current Weight Goal Field
        TextField targetWeightDisplay = new TextField();
        gP.add(targetWeightDisplay, 1, 2);
        targetWeightDisplay.setEditable(true);
        targetWeightDisplay.setPrefWidth(150);
        this.initializeMongoConnection();
        if (userObj.get("goal") != null) {
            targetWeightDisplay.setText(userObj.get("goal").toString());
        }

        //Change Goal Button
        Button changeGoalButton = new Button("Change");
        changeGoalButton.setPrefHeight(40);
        changeGoalButton.setDefaultButton(true);
        changeGoalButton.setPrefWidth(100);
        changeGoalButton.setAlignment(Pos.CENTER);
        changeGoalButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(changeGoalButton, 2, 2);
        changeGoalButton.setOnAction((ActionEvent event) -> {
            this.initializeMongoConnection();
            BasicDBObject queryObj = new BasicDBObject();
            queryObj.put("username", this.userName);

            BasicDBObject newDoc = new BasicDBObject();

            if (!targetWeightDisplay.getText().isEmpty()) {
                newDoc.put("goal", targetWeightDisplay.getText());
            }

            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDoc);

            this.usersDB.getCollection("USERS").updateOne(queryObj, updateObject);
            this.closeMongoConnection();
        });

        //Weight Lost Label
        Label weightLost = new Label("Total Weight Lost:");
        weightLost.getStyleClass().setAll("strong", "lead");
        gP.add(weightLost, 0, 3);

        //Weight Lost Field
        TextField weightDisplay = new TextField();
        gP.add(weightDisplay, 1, 3);
        weightDisplay.setEditable(false);
        weightDisplay.setPrefWidth(150);
        if (userObj.get("currentWeight") != null) {
            weightDisplay.setText((Double.parseDouble(userObj.get("weight").toString()) - Double.parseDouble(userObj.get("currentWeight").toString()) + " lbs"));
        }

        //Weight Remaining Label
        Label weightToGo = new Label("Weight To Go:");
        weightToGo.getStyleClass().setAll("strong", "lead");
        gP.add(weightToGo, 0, 4);

        //Weight Remaining Field
        TextField weightToGoDisplay = new TextField();
        gP.add(weightToGoDisplay, 1, 4);
        weightToGoDisplay.setEditable(false);
        weightToGoDisplay.setPrefWidth(150);
        if (userObj.get("currentWeight") != null && userObj.get("goal") != null) {
            weightToGoDisplay.setText((Double.parseDouble(userObj.get("currentWeight").toString()) - Double.parseDouble(userObj.get("goal").toString()) + " lbs"));
        }

        //Creating Pie Chart
        PieChart.Data data[] = new PieChart.Data[2];
        String keyTerms[] = {"Completed", "To Go"};
        double iAmTemporary[] = {0, 1};

        if (userObj.get("currentWeight") != null && userObj.get("goal") != null) {
            double actualValues[] = {Double.parseDouble(userObj.get("weight").toString()) - Double.parseDouble(userObj.get("currentWeight").toString()), Double.parseDouble(userObj.get("currentWeight").toString()) - Double.parseDouble(userObj.get("goal").toString())};
            for (int i = 0; i < 2; i++) {
                data[i] = new PieChart.Data(keyTerms[i], actualValues[i]);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                data[i] = new PieChart.Data(keyTerms[i], iAmTemporary[i]);
            }
        }
        PieChart pie_chart = new PieChart(FXCollections.observableArrayList(data));
        pie_chart.setPrefSize(300, 300);
        gP.add(pie_chart, 0, 5, 2, 1);

        // refresh ui elements
        Label refresh = new Label("Click Refresh to See New Changes");
        refresh.getStyleClass().setAll("strong", "lead");
        gP.add(refresh, 0, 7);
        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(refreshBtn, 1, 7);
        
        //refresh page
        refreshBtn.setOnAction((ActionEvent event) -> {
        	Stage stage = (Stage) refreshBtn.getScene().getWindow();
            stage.close();
            ReportView rVV = new ReportView(this.userName);
        });
        
        //Go Back Button and Event Handler
        Button goBackButton = new Button("Go Back");
        goBackButton.setPrefHeight(40);
        goBackButton.setDefaultButton(true);
        goBackButton.setPrefWidth(100);
        goBackButton.setAlignment(Pos.CENTER);
        goBackButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(goBackButton, 0, 6);
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

    private void getUserObj(String uName) {
        FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", uName));
        this.userObj = iterable.first();
    }

}
