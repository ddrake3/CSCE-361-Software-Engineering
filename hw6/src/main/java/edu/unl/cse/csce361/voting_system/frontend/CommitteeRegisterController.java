package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;

public class CommitteeRegisterController extends Controller {

    @FXML private TextField name;
    @FXML private PasswordField password;
    @FXML private Label userMessage;
    @FXML private ToggleGroup signUp;


    public void onClickBack(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"committeeLogin.fxml");
    }

    public void onClickSubmit(javafx.event.ActionEvent event) throws IOException {
        String message;
        RadioButton selectedRadioButton = (RadioButton) signUp.getSelectedToggle();
        String selectedJobTitle = selectedRadioButton.getText();
        message = VotingLogic.getInstance().createCommittee(name.getText(), password.getText(), selectedJobTitle);

        if (message == null) {
            switchScreen(event, "committeeLogin.fxml");
        } else {
            userMessage.setText("* " + message);
            userMessage.setTextFill(Color.RED);
        }
    }
}
