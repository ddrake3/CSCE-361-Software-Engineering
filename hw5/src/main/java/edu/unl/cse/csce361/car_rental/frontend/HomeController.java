package edu.unl.cse.csce361.car_rental.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeController {

    private void openScreen(String file, ActionEvent event)throws IOException{
        FXMLLoader loaderSelectModel = new FXMLLoader();
        loaderSelectModel.setLocation(getClass().getResource(file));
        Parent parent = loaderSelectModel.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    /**
     * Swtich to the Home screen upon button press.
     * @param event
     * @throws IOException
     */
    public void switchHome(ActionEvent event) throws IOException {
        openScreen("home.fxml", event);
    }

    /**
     * Swtich to the SignIn screen upon button press.
     * @param event
     * @throws IOException
     */
    public void switchSignIn(ActionEvent event)throws IOException{
        openScreen("SignIn.fxml", event);

    }

    /**
     * Swtich to the SelectCar screen upon button press.
     * @param event
     * @throws IOException
     */
    public void switchSelectCar(ActionEvent event)throws IOException {
        openScreen("SelectCar.fxml", event);

    }

    /**
     * Swtich to the Checkout screen upon button press.
     * @param event
     * @throws IOException
     */
    public void swtichCheckout(ActionEvent event)throws IOException {
        openScreen("Checkout.fxml", event);

    }

    /**
     * Swtich to the ThankYou screen upon button press.
     * @param event
     * @throws IOException
     */
    public void switchThankYou(ActionEvent event)throws IOException {
        openScreen("ThankYou.fxml", event);

    }

}
