package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VoterReviewController extends Controller {

    @FXML private ListView<BallotResultReview> voteTable;

    private String voteConfirmationCode;
    private static final Map<String, String> voteResultSet = new HashMap<>();

    private static VoterReviewController instance;

    public static VoterReviewController getInstance(){
        return instance;
    }

    static class BallotResultReview {

        private final String ballotQuestion;
        private final String voterChoice;
        private final List<String> ballotOptions;

        public BallotResultReview(String ballotQuestion, String voterChoice, List<String> ballotOptions) {
            this.ballotQuestion = ballotQuestion;
            this.voterChoice = voterChoice;
            this.ballotOptions = ballotOptions;
        }

        public String getBallotQuestion() {return ballotQuestion;}

        public String getVoterChoice() {return  voterChoice;}

        public List<String> getBallotOptions () {return ballotOptions;}
    }

    static class ReviewCell extends ListCell<BallotResultReview> {
        VBox voteCart;
        Text ballotQuestion = new Text();
        Text ballotSelection = new Text();
        Text voterChoice = new Text();
        Button editButton = new Button("Edit");
        ChoiceBox<String> optionsList = new ChoiceBox<>();

        HBox editBox;
        HBox ballotAnswer;

        public ReviewCell() {
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

            // HBox configuration for editBox that contains the edit button and choiceBox
            // that consists of options for the particular question
            optionsList.setVisible(false);
            editButton.setPrefWidth(80);
            editBox = new HBox(editButton, optionsList);
            editBox.setSpacing(20);
            editBox.setTranslateX(30);

            // VBox configuration
            voteCart = new VBox(ballotQuestion, ballotAnswer, editBox);
            voteCart.setSpacing(20);
        }

        protected void updateItem(BallotResultReview ballotResult, boolean empty) {
            super.updateItem(ballotResult, empty);

            int index = this.getIndex();
            String question;

            // Format ballot cell
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                optionsList.setItems(FXCollections.observableList(ballotResult.getBallotOptions()));
                question = (index + 1) + ".     " + ballotResult.getBallotQuestion();
                ballotQuestion.setText(question);
                voterChoice.setText(ballotResult.getVoterChoice());
                editButton.setOnMouseClicked(event -> optionsList.setVisible(true));
                optionsList.setOnAction(event -> {
                    voterChoice.setText(optionsList.getValue());
                    voteResultSet.put(ballotResult.getBallotQuestion(), optionsList.getValue());
                });
                setGraphic(voteCart);
            }
        }
    }

    @FXML
    public void initialize() {
        // whatever other initialization you need to do
        instance = this;
        Map<String, Map<String, List<String>>> ballotResult = VoterHomeController.getInstance().getVoteResult();
        ObservableList<BallotResultReview> ballotResultObservableList = FXCollections.observableArrayList();

        for (Map.Entry<String, Map<String, List<String>>> entry : ballotResult.entrySet()) {
            ballotResultObservableList.add(
                    new BallotResultReview(entry.getKey(),
                            entry.getValue().entrySet().iterator().next().getKey(),
                            entry.getValue().entrySet().iterator().next().getValue())
            );
            voteResultSet.put(entry.getKey(), entry.getValue().entrySet().iterator().next().getKey());
        }

        voteTable.setEditable(false);
        voteTable.setItems(ballotResultObservableList);
        voteTable.setCellFactory(param -> new ReviewCell());
    }

    public void onClickBack(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"voterHome.fxml");
    }

    public void onClickSubmit(javafx.event.ActionEvent event) throws IOException {
        Random randomVoteId = new Random();
        String voteId = Integer.toString(randomVoteId.nextInt(899999999) + 100000000);
        this.voteConfirmationCode = voteId;
        VotingLogic.getInstance().createVoteResult(
                VoterHomeController.getInstance().getBallotName(),
                VoterLoginController.getInstance().getVoterName(),
                voteResultSet,
                voteId
        );
        switchScreen(event,"voterCompletion.fxml");
    }

    public String getVoteConfirmationCode() {
        return voteConfirmationCode;
    }
}
