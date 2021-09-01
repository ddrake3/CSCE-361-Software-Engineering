package edu.unl.cse.csce361.gui_starter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;
import java.util.HashMap;
import java.io.IOException;

import com.sun.glass.ui.MenuItem;

public class HomeController{
	
	@FXML private Label middle;  
	
	@FXML private SplitMenuButton carModel;
	@FXML private SplitMenuButton carClass;
	@FXML private SplitMenuButton numberOfDoors;
	@FXML private SplitMenuButton fuelEconomy;
	@FXML private SplitMenuButton fuelType;
	@FXML private SplitMenuButton seatCapacity;
	@FXML private SplitMenuButton preferColors;

	protected HashMap<String, String> searchMap = new HashMap<String, String>();
	protected HashMap<Integer, String> filter = new HashMap<Integer, String>();
	
	final static Integer modelFilter = 1;
	final static Integer classFilter = 2;
	final static Integer doorsFilter = 3;
	final static Integer fuelEconFilter = 4;
	final static Integer typeFilter = 5;
	final static Integer seatFilter = 6;
	final static Integer colorFilter = 7;
	
	public void clickReset() {
		middle.setText("Welcome to the Husker Car Rental ");
	}
	
	final public void createMap() {
		for(int i=0;i<carModel.getItems().size();i++) {
			searchMap.put("Model", carModel.getItems().get(i).getText());
		}
		for(int i=0;i<carClass.getItems().size();i++) {
			searchMap.putIfAbsent("Class", carClass.getItems().get(i).getText());
		}
		for(int i=0;i<numberOfDoors.getItems().size();i++) {
			searchMap.putIfAbsent("Doors", numberOfDoors.getItems().get(i).getText());
		}
		for(int i=0;i<fuelEconomy.getItems().size();i++) {
			searchMap.putIfAbsent("Economy", fuelEconomy.getItems().get(i).getText());
		}
		for(int i=0;i<fuelType.getItems().size();i++) {
			searchMap.putIfAbsent("fuelType", fuelType.getItems().get(i).getText());
		}
		for(int i=0;i<seatCapacity.getItems().size();i++) {
			searchMap.putIfAbsent("seat", seatCapacity.getItems().get(i).getText());
		}
		for(int i=0;i<preferColors.getItems().size();i++) {
			searchMap.putIfAbsent("seat", preferColors.getItems().get(i).getText());
		}
		
	}
	
	public void clickModel() {  
		
		carModel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Model button clicked");
			}
			
		});
		
		for(int i=0;i<carModel.getItems().size();i++) {
			
			String modelItem = carModel.getItems().get(i).getText();
			carModel.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					carModel.setText(modelItem);
					filter.put(modelFilter, modelItem);
					System.out.println("User Select Model: " + modelItem);
					middle.setText("Select Model - " + modelItem);
				}
			   
			});
		}
		
	}  
	
	
	public void clickClass() {
		
		carClass.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Class button clicked");
			}
			
		});
		
		for(int i=0;i<carClass.getItems().size();i++) {
			
			String item = carClass.getItems().get(i).getText();
			carClass.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					carClass.setText(item);
					filter.put(classFilter, item);
					System.out.println("User Select class: " + item);
					middle.setText("Select the -- " + item + " type");
				}
			   
			});
		}
		
	}  
	
	public void clickNumberOfDoors() {
		
		/* For Debug */
		numberOfDoors.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Doors Selection button clicked");
			}
			
		});
		
		for(int i=0;i<numberOfDoors.getItems().size();i++) {
			
			String doorsNumber = numberOfDoors.getItems().get(i).getText();
			numberOfDoors.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					numberOfDoors.setText(doorsNumber);
					filter.put(doorsFilter, doorsNumber);
					System.out.println("Select Number of doors: " + doorsNumber);
					middle.setText("Select type of " + doorsNumber + " cars");
				}
			   
			});
		}
	}
	
	
	public void clickFuleEconomy() {
		
		fuelEconomy.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Fule Economy Button was clicked");
			}
			
		});
		
		for(int i=0;i<fuelEconomy.getItems().size();i++) {
			
			String economyType = fuelEconomy.getItems().get(i).getText();
			fuelEconomy.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					fuelEconomy.setText(economyType);
					filter.put(fuelEconFilter, economyType);
					System.out.println("Select fuel Economy type: " + economyType);
					middle.setText("Select economy of " + economyType + "cars");
				}
			   
			});
		}
	}
	
	public void clickFuelType() {
		fuelType.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Fule Type Button was clicked");
			}
			
		});
		
		for(int i=0;i<fuelType.getItems().size();i++) {
			
			String fueltype = fuelType.getItems().get(i).getText();
			fuelType.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					fuelType.setText(fueltype);
					filter.put(typeFilter, fueltype);
					System.out.println("Select type of fuel: " + fueltype);
					middle.setText("Select type of fuel : " + fueltype);
				}
			   
			});
		}
	}
	
	public void clickSeatCapacity() {
		
		seatCapacity.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Seat Selection button was clicked");
			}
			
		});
		
		for(int i=0;i<seatCapacity.getItems().size();i++) {
			
			String seattype = seatCapacity.getItems().get(i).getText();
			seatCapacity.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					seatCapacity.setText(seattype);
					filter.put(seatFilter, seattype);
					System.out.println("Select number of seats: " + seattype);
					middle.setText("Select number of seats :  " + seattype);
				}
			   
			});
		}
	}
	
	public void clickColorPreference() {
		preferColors.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("Select preference of color button was clicked");
			}
			
		});
		
		for(int i=0;i<preferColors.getItems().size();i++) {
			
			String color = preferColors.getItems().get(i).getText();
			preferColors.getItems().get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					preferColors.setText(color);
					filter.put(colorFilter, color);
					System.out.println("Select the preference color: " + color);
					middle.setText("Preference Color --- " + color);
				}
			   
			});
		}
	}
	
	public void reset() {
		
		carModel.setText("Model");
		carClass.setText("Class");
		preferColors.setText("Prefered Color");
		numberOfDoors.setText("Number Of doors");
		fuelEconomy.setText("Fuel Ecnonomy");
		seatCapacity.setText("Seating Capacity");
		fuelType.setText("Fuel type");
		middle.setText("Alright, you reset all the selection...");
		filter.clear();

	}
	
	public HashMap<Integer, String> getFilter() {
		return this.filter;
	}
	
	public Stage switchPageButtonPushedSelectModel(ActionEvent event) throws IOException {
		/* Loading the secondary scene of Select Car */
		/* For Debug */
		System.out.println("Attributes: ");
		System.out.println(filter);
		
		FXMLLoader loaderSelectModel = new FXMLLoader();
		loaderSelectModel.setLocation(getClass().getResource("SelectModel.fxml"));
		Parent child = loaderSelectModel.load();

		Scene scene = new Scene(child);
		Stage secondaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		secondaryStage.setTitle("Husker Klunker Car Rentals");
		
		secondaryStage.setScene(scene);
		secondaryStage.show();
		
//		SelectModelController controller = loaderSelectModel.<SelectModelController>getController();
//		controller.initData(customer);
		return secondaryStage;
	}
	
	public void switchPageButtonPushedCheckoutModel(javafx.scene.input.MouseEvent event) throws IOException{
		
		FXMLLoader loaderSelectModel = new FXMLLoader();
		loaderSelectModel.setLocation(getClass().getResource("CheckoutModel.fxml"));
		Parent parent = loaderSelectModel.load();

		Scene scene = new Scene(parent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}

	public void switchPageButtonHomeModel(ActionEvent event)throws IOException{
		FXMLLoader loaderSelectModel = new FXMLLoader();
		loaderSelectModel.setLocation(getClass().getResource("Home.fxml"));
		Parent parent = loaderSelectModel.load();

		Scene scene = new Scene(parent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}

	public void switchPageButtonThankYouModel(ActionEvent event)throws  IOException{
		FXMLLoader loaderSelectModel = new FXMLLoader();
		loaderSelectModel.setLocation(getClass().getResource("ThankYou.fxml"));
		Parent parent = loaderSelectModel.load();

		Scene scene = new Scene(parent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		window.setScene(scene);
		window.show();
	}
}

