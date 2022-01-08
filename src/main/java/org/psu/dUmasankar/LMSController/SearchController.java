package org.psu.dUmasankar.LMSController;

import java.io.IOException;
import java.util.List;

import org.psu.dUmasankar.LMS.LMSBookManager;
import org.psu.dUmasankar.LMS.LMSSearch;
import org.psu.dUmasankar.LMS.UserSession;
import org.psu.dUmasankar.LMSModel.BookModel;

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

public class SearchController {
	
	private UserSession currentUser;
	
	public void initUserSession(UserSession currentUser) {
		this.currentUser = currentUser;
	}
	
    @FXML
    private Button returnToHomeButton;
    
    @FXML
    private Button addBookButton;

    @FXML
    private Label currentUserLabel;

    @FXML
    private RadioButton searchAuthorRadioButton;

    @FXML
    private RadioButton searchTitleRadioButton;

    @FXML
    private RadioButton searchCategoryRadioButton;

    @FXML
    private TextField searchboxTextField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<BookModel> booksTableView;

    @FXML
    void returnHome(ActionEvent event) 
    {
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
    String searchType = "Title";
    
    final ObservableList<BookModel> bookList = FXCollections.observableArrayList();
    
    @SuppressWarnings("unchecked")
	@FXML
    public void initialize()
    {
    	currentUserLabel.setText("Signed In As: " + currentUser.getUsername());
		addBookButton.setVisible(false);
		addBookButton.setDisable(true);
    	
    	if (currentUser.getRole().equals("Staff"))
    	{
    		addBookButton.setVisible(true);
    		addBookButton.setDisable(false);
    	}
    	
    	searchAuthorRadioButton.setToggleGroup(searchCategoryRG);
    	searchTitleRadioButton.setToggleGroup(searchCategoryRG);
    	searchCategoryRadioButton.setToggleGroup(searchCategoryRG);
    	searchTitleRadioButton.setSelected(true);
    	
    	searchCategoryRG.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> observable, Toggle old_toggle, Toggle new_toggle) {
				RadioButton watcher = (RadioButton)new_toggle.getToggleGroup().getSelectedToggle();
				
				if (watcher.getText().equals("Search by Author"))
					searchType = "Author";
				else if (watcher.getText().equals("Search by Title"))
					searchType = "Title";
				else
					searchType = "Category";
			}
    	});
    	
    	TableColumn<BookModel, String> idColumn = new TableColumn<>("Book ID");
    	idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
    	idColumn.setMinWidth(50);
    	
    	TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
    	titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    	titleColumn.setMinWidth(255);
    	
    	TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
    	authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
    	authorColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> categoryColumn = new TableColumn<>("Category");
    	categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    	categoryColumn.setMinWidth(100);
    	
    	booksTableView.setPlaceholder(new Label("No books found! Search for another book!"));
    	booksTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, categoryColumn);
    	
    	if (!(currentUser.getRole().equals("Guest")))
    	{
    		addBorrowButtonToTable();
        	addHoldButtonToTable();
    	}
    	
    	booksTableView.setItems(bookList);
    }

    @FXML
    void search(ActionEvent event) 
    {
    	String searchQuery = searchboxTextField.getText();
    	
    	if (searchQuery.isBlank())
    		return;
    	else
    	{
    		bookList.clear();
    		LMSSearch search = new LMSSearch();
    		
    		if (searchType.equals("Author"))
    		{
    			List<BookModel> queryResult = search.searchBookByAuthor(searchQuery);
    			bookList.addAll(queryResult);
    		}
    		else if (searchType.equals("Title"))
    		{
    			List<BookModel> queryResult = search.searchBookByTitle(searchQuery);
    			bookList.addAll(queryResult);
    		}
    		else
    		{
    			List<BookModel> queryResult = search.searchBookByCategory(searchQuery);
    			bookList.addAll(queryResult);
    		}
    	}
    }
    
    @FXML
    void addBook(ActionEvent event)
    {
    	
    }
    
    private void addHoldButtonToTable() {
    	TableColumn<BookModel, Void> holdButtonColumn = new TableColumn<BookModel, Void>("Hold Book");
    	
    	Callback<TableColumn<BookModel, Void>, TableCell<BookModel, Void>> cellFactory = new Callback<TableColumn<BookModel, Void>, TableCell<BookModel, Void>>() {
    		@Override
    		public TableCell<BookModel, Void> call(final TableColumn<BookModel, Void> param) {
    			final TableCell<BookModel, Void> cell = new TableCell<BookModel, Void>() {
    				
    				private final Button btn = new Button("Hold");
    				
    				{
    					btn.setOnAction((ActionEvent event) -> {
    						LMSBookManager bookManager = new LMSBookManager();
    						
    						BookModel clickedBook = getTableView().getItems().get(getIndex());
    						
    						boolean borrowedStatus = bookManager.checkBorrowedStatus(Integer.toString(clickedBook.getId()));
    						
    						bookManager.holdBook(currentUser.getUsername(), Integer.toString(clickedBook.getId()), borrowedStatus);
    						btn.setDisable(true);
    					});
    				}
    				
    				@Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            LMSBookManager bookManager = new LMSBookManager();
                            
                            BookModel rowBook = getTableView().getItems().get(getIndex());
                            
                            if (bookManager.checkHoldStatus(Integer.toString(rowBook.getId())))
                            {
                            	btn.setDisable(true);
                            }
                        }
                    }
    			};
                return cell;	
    		}
    	};
    	
    	holdButtonColumn.setCellFactory(cellFactory);
    	holdButtonColumn.setMinWidth(100);
    	booksTableView.getColumns().add(holdButtonColumn);
    }
    
    private void addBorrowButtonToTable() {
    	TableColumn<BookModel, Void> borrowButtonColumn = new TableColumn<BookModel, Void>("Borrow Book");
    	
    	Callback<TableColumn<BookModel, Void>, TableCell<BookModel, Void>> cellFactory = new Callback<TableColumn<BookModel, Void>, TableCell<BookModel, Void>>() {
    		@Override
    		public TableCell<BookModel, Void> call(final TableColumn<BookModel, Void> param) {
    			final TableCell<BookModel, Void> cell = new TableCell<BookModel, Void>() {
    				
    				private final Button btn = new Button("Borrow");
    				
    				{
    					btn.setOnAction((ActionEvent event) -> {
    						LMSBookManager bookManager = new LMSBookManager();
    						
    						BookModel clickedBook = getTableView().getItems().get(getIndex());
    						
    						boolean holdStatus = bookManager.checkHoldStatus(Integer.toString(clickedBook.getId()));
    						
    						bookManager.borrowBook(currentUser.getUsername(), Integer.toString(clickedBook.getId()), holdStatus);
    						
    						btn.setDisable(true);
    					});
    				}
    				
    				@Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            LMSBookManager bookManager = new LMSBookManager();
                            
                            BookModel rowBook = getTableView().getItems().get(getIndex());
                            
                            if (bookManager.checkBorrowedStatus(Integer.toString(rowBook.getId())))
                            {
                            	btn.setDisable(true);
                            }
                        }
                    }
    			};
                return cell;	
    		}
    	};
    	
    	borrowButtonColumn.setCellFactory(cellFactory);
    	borrowButtonColumn.setMinWidth(100);
    	booksTableView.getColumns().add(borrowButtonColumn);
    }
}