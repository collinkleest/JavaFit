package com.javafit.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;


public class RoutineView {
	private Scene routineScene;
	
	
	public void start(){
		Stage primaryStage = new Stage();
		primaryStage.setScene(this.routineScene);
		primaryStage.setTitle("Reset Password");
		primaryStage.show();
	}
	
	public RoutineView(){
		GridPane gP = new GridPane();
		gP.setAlignment(Pos.CENTER);
		gP.setHgap(10);
		gP.setVgap(10);
		gP.setPadding(new Insets(25, 25, 25, 25));
		this.routineScene = new Scene(gP, 1000, 650);
		
		Panel panel = new Panel("Routine 1");
		
		
		
		
		
		routineScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		
		this.start();
	}
	
}


