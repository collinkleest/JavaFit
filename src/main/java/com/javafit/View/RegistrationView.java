package com.javafit.View;

import javafx.scene.control.TextField;

import com.javafit.Controller.NewUserController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegistrationView {
	
	
	private Scene scene;
	
	public RegistrationView(){
		GridPane gP = new GridPane();
		gP.setAlignment(Pos.CENTER);
		gP.setHgap(10);
		gP.setVgap(10);
		gP.setPadding(new Insets(25, 25, 25, 25));
		this.scene = new Scene(gP, 850, 500);
		
		Text scenetitle = new Text("Register a JavaFit Account");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gP.add(scenetitle, 0, 0, 2, 1);

		Label name = new Label("Name:");
		gP.add(name, 0, 1);
		
		TextField nameInput = new TextField("First and Last");
		gP.add(nameInput, 1, 1);
		
		Label userName = new Label("User Name:");
		gP.add(userName, 0, 2);

		TextField userNameInput = new TextField("User Name");
		gP.add(userNameInput, 1, 2);

		Label pw = new Label("Password:");
		gP.add(pw, 0, 3);

		
		PasswordField pwBox = new PasswordField();
		gP.add(pwBox, 1, 3);
		

		Label pc = new Label("Confirm Password:");
		gP.add(pc, 0, 4);
		
		PasswordField pcBox = new PasswordField();
		gP.add(pcBox, 1, 4);
		
		Label weightLabel = new Label("Weight(lbs):");
		gP.add(weightLabel, 0, 5);
		
		TextField weightInput = new TextField("weight (lbs)");
		gP.add(weightInput, 1, 5);
	
		Label heightLabel = new Label("Height:");
		gP.add(heightLabel, 0, 6);
		
		TextField feetInput = new TextField("feet");
		gP.add(feetInput, 1, 6);
		
		TextField inchesInput = new TextField("inches");
		gP.add(inchesInput, 2, 6);
		
		Label dobLabel = new Label("Date of Birth:");
		gP.add(dobLabel, 0, 7);
		
		DatePicker datePicker = new DatePicker();
		gP.add(datePicker, 1, 7);		
		
		Label objLabel = new Label("Objectives");
		gP.add(objLabel, 0, 8);
		
		CheckBox gainMuscle = new CheckBox("Gain Muscle");
		gP.add(gainMuscle, 1, 8);
		
		CheckBox gainStrength = new CheckBox("Gain Stength");
		gP.add(gainStrength, 2, 8);
		
		CheckBox loseWeight = new CheckBox("Lose Weight");
		gP.add(loseWeight, 3, 8);
		
		Label workoutLocation = new Label("Where do you work out?");
		gP.add(workoutLocation, 0, 9);
		
		CheckBox home = new CheckBox("Home");
		gP.add(home, 1, 9);
		
		CheckBox gym = new CheckBox("Gym");
		gP.add(gym, 2, 9);
		
		Label terms = new Label("Do you accept JavaFits terms and conditions");
		gP.add(terms, 0, 10);
		
		RadioButton termRadio = new RadioButton("Yes, I accept");
		gP.add(termRadio, 1, 10);
		

		RadioButton termRadio2 = new RadioButton("No, I decline");
		gP.add(termRadio2, 2, 10);
		
		Button loginButton = new Button("Login");
		loginButton.setPrefHeight(40);
		loginButton.setDefaultButton(true);
		loginButton.setPrefWidth(100);
	    gP.add(loginButton, 0, 11);
		
		Button submitButton = new Button("Submit");
	    submitButton.setPrefHeight(40);
	    submitButton.setDefaultButton(true);
	    submitButton.setPrefWidth(100);
	    gP.add(submitButton, 1, 11);
	    
	    
	    loginButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		Stage stage = (Stage) loginButton.getScene().getWindow();
	    		stage.close();
	    		LoginView lV = new LoginView();
	    	}
	    });
	    
	    submitButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if(nameInput.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	                "Form Error!", "Please enter your name");
	                return;
	            }
	            if(userNameInput.getText().isEmpty()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please enter a username");
	    	       return;
	            }
	            if(pwBox.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	                "Form Error!", "Please enter a password");
	                return;
	            }
	            if(pcBox.getText().isEmpty()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please enter a confirmed password");
	    	       return;
	            }
	            if(!pcBox.getText().equalsIgnoreCase(pwBox.getText())) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Passwords don't match");
	    	       return;
	            }
	            
	            if(weightInput.getText().isEmpty()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please enter a weight");
	    	       return;
	            }
	            if(inchesInput.getText().isEmpty()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please fill out the inches input, if 0 please enter 0.");
	    	       return;
	            }
	            if(feetInput.getText().isEmpty()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please fill in the feet input under height!");
	    	       return;
	            }
	            if(datePicker.getValue() == null) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Enter a date for your date of birth!");
	    	       return;
	            }
	            if(gainMuscle.isSelected()==false && gainStrength.isSelected()==false && loseWeight.isSelected()==false) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please choose at least one objective!");
	    	       return;
	            }
	            if(home.isSelected()==false && gym.isSelected()==false) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Please select a workout location!");
	    	       return;
	            }
	            if(!termRadio.isSelected()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	        "Form Error!", "Must select terms to create an account!");
	    	       return;
	            }
	            if(termRadio2.isSelected()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "You must accept the terms to create an account!");
	    	    	return;
	            }
	            
	            
	            String height = feetInput.getText() + "." + inchesInput.getText();
	            String dob = datePicker.getValue().toString();
	            NewUserController tempUser = new NewUserController(
	            		nameInput.getText().strip(), height, weightInput.getText().strip(), dob, 
	            		userNameInput.getText().strip(),pwBox.getText().strip(), gainMuscle.isSelected(), gainStrength.isSelected(),
	            		loseWeight.isSelected(), home.isSelected(), gym.isSelected());
	            
	            if (tempUser.checkUserExistence()) {
	            	showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(), 
	    	    	"Form Error!", "User already exists!");
	    	    	return;
	            }
	            else {
	            	tempUser.insertUser();
	            }
	            
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setTitle("User Created Confirmation");
	            alert.setContentText("Registration Succesful! " + "Welcome to JavaFit " + nameInput.getText());
	            alert.showAndWait();
	            tempUser.closeMongoConnection();
	            Stage stage = (Stage) loginButton.getScene().getWindow();
	    		stage.close();
	    		LoginView lV = new LoginView();
	        }
	    });
	    
		
	}
	
	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
	
	public Scene getScene() {
		return this.scene;
	}
	
	
}
