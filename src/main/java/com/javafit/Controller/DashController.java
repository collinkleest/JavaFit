package com.javafit.Controller;

//class imports
import java.io.IOException;
import java.util.HashMap;

import org.bson.Document;

import com.javafit.View.BMICalculatorView;
import com.javafit.View.LoginView;
import com.javafit.View.RegistrationView;
import com.javafit.View.ReportView;
import com.javafit.View.RoutineView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static java.lang.Math.round;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashController {

	//class attributes includes FXML elements
    private String userName;
    private String weightString;
    private Stage dashStage;
    private Scene dashScene;
    private MongoClient mongoClient;
    private MongoDatabase usersDB;
    private Document userObj;
    private HashMap<String, Double> macros;
    private Double totalCalories;
    private Integer weightNum;

    public DashController(String uName) throws IOException {
        this.userName = uName;
        this.dashStage = new Stage();
        this.dashScene = new Scene(loadFXML(), 1100, 658);
        this.dashStage.setScene(this.dashScene);
        this.dashStage.setTitle("DashBoard");

        Label welcomeString = (Label) dashScene.lookup("#welcomeString");
        welcomeString.setText("Welcome " + uName + "!");
        this.initializeMongoConnection();
        this.queryWeight();

        if ((boolean) this.userObj.get("gainStrength")) {
            this.totalCalories = (double) (((Integer.parseInt(this.weightString)) * 17.5) + 500);
        } else if ((boolean) this.userObj.get("gainMuscle")) {
            this.totalCalories = (double) (((Integer.parseInt(this.weightString)) * 17) + 500);
        } else {
            this.totalCalories = (double) (((Integer.parseInt(this.weightString)) * 17) - 500);
        }

        this.weightNum = Integer.parseInt(this.weightString);
        Label weightLabel = (Label) this.dashScene.lookup("#weight");
        weightLabel.setText(this.weightString);

        Label caloriesLabel = (Label) this.dashScene.lookup("#calories");
        caloriesLabel.setText("" + (this.totalCalories));
        this.determineMacros();

        PieChart pC = (PieChart) this.dashScene.lookup("#piechart");
        double proteins = round(this.macros.get("protein") * 100.0) / 100.0;
        double carb = round(this.macros.get("carbs") * 100.0) / 100.0;
        double fat = round(this.macros.get("fats") * 100.0) / 100.0;

        PieChart.Data protein = new PieChart.Data("Protein (" + proteins + "g)", this.macros.get("protein"));
        PieChart.Data carbs = new PieChart.Data("Carbs (" + carb + "g)", this.macros.get("carbs"));
        PieChart.Data fats = new PieChart.Data("Fats (" + fat + "g)", this.macros.get("fats"));

        pC.getData().add(protein);
        pC.getData().add(carbs);
        pC.getData().add(fats);
        pC.setLabelsVisible(false);

        JFXCheckBox gMuscle = (JFXCheckBox) this.dashScene.lookup("#gMuscle");
        JFXCheckBox gStrength = (JFXCheckBox) this.dashScene.lookup("#gStrength");
        JFXCheckBox lWeight = (JFXCheckBox) this.dashScene.lookup("#lWeight");
        gMuscle.setSelected((Boolean) this.userObj.get("gainMuscle"));
        gStrength.setSelected((Boolean) this.userObj.get("gainStrength"));
        lWeight.setSelected((Boolean) this.userObj.get("loseWeight"));

        JFXButton routineBtn = (JFXButton) this.dashScene.lookup("#routinebtn");
        routineBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) routineBtn.getScene().getWindow();
            stage.close();
            try {
                RoutineView routineView = new RoutineView(uName);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }
        });

        JFXButton settingsBtn = (JFXButton) this.dashScene.lookup("#actBtn");
        settingsBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) routineBtn.getScene().getWindow();
            stage.close();
            try {
                SettingsController sC = new SettingsController(this.userName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        JFXButton logOut = (JFXButton) this.dashScene.lookup("#logoutBtn");
        logOut.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) routineBtn.getScene().getWindow();
            stage.close();
            LoginView lV = new LoginView();
        });

        JFXButton reportBtn = (JFXButton) this.dashScene.lookup("#reportbtn");
        reportBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) routineBtn.getScene().getWindow();
            stage.close();

            ReportController reportController = new ReportController(uName);
        });
        JFXButton bmiBtn = (JFXButton) this.dashScene.lookup("#bmibtn");
        bmiBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) routineBtn.getScene().getWindow();
            stage.close();
            BMICalculatorView bmiView = new BMICalculatorView(uName);
        });

        this.dashStage.show();
    }

    private void queryWeight() {
        FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
        this.userObj = iterable.first();
        if(this.userObj.get("currentWeight") == null) {
            this.weightString = "" + this.userObj.get("weight");
        } else {
            this.weightString = "" + this.userObj.get("currentWeight");
        }
    }

    private void determineMacros() {
        this.macros = new HashMap<>();
        System.out.print(this.totalCalories);
        if ((boolean) this.userObj.get("gainMuscle")) {
            this.macros.put("protein", (double) (this.weightNum * 1));
            this.macros.put("fats", ((weightNum * 17.5) * .25) / 9);
            this.macros.put("carbs", ((this.totalCalories - ((weightNum * 4) + (this.totalCalories * .25))) / 4));
        } else if ((boolean) this.userObj.get("loseWeight")) {
            this.macros.put("protein", (double) (weightNum * 1));
            this.macros.put("fats", ((this.totalCalories * .2) / 9));
            this.macros.put("carbs", ((this.totalCalories - ((this.totalCalories * .2) + (weightNum * 4))) / 4));
        } else if ((boolean) this.userObj.get("gainStrength")) {
            this.macros.put("protein", (1.2 * weightNum));
            this.macros.put("fats", ((this.totalCalories * .3) / 9));
            this.macros.put("carbs", ((this.totalCalories - ((this.totalCalories * .3) + (weightNum * 4))) / 4));
        }
        System.out.println(macros.toString());
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

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationView.class.getResource("/dash.fxml"));
        return fxmlLoader.load();
    }

}
