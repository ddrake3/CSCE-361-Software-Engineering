package edu.unl.cse.csce361.voting_system.frontend;

import edu.unl.cse.csce361.voting_system.voting_logic.VotingLogic;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AuditorVoterStatController extends Controller {

    private static AuditorVoterStatController instance;

    public static AuditorVoterStatController getInstance(){
        return instance;
    }

    ObservableList<Statistic> lists;

    @FXML TableView<Statistic> statisticTable;
    @FXML Label userMessage;
    @FXML
    public void initialize() {
        displayTable();
        instance = this;
    }

    public void displayTable() {
        TableColumn<Statistic, String> voterName = new TableColumn<>("Voter Name");
        TableColumn<Statistic, String> voted = new TableColumn<>("Voted");

        voterName.setCellValueFactory(new PropertyValueFactory<>("voterName"));
        voted.setCellValueFactory(new PropertyValueFactory<>("Voted"));

        statisticTable.getColumns().addAll(voterName, voted);

        setTableItems();
    }

    public void setTableItems() {
        lists = FXCollections.observableArrayList();

        Map<String, String> voterMap = VotingLogic.getInstance().
                getVoterAction(AuditorOptionController.getInstance().getBallotName());


        List<String> voterName = List.copyOf(voterMap.keySet());
        List<String> voted = List.copyOf(voterMap.values());

        int size = voterName.size();

        for (int i = 0; i < size; i++) {
            Statistic variable = new Statistic(voterName.get(i), voted.get(i));
            lists.add(variable);
        }

        statisticTable.setItems(lists);
    }

    public void switchToAuditorOption(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"auditorOption.fxml");
    }

    /**
     * Statistic class used to help on display table
     */
    protected static class Statistic {
        private final SimpleStringProperty voterName;
        private final SimpleStringProperty voted;

        private Statistic(String voterName, String voted) {
            this.voterName = new SimpleStringProperty(voterName);
            this.voted = new SimpleStringProperty(voted);
        }

        public String getVoterName(){
            return voterName.get();
        }

        public String getVoted(){
            return voted.get();
        }

    }
}
