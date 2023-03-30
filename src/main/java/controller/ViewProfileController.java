package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class ViewProfileController {
	
	@FXML
	private Label userName;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	@FXML
	private Button okHome;
	@FXML
	private ImageView imageView;
	

	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public ViewProfileController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	@FXML
	public void initialize() {
		File file = new File("src/resources/golang.jpeg");
		imageView.setImage(new Image("file:"+file.getAbsolutePath()));

		okHome.setOnAction(event->{
				
				stage.close();
				parentStage.show();
		
		});
	}

	public void showStage(Pane root) {
		System.out.println(model.getCurrentUser().getUsername());
		User user = model.getCurrentUser();
		userName.setText(user.getUsername());
		firstName.setText(user.getFirstName());
		lastName.setText(user.getLastName());
		Scene scene = new Scene(root, 500, 500);
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("View Profile");
		stage.show();
	}
	

}
