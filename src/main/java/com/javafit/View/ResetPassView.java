package com.javafit.View;

//class imports 
import com.javafit.Controller.ResetController;
import javafx.event.ActionEvent;
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

    //class attibute 
    private Scene resetScene;

    /*
     * start method, creates the current scene
     */
    private void start() {
        Stage primaryStage = new Stage();
        primaryStage.setScene(this.resetScene);
        primaryStage.setTitle("Reset Password");
        primaryStage.show();
    }

    /*
     * Class constructor, creates the UI Scene for the reset password view
     */
    public ResetPassView() {

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
        scenetitle.getStyleClass().setAll("strong", "h1");
        gP.add(scenetitle, 0, 0, 2, 1);

        Label userNameLabel = new Label("Username:");
        userNameLabel.getStyleClass().setAll("strong", "lead");
        gP.add(userNameLabel, 0, 1);

        TextField userNameInput = new TextField("username");
        gP.add(userNameInput, 1, 1);

        Label dobLabel = new Label("Date of Birth: ");
        dobLabel.getStyleClass().setAll("strong", "lead");
        gP.add(dobLabel, 0, 2);

        DatePicker datePicker = new DatePicker();
        gP.add(datePicker, 1, 2);

        Button resetButton = new Button("Request Reset");
        resetButton.setPrefHeight(40);
        resetButton.setDefaultButton(true);
        resetButton.setPrefWidth(200);
        resetButton.setAlignment(Pos.CENTER);
        resetButton.getStyleClass().setAll("strong", "lead", "btn-info", "btn-sm");
        gP.add(resetButton, 0, 3);

        Button goBackButton = new Button("Go Back");
        goBackButton.setPrefHeight(40);
        goBackButton.setDefaultButton(false);
        goBackButton.setPrefWidth(200);
        goBackButton.setAlignment(Pos.CENTER);
        goBackButton.getStyleClass().setAll("strong", "lead", "btn-info", "btn-sm");
        gP.add(goBackButton, 1, 3);

        /*
         * JAVAFX EVENT LISTENERS
         */
        resetButton.setOnAction((ActionEvent event) -> {
            if (userNameLabel.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Form Error!", "Cannot verify you!");
                return;
            }
            if (datePicker.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Form Error!", "Cannot verify you!");
                return;
            }

            ResetController rC = new ResetController(userNameInput.getText().strip(), datePicker.getValue().toString());
            if (!rC.validate()) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Form Error!", "Cannot verify you!");
            } else {
                rC.closeMongoConnection();
                Stage stage = (Stage) resetButton.getScene().getWindow();
                stage.close();
                NewPassView npV = new NewPassView(userNameInput.getText().strip());
            }
        });

        goBackButton.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.close();

            LoginView loginView = new LoginView();
        });

        //grabs bootstrapfx css
        resetScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //starts the current scene
        this.start();
    }

    /*
     * Method to create and display an alert with specific parameters
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
