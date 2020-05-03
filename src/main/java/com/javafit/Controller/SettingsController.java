package com.javafit.Controller;
 
//class imports
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import org.bson.Document;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;


public class SettingsController {
	// general settings attributes including a user object, username, mongo db and client.
	private String userName;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;
	private Document userObj;
    
    //fxml ui elements
	private Stage settingsStage;
    private Scene settingsScene;
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
    private DatePicker dob;
    
	public SettingsController(String uName) throws IOException {
		/*
		 * Instantiate All Feels
		 * FXML Elements, and Mongo Connection Fields
		 * Allows for our class methods to accessFields
		 */
		this.userName = uName;
        this.settingsStage = new Stage();
        this.settingsStage.setTitle("User Settings");
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
        this.dob = (DatePicker) this.settingsScene.lookup("#dob");
        
        
        // start mongodb connection
        // grab the current users object from DB
        // make initial query for form info
        this.initializeMongoConnection();
        this.getUserObj(uName);
        this.fillFields();
        
         

        // submit button action event
        // starts a mongo connection, uses update account function to update the account fields
        // shows an alert, refills the field
        JFXButton submitBtn = (JFXButton) this.settingsScene.lookup("#submitBtn");
        submitBtn.setOnAction((ActionEvent event) -> {
        	this.initializeMongoConnection();
        	this.updateAccount();
        	this.showAlert(AlertType.CONFIRMATION, this.settingsScene.getWindow(), "Update Successful", "Your Update has been successful!");
        	this.getUserObj(uName);
        	this.fillFields();
        	this.closeMongoConnection();
        });
        
        /*
         * Back btn, action event
         * sends user back to dashboard
         */
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
	
	/*
	 * Loads FXML view from project resources folder.
	 * returns an FXML document to controller
	 */
	private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SettingsController.class.getResource("/settings.fxml"));
        return fxmlLoader.load();
    }
	
	
	/*
	 * Makes a database query to update the current user with the fields on the settings page
	 * Even if fields aren't filled and left default, query will still occur, and user object will stay the same.
	 * Method gets activated by the submit button.
	 */
	private void updateAccount() {
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("username", this.userName);
		
		BasicDBObject newDoc = new BasicDBObject();
		if (!this.userNameField.getText().isEmpty()) {
			newDoc.put("username", this.userNameField.getText());
		}
		if (!this.heightField.getText().isEmpty()) {
			newDoc.put("height", this.heightField.getText());
		}
		if(!this.weightField.getText().isEmpty()) {
			newDoc.put("weight", this.weightField.getText());
		}
		if(!this.passField.getText().isEmpty() || !this.passCField.getText().isEmpty()) {
			if(this.passField.getText().equalsIgnoreCase(this.passCField.getText())) {
				newDoc.put("password", this.generatePasswordHash(this.passField.getText()));
			}
		}
		newDoc.put("gainMuscle", this.gMuscle.isSelected());
		newDoc.put("gainStrength", this.gStrength.isSelected());
		newDoc.put("loseWeight", this.loseWeight.isSelected());
		newDoc.put("home", this.home.isSelected());
		newDoc.put("gym", this.gym.isSelected());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDoc);
		
		this.usersDB.getCollection("USERS").updateOne(queryObj, updateObject);	
	}

	/*
	 * This method fills all the FXML fields and checkboxes with the current users selection of settings.
	 * Makes a query to the database and fills fields with the response of the query.
	 */
	private void fillFields(){
		this.userNameField.setText((String) this.userObj.get("username"));
		this.weightField.setText((String) this.userObj.get("weight"));
		this.heightField.setText((String) this.userObj.get("height"));
		this.gMuscle.setSelected((Boolean) this.userObj.get("gainMuscle"));
		this.loseWeight.setSelected((Boolean) this.userObj.get("loseWeight"));
		this.gStrength.setSelected((Boolean) this.userObj.get("gainStrength"));
		this.home.setSelected((Boolean) this.userObj.get("home"));
		this.gym.setSelected((Boolean)this.userObj.get("gym"));
		this.dob.setValue(LocalDate.parse((CharSequence) this.userObj.get("dob")));
	}

	/*
	 * This function makes a query to grab the user object depending on the current user that has logged on.
	 */
	private void getUserObj(String uName) {
		FindIterable<Document> iterable = this.usersDB.getCollection("USERS").find(new Document("username", this.userName));
		this.userObj = iterable.first();
	}
	
	/*
	 * This method starts an active connection with the MongoDB database.
	 */
	private void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.usersDB = mongoClient.getDatabase("USERS");
    }
	
	/*
	 * This method creates a JavaFX alert with some parameters ( a JavaFX enum of AlertType, the window to display over,
	 * the alert title, and the alert message.
	 */
	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
	/*
	 * This method takes in a password and coverts it to a MD5 hash of the password.
	 */
	private String generatePasswordHash(String passWord) {
		StringBuilder hash = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = md.digest(passWord.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; idx++) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch(NoSuchAlgorithmException e) {
			System.out.println("no such algo error");
		}
		return hash.toString();
	}
	
	/*
	 * This method simply closes the current mongodb connection. 
	 */
    public void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }
	
}
