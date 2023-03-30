package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

public class AboutViewController {
	
	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public AboutViewController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root,200,200);
		
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("About");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.showAndWait();
		
	}
	

}
