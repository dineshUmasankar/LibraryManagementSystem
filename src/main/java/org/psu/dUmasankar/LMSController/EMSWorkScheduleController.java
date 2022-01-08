package org.psu.dUmasankar.LMSController;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EMSWorkScheduleController {
	
    @FXML
    private GridPane gridPane;

    @FXML
    private CheckBox sunCheckbox;

    @FXML
    private TextField sunStartText;

    @FXML
    private ChoiceBox<String> sunStartAMPM;

    @FXML
    private TextField sunEndText;

    @FXML
    private ChoiceBox<String> sunEndAMPM;

    @FXML
    private CheckBox monCheckbox;

    @FXML
    private TextField monStartText;

    @FXML
    private ChoiceBox<String> monStartAMPM;

    @FXML
    private TextField monEndText;

    @FXML
    private ChoiceBox<String> monEndAMPM;

    @FXML
    private CheckBox tueCheckbox;

    @FXML
    private TextField tueStartText;

    @FXML
    private ChoiceBox<String> tueStartAMPM;

    @FXML
    private TextField tueEndText;

    @FXML
    private ChoiceBox<String> tueEndAMPM;

    @FXML
    private CheckBox wedCheckbox;

    @FXML
    private TextField wedStartText;

    @FXML
    private ChoiceBox<String> wedStartAMPM;

    @FXML
    private TextField wedEndText;

    @FXML
    private ChoiceBox<String> wedEndAMPM;

    @FXML
    private CheckBox thuCheckbox;

    @FXML
    private TextField thuStartText;

    @FXML
    private ChoiceBox<String> thuStartAMPM;

    @FXML
    private TextField thuEndText;

    @FXML
    private ChoiceBox<String> thuEndAMPM;

    @FXML
    private CheckBox friCheckbox;

    @FXML
    private TextField friStartText;

    @FXML
    private ChoiceBox<String> friStartAMPM;

    @FXML
    private TextField friEndText;

    @FXML
    private ChoiceBox<String> friEndAMPM;

    @FXML
    private CheckBox satCheckbox;

    @FXML
    private TextField satStartText;

    @FXML
    private ChoiceBox<String> satStartAMPM;

    @FXML
    private TextField satEndText;

    @FXML
    private ChoiceBox<String> satEndAMPM;

    @FXML
    private Label errorLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;
    
	public Time[] weeklyStartSchedule = null;
	public Time[] weeklyEndSchedule = null;
	
	public Time[] getWeeklyStartSchedule()
	{
		return weeklyStartSchedule;
	}
	
	public Time[] getWeeklyEndSchedule() 
	{
		return weeklyEndSchedule;
	}
	
    @FXML
    void back(ActionEvent event) {
    	Stage stage = (Stage) backButton.getScene().getWindow();
    	stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    	stage.close();
    }

    @SuppressWarnings("rawtypes")
	@FXML
    void submitSchedule(ActionEvent event) 
    {
    	weeklyStartSchedule = new Time[7];
    	weeklyEndSchedule = new Time[7];
    	
    	for (int rowIndex = 1; rowIndex < gridPane.getRowCount(); rowIndex++)
    	{
        	String startTimeStr = "";
        	String endTimeStr = "";
        	
    		TextField startTimeTextField = (TextField) getNodeByCoordinate(rowIndex, 1);
    		ChoiceBox startTimeChoiceBox = (ChoiceBox) getNodeByCoordinate(rowIndex, 2);
    		
    		TextField endTimeTextField = (TextField) getNodeByCoordinate(rowIndex, 3);
    		ChoiceBox endTimeChoiceBox = (ChoiceBox) getNodeByCoordinate(rowIndex, 4);
    		
    		if ((startTimeTextField.getText().isBlank() == false) && (startTimeTextField.isDisabled() == false))
    		{
    			startTimeStr += startTimeTextField.getText() + startTimeChoiceBox.getSelectionModel().getSelectedItem().toString();
    		}
    		
    		if ((endTimeTextField.getText().isBlank() == false) && (endTimeTextField.isDisabled() == false))
    		{
    			endTimeStr += endTimeTextField.getText() + endTimeChoiceBox.getSelectionModel().getSelectedItem().toString();
    		}
    		
    		if (startTimeStr.isBlank() || endTimeStr.isBlank())
    		{
    			startTimeStr = "";
    			endTimeStr = "";
    		}
    		else
    		{
    			try
    			{
    				LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("h:mma"));
    				LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("h:mma"));
    				
    				weeklyStartSchedule[rowIndex-1] = Time.valueOf(startTime);
    				weeklyEndSchedule[rowIndex-1] = Time.valueOf(endTime);
    			} catch (DateTimeParseException e)
    			{
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	Stage stage = (Stage) submitButton.getScene().getWindow();
    	stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    	stage.close();
    	    	
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
    public void initialize()
    {
    	for (final Node child : gridPane.getChildren())
    	{
    	    if (child instanceof ChoiceBox)
    		{
    			((ChoiceBox) child).getItems().add("AM");
    			((ChoiceBox) child).getItems().add("PM");
    			
    			if (GridPane.getColumnIndex(child) == 2)
    			{
    				((ChoiceBox<String>) child).getSelectionModel().selectFirst();
    			}
    			else if(GridPane.getColumnIndex(child) == 4)
    			{
    				((ChoiceBox<String>) child).getSelectionModel().selectLast();
    			}
    			
    			child.setDisable(true);
    		}
    		else if (child instanceof TextField)
    		{
    			((TextField) child).setPromptText("00:00");
    			child.setDisable(true);
    		}
    		else if (child instanceof CheckBox)
    		{
    			((CheckBox) child).selectedProperty().addListener(new ChangeListener<Boolean>() {
    				public void changed(ObservableValue <? extends Boolean> ov, Boolean old_val, Boolean new_val) {
    					int row = GridPane.getRowIndex(child);
    					try
    					{
    						for(int colIndex = 1; colIndex < gridPane.getColumnCount(); colIndex++)
    						{
    							if(getNodeByCoordinate(row, colIndex) instanceof TextField)
    							{
    								((TextField) getNodeByCoordinate(row, colIndex)).setText("");
    							}
    							else if(getNodeByCoordinate(row, colIndex) instanceof ChoiceBox)
    							{
    								if (colIndex == 2)
    								{
    									((ChoiceBox) getNodeByCoordinate(row, colIndex)).getSelectionModel().select(0);
    								}
    								else if (colIndex == 4)
    								{
    									((ChoiceBox) getNodeByCoordinate(row, colIndex)).getSelectionModel().select(1);
    								}
    							}
    							
    							if (getNodeByCoordinate(row, colIndex).isDisabled())
    								getNodeByCoordinate(row, colIndex).setDisable(false);
    							else
    								getNodeByCoordinate(row, colIndex).setDisable(true);
    						}
    					} catch (Exception e)
    					{
    						e.printStackTrace();
    					}
    				}
    			});
    		}
    	}
    	

    }
    
    public Node getNodeByCoordinate(Integer row, Integer column) {
        for (Node node : gridPane.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return node;
            }
        }
        return null;
    }
}
