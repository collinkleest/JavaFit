package com.javafit.Controller;

import java.io.IOException;

import org.bson.Document;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {
	private String userName;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	private Stage settingsStage;
    private Scene settingsScene;
	private Document userObj;
    
    //fxml ui elements
    private TextField userNameField;
    private TextField weightField;
    private TextField heightField;
    private PasswordField passField;
    private PasswordField passCField;
    private JFXCheckBox gMuscle;
    private JFXCheckBox gStrength;
    private JFXCheckBox loseWeight;
    private JFXCheckBox home;
    private JFXCheckBox gym;
    
	public SettingsController(String uName) throws IOException {
		/*
		 * Instantiate All Feels
		 * FXML Elements, and Mongo Connection Fields
		 * Allows for our class methods to accessFields
		 */
		this.userName = uName;
        this.settingsStage = new Stage();
        this.settingsScene = new Scene(loadFXML(), 505, 768);
        this.settingsStage.setScene(this.settingsScene);
        this.userNameField = (TextField) this.settingsScene.lookup("#userField");
        this.weightField = (TextField) this.settingsScene.lookup("#weightField");
        this.heightField = (TextField) this.settingsScene.lookup("#hField");
        this.passField = (PasswordField) this.settingsScene.lookup("#passField");
        this.passCField = (PasswordField) this.settingsScene.lookup("#passCField");
        this.gMuscle = (JFXCheckBox) this.settingsScene.lookup("#gMuscle");
        this.gStrength = (JFXCheckBox) this.settingsScene.lookup("#gStrength");
        this.loseWeight = (JFXCheckBox) this.settingsScene.lookup("#loseWeight");
        this.home = (JFXCheckBox) this.settingsScene.lookup("#home");
        this.gym = (JFXCheckBox) this.settingsScene.lookup("#gym");
        
        // make initial query for form info
        this.initializeMongoConnection();
        this.getUserObj(uName);
        this.fillFields();
        
         


        JFXButton submitBtn = (JFXButton) this.settingsScene.lookup("#submitBtn");
        JFXButton backBtn = (JFXButton) this.settingsScene.lookup("#dashBtn");
        backBtn.setOnAction((ActionEvent event) -> {
        	Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            try {
				DashController dC = new DashController(this.userName);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
		
		this.settingsStage.show();
	}
	
	private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SettingsController.class.getResource("/settings.fxml"));
        return fxmlLoader.load();
    }
	
	private void fillFields(){
		this.userNameField.setText((String) this.userObj.get("username"));
		this.weightField.setText((String) this.userObj.get("weight"));
		this.heightField.setText((String) this.userObj.get("height"));
		this.gMuscle.setSelected((Boolean) this.userObj.get("gainMuscle"));
		this.loseWeight.setSelected((Boolean) this.userObj.get("loseWeight"));
		this.gStrength.setSelected((Boolean) this.userObj.get("gainStrength"));
		this.home.setSelected((Boolean) this.userObj.get("home"));
		this.gym.setSelected((Boolean)this.userObj.get("gym"));
		
	}
	
	private void getUserObj(String uName) {
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
		this.userObj = iterable.first();
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
