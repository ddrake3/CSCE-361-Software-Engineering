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
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuditorBallotStatController extends Controller {

    private static AuditorBallotStatController instance;

    public static AuditorBallotStatController getInstance(){
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
        TableColumn<Statistic, String> question = new TableColumn<>("Question");
        TableColumn<Statistic, String> answer = new TableColumn<>("Answer");
        TableColumn<Statistic, String> result = new TableColumn<>("Result");

        question.setCellValueFactory(new PropertyValueFactory<>("question"));
        answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        result.setCellValueFactory(new PropertyValueFactory<>("result"));

        statisticTable.getColumns().addAll(question, answer, result);

        setTableItems();
    }

    public void setTableItems() {
        lists = FXCollections.observableArrayList();
        List<List<String>> resultList = VotingLogic.getInstance().
                getResultsList(AuditorOptionController.getInstance().getBallotName());

        if (resultList.size() > 3) {
            userMessage.setText("* Exceed expected number of list column");
            userMessage.setTextFill(Color.RED);
        }

        List<String> question = new ArrayList<>(resultList.get(0));
        List<String> answer = new ArrayList<>(resultList.get(1));
        List<String> result = new ArrayList<>(resultList.get(2));

        int size = question.size();

        for (int i = 0; i < size; i++) {
            Statistic variable = new Statistic(question.get(i), answer.get(i), result.get(i));
            lists.add(variable);
        }

        statisticTable.setItems(lists);
    }

    public void switchToAuditorOption(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event,"auditorOption.fxml");
    }

    /**
     * Statistic class to help on display table
     */
    protected static class Statistic {
        private final SimpleStringProperty question;
        private final SimpleStringProperty answer;
        private final SimpleStringProperty result;

        private Statistic(String question, String answer, String result) {
            this.question = new SimpleStringProperty(question);
            this.answer = new SimpleStringProperty(answer);
            this.result = new SimpleStringProperty(result);
        }

        public String getQuestion(){
            return question.get();
        }

        public String getAnswer(){
            return answer.get();
        }

        public String getResult(){
            return result.get();
        }
    }

}
