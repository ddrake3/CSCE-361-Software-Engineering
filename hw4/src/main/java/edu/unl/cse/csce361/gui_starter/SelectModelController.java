package edu.unl.cse.csce361.gui_starter;

import java.util.HashMap;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class SelectModelController extends HomeController{
//	HomeController home = new HomeController();
	@FXML private Label customerName;
	  void initialize() {
		  FXMLLoader newHomeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
	  }
	  

	public void printFilters() {
		HashMap<Integer, String> filterlist = this.getFilter();
		System.out.println("Button Clicked");
		/* For Debug */
		System.out.println("\n");
		System.out.println("Attributes: ");
		System.out.println(this.filter);
	}
	
		
		
	
	
}
