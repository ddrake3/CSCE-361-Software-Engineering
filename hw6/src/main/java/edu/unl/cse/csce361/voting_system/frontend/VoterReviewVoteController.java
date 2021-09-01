package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class VoterReviewVoteController extends Controller {

    @FXML private TextField voteConfirmationCode;
    @FXML private ListView<Map.Entry<String, String>> reviewTable;

    static class ReviewVoteCell extends ListCell<Map.Entry<String, String>> {

        VBox voteCart;
        Text ballotQuestion = new Text();
        Text ballotSelection = new Text();
        Text voterChoice = new Text();

        HBox ballotAnswer;

        public ReviewVoteCell() {
            super();

            // Text configuration for ballot question
            ballotQuestion.setFont(Font.font("arial", FontWeight.BOLD, 15));

            // Text configuration for "You have selected"
            ballotSelection.setText("You have selected");
            ballotSelection.setFont(Font.font("arial", FontWeight.NORMAL, FontPosture.ITALIC, 15));
            ballotSelection.setTranslateX(30);

            // Text configuration for the choice of selection made by Voter from Home Page
            voterChoice.setFont(Font.font("arial", FontWeight.BOLD, 15));
            voterChoice.setTranslateX(35);
            ballotAnswer = new HBox(ballotSelection, voterChoice);

            // VBox configuration
            voteCart = new VBox(ballotQuestion, ballotAnswer);
            voteCart.setSpacing(20);
        }

        protected void updateItem(Map.Entry<String, String> voteResult, boolean empty) {
            super.updateItem(voteResult, empty);

            int index = this.getIndex();
            String question;

            // Format ballot cell
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                question = (index + 1) + ".     " + voteResult.getKey();
                ballotQuestion.setText(question);
                voterChoice.setText(voteResult.getValue());
                setGraphic(voteCart);
            }
        }
    }

    public void onClickReturnLogin(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

    public void onClickGetVoteResult() {
        Map<String, String> voteResults = VotingLogic.getInstance().getVoteResultsMap(voteConfirmationCode.getText());
        ObservableList<Map.Entry<String, String>> reviewVoteObservableList = FXCollections.observableArrayList();
        reviewVoteObservableList.addAll(voteResults.entrySet());

        reviewTable.setEditable(false);
        reviewTable.setItems(reviewVoteObservableList);
        reviewTable.setCellFactory(param -> new ReviewVoteCell());
    }
}
