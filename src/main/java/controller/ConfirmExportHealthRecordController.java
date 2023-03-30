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

public class ConfirmExportHealthRecordController {
	
	
	@FXML
	private Button yesEdit;
	@FXML
	private Button cancel;
	
	private List<HealthRecord> healthRecordList;

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public ConfirmExportHealthRecordController(Stage parentStage, Model model,List<HealthRecord> healthRecord) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.healthRecordList = new ArrayList<HealthRecord>(healthRecord);
	}
	@FXML
	public void initialize() {
		yesEdit.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportPathView.fxml"));
				
				// Customize controller instance
				ExportPathController exportPathController =  new ExportPathController(parentStage,stage, model,this.healthRecordList);

				loader.setController(exportPathController);
				VBox root = loader.load();
				
				exportPathController.showStage(root);
				stage.close();

				
			} catch (IOException e) {
				//message.setText(e.getMessage());
			}
			
		});
		cancel.setOnAction(event->{
			stage.close();
		});
	}

	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 300, 100);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Export Records");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.show();

	}
	

}
