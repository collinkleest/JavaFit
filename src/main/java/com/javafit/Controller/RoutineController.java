package com.javafit.Controller;

//class imports
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import org.bson.Document;
import com.javafit.View.RegistrationView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class RoutineController {

    //class attributes
    private final Scene routineScene;
    private String userName;
    private final Stage routineStage;
    private MongoClient mongoClient;
    private MongoDatabase routinesDB;
    private Document userObject;

    /*
     * Class constuctor, takes in a username
     * sets FXML UI elements with proper data.
     * Dynamically adds Routines
     */
    public RoutineController(String userName) throws IOException {
        this.userName = userName;
        this.routineStage = new Stage();
        this.routineScene = new Scene(loadFXML(), 956, 800);
        this.routineStage.setScene(this.routineScene);

        // main pane for routines (scroll pane
        ScrollPane mainPane = (ScrollPane) this.routineScene.lookup("#mainPane");
        this.initializeMongoConnection();
        MongoCollection<Document> routinesCollection = this.routinesDB.getCollection("ROUTINES");
        FindIterable<Document> iterable = routinesCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        GridPane gP = new GridPane();
        int rowIndex = 0;

        mainPane.setPadding(new Insets(25, 50, 25, 145));

        // dynamically adds routines to main pane
        try {
            while (cursor.hasNext()) {
                Document obj = cursor.tryNext();

                AnchorPane newPane = FXMLLoader.load(RoutineController.class.getResource("/routinePane.fxml"));
                newPane.setPadding(new Insets(25, 10, 25, 10));
                Label routineName = (Label) newPane.lookup("#routineName");
                Label userNameLabel = (Label) newPane.lookup("#userName");
                Label reps = (Label) newPane.lookup("#reps");
                Label mGroup = (Label) newPane.lookup("#mGroup");
                JFXCheckBox muscle = (JFXCheckBox) newPane.lookup("#muscle");
                JFXCheckBox strength = (JFXCheckBox) newPane.lookup("#strength");
                JFXCheckBox weight = (JFXCheckBox) newPane.lookup("#weight");
                JFXCheckBox home = (JFXCheckBox) newPane.lookup("#home");
                JFXCheckBox gym = (JFXCheckBox) newPane.lookup("#gym");

                if (obj != null) {
                    if (obj.getBoolean("gainMuscle")) {
                        muscle.setSelected(true);
                    }
                    if (obj.getBoolean("gainStrength")) {
                        strength.setSelected(true);
                    }
                    if (obj.getBoolean("loseWeight")) {
                        weight.setSelected(true);
                    }
                    if (obj.getString("location").strip().equalsIgnoreCase("home")) {
                        home.setSelected(true);
                    }
                    if (obj.getString("location").strip().equalsIgnoreCase("gym")) {
                        gym.setSelected(true);
                    }

                    routineName.setText((obj.get("name").toString().toUpperCase()));
                    userNameLabel.setText((String) obj.get("userName"));
                    reps.setText((String) obj.get("reps"));
                    mGroup.setText((String) obj.get("muscleGroup"));
                }

                gP.add(newPane, 0, rowIndex);
                rowIndex++;
            }
        } finally {
            cursor.close();
        }
        gP.setAlignment(Pos.CENTER);

        mainPane.setPannable(true);
        mainPane.setContent(gP);

        // action listener
        JFXButton dashBtn = (JFXButton) this.routineScene.lookup("#dashBtn");
        dashBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) dashBtn.getScene().getWindow();
            stage.close();

            try {
                DashController dC = new DashController(this.userName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        // action listener for custom routine
        JFXButton customBtn = (JFXButton) this.routineScene.lookup("#customBtn");
        customBtn.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) customBtn.getScene().getWindow();
            stage.close();

            try {
                CustomRoutineController crC = new CustomRoutineController(this.userName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        this.closeMongoConnection();
        this.routineStage.show();
    }

    /*
     * Starts mongodb connection
     */
    private void initializeMongoConnection() {
        this.mongoClient = MongoClients.create(
                "mongodb+srv://ckleest:ckk@javafit-qy8fa.mongodb.net/test?retryWrites=true&w=majority");
        this.routinesDB = mongoClient.getDatabase("ROUTINES");
    }

    /*
     * Closes mongodb connection
     */
    private void closeMongoConnection() {
        System.out.println("closing mongo client");
        this.mongoClient.close();
        System.out.println("successfully closed mongo connection");
    }

    /*
     * loads FXML file
     */
    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationView.class.getResource("/routines.fxml"));
        return fxmlLoader.load();
    }

}
