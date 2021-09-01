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

public class ElectionPropositionController extends Controller {

    private static ElectionPropositionController instance;

    public static ElectionPropositionController getInstance(){
        return instance;
    }

    ObservableList<String> list;

    @FXML private TextField proposition;
    @FXML private TextField keyWord;
    @FXML private TableView <String> resultTable;
    @FXML private Label userMessage;
    @FXML
    public void initialize() {
        displayTable();
        instance = this;
    }

    private final String ballotName = ElectionBallotController.getInstance().getBallotName();

    public void switchToBallotOption (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"electionBallot.fxml");
    }

    public void switchToPosition (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"electionPosition.fxml");
    }

    public void displayTable() {

        TableColumn<String, String> proposition = new TableColumn<>("Proposition");
        TableColumn<String, String> delete = new TableColumn<>("Delete");

        // fill data into each cell
        proposition.setCellValueFactory(c-> new SimpleStringProperty(c.getValue()));
        delete.setCellValueFactory(new PropertyValueFactory<>(null));

        // Add delete button
        Callback<TableColumn<String, String>, TableCell<String, String>> cellFactory = new Callback<>() {

            @Override
            public TableCell call(final TableColumn<String, String> param) {
                return new TableCell<String, String>() {
                    final Button deleteButton = new Button("Delete");

                    @Override
                    public void updateItem(String button, boolean empty) {
                        super.updateItem(button, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            deleteButton.setOnAction(event -> {
                                String message = VotingLogic.getInstance().removeVoteCartFromBallot(
                                        ballotName, getTableView().getItems().get(getIndex()));
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

        resultTable.getColumns().addAll(proposition, delete);

        setTableItems();

    }

    public void setTableItems() {
        // Set list and display
        list = FXCollections.observableArrayList(
                VotingLogic.getInstance().getPropositionList(ballotName));
        resultTable.setItems(list);
    }

    public void addNewProposition() {
        String propositionInput = proposition.getText();
        String keywordInput = keyWord.getText();
        if (!list.contains(propositionInput) && !propositionInput.trim().isEmpty() && !keywordInput.trim().isEmpty()) {
            String message = VotingLogic.getInstance().addProposition(ballotName, propositionInput, keywordInput);
            if (message.equals("")) {
                userMessage.setText("Proposition added");
                userMessage.setTextFill(Color.BLACK);
                setTableItems();
            } else {
                userMessage.setText(message);
                userMessage.setTextFill(Color.RED);
            }
        } else {
            userMessage.setText("* duplicate proposition entry / proposition is empty");
            userMessage.setTextFill(Color.RED);
        }
    }
}
