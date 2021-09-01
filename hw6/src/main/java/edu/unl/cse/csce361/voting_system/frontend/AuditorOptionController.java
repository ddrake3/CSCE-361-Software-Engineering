package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuditorOptionController extends Controller {

    private static AuditorOptionController instance;

    public static AuditorOptionController getInstance(){
        return instance;
    }

    boolean isAuditor = VotingLogic.getInstance().isAuditor(CommitteeLoginController.getInstance().getCommitteeName());

    @FXML TextField ballotName;
    @FXML Label userMessage;
    @FXML Button signOutBack;
    @FXML
    public void initialize() {
        if (isAuditor) {
            signOutBack.setText("SIGN OUT");
        } else {
            signOutBack.setText("BACK");
        }
        instance = this;
    }

    public String getBallotName(){
        return this.ballotName.getText();
    }

    public void switchToOriginal(javafx.event.ActionEvent event) throws IOException {
        if (VotingLogic.getInstance().isAuditor(CommitteeLoginController.getInstance().getCommitteeName())) {
            switchScreen(event,"committeeLogin.fxml");
        } else {
            switchScreen(event, "electionBallot.fxml");
        }
    }

    public void switchToBallotStatistics(javafx.event.ActionEvent event) throws IOException {
            switchScreen(event,"auditorBallotStat.fxml");
    }

    public void switchToVoterStatistics(javafx.event.ActionEvent event) throws IOException {
            switchScreen(event,"auditorVoterStat.fxml");
    }

    public void switchToFinal(javafx.event.ActionEvent event) throws IOException {
            switchScreen(event,"auditorFinalResult.fxml");
    }

}
