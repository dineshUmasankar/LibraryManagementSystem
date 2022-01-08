package org.psu.dUmasankar.LMS;

import org.psu.dUmasankar.LMSController.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LMSDriver extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("../LMSView/LoginForm.fxml"));
		LoginController controller = new LoginController();
		
		loader.setController(controller);
		Parent root = loader.load();
		
		Scene loginScene = new Scene(root);
		primaryStage.setTitle("Login into LMS to view books");
		primaryStage.setScene(loginScene);
		primaryStage.show();
		
	}
	
    public static void main(String[] args)
    {
    	launch(args);
    }
}