package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.BloodPressure;
import model.HealthRecord;
import model.Model;
import model.User;

public class UpdateHealthRecordController {
	

	@FXML
	private TableView<HealthRecord> table;
	@FXML
	private TableColumn<HealthRecord,DateCell> dateColumn;
	@FXML
	private TableColumn<HealthRecord,Integer> weightColumn;
	@FXML
	private TableColumn<HealthRecord,Integer> temperatureColumn;
	@FXML
	private TableColumn<HealthRecord,String> bloodPressureColumn;
	@FXML
	private TableColumn<HealthRecord,String> notesColumn;
	@FXML
	private Button okHome;
	@FXML
	private Button cancel;
	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public UpdateHealthRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	@FXML
	public void initialize() {
		dateColumn.setCellValueFactory(new PropertyValueFactory<HealthRecord,DateCell>("date"));
		weightColumn.setCellValueFactory(new PropertyValueFactory<HealthRecord,Integer>("weight"));
		temperatureColumn.setCellValueFactory(new PropertyValueFactory<HealthRecord,Integer>("temperature"));
		bloodPressureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBloodPressure().getLow())+"-"+String.valueOf(cellData.getValue().getBloodPressure().getHigh())));

		notesColumn.setCellValueFactory(new PropertyValueFactory<HealthRecord,String>("bloodPressure")); 
		
		notesColumn.setCellValueFactory(new PropertyValueFactory<HealthRecord,String>("notes"));
		
		table.setOnMouseClicked((MouseEvent m)->{
			HealthRecord selected = table.getSelectionModel().getSelectedItem();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ConfirmEditHealthRecordView.fxml"));
				
				// Customize controller instance
				System.out.println(selected.getId());
				ConfirmEditHealthRecordController confirmEditRecordController =  new ConfirmEditHealthRecordController(stage,parentStage, model,selected);
				System.out.println("Called edithealth");

				loader.setController(confirmEditRecordController);
				VBox root = loader.load();
				System.out.println("loaded edithealth");

				confirmEditRecordController.showStage(root);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
		cancel.setOnAction(event->{
			stage.close();
			parentStage.show();
			
		});
		okHome.setOnAction(event->{
			stage.close();
			parentStage.show();
			
		});
	}
	

	public void showStage(Pane root) {
		User user = model.getCurrentUser();
		
		ArrayList<HealthRecord> healthRecords = null;
		try {
			healthRecords = model.getHealthRecordDao().getHealthRecords(user.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(HealthRecord healthRecord : healthRecords ) { table.getItems().add(healthRecord); }

		Scene scene = new Scene(root, 700, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update Records");
		stage.show();
	}
	

}
