package org.psu.dUmasankar.LMSController;

import java.io.IOException;
import java.sql.Time;
import java.util.Objects;

import org.psu.dUmasankar.LMS.LMSAuth;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisterController {
	@FXML
	private RadioButton memberRadio;

	@FXML
	private RadioButton staffRadio;

	@FXML
	private TextField usernameText;

	@FXML
	private Button workScheduleButton;

	@FXML
	private Label workScheduleLabel;

	@FXML
	private TextField emailText;

	@FXML
	private TextField phoneNumText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private Button registerButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label errorLabel;

	final ToggleGroup accountRadioGroup = new ToggleGroup();
	String role = "Member";
	
	public Time[] weeklyStartSchedule;
	public Time[] weeklyEndSchedule;
	
	@FXML
	public void initialize()
	{
		memberRadio.setToggleGroup(accountRadioGroup);
		staffRadio.setToggleGroup(accountRadioGroup);
		memberRadio.setSelected(true);
		
		workScheduleLabel.setDisable(true);
		workScheduleLabel.setVisible(false);
		
		workScheduleButton.setDisable(true);
		workScheduleButton.setVisible(false);
		
		accountRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> observable, Toggle old_toggle, Toggle new_toggle) {
				RadioButton watcher = (RadioButton)new_toggle.getToggleGroup().getSelectedToggle();
				
				if (watcher.getText().equals("Member Account"))
				{
					role = "Member";
					
					workScheduleLabel.setDisable(true);
					workScheduleLabel.setVisible(false);
					
					workScheduleButton.setDisable(true);
					workScheduleButton.setVisible(false);
				}
				else
				{
					role = "Staff";
					
					workScheduleLabel.setDisable(false);
					workScheduleLabel.setVisible(true);
					
					workScheduleButton.setDisable(false);
					workScheduleButton.setVisible(true);
				}
			}
		});
	}

	@FXML
	void cancelRegister(ActionEvent event) 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("../LMSView/LoginForm.fxml"));
		LoginController controller = new LoginController();
		loader.setController(controller);
		
		Stage currentStage = (Stage) registerButton.getScene().getWindow();
		Stage newStage = new Stage();
		try {
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			newStage.setScene(registerScene);
			newStage.setTitle("Login Page");
			
			currentStage.close();
			newStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void register(ActionEvent event) 
	{
		if (usernameText.getText().isBlank() || passwordText.getText().isBlank() || emailText.getText().isBlank() || phoneNumText.getText().isBlank())
		{
			errorLabel.setText("Please make sure to fill out all the fields shown above.");
		}
		else if (passwordText.getText().length() < 6)
		{
			errorLabel.setText("Make sure your password is at least 7 characters");
		}
		else
		{
			String username = usernameText.getText();
			String password = passwordText.getText();
			String email = emailText.getText();
			String phoneNum = phoneNumText.getText();
			
			LMSAuth auth = new LMSAuth();
			
			if (role.equals("Member"))
			{
				int rowsAffected = auth.createAccount(username, password, email, phoneNum, role);
				if (rowsAffected != 0)
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("../LMSView/LoginForm.fxml"));
					LoginController controller = new LoginController();
					loader.setController(controller);
					
					Stage currentStage = (Stage) registerButton.getScene().getWindow();
					Stage newStage = new Stage();
					try {
						Parent root = loader.load();
						Scene registerScene = new Scene(root);
						
						newStage.setScene(registerScene);
						newStage.setTitle("Login Page");
						
						currentStage.close();
						newStage.show();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					errorLabel.setText("There was an error in creating your account!"
							+ "\nPlease try a different username!");
				}
			}
			else
			{
				int rowsAffected = auth.createAccount(username, password, email, phoneNum, role, weeklyStartSchedule, weeklyEndSchedule);
				if (rowsAffected != 0)
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("../LMSView/LoginForm.fxml"));
					LoginController controller = new LoginController();
					loader.setController(controller);
					
					Stage currentStage = (Stage) registerButton.getScene().getWindow();
					Stage newStage = new Stage();
					try {
						Parent root = loader.load();
						Scene registerScene = new Scene(root);
						
						newStage.setScene(registerScene);
						newStage.setTitle("Login Page");
						
						currentStage.close();
						newStage.show();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					errorLabel.setText("There was an error in creating your account!"
							+ "\nPlease try a different username!");
				}
			}
		}
	}

	@FXML
	void setWorkSchedule(ActionEvent event) 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("../LMSView/EMSWorkScheduleForm.fxml"));
		EMSWorkScheduleController controller = new EMSWorkScheduleController();
		loader.setController(controller);
		
		Stage currentStage = (Stage) workScheduleButton.getScene().getWindow();
		try {
			Stage newStage = new Stage();
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			newStage.setScene(registerScene);
			newStage.setTitle("Set Weekly Work Schedule");
			
			workScheduleButton.setDisable(true);
			memberRadio.setDisable(true);
			staffRadio.setDisable(true);
			cancelButton.setDisable(true);
			registerButton.setDisable(true);
			
			errorLabel.setText("Close Work Schedule Setter First!");
			newStage.show();
			
			currentStage.setOnCloseRequest(evt -> {
				evt.consume();
			});
			
			newStage.setOnCloseRequest(evt -> {
				newStage.getScene().getWindow().getOnCloseRequest();
				
				weeklyStartSchedule = controller.getWeeklyStartSchedule();
				weeklyEndSchedule = controller.getWeeklyEndSchedule();
				
				try
				{
					boolean emptyStartSchedule = true;
					boolean emptyEndSchedule = true;
					
					for (int day = 0; day < 7; day++)
					{
						System.out.println("Start: " + weeklyStartSchedule[day]);
						System.out.println("End: " + weeklyEndSchedule[day]);
						if (!(Objects.isNull(weeklyStartSchedule[day])))
						{
							emptyStartSchedule = false;
							emptyEndSchedule = false;
						}
					}
					
					if (emptyStartSchedule || emptyEndSchedule)
					{
						workScheduleButton.setDisable(false);
						workScheduleButton.setText("Set Work Schedule");
					}
					else
					{
						workScheduleButton.setText("Schedule Set");
					}
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				
				workScheduleButton.setDisable(false);
				memberRadio.setDisable(false);
				staffRadio.setDisable(false);
				cancelButton.setDisable(false);
				registerButton.setDisable(false);
				errorLabel.setText("");
				currentStage.setOnCloseRequest(currentStageEvt -> {
					currentStageEvt.consume();
					
					currentStage.close();
				});
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
