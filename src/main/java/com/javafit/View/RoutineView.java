package com.javafit.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import com.javafit.Controller.CustomRoutineController;
import com.javafit.Controller.DashController;
import com.javafit.View.RegistrationView;
import com.jfoenix.controls.JFXButton;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class RoutineView {
	
	private Scene routineScene;
	private String userName;
	private Stage routineStage;
	private MongoClient mongoClient;
	private MongoDatabase usersDB;

	
	public RoutineView(String userName) throws IOException{
		this.userName = userName;
		this.routineStage = new Stage();
        this.routineScene = new Scene(loadFXML(), 956, 800);
        this.routineStage.setScene(this.routineScene);
		
		
        
        JFXButton dashBtn =  (JFXButton) this.routineScene.lookup("#dashBtn");
        dashBtn.setOnAction((ActionEvent event) -> {
        	Stage stage = (Stage) dashBtn.getScene().getWindow();
            stage.close();
            
            try {
				DashController dC = new DashController(this.userName);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        
        JFXButton customBtn =  (JFXButton) this.routineScene.lookup("#customBtn");
        customBtn.setOnAction((ActionEvent event) -> {
        	Stage stage = (Stage) customBtn.getScene().getWindow();
            stage.close();
            
            try {
				CustomRoutineController crC = new CustomRoutineController(this.userName);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        
		
		this.routineStage.show();
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
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationView.class.getResource("/routines.fxml"));
        return fxmlLoader.load();
    }
	
	
}


