package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem viewProfile; // Corresponds to the Menu item "viewProfile" in HomeView.fxml
	@FXML
	private MenuItem updateProfile; // // Corresponds to the Menu item "updateProfile" in HomeView.fxml
	@FXML
	private Label welcomeName;
	@FXML
	private MenuItem createHealthRecord;
	@FXML
	private MenuItem updateHealthRecord;
	@FXML
	private MenuItem deleteHealthRecord;
	@FXML
	private MenuItem viewHealthRecord;
	@FXML
	private MenuItem export;
	@FXML
	private MenuItem about;
	@FXML
	private MenuItem logOut;

	
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	// Add your code to complete the functionality of the program
	@FXML
	public void initialize() {
		User user = model.getCurrentUser();
		welcomeName.setText("Welcome "+user.getFirstName()+" "+user.getLastName());
		welcomeName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		welcomeName.setTextFill(Color.RED); 

		//Setting the controller for viewing profile
		viewProfile.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewProfileView.fxml"));
				
				// Customize controller instance
				ViewProfileController viewProfileController =  new ViewProfileController(stage, model);

				loader.setController(viewProfileController);
				HBox root = loader.load();
				
				viewProfileController.showStage(root);
				
			} catch (IOException e) {
				//message.setText(e.getMessage());
			}
		});
		
		//Setting the controller for updating profile
		updateProfile.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfileView.fxml"));
				
				// Customize controller instance
				EditProfileController editProfileController =  new EditProfileController(stage, model,this);

				loader.setController(editProfileController);
				HBox root = loader.load();
				
				editProfileController.showStage(root);
				
			} catch (IOException e) {
				//message.setText(e.getMessage());
			}

		});
		//Setting the controller for creating record

		createHealthRecord.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateHealthRecordView.fxml"));
				
				// Customize controller instance
				CreateHealthRecordController createHealthRecordController =  new CreateHealthRecordController(stage, model);

				loader.setController(createHealthRecordController);
				GridPane root = loader.load();
				
				createHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				//message.setText(e.getMessage());
			}
		});
		//Setting the controller for viewing health record

		viewHealthRecord.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewHealthRecordView.fxml"));
				
				// Customize controller instance
				ViewHealthRecordController viewHealthRecordController =  new ViewHealthRecordController(stage, model);

				loader.setController(viewHealthRecordController);
				Pane root = loader.load();
				
				viewHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		//Setting the controller for updating record

		updateHealthRecord.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateHealthRecordView.fxml"));
				
				// Customize controller instance
				UpdateHealthRecordController updateHealthRecordController =  new UpdateHealthRecordController(stage, model);

				loader.setController(updateHealthRecordController);
				Pane root = loader.load();
				
				updateHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		//Setting the controller for deleting profile

		deleteHealthRecord.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeleteHealthRecordView.fxml"));
				
				// Customize controller instance
				DeleteHealthRecordController deleteHealthRecordController =  new DeleteHealthRecordController(stage, model);

				loader.setController(deleteHealthRecordController);
				Pane root = loader.load();
				
				deleteHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		//Setting the controller for exporting record

		export.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportHealthRecordView.fxml"));
				
				// Customize controller instance
				ExportHealthRecordController exportHealthRecordController =  new ExportHealthRecordController(stage, model);

				loader.setController(exportHealthRecordController);
				Pane root = loader.load();
				
				exportHealthRecordController.showStage(root);
				
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		//Setting the controller for about page

		about.setOnAction(event->{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AboutView.fxml"));
				
				// Customize controller instance
				AboutViewController aboutViewController =  new AboutViewController(stage, model);

				loader.setController(aboutViewController);
				VBox root = loader.load();
				
				aboutViewController.showStage(root);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		//Setting the controller for logging out

		logOut.setOnAction(event->{
			try {
				model.setCurrentUser(null);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
				
				// Customize controller instance
				LoginController loginController =  new LoginController(stage, model);

				loader.setController(loginController);
				Pane root = loader.load();
				
				loginController.showStage(root);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
	}
	
	
	
	
	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();
	}
}
