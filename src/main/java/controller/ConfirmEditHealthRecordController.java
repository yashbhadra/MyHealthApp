package controller;

import java.io.IOException;
import java.sql.SQLException;

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

public class ConfirmEditHealthRecordController {
	
	
	@FXML
	private Button yesEdit;
	@FXML
	private Button cancel;
	
	HealthRecord healthRecord;

	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Stage grandParentStage;

	
	public ConfirmEditHealthRecordController(Stage parentStage, Stage grandParentStage,Model model,HealthRecord healthRecord) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.healthRecord = healthRecord;
		this.grandParentStage = grandParentStage;
	}
	@FXML
	public void initialize() {
		yesEdit.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditHealthRecordView.fxml"));
				
				// Customize controller instance
				EditHealthRecordController editHealthRecordController =  new EditHealthRecordController(parentStage, grandParentStage,model,this.healthRecord);
				loader.setController(editHealthRecordController);
				GridPane root = loader.load();

				editHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				//message.setText(e.getMessage());
			}
			
		});
		cancel.setOnAction(event->{
			parentStage.show();
			stage.close();
		});
	}

	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 250, 100);
		
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Edit Record");
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.show();
	}
	

}
