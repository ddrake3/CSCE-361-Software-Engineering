package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class AuditorFinalResultController extends Controller {

    private static AuditorFinalResultController instance;

    public static AuditorFinalResultController getInstance(){
        return instance;
    }

    private final String ballotName = AuditorOptionController.getInstance().getBallotName();

    @FXML ListView<Map.Entry<String, String>> statisticList;
    @FXML TextField ballotCount;
    @FXML TextField voterCount;
    @FXML Label userMessage;
    @FXML
    public void initialize() {
        displayList();
        voterCount.setText(String.valueOf(VotingLogic.getInstance().getNumberOfVotersVoted(ballotName)));
        ballotCount.setText(String.valueOf(VotingLogic.getInstance().getNumberOfBallotVoted(ballotName)));
        instance = this;
    }

    public void switchToAuditorOption(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"auditorOption.fxml");
    }

    public void displayList() {
        ObservableList<Map.Entry<String, String>> lists = FXCollections.observableArrayList();
        lists.addAll(VotingLogic.getInstance().getWinners(ballotName).entrySet());

        statisticList.setEditable(false);
        statisticList.setItems(lists);
        statisticList.setCellFactory(param -> new ResultCell());
    }

    //set the format and input into list cells
    static class ResultCell extends ListCell<Map.Entry<String, String>> {
        VBox voteCart;
        Text question = new Text();
        Text answer = new Text();
        Text empty = new Text();

        public ResultCell() {
            super();
            voteCart = new VBox(question, answer, empty);
            voteCart.setSpacing(20);
        }

        protected void updateItem(Map.Entry<String, String> result, boolean empty) {
            super.updateItem(result, empty);

            int index = this.getIndex();
            String questionList;

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                questionList = (index + 1) + ".  " + result.getKey();
                question.setText(questionList);
                question.setFont(Font.font("arial", FontWeight.NORMAL, 15));
                answer.setText(" ".repeat(6) + result.getValue());
                answer.setFont(Font.font("arial", FontWeight.BOLD, 15));

                setGraphic(voteCart);
            }
        }
    }

}
