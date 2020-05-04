package com.javafit.View;
//class imports

import javafx.application.Application;
import javafx.stage.Stage;

public class Register extends Application {

    /*
     * THIS CLASS GETS CALLED FROM POM.XML FILE 
     * IT IS THE ENTRY POINT OF THE APPLICATION
     */
 /*APPLICATION ENTRY POINT */
    @Override
    public void start(Stage stage) throws Exception {
        RegistrationView rV = new RegistrationView();
        stage.setScene(rV.getScene());
        stage.setTitle("JavaFit");
        stage.show();
    }

    /*
     * Launches application
     */
    public static void main(String[] args) {
        launch();
    }
}
