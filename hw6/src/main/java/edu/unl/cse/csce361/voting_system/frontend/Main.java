package edu.unl.cse.csce361.voting_system.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("startPage.fxml"));
        primaryStage.setTitle("36team4 Voting System");
        primaryStage.setScene(new Scene(root, 375, 667));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
