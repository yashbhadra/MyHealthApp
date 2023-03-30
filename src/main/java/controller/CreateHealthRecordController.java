package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

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
import javafx.stage.Stage;
import model.BloodPressure;
import model.HealthRecord;
import model.Model;
import model.User;

public class CreateHealthRecordController {
	

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
	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public CreateHealthRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	@FXML
	public void initialize() {
		date.setEditable(false);

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
					
					healthRecord = model.getHealthRecordDao().createHealthRecord(model.getCurrentUser().getUsername(),java.sql.Date.valueOf(date.getValue()),integerTemperature,integerWeight,new BloodPressure(integerLow,integerHigh),notes.getText());
					if (healthRecord != null) {
						status.setText("Record Created ");
						status.setTextFill(Color.GREEN);
						var records = model.getHealthRecordDao().getHealthRecords(model.getCurrentUser().getUsername());

					} else {
						status.setText("Cannot create record");
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
				status.setText("All fields cannot be empty");
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
		stage.setTitle("New Record");
		stage.show();
	}
	

}
