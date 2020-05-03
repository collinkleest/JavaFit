package com.javafit.View;

import java.io.IOException;

import com.javafit.Controller.DashController;
import com.javafit.Controller.LoginController;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginView {

    private Scene loginScene;

    private void start() {
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
        this.loginScene = new Scene(gP, 500, 300);

        Text scenetitle = new Text("Login to JavaFit!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.getStyleClass().setAll("strong", "h1");
        gP.add(scenetitle, 0, 0, 2, 1);

        Label userNameLabel = new Label("Username:");
        userNameLabel.getStyleClass().setAll("strong", "lead");
        gP.add(userNameLabel, 0, 1);

        TextField userNameInput = new TextField("username");
        gP.add(userNameInput, 1, 1);

        Label passWordLabel = new Label("Password: ");
        passWordLabel.getStyleClass().setAll("strong", "lead");
        gP.add(passWordLabel, 0, 2);

        PasswordField passWordInput = new PasswordField();
        gP.add(passWordInput, 1, 2);

        Button registerButton = new Button("Register");
        registerButton.setPrefHeight(40);
        registerButton.setDefaultButton(false);
        registerButton.setPrefWidth(100);
        registerButton.setAlignment(Pos.CENTER);
        registerButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(registerButton, 0, 3);

        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(40);
        loginButton.setAlignment(Pos.CENTER);
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(100);
        loginButton.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(loginButton, 1, 3);

        Button resetPasswordBtn = new Button("Reset Password");
        resetPasswordBtn.setPrefHeight(40);
        resetPasswordBtn.setDefaultButton(false);
        resetPasswordBtn.setPrefWidth(400);
        resetPasswordBtn.setAlignment(Pos.CENTER);
        resetPasswordBtn.getStyleClass().setAll("btn-sm", "btn-info", "lead");
        gP.add(resetPasswordBtn, 0, 4, 2, 1);

        registerButton.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            newStage.setTitle("JavaFit Registration");
            RegistrationView rV = new RegistrationView();
            newStage.setScene(rV.getScene());
            newStage.show();
        });

        resetPasswordBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            ResetPassView rpV = new ResetPassView();
        });

        loginButton.setOnAction((ActionEvent event) -> {
            if (userNameInput.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Form Error!", "You must enter a username!");
                return;
            }
            if (passWordInput.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Form Error!", "You must enter a password!");
                return;
            }

            LoginController lC = new LoginController(userNameInput.getText(), passWordInput.getText());
            if (!lC.login()) {
                showAlert(Alert.AlertType.ERROR, gP.getScene().getWindow(),
                        "Authentication Failed!", "Invalid username or password try again!");

            } else {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Successful Login");
                alert.setHeaderText("Login Successful!");
                alert.setContentText("Welcome " + userNameInput.getText().strip() + " click ok to continue");
                alert.initOwner(gP.getScene().getWindow());
                alert.showAndWait();
                lC.closeMongoConnection();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                try {
                    DashController dashC = new DashController(userNameInput.getText());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //DashboardView dashboardView = new DashboardView();
            }
        });

        loginScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

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
