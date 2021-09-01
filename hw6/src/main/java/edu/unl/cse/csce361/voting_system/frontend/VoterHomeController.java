package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoterHomeController extends Controller {

    @FXML private ListView<Map.Entry<String, List<String>>> voteTable;
    @FXML private Label userMessage;
    @FXML private Label ballotName;

    private final String ballot = VotingLogic.getInstance().getPublishingBallotName();

    private static VoterHomeController instance;

    public static VoterHomeController getInstance(){
        return instance;
    }

    private static final Map<String, Map<String, List<String>>> voteResult = new HashMap<>();
    private List<String> ballotQuestions;

    static class BallotCell extends ListCell<Map.Entry<String, List<String>>> {

        VBox voteCart;
        Text ballotQuestion = new Text();
        ChoiceBox<String> optionsList = new ChoiceBox<>();

        public BallotCell() {
            super();
            voteCart = new VBox(ballotQuestion, optionsList);
            voteCart.setSpacing(20);
        }

        protected void updateItem(Map.Entry<String, List<String>> ballot, boolean empty) {
            super.updateItem(ballot, empty);

            int index = this.getIndex();
            String question;

            // Format ballot cell
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                question = (index + 1) + ".     " + ballot.getKey();
                ballotQuestion.setText(question);
                ballotQuestion.setFont(Font.font("arial", FontWeight.BOLD, 15));
                optionsList.setItems(FXCollections.observableList(ballot.getValue()));
                optionsList.setTranslateX(30);
                setGraphic(voteCart);
                optionsList.setOnAction(e -> voteResult.put(ballot.getKey(), new HashMap() {
                    {
                        put(optionsList.getValue(), ballot.getValue());
                    }
                }));
            }
        }
    }

    @FXML
    public void initialize() {
        ballotName.setText(VotingLogic.getInstance().getPublishingBallotName());
        ballotQuestions = VotingLogic.getInstance().getPositionList(ballot);
        ballotQuestions.addAll(VotingLogic.getInstance().getPropositionList(ballot));
        Map<String, List<String>> ballot = new HashMap<>();
        ObservableList<Map.Entry<String, List<String>>> ballotObservableList = FXCollections.observableArrayList();

        for (String question : ballotQuestions) {
            ballot.put(question, VotingLogic.getInstance().getOptionsListForQuestion(this.ballot, question));
        }

        ballotObservableList.addAll(ballot.entrySet());

        voteTable.setEditable(false);
        voteTable.setItems(ballotObservableList);
        voteTable.setCellFactory(param -> new BallotCell());

        instance = this;
    }

    public void onClickSignOut(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterLogin.fxml");
    }

    public void onClickReview(javafx.event.ActionEvent event) throws IOException {
        if (voteResult.size() != ballotQuestions.size()) {
            userMessage.setText("* Please enter your choice for each question.");
            userMessage.setTextFill(Color.RED);
        } else {
            switchScreen(event,"voterReview.fxml");
        }
    }

    public Map<String, Map<String, List<String>>> getVoteResult() {
        return voteResult;
    }

    public String getBallotName() {
        return ballotName.getText();
    }
}
