package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectionPositionController extends Controller {

    private static ElectionPositionController instance;

    public static ElectionPositionController getInstance(){
        return instance;
    }

    ObservableList<Position> lists;

    @FXML private TextField position;
    @FXML private TextField option1;
    @FXML private TextField option2;
    @FXML private TextField option3;
    @FXML private TextField option4;
    @FXML private TextField option5;
    @FXML private TextField option6;
    @FXML private TableView <Position> resultTable;
    @FXML private Label userMessage;
    @FXML
    public void initialize() {
        displayTable();
        instance = this;
    }

    private final String ballotName = ElectionBallotController.getInstance().getBallotName();

    public void switchToBallotOption(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"electionBallot.fxml");
    }

    public void switchToProposition(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"electionProposition.fxml");
    }

    public void displayTable() {

        TableColumn<Position, String> position = new TableColumn<>("Position");
        TableColumn<Position, String> options = new TableColumn<>("Options");
        TableColumn<Position, String> delete = new TableColumn<>("Delete");

        // fill data into each cell
        position.setCellValueFactory(new PropertyValueFactory<>("positionQuestion"));
        options.setCellValueFactory(new PropertyValueFactory<>("positionAnswer"));
        delete.setCellValueFactory(new PropertyValueFactory<>(null));

        // Add delete button
        Callback<TableColumn<Position, String>, TableCell<Position, String>> cellFactory = new Callback<>() {

            @Override
            public TableCell call(final TableColumn<Position, String> param) {
                return new TableCell<Position, String>() {
                    final Button deleteButton = new Button("Delete");

                    @Override
                    public void updateItem(String button, boolean empty) {
                        super.updateItem(button, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            deleteButton.setOnAction(event -> {
                                Position pos = getTableView().getItems().get(getIndex());
                                String message = VotingLogic.getInstance().removeVoteCartFromBallot(
                                        ballotName, pos.getPositionQuestion());
                                userMessage.setText(message);
                                setTableItems();
                            });
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        };
        delete.setCellFactory(cellFactory);

        // display the column in table view
        resultTable.getColumns().addAll(position, options, delete);

        setTableItems();
    }

    public void setTableItems() {
        // Initialize method
        lists = FXCollections.observableArrayList();
        List<String> positionQuestion = VotingLogic.getInstance().
                getPositionList(ballotName);
        List<String> positionAnswer = VotingLogic.getInstance().
                getPositionAnswerList(ballotName);

        int size = positionQuestion.size();

        // Put into list
        for (int i = 0; i < size; i++) {
            Position variable = new Position(positionQuestion.get(i), positionAnswer.get(i));
            lists.add(variable);
        }

        //Set list
        resultTable.setItems(lists);
    }

    public void addNewPosition() {
        String option1Input = option1.getText();
        String option2Input = option2.getText();
        String option3Input = option3.getText();
        String option4Input = option4.getText();
        String option5Input = option5.getText();
        String option6Input = option6.getText();
        String positionInput = position.getText();

        Set<String> options = new HashSet<>();
        if (!option1Input.isEmpty()) {
            options.add(option1Input);
        }
        if (!option2Input.isEmpty()) {
            options.add(option2Input);
        }
        if (!option3Input.isEmpty()) {
            options.add(option3Input);
        }
        if (!option4Input.isEmpty()) {
            options.add(option4Input);
        }
        if (!option5Input.isEmpty()) {
            options.add(option5Input);
        }
        if (!option6Input.isEmpty()) {
            options.add(option6Input);
        }

        if (positionInput.isEmpty() || option1Input.isEmpty()) {
            userMessage.setText("* position and option 1 cannot be empty!");
            userMessage.setTextFill(Color.RED);
        } else {
            List<String> positionList = new ArrayList<>();
            for (Position list : lists) {
                positionList.add(list.getPositionQuestion());
            }
            if (!positionList.contains(positionInput)) {
                VotingLogic.getInstance().addPosition(ballotName, positionInput, options);
                userMessage.setText("Position and Options added");
                userMessage.setTextFill(Color.BLACK);
                setTableItems();
            } else {
                userMessage.setText("* duplicate entry of position");
                userMessage.setTextFill(Color.RED);
            }
        }
    }

    /**
     * Position class created for the use of displaying table view
     */
    protected static class Position {
        private final SimpleStringProperty positionQuestion;
        private final SimpleStringProperty positionAnswer;

        private Position(String positionQuestion, String positionAnswer) {
            this.positionQuestion = new SimpleStringProperty(positionQuestion);
            this.positionAnswer = new SimpleStringProperty(positionAnswer);
        }

        public String getPositionQuestion() {
            return positionQuestion.get();
        }

        public String getPositionAnswer() {
            return positionAnswer.get();
        }
    }

}
