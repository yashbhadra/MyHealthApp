package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BloodPressure;
import model.HealthRecord;
import model.Model;

public class ExportPathController {
	
	
	@FXML
	private Button choosePath;
	@FXML
	private Button cancel;
	@FXML
	private TextField filePath;
	@FXML
	private Button save;
	@FXML
	private TextField fileName;
	@FXML
	private Label status;
	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Stage grandParentStage;
	
	private List<HealthRecord> recordList;
	
	public ExportPathController(Stage grandParent,Stage parentStage, Model model, List<HealthRecord> recordList) {
		this.stage = new Stage();
		this.grandParentStage=grandParent;
		this.parentStage = parentStage;
		this.model = model;
		this.recordList=recordList;
	}
	@FXML
	public void initialize() {
		choosePath.setOnAction(event->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(stage);

			if(selectedDirectory == null){
			     //No Directory selected
			}else{
				 filePath.setText(selectedDirectory.getAbsolutePath());
			}
			
		});
		save.setOnAction(event->{
			File csvFile = null;

			if (!filePath.getText().isEmpty() && !fileName.getText().isEmpty()) {
				//save
				try {
					List<String> col = new ArrayList<String>();
					col.add("ID");
					col.add("Date");
					col.add("temperature");
					col.add("weight");
					col.add("BloodPressure_LOW");
					col.add("BloodPressure_High");
					col.add("Notes");
					
					this.recordList.forEach(System.out::println);

				    csvFile = new File(filePath.getText()+"/"+fileName.getText()+".csv");
				    PrintWriter pw = new PrintWriter(csvFile);
					pw.println(col.stream().map(this::escapeSpecialCharacters).collect(Collectors.joining(",")));
				    this.recordList.stream().map(this::convertToCSV).forEach(pw::println);
				    pw.close();
				    
				    System.out.println(csvFile.exists());

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} else {
				status.setText("Any field cannot be empty");
				status.setTextFill(Color.RED);
			}
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportSuccessfulView.fxml"));
				ExportSuccessfulController exportSuccessController = new ExportSuccessfulController(grandParentStage, model,csvFile.getPath());

				loader.setController(exportSuccessController);
				VBox root = loader.load();
					
				exportSuccessController.showStage(root);
				stage.close();

			}catch (IOException e) {
				//message.setText(e.getMessage());
			}
		});
		cancel.setOnAction(event->{
			stage.close();
			grandParentStage.show();
		});
		
	}

	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 500, 300);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Export");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(grandParentStage);
		stage.show();
	}
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	public String convertToCSV(HealthRecord data) {
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(String.valueOf(data.getId()));
		temp.add(String.valueOf(data.getDate()));
		temp.add(String.valueOf(data.getTemperature()));
		temp.add(String.valueOf(data.getWeight()));
		temp.add(String.valueOf(data.getBloodPressure().getLow()));
		temp.add(String.valueOf(data.getBloodPressure().getHigh()));
		temp.add(String.valueOf(data.getNotes()));
	    return temp.stream().map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}
	
	

}
