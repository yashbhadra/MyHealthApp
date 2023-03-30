package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import exceptions.LengthExceededException;
import exceptions.NegativeNumberException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BloodPressure;
import model.HealthRecord;
import model.Model;
import model.User;

public class EditHealthRecordController {
	

	@FXML
	private DatePicker date;
	@FXML
	private TextField weight;
	@FXML
	private TextField temperature;
	@FXML
	private TextField low;
	@FXML
	private TextField high;
	@FXML
	private TextArea notes;
	@FXML
	private Button okHome;
	@FXML
	private Label status;
	@FXML
	private Button cancelHome;
	
	private HealthRecord healthRecord;

	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Stage grandParentStage;
	
	public EditHealthRecordController(Stage parentStage,Stage grandParentStage, Model model, HealthRecord healthRecord) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.healthRecord = healthRecord;
		this.grandParentStage = grandParentStage;
	}
	@FXML
	public void initialize() {
		try {
			HealthRecord healthRecordOld = this.model.getHealthRecordDao().getHealthRecordById(this.healthRecord.getId());
			
		    date.setValue(healthRecord.getDate().toLocalDate());
		    
			weight.setText(String.valueOf(healthRecord.getWeight()));
			temperature.setText(String.valueOf(healthRecord.getTemperature()));
			low.setText(String.valueOf(healthRecord.getBloodPressure().getLow()));
			high.setText(String.valueOf(healthRecord.getBloodPressure().getHigh()));
			notes.setText(String.valueOf(healthRecord.getNotes()));
			date.setEditable(false);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		okHome.setOnAction(event->{
			if (!(date.getValue()==null) && (!weight.getText().isEmpty() || !temperature.getText().isEmpty() || !low.getText().isEmpty() || !high.getText().isEmpty() || !notes.getText().isEmpty())) {
				HealthRecord healthRecord;
				int integerWeight=0;
				int integerTemperature=0;
				int integerLow=0;
				int integerHigh=0;
				
				try {
					if(!weight.getText().isEmpty()) {
						integerWeight = Integer.parseInt(weight.getText());
						if(integerWeight<0) {
							throw new NegativeNumberException("Please enter a non-negative number");
						}
					}
					if(!temperature.getText().isEmpty()) {
						integerTemperature = Integer.parseInt(temperature.getText());
					}
					if(!low.getText().isEmpty()) {
						integerLow = Integer.parseInt(low.getText());
						if(integerLow<0) {
							throw new NegativeNumberException("Please enter a non-negative number");
						}
					}
					if(!high.getText().isEmpty()) {
						integerHigh = Integer.parseInt(high.getText());
						if(integerHigh<0) {
							throw new NegativeNumberException("Please enter a non-negative number");
						}
					}
					if(!notes.getText().isEmpty()) {
						String words[] = notes.getText().split(" ");
						if(words.length>50) {
							throw new LengthExceededException("Notes length exceeded");
						}
					}
					
					healthRecord = model.getHealthRecordDao().updateHealthRecord(model.getCurrentUser().getUsername(),java.sql.Date.valueOf(date.getValue()),integerTemperature,integerWeight,new BloodPressure(integerLow,integerHigh),notes.getText(),this.healthRecord.getId());
					if (healthRecord != null) {
						status.setText("Record Updated");
						status.setTextFill(Color.GREEN);
						
						var records = model.getHealthRecordDao().getHealthRecords(model.getCurrentUser().getUsername());
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportSuccessfulView.fxml"));
							ExportSuccessfulController exportSuccessController = new ExportSuccessfulController(grandParentStage, model,"");

							loader.setController(exportSuccessController);
							VBox root = loader.load();
							parentStage.close();

							exportSuccessController.showStage(root);

						}catch (IOException e) {
							//message.setText(e.getMessage());
						}
					} else {
						status.setText("Cannot edit record");
						status.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				}
				catch (NumberFormatException ne) {
					status.setText("Please enter a valid number");
					status.setTextFill(Color.RED);
				}
				catch (NegativeNumberException nne) {
					status.setText(nne.getMessage());
					status.setTextFill(Color.RED);
				} catch (LengthExceededException le) {
					// TODO Auto-generated catch block
					status.setText(le.getMessage());
					status.setTextFill(Color.RED);
				}
				
				
			} else {
				status.setText("Any field cannot be empty");
				status.setTextFill(Color.RED);
			}
			
			
		});
		cancelHome.setOnAction(event->{
			stage.close();
			parentStage.show();
		});
	}

	public void showStage(Pane root) {
		User user = model.getCurrentUser();
		Scene scene = new Scene(root, 700, 700);
		
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Edit Record");
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);

		stage.show();
	}
	

}
