package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class VoterCompletionController extends Controller {

    @FXML private Label name;
    @FXML private TextField voteConfirmationCode;

    @FXML
    public void initialize() {
        name.setText(VoterLoginController.getInstance().getVoterName());
        voteConfirmationCode.setText(VoterReviewController.getInstance().getVoteConfirmationCode());
    }

    public void onClickSignOut (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

}
