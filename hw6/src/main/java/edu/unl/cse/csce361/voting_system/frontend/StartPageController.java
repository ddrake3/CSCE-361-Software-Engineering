package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;

public class StartPageController extends Controller {

    public void onClickVoter(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

    public void onClickCommittee(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "committeeLogin.fxml");
    }
}
