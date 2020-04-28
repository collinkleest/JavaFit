package com.javafit.View;

import com.javafit.Controller.ResetController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ResetPassView {
	private Scene resetScene;
	
	
	public void start(){
		Stage primaryStage = new Stage();
		primaryStage.setScene(this.resetScene);
		primaryStage.setTitle("Reset Password");
		primaryStage.show();
	}
	
	public ResetPassView() {
		GridPane gP = new GridPane();
		gP.setAlignment(Pos.CENTER);
		gP.setHgap(10);
		gP.setVgap(10);
		gP.setPadding(new Insets(25, 25, 25, 25));
		this.resetScene = new Scene(gP, 500, 500);
		
		Text scenetitle = new Text("Reset Password");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gP.add(scenetitle, 0, 0, 2, 1);
		
		Label userNameLabel = new Label("Username:");
		gP.add(userNameLabel, 0, 1);
		
		TextField userNameInput = new TextField("username");
		gP.add(userNameInput, 1, 1);
		
		Label dobLabel = new Label("Date of Birth: ");
		gP.add(dobLabel, 0, 2);
		
		DatePicker datePicker = new DatePicker();
		gP.add(datePicker, 1, 2);
		
		Button resetButton = new Button("Request Reset");
		resetButton.setPrefHeight(40);
		resetButton.setDefaultButton(true);
		resetButton.setPrefWidth(200);
	    gP.add(resetButton, 0, 3);
		
	    
	    resetButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if (userNameLabel.getText().isEmpty()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "Cannot verify you!");
	    	    	return;
	    		}
	    		if(datePicker.getValue() == null) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "Cannot verify you!");
	    	    	return;
	    		}
	    		
	    		
	    		ResetController rC = new ResetController(userNameInput.getText().strip(), datePicker.getValue().toString());
	    		if(!rC.validate()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "Cannot verify you!");
	    	    	return;
	    		}
	    		else {
	    			rC.closeMongoConnection();
	    			Stage stage = (Stage) resetButton.getScene().getWindow();
		    		stage.close();
	    			NewPassView npV = new NewPassView(userNameInput.getText().strip());
	    		}
	    	}
	    });
	    
	    
		this.start();
	}
	
	
	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
	
}
