package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class VoterAuthCodeController extends Controller {

    @FXML private Label name;
    @FXML private TextField authCode;
    @FXML private Label userMessage;
    @FXML private TextArea successMessage;

    @FXML
    public void initialize() {
        successMessage.setEditable(false);
        String voterName = VoterRegisterController.getInstance().getInputName();
        String voterSsn = VoterRegisterController.getInstance().getInputSsn();
        name.setText(voterName + "!");
        String voterAuthCode = VotingLogic.getInstance().getVoterAuthenticationCode(voterName, voterSsn);
        if (voterAuthCode.contains("Invalid") || voterAuthCode.contains("Incorrect")) {
            userMessage.setText("* " + voterAuthCode + " *");
            userMessage.setTextFill(Color.RED);
        } else {
            authCode.setText(voterAuthCode);
        }
    }

    public void onClickReturnLogin(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }
}
