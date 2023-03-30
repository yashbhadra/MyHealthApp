package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class ExportSuccessfulController {
	
	@FXML
	private Label message;
	@FXML
	private Button yesEdit;
	
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	private String path;
	
	public ExportSuccessfulController(Stage parentStage, Model model,String path) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.path = path;
	}
	@FXML
	public void initialize() {
		message.setText("Operation Successfull  "+this.path);
		yesEdit.setOnAction(event->{	
			
			stage.close();
			parentStage.show();
			
		});
		
	}

	public void showStage(Pane root) {
		
		Scene scene = new Scene(root,  300, 100);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Success");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.show();
	}
	

}
