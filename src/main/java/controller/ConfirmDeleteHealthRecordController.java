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
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;
import model.User;

public class ConfirmDeleteHealthRecordController {
	
	
	@FXML
	private Button yesEdit;
	@FXML
	private Button cancel;
	
	HealthRecord healthRecord;

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public ConfirmDeleteHealthRecordController(Stage parentStage, Model model,HealthRecord healthRecord) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.healthRecord = healthRecord;
	}
	@FXML
	public void initialize() {
		yesEdit.setOnAction(event->{
			
			
		try {
			this.model.getHealthRecordDao().deleteHealthRecord(this.healthRecord.getId());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
			HomeController homeController = new HomeController(stage, model);

			loader.setController(homeController);
			VBox root = loader.load();
				
			homeController.showStage(root);
			stage.close();
		}catch (IOException e) {
			//message.setText(e.getMessage());
		}catch(SQLException s) {
			
		}
		});
		cancel.setOnAction(event->{
			stage.close();
			parentStage.show();
		});
	}

	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 500, 500);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Delete Record");
		stage.show();
	}
	

}
