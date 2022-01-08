package org.psu.dUmasankar.LMSController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.psu.dUmasankar.LMS.LMSAuth;
import org.psu.dUmasankar.LMS.LMSBookManager;
import org.psu.dUmasankar.LMS.UserSession;
import org.psu.dUmasankar.LMSModel.BookModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HomeController {
	
	private UserSession currentUser;
	
	public void initUserSession(UserSession currentUser) {
		this.currentUser = currentUser;
	}
	
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button signOutButton;

    @FXML
    private Tab booksHeldTab;

    @FXML
    private TableView<BookModel> booksHeldTableView;

    @FXML
    private Tab booksBorrowedTab;

    @FXML
    private TableView<BookModel> booksBorrowedTableView;

    @FXML
    private Tab balanceTab;

    @FXML
    private TableView<?> balanceTableView;

    @FXML
    private Label membershipLabel;

    @FXML
    private Button searchBooksButton;

    @FXML
    private Button extendMembershipButton;

    @FXML
    private Button openEMSButton;

    @FXML
    void extendMembership(ActionEvent event) {

    }

    @FXML
    void openEMS(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/EMSForm.fxml"));
    	EMSController controller = new EMSController();
    	controller.initUserSession(currentUser);
    	loader.setController(controller);
    	
    	Stage primaryStage = (Stage) openEMSButton.getScene().getWindow();
    	Stage secondStage = new Stage();
    	try {
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			secondStage.setScene(registerScene);
			secondStage.setTitle("Employee Search");
			
			primaryStage.close();
			secondStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void searchBooks(ActionEvent event) 
    {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/SearchForm.fxml"));
    	SearchController controller = new SearchController();
    	controller.initUserSession(currentUser);
    	loader.setController(controller);
    	
    	Stage primaryStage = (Stage) searchBooksButton.getScene().getWindow();
    	Stage secondStage = new Stage();
    	try {
			Parent root = loader.load();
			Scene registerScene = new Scene(root);
			
			secondStage.setScene(registerScene);
			secondStage.setTitle("Book Search");
			
			primaryStage.close();
			secondStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void signOut(ActionEvent event) {
    	currentUser.cleanUserSession();
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("../LMSView/LoginForm.fxml"));
    	LoginController controller = new LoginController();
    	loader.setController(controller);
    	
    	Stage currentStage = (Stage) signOutButton.getScene().getWindow();
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
    
    final ObservableList<BookModel> bookBorrowedList = FXCollections.observableArrayList();
    final ObservableList<BookModel> bookHoldList = FXCollections.observableArrayList();
    
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize()
	{
		welcomeLabel.setText("Welcome " + currentUser.getUsername());
		openEMSButton.setDisable(true);
		openEMSButton.setVisible(false);
		
		if (currentUser.getRole().equals("Staff"))
		{
			openEMSButton.setDisable(false);
			openEMSButton.setVisible(true);
		}
		
		if (currentUser.getRole().equals("Guest"))
		{
			booksHeldTab.setDisable(true);
			booksBorrowedTab.setDisable(true);
			balanceTab.setDisable(true);
			
			booksHeldTableView.setDisable(true);
			booksBorrowedTableView.setDisable(true);
			balanceTableView.setDisable(true);
			
			extendMembershipButton.setDisable(true);
		}
		
    	TableColumn<BookModel, String> idBorrowColumn = new TableColumn<>("Book ID");
    	idBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
    	idBorrowColumn.setMinWidth(50);
    	
    	TableColumn<BookModel, String> titleBorrowColumn = new TableColumn<>("Title");
    	titleBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    	titleBorrowColumn.setMinWidth(204);
    	
    	TableColumn<BookModel, String> authorBorrowColumn = new TableColumn<>("Author");
    	authorBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
    	authorBorrowColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> categoryBorrowColumn = new TableColumn<>("Category");
    	categoryBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    	categoryBorrowColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> returnBorrowColumn = new TableColumn<>("Return By");
    	returnBorrowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowUntil().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))));
    	returnBorrowColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> idHoldColumn = new TableColumn<>("Book ID");
    	idHoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
    	idHoldColumn.setMinWidth(50);
    	
    	TableColumn<BookModel, String> titleHoldColumn = new TableColumn<>("Title");
    	titleHoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    	titleHoldColumn.setMinWidth(204);
    	
    	TableColumn<BookModel, String> authorHoldColumn = new TableColumn<>("Author");
    	authorHoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
    	authorHoldColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> categoryHoldColumn = new TableColumn<>("Category");
    	categoryHoldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    	categoryHoldColumn.setMinWidth(100);
    	
    	TableColumn<BookModel, String> holdColumn = new TableColumn<>("Pick Up By");
    	holdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoldUntil().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))));
    	holdColumn.setMinWidth(100);
    	
    	booksBorrowedTableView.setPlaceholder(new Label("No books borrowed!"));
    	booksHeldTableView.setPlaceholder(new Label("No books held!"));
    	
    	booksBorrowedTableView.getColumns().addAll(idBorrowColumn, titleBorrowColumn, authorBorrowColumn, categoryBorrowColumn, returnBorrowColumn);
    	booksHeldTableView.getColumns().addAll(idHoldColumn, titleHoldColumn, authorHoldColumn, categoryHoldColumn, holdColumn);
    	
    	if (currentUser.getRole() != "Guest")
    	{
    		LMSBookManager bookManager = new LMSBookManager();
    		
    		bookHoldList.addAll(bookManager.getHeldBooks(currentUser.getUsername()));
    		bookBorrowedList.addAll(bookManager.getBorrowedBooks(currentUser.getUsername()));
    		
    		booksHeldTableView.setItems(bookHoldList);
    		booksBorrowedTableView.setItems(bookBorrowedList);
    	}
	}
}
