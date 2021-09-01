package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class CommitteeForgotPasswordController extends Controller {

    @FXML private TextField name;
    @FXML private TextField permissionCode;
    @FXML private PasswordField newPassword;
    @FXML private Label userMessage;

    public void onClickBack (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"committeeLogin.fxml");
    }

    public void onClickConfirm (javafx.event.ActionEvent event) throws IOException {
        String committeeName = name.getText();
        String committeePassword = newPassword.getText();

        String message;
        if (permissionCode.getText().equals("123456")) {
            message = VotingLogic.getInstance().updateCommitteePassword(committeeName, committeePassword);
            if (message == null) {
                switchScreen(event, "committeeLogin.fxml");
            } else {
                userMessage.setText("* " + message);
                userMessage.setTextFill(Color.RED);
            }
        } else {
            userMessage.setText("* Invalid permission code. Unable to perform this action.");
            userMessage.setTextFill(Color.RED);
        }
    }
}
