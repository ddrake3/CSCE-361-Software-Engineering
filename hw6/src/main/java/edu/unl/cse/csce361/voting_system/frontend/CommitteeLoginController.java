package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class CommitteeLoginController extends Controller {

    @FXML private TextField name;
    @FXML private PasswordField password;
    @FXML private Label permissionCodeLabel;
    @FXML private TextField permissionCode;
    @FXML private Button signUp;
    @FXML private Label userMessage;

    private static CommitteeLoginController instance;

    public static CommitteeLoginController getInstance() {
        return instance;    // guaranteed not to be null
    }

    @FXML
    public void initialize() {
        permissionCodeLabel.setVisible(false);
        permissionCode.setVisible(false);
        signUp.setVisible(false);
        instance = this;
    }

    public void onClickLogin(javafx.event.ActionEvent event) throws IOException {
        String inputName = name.getText();
        String inputPassword = password.getText();
        String message;
        message = VotingLogic.getInstance().isRegisteredCommittee(inputName, inputPassword);
        if (message == null) {
            if (VotingLogic.getInstance().isAuditor(inputName)) {
                switchScreen(event,"auditorOption.fxml");
            } else {
                switchScreen(event,"electionBallot.fxml");
            }
        } else {
            userMessage.setText("* " + message);
            userMessage.setTextFill(Color.RED);
        }
    }

    public void onClickForgotPassword(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"committeeForgotPassword.fxml");
    }

    public void onClickSignUpLink() {
        permissionCodeLabel.setVisible(true);
        permissionCode.setVisible(true);
        signUp.setVisible(true);
    }

    public void onClickSignUp(javafx.event.ActionEvent event) throws IOException {
        if (permissionCode.getText().equals("123456")) {
            switchScreen(event, "committeeRegister.fxml");
        } else {
            userMessage.setText("* Invalid permission code, please try again *");
            userMessage.setTextFill(Color.RED);
        }
    }

    public void onClickBack(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"startPage.fxml");
    }

    public String getCommitteeName(){
        return this.name.getText();
    }
}
