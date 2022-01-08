package org.psu.dUmasankar.LMSController;

import java.io.IOException;
import java.util.List;

import org.psu.dUmasankar.LMS.LMSSearch;
import org.psu.dUmasankar.LMS.UserSession;
import org.psu.dUmasankar.LMSModel.StaffModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EMSController {
	
	private UserSession currentUser;
	
	public void initUserSession(UserSession currentUser) {
		this.currentUser = currentUser;
	}
	
    @FXML
    private Button returnToHomeButton;

    @FXML
    private Label currentUserLabel;

    @FXML
    private RadioButton searchEmployeeIDRadioButton;

    @FXML
    private RadioButton searchEmployeeNameRadioButton;

    @FXML
    private TextField searchboxTextField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<StaffModel> employeeTableView;

    @FXML
    void returnHome(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/HomeForm.fxml"));
    	HomeController controller = new HomeController();
    	controller.initUserSession(currentUser);
    	loader.setController(controller);
    	
    	Stage currentStage = (Stage) returnToHomeButton.getScene().getWindow();
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


    
    final ToggleGroup searchCategoryRG = new ToggleGroup();
    String searchType = "ID";
    
    final ObservableList<StaffModel> staffList = FXCollections.observableArrayList();
    
    @FXML
    void search(ActionEvent event) 
    {
    	String searchQuery = searchboxTextField.getText();
    	
    	if (searchQuery.isBlank())
    		return;
    	else
    	{
        	staffList.clear();
        	LMSSearch search = new LMSSearch();
        	if (searchType.equals("ID"))
        	{
        		try
        		{
        			List<StaffModel> queryResult = search.searchEmployeeByID(searchQuery);
            		staffList.addAll(queryResult);
        		} catch (Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        	else
        	{
        		List<StaffModel> queryResult = search.searchEmployeeByUserName(searchQuery);
        		staffList.addAll(queryResult);
        	}
    	}
    }
    
    @SuppressWarnings("unchecked")
	@FXML
    public void initialize()
    {
    	currentUserLabel.setText("Signed In As: " + currentUser.getUsername());
    	
    	searchEmployeeIDRadioButton.setToggleGroup(searchCategoryRG);
    	searchEmployeeNameRadioButton.setToggleGroup(searchCategoryRG);
    	
    	searchEmployeeIDRadioButton.setSelected(true);
    	
    	searchCategoryRG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
    		public void changed(ObservableValue<? extends Toggle> observable, Toggle old_toggle, Toggle new_toggle) {
    			RadioButton watcher = (RadioButton)new_toggle.getToggleGroup().getSelectedToggle();
    			
    			if (watcher.getText().equals("Search by Employee ID"))
    				searchType = "ID";
    			else
    				searchType = "Name";
    		}
    	});
    	
    	TableColumn<StaffModel, String> idColumn = new TableColumn<>("Staff ID");
    	idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getAccount_ID())));
    	idColumn.setMinWidth(50);
    	
    	TableColumn<StaffModel, String> usernameColumn = new TableColumn<>("Staff Username");
    	usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
    	usernameColumn.setMinWidth(100);
    	
    	employeeTableView.setPlaceholder(new Label("No Employees found!"));
    	employeeTableView.getColumns().addAll(idColumn, usernameColumn);
    	addViewScheduleButtonToTable();
    	employeeTableView.setItems(staffList);
    }
    
    private void addViewScheduleButtonToTable() {
    	TableColumn<StaffModel, Void> viewScheduleButtonColumn = new TableColumn<StaffModel, Void>("Schedules");
    	
    	Callback<TableColumn<StaffModel, Void>, TableCell<StaffModel, Void>> cellFactory = new Callback<TableColumn<StaffModel, Void>, TableCell<StaffModel, Void>>() {
    		@Override
    		public TableCell<StaffModel, Void> call(final TableColumn<StaffModel, Void> param) {
    			final TableCell<StaffModel, Void> cell = new TableCell<StaffModel, Void>() {
    				
    				private final Button btn = new Button("View");
    				
    				{
    					btn.setOnAction((ActionEvent event) -> {
    						
    					});
    				}
    				
    				@Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
    			};
                return cell;	
    		}
    	};
    	
    	viewScheduleButtonColumn.setCellFactory(cellFactory);
    	viewScheduleButtonColumn.setMinWidth(100);
    	employeeTableView.getColumns().add(viewScheduleButtonColumn);
    }
}
