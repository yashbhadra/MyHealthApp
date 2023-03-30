package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);

		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
				User user;
				try {
					user = model.getUserDao().getUserByUsername(username.getText());
					if(user == null) {
						Matcher m = p.matcher(password.getText());
						if(m.matches()!=false) {
							MessageDigest md = MessageDigest.getInstance("SHA-512");
							byte[] hashedPassword = md.digest(password.getText().getBytes(StandardCharsets.UTF_8));

							user = model.getUserDao().createUser(username.getText(), new String(hashedPassword,StandardCharsets.UTF_8),firstName.getText(),lastName.getText());
							if (user != null) {
								status.setText("Created " + user.getUsername());
								status.setTextFill(Color.GREEN);
							} else {
								status.setText("Cannot create user");
								status.setTextFill(Color.RED);
							}
						}else {
							status.setText("Please set a Stronger password(uupercase,lowercase,special,number)");
							status.setTextFill(Color.RED);
						}
						
						
						}else {
							status.setText("Username is already taken");
							status.setTextFill(Color.RED);
						}
					} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			} else {
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}
		});

		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
}
