package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class VoterLoginController extends Controller {

    @FXML TextField name;
    @FXML TextField authCode;
    @FXML Label userMessage;

    private String voterName;

    private static VoterLoginController instance;

    public static VoterLoginController getInstance() {
        return instance;    // guaranteed not to be null
    }

    @FXML
    public void initialize() {
        instance = this;
    }

    public void onClickLogin (javafx.event.ActionEvent event) throws IOException {
        if(!VotingLogic.getInstance().getPublishingBallotName().trim().isEmpty()) {
            this.voterName = name.getText();
            String voterAuthCode = authCode.getText();

            String message;
            message = VotingLogic.getInstance().isRegisteredVoter(voterName, voterAuthCode);
            if (message == null) {
                switchScreen(event, "voterHome.fxml");
            } else {
                userMessage.setText("* " + message);
                userMessage.setTextFill(Color.RED);
            }
        } else {
            userMessage.setText("* There is no ballot available to vote");
            userMessage.setTextFill(Color.RED);
        }
    }

    public void onClickForgotAuthCode(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterForgotCode.fxml");
    }

    public void onClickRegister(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterRegister.fxml");
    }

    public void onClickBack(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"startPage.fxml");
    }

    public void onClickReviewVote(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "voterReviewVote.fxml");
    }

    public String getVoterName() {
        return this.voterName;
    }
}
