package com.javafit.View;

import com.javafit.Controller.LoginController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginView {

	private Scene loginScene;
	
	public void start(){
		Stage primaryStage = new Stage();
		primaryStage.setScene(this.loginScene);
		primaryStage.setTitle("JavaFit Login");
		primaryStage.show();
	}
	
	public LoginView() {
		GridPane gP = new GridPane();
		gP.setAlignment(Pos.CENTER);
		gP.setHgap(10);
		gP.setVgap(10);
		gP.setPadding(new Insets(25, 25, 25, 25));
		this.loginScene = new Scene(gP, 500, 500);
		
		
		Text scenetitle = new Text("Login to JavaFit!");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gP.add(scenetitle, 0, 0, 2, 1);
		
		Label userNameLabel = new Label("Username:");
		gP.add(userNameLabel, 0, 1);
		
		TextField userNameInput = new TextField("username");
		gP.add(userNameInput, 1, 1);
		
		Label passWordLabel = new Label("Password: ");
		gP.add(passWordLabel, 0, 2);

		TextField passWordInput = new TextField("password");
		gP.add(passWordInput, 1, 2);
		
		
		Button registerButton = new Button("Register");
		registerButton.setPrefHeight(40);
	    registerButton.setDefaultButton(true);
	    registerButton.setPrefWidth(100);
	    gP.add(registerButton, 0, 3);
		
		Button loginButton = new Button("Login");
		loginButton.setPrefHeight(40);
		loginButton.setDefaultButton(true);
		loginButton.setPrefWidth(100);
	    gP.add(loginButton, 1, 3);
		
	    registerButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Stage stage = (Stage) loginButton.getScene().getWindow();
	    		stage.close();
	    		Stage newStage = new Stage();
	    		newStage.setTitle("JavaFit Registration");
	    		RegistrationView rV = new RegistrationView();
	    		newStage.setScene(rV.getScene());
	    		newStage.show();
	    	}
	    });
	    
	    loginButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if(userNameInput.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	                "Form Error!", "You must enter a username!");
	                return;
	            }
	    		if(passWordInput.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	                "Form Error!", "You must enter a password!");
	                return;
	            }
	    		
	    		LoginController lC = new LoginController(userNameInput.getText(), passWordInput.getText());
	    		if (!lC.login()) {
	    			showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Authentication Failed!", "Invalid username or password try again!");
	    			return;
	    		}
	    		else {
	    			showAlert(Alert.AlertType.CONFIRMATION, gP.getScene().getWindow(), 
	    		    	    "Login Successful!", "Welcome " + userNameInput.getText());
	    		}
	    		lC.closeMongoConnection();
	    		
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
