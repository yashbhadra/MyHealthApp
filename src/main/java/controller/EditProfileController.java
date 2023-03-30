package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class EditProfileController {
	
	@FXML
	private Label userName;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button okHome;
	@FXML
	private Label status;
	@FXML
	private ImageView imageView;

	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	private HomeController homeController;
	
	public EditProfileController(Stage parentStage, Model model,HomeController homeController) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.homeController = homeController;
	}
	@FXML
	public void initialize() {
		
		File file = new File("src/resources/golang.jpeg");
		imageView.setImage(new Image("file:"+file.getAbsolutePath()));			
		   
		okHome.setOnAction(event->{
			if (!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
				User user;
				try {
					user = model.getUserDao().updateUser(model.getCurrentUser().getUsername(),model.getCurrentUser().getPassword(),firstName.getText(),lastName.getText());
					if (user != null) {
						status.setText("Created " + user.getUsername());
						status.setTextFill(Color.GREEN);
						model.setCurrentUser(user);
					} else {
						status.setText("Cannot create user");
						status.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				}
				
			} else {
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
				HomeController homeController = new HomeController(stage, model);
				
				loader.setController(homeController);
				VBox root = loader.load();
				stage.close();
				parentStage.close();
				homeController.showStage(root);
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		});;
	}

	public void showStage(Pane root) {
		User user = model.getCurrentUser();
		userName.setText(user.getUsername());
		Scene scene = new Scene(root, 600, 400);
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Edit Profile");
		stage.show();
	}
	

}
