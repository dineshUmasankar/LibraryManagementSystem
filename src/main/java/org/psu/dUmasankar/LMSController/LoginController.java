package org.psu.dUmasankar.LMSController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.psu.dUmasankar.LMS.LMSAuth;
import org.psu.dUmasankar.LMS.UserSession;


public class LoginController 
{

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button guestSignInButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    void guestSignIn(ActionEvent event) 
    {
    	UserSession guestUser = new UserSession();
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/HomeForm.fxml"));
    	HomeController controller = new HomeController();
    	controller.initUserSession(guestUser);
    	loader.setController(controller);
    	
    	Stage currentStage = (Stage) guestSignInButton.getScene().getWindow();
    	Stage newStage = new Stage();
    	try {
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			newStage.setScene(registerScene);
			newStage.setTitle("Home Page");
			
			currentStage.close();
			newStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void login(ActionEvent event) 
    {
    	
    	String usernameText = usernameTextField.getText();
    	String passwordText = passwordTextField.getText();
    	
    	// Basic TextField Validation
    	if (usernameText.isEmpty() || passwordText.isEmpty())
    	{
    		errorLabel.setText("Please make sure username/password fields are filled out.");
    		usernameTextField.setText("");
    		passwordTextField.setText("");
    	}
    	else
    	{
    		LMSAuth auth = new LMSAuth();
    		
    		// On Valid Login -> HomeForm
    		if(auth.login(usernameText, passwordText))
    		{
    			UserSession currentUser = new UserSession(usernameText, auth.getRole(usernameText));
    			
            	FXMLLoader loader = new FXMLLoader();
            	loader.setLocation(this.getClass().getResource("../LMSView/HomeForm.fxml"));
            	HomeController controller = new HomeController();
            	controller.initUserSession(currentUser);
            	loader.setController(controller);
            	
            	Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            	Stage secondStage = new Stage();
            	try {
        			Parent root = loader.load();
        			Scene registerScene = new Scene(root);
        			
        			secondStage.setScene(registerScene);
        			secondStage.setTitle("Home Page");
        			
        			primaryStage.close();
        			secondStage.show();
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		} 
    		else
    		{
    			errorLabel.setText("Login Failed! Please try again!");
        		usernameTextField.setText("");
        		passwordTextField.setText("");
    		}
    	}
    }

    @FXML
    void register(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/RegisterForm.fxml"));
    	RegisterController controller = new RegisterController();
    	loader.setController(controller);
    	
    	Stage primaryStage = (Stage) registerButton.getScene().getWindow();
    	Stage secondStage = new Stage();
    	try {
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			secondStage.setScene(registerScene);
			secondStage.setTitle("Register for a library account");
			
			primaryStage.close();
			secondStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
