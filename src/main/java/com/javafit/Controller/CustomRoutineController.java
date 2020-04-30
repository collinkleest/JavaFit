package com.javafit.Controller;

import java.io.IOException;

import org.bson.Document;

import com.javafit.Model.Routine;
import com.javafit.View.RegistrationView;
import com.javafit.View.RoutineView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CustomRoutineController {
		
	private MongoClient mongoClient;
    private MongoDatabase usersDB;
	private String userName;
	private Stage cRoutineStage;
    private Scene cRoutineScene;
    
    private Routine tempRoutine;
    
    
	public CustomRoutineController(String uName) throws IOException {
		this.userName = uName;
		this.cRoutineStage = new Stage();
        this.cRoutineScene = new Scene(loadFXML(), 449, 750);
        this.cRoutineStage.setScene(this.cRoutineScene);
		
		TextField rInput = (TextField) this.cRoutineScene.lookup("#rInput");
		TextField setsInput = (TextField) this.cRoutineScene.lookup("#setsInput");
		TextField mInput = (TextField) this.cRoutineScene.lookup("#mInput");
		
		JFXCheckBox sCheck = (JFXCheckBox) this.cRoutineScene.lookup("#sCheck");
		JFXCheckBox mCheck = (JFXCheckBox) this.cRoutineScene.lookup("#mCheck");
		JFXCheckBox lCheck = (JFXCheckBox) this.cRoutineScene.lookup("#lCheck");
		JFXCheckBox hCheck = (JFXCheckBox) this.cRoutineScene.lookup("#hCheck");
		JFXCheckBox gCheck = (JFXCheckBox) this.cRoutineScene.lookup("#gCheck");
		
		JFXButton submitBtn = (JFXButton) this.cRoutineScene.lookup("#submitBtn");
		JFXButton routinesBtn = (JFXButton) this.cRoutineScene.lookup("#routinesBtn");
		JFXButton dashBtn = (JFXButton) this.cRoutineScene.lookup("#dashBtn");
		
		
		routinesBtn.setOnAction((ActionEvent event) -> {
			Stage stage = (Stage) routinesBtn.getScene().getWindow();
            stage.close();
            try {
				RoutineView rV = new RoutineView(this.userName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		dashBtn.setOnAction((ActionEvent event) -> {
			Stage stage = (Stage) routinesBtn.getScene().getWindow();
            stage.close();
            try {
				DashController dC = new DashController(this.userName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		submitBtn.setOnAction((ActionEvent event) -> {
			if(rInput.getText().isEmpty() || setsInput.getText().isEmpty() || mInput.getText().isEmpty()) {
				this.showAlert(AlertType.ERROR, this.cRoutineScene.getWindow(), "Error", "Fill out all inputs!");
				return;
			}
			if(!sCheck.isSelected() && !mCheck.isSelected() && !lCheck.isSelected()) {
				this.showAlert(AlertType.ERROR, this.cRoutineScene.getWindow(), "ERROR", "Must Check one of intended objectives!");
				return;
			}
			if(!hCheck.isSelected() && !gCheck.isSelected()) {
				this.showAlert(AlertType.ERROR, this.cRoutineScene.getWindow(), "ERROR", "Must select gym or home!");
				return;
			}
			this.initializeMongoConnection();
			this.tempRoutine = new Routine(rInput.getText(), setsInput.getText(), sCheck.isSelected(), mCheck.isSelected(), lCheck.isSelected(),
					mInput.getText(), hCheck.isSelected(), gCheck.isSelected());
			this.insertRoutine();
			this.showAlert(AlertType.CONFIRMATION, this.cRoutineScene.getWindow(), "Success", "Custom Routine Created!");
			this.closeMongoConnection();
		});
		
		this.cRoutineStage.show();
	}

	
	private void insertRoutine() {
		MongoCollection<Document> collection = usersDB.getCollection("ROUTINES");
		Document tempDoc = new Document();
		tempDoc.put("name", this.tempRoutine.getRoutineName());
		tempDoc.put("reps", this.tempRoutine.getSets());
		tempDoc.put("userName", this.userName);
		tempDoc.put("gainMuscle", this.tempRoutine.isGainMuscle());
		tempDoc.put("gainStrength", this.tempRoutine.isGainStrength());
		tempDoc.put("loseWeight", this.tempRoutine.isLoseWeight());
		tempDoc.put("muscleGroup", this.tempRoutine.getMuscleGroup());
		if(tempRoutine.isHome()) {
			tempDoc.put("location", "home");
		}
		else {
			tempDoc.put("location", "gym");
		}
		collection.insertOne(tempDoc);
	}
	
	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
	private void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.usersDB = mongoClient.getDatabase("ROUTINES");
    }

    public void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }
	
	private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationView.class.getResource("/routinemaker.fxml"));
        return fxmlLoader.load();
    }
	
}
