package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    public void switchScreen(javafx.event.ActionEvent event, String string) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(string));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
