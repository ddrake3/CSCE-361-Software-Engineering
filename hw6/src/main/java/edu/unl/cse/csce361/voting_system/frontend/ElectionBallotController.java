package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionBallotController extends Controller {

    private static ElectionBallotController instance;

    public static ElectionBallotController getInstance(){
        return instance;
    }

    ObservableList<BallotView> list;
    List<String> ballotNameList = new ArrayList<>();

    @FXML TableView<BallotView> ballotView;
    @FXML TextField ballotName;
    @FXML Label userMessage;
    @FXML
    public void initialize() {
        displayTable();
        instance = this;
    }

    public void switchToAuditorPage (javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "auditorOption.fxml");
    }

    public void displayTable() {

        TableColumn<BallotView, String> ballotNameCol = new TableColumn<>("Ballot Name");
        TableColumn<BallotView, String> ballotStatusCol = new TableColumn<>("Ballot Status");
        TableColumn<BallotView, String> publishCol = new TableColumn<>("Publish Selection");

        // fill data into each cell
        ballotNameCol.setCellValueFactory(new PropertyValueFactory<>("ballotName"));
        ballotStatusCol.setCellValueFactory(new PropertyValueFactory<>("ballotStatus"));
        publishCol.setCellValueFactory(new PropertyValueFactory<>(null));

        // Add publish button
        Callback<TableColumn<BallotView, String>, TableCell<BallotView, String>> cellFactory = new Callback<>() {

            @Override
            public TableCell call(final TableColumn<BallotView, String> param) {
                return new TableCell<BallotView, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Button publishButton = null;
                            BallotView ballot = getTableView().getItems().get(getIndex());
                            if (ballot.getBallotStatus().equalsIgnoreCase("closed")) {
                                publishButton = new Button();
                                publishButton.setVisible(false);
                            }else {
                                if (ballot.getBallotStatus().equalsIgnoreCase("open")) {
                                    publishButton = new Button("Publish");
                                }
                                else if (ballot.getBallotStatus().equalsIgnoreCase("ongoing")) {
                                    publishButton = new Button("End");
                                }
                                publishButton.setOnAction(event -> {
                                    String message = VotingLogic.getInstance().proceedBallot(ballot.getBallotNaming());
                                    userMessage.setText(message);
                                    setTableItems();
                                });
                            }
                            setGraphic(publishButton);
                        }
                    }
                };
            }
        };
        publishCol.setCellFactory(cellFactory);

        // display the column in table view
        ballotView.getColumns().addAll(ballotNameCol, ballotStatusCol, publishCol);

        setTableItems();
    }

    public void setTableItems() {
        // Initialize method
        list = FXCollections.observableArrayList();
        List<List<String>> ballotList = VotingLogic.getInstance().getBallotsStatus();
        if (ballotList.size() > 2) {
            userMessage.setText("* Exceed expected number of list column");
            userMessage.setTextFill(Color.RED);
        }
        List<String> ballotNameList = ballotList.get(0);
        List<String> ballotStatusList = ballotList.get(1);

        int size = ballotNameList.size();

        // Put into list
        for (int i = 0; i < size; i++) {
            BallotView variable = new BallotView(ballotNameList.get(i), ballotStatusList.get(i));
            list.add(variable);
        }

        // Set list
        ballotView.setItems(list);
    }

    public void addBallot(javafx.event.ActionEvent event) throws IOException {
        for (BallotView lists : list) {
            ballotNameList.add(lists.getBallotNaming());
        }
        if (ballotNameList.contains(getBallotName()) || getBallotName().trim().isEmpty()) {
            userMessage.setText("* Duplicate entry or you do not enter any ballot");
            userMessage.setTextFill(Color.RED);
        }
        else {
            String message;
            message = VotingLogic.getInstance().createBallot(getBallotName());
            if (message == null) {
                switchScreen(event, "electionPosition.fxml");
            } else {
                userMessage.setText("* " + message);
                userMessage.setTextFill(Color.RED);
            }
        }
    }

    public void editBallot(javafx.event.ActionEvent event) throws IOException {
        String status = null;
        for (BallotView lists : list) {
            ballotNameList.add(lists.getBallotNaming());
            if (getBallotName().equalsIgnoreCase(lists.getBallotNaming())) {
                status = lists.getBallotStatus();
            }
        }
        if (ballotNameList.contains(getBallotName()) && !getBallotName().trim().isEmpty()) {
            if (status.equalsIgnoreCase("open")) {
                switchScreen(event, "electionPosition.fxml");
            }
            else {
                userMessage.setText("* The ballot is published");
                userMessage.setTextFill(Color.RED);
            }
        }
        else {
            userMessage.setText("* The ballot does not exist or you do not enter any ballot");
            userMessage.setTextFill(Color.RED);
        }
    }

    public void onClickLogout(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"committeeLogin.fxml");
    }

    public String getBallotName(){
        return this.ballotName.getText();
    }

    /**
     * BallotView Class created for the use of displaying table view
     */
    protected static class BallotView{
        private final SimpleStringProperty ballotName;
        private final SimpleStringProperty ballotStatus;

        private BallotView(String ballotName, String ballotStatus) {
            this.ballotName = new SimpleStringProperty(ballotName);
            this.ballotStatus = new SimpleStringProperty(ballotStatus);
        }

        public String getBallotNaming() {
            return ballotName.get();
        }

        public String getBallotStatus() {
            return ballotStatus.get();
        }

        // return property value factory for ballot name in table cell
        public StringProperty ballotNameProperty() {
            return ballotName;
        }
    }
}
