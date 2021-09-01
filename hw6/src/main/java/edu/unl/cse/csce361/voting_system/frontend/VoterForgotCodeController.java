package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class VoterForgotCodeController extends Controller {

    @FXML TextField name;
    @FXML TextField socialSecurityNumber;
    @FXML TextField authCode;
    @FXML Label userMessage;

    @FXML
    public void initialize() {
        authCode.setEditable(false);
    }

    public void onClickReturnLogin (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

    public void getAuthenticationCode() {
        String voterName = name.getText();
        String voterSsn = socialSecurityNumber.getText();

        String message;
        message = VotingLogic.getInstance().getVoterAuthenticationCode(voterName, voterSsn);
        if (message.contains("Invalid") || message.contains("Incorrect")) {
            userMessage.setText("* " + message);
            userMessage.setTextFill(Color.RED);
        } else {
            authCode.setText(message);
        }
    }
}
