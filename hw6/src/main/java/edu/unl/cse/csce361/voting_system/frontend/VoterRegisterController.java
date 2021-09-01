package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class VoterRegisterController extends Controller {

    @FXML private TextField name;
    @FXML private TextField streetAddress1;
    @FXML private TextField streetAddress2;
    @FXML private TextField city;
    @FXML private TextField state;
    @FXML private TextField zipCode;
    @FXML private TextField ssn;
    @FXML private Label userMessage;

    private String inputName;
    private String inputSsn;

    private static VoterRegisterController instance;

    public static VoterRegisterController getInstance() {
        return instance;    // guaranteed not to be null
    }

    @FXML
    public void initialize() {
        // whatever other initialization you need to do
        instance = this;
    }

    public void onClickBack(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

    public void onClickSubmit(javafx.event.ActionEvent event) throws IOException {
        String message;
        this.inputName = name.getText();
        this.inputSsn = ssn.getText();

        message = VotingLogic.getInstance().createVoter(
                name.getText(),
                streetAddress1.getText(),
                streetAddress2.getText(),
                city.getText(),
                state.getText(),
                zipCode.getText(),
                ssn.getText()
                );

        if (message == null) {
            switchScreen(event, "voterAuthCode.fxml");
        } else {
            userMessage.setText("* " + message);
            userMessage.setTextFill(Color.RED);
        }
    }

    public String getInputName() {
        return inputName;
    }

    public String getInputSsn() {
        return inputSsn;
    }
}
