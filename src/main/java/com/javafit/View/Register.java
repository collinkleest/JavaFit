package com.javafit.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Register extends Application {

		/*APPLICATION ENTRY POINT */
	
	    public void start(Stage stage) throws Exception{
	        RegistrationView rV = new RegistrationView();
	        stage.setScene(rV.getScene());
	        stage.setTitle("JavaFit");
	        stage.show();
	    }

	    public static void main(String[] args) {
	        launch();
	    }
}
