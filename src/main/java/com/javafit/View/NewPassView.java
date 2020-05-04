package com.javafit.View;

/*
 * Class Imports
 */
import com.javafit.Controller.SendResetController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class NewPassView {
	//class attributes
	private String userName;
	private Scene resetScene;
	
	/*
	 * Start method, instatiatiates current scene
	 */
	public void start(){
		Stage primaryStage = new Stage();
		primaryStage.setScene(this.resetScene);
		primaryStage.setTitle("Reset Password");
		primaryStage.show();
	}
	
	/*
	 * Class constructor, createes a view for new password scene
	 */
	public NewPassView(String uName) {
		this.userName = uName;
		
		
		/*
		 * JAVAFX UI ELEMENTS
		 */
		GridPane gP = new GridPane();
		gP.setAlignment(Pos.CENTER);
		gP.setHgap(10);
		gP.setVgap(10);
		gP.setPadding(new Insets(25, 25, 25, 25));
		this.resetScene = new Scene(gP, 500, 300);
		
		Text scenetitle = new Text("Reset Password");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.getStyleClass().setAll("strong","h1");
		gP.add(scenetitle, 0, 0, 2, 1);
		
		Label passWordLabel = new Label("Password:");
		passWordLabel.getStyleClass().setAll("strong","lead");
		gP.add(passWordLabel, 0, 1);
		
		PasswordField pwField = new  PasswordField();
		gP.add(pwField, 1, 1);
		
		Label passWordConfirmLabel = new Label("Confirm Password:");
		passWordConfirmLabel.getStyleClass().setAll("strong","lead");
		gP.add(passWordConfirmLabel, 0, 2);
		
		PasswordField pwCField = new  PasswordField();
		gP.add(pwCField, 1, 2);
		
		Button resetBtn = new Button("Reset Password");
		resetBtn.setPrefHeight(40);
		resetBtn.setDefaultButton(true);
		resetBtn.setPrefWidth(200);
		resetBtn.setAlignment(Pos.CENTER);
		resetBtn.getStyleClass().setAll("strong","lead", "btn-info", "btn-sm");
		gP.add(resetBtn, 0, 3);
		
		/*
		 * JavaFX event handlers
		 */
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if (pwField.getText().isEmpty()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "Please enter a password.");
	    	    	return;
	    		}
	    		if (pwCField.getText().isEmpty()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "Please enter a password confirmation.");
	    	    	return;
	    		}
	    		if(!pwField.getText().equalsIgnoreCase(pwCField.getText())){
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "passwords do not match.");
	    	    	return;
	    		}
	    		SendResetController srC = new SendResetController(pwField.getText().strip(), uName);
	    		if (!srC.reset()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "couldn't reset password.");
	    	    	return;
	    		}
	    		else {
	    			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		            alert.setTitle("User Created Confirmation");
		            alert.setContentText("Password Successfully Reset for "+uName);
		            alert.showAndWait();
	    			Stage stage = (Stage) resetBtn.getScene().getWindow();
		    		stage.close();
		    		srC.closeMongoConnection();
		    		LoginView lV = new LoginView();
	    		}
	    	}
	    });
	    
		//grabs bootstrapfx css
		 resetScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		
		 //instantiates current scene
		this.start();
	}
	
	/*
	 * Shows alert for given arguments
	 */
	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
	
	
	
}
