package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;



import javafx.scene.control.TextField;


import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;


public class MainController {
	
    public static class column {
   	 
        private final String name;
        private final String quantity;
        private final String company;
        private final Hyperlink date;
        private final Hyperlink delete;
        
         column(String name, String quantity, String company,Hyperlink date,Hyperlink delete) {
            this.name = new String(name);
            this.quantity = new String(quantity);
            this.company = new String(company);
            this.date = date;
            this.delete = delete;
    }

		public String getName() {
			return name;
		}

		public String getQuantity() {
			return quantity;
		}

		public String getCompany() {
			return company;
		}

		public Hyperlink getDate() {
			return date;
		}

		public Hyperlink getDelete() {
			return delete;
		}


    }

    public static class msrofColumn {

		private final String name;
        private final String quantity;
        private final String dep;
        private final Date date;
        
        msrofColumn(String name, String quantity, String dep,Date date) {
            this.name = new String(name);
            this.quantity = new String(quantity);
            this.dep = new String(dep);
            this.date = date;
         }
        public String getName() {
			return name;
		}

		public String getQuantity() {
			return quantity;
		}

		public String getDep() {
			return dep;
		}

		public Date getDate() {
			return date;
		}
    }
    
    public static ObservableList<column> data =FXCollections.observableArrayList( );
    public static ObservableList<msrofColumn> msrofData =FXCollections.observableArrayList( );
    public static ObservableList<msrofColumn> searchResult =FXCollections.observableArrayList( );

    
    database DB = new database();
	
	@FXML
	private TableView<column> dataTable = new TableView<column>();
	
	@FXML
	private TableView<msrofColumn> msrofTable = new TableView<msrofColumn>();
	

	@FXML
	private TableColumn<column, String> col1 = new TableColumn<>("name");
	@FXML
	private TableColumn<column, String> col2 = new TableColumn<>("quantity");
	@FXML
	private TableColumn<column, String> col3 = new TableColumn<>("company");
	@FXML
	private TableColumn<column, Hyperlink> col4 = new TableColumn<>("date");
	@FXML
	private TableColumn<column, Hyperlink> col5 = new TableColumn<>("delete");
	
	@FXML
	private TableColumn<column, String> msrofName = new TableColumn<>("name");
	@FXML
	private TableColumn<column, String> msrofQuantity = new TableColumn<>("quantity");
	@FXML
	private TableColumn<column, String> msrofDep = new TableColumn<>("dep");
	@FXML
	private TableColumn<column, String> msrofDate = new TableColumn<>("date");
	
	
	@FXML
	private TextArea addItemTextArea;
	
	@FXML
	private TextArea addItemCompanyTextArea;
	
	@FXML
	private TextField usernameTextArea;
	
	@FXML
	private TextField searchBox;
	
	@FXML
	private TextField searchItems;
	
	@FXML
	private TextField sarfDepTextField;
	
	@FXML
	private DatePicker sarfDateField;
	
	@FXML
	private DatePicker	addtoItemDatePicker;
	
	Alert SarfAlert = new Alert(Alert.AlertType.CONFIRMATION);
	Alert addItemAlert = new Alert(Alert.AlertType.CONFIRMATION);
	Alert AddToItemlert = new Alert(Alert.AlertType.CONFIRMATION);
	ButtonType okButton = new ButtonType("نعم", ButtonBar.ButtonData.YES);
	ButtonType noButton = new ButtonType("لا", ButtonBar.ButtonData.NO);
	
	
	@FXML
	private PasswordField passwordTextArea;
	
	@FXML
	private AnchorPane loginPane;
	
	@FXML
	private Pane sarfPane;
	
	@FXML
	private Pane addItemPane;
	
	@FXML
	private Pane addToItemPane;
	
	@FXML
	private Pane showItemsPane;
	
	@FXML
	private Pane msrofPane;
	
	@FXML
	private ImageView logo;
	
	@FXML
	private ChoiceBox<String> sarfChoiceBox = new ChoiceBox<String>();
	
	@FXML
	private ChoiceBox<String> addItemTypeChoiceBox = new ChoiceBox<String>();
	
	@FXML
	private ChoiceBox<String> addToItemChoiceBox = new ChoiceBox<String>();
	
	@FXML
	private Spinner<Integer> sarfSpinner = new Spinner<Integer>();
	
	@FXML
	private Spinner<Integer> addToItemSpinner = new Spinner<Integer>();
	
	@FXML
	private Spinner<Integer> addItemSpinner = new Spinner<Integer>();
	
	@FXML
	private Label wrongDataMessage ;
	
	@FXML
	private Button allItemsBtn;
	
	@FXML
	private Button motghirBtn;
	
	@FXML
	private Button sabtBtn;
	
	@FXML
	private Button allItemsBtn1;
	
	@FXML
	private Button motghirBtn1;
	
	@FXML
	private Button sabtBtn1;

    
	private int initialValue = 1;

	// Value factory.
    SpinnerValueFactory<Integer> valueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, initialValue);
    

    
    
	
	
	public void initialize() {
		

		

		
		DB.connectToDB();
		DB.getItems("all");
		
		Image image;
		try {
			image = new Image(new FileInputStream("./resources/icon.png"));
			logo.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sarfSpinner.setValueFactory(valueFactory);
		addItemSpinner.setValueFactory(valueFactory);
		addToItemSpinner.setValueFactory(valueFactory);
		addItemTypeChoiceBox.getItems().addAll("ثابت","متغير");
//		System.out.println(addItemSpinner.getValue());
		
		
		col1.setCellValueFactory(new PropertyValueFactory<column, String>("name"));
		col2.setCellValueFactory(new PropertyValueFactory<column, String>("quantity"));
		col3.setCellValueFactory(new PropertyValueFactory<column, String>("company"));
		col4.setCellValueFactory(new PropertyValueFactory<column, Hyperlink >("date"));
		col5.setCellValueFactory(new PropertyValueFactory<column, Hyperlink >("delete"));
		
		dataTable.setItems(data);
		
		msrofName.setCellValueFactory(new PropertyValueFactory<column, String>("name"));
		msrofQuantity.setCellValueFactory(new PropertyValueFactory<column, String>("quantity"));
		msrofDep.setCellValueFactory(new PropertyValueFactory<column, String>("dep"));
		msrofDate.setCellValueFactory(new PropertyValueFactory<column, String>("date"));
		
		msrofTable.setItems(msrofData);


	}
	
	
	public void showSarfPane(ActionEvent event) {
		sarfPane.toFront();
		sarfChoiceBox.getItems().clear();
		sarfChoiceBox.getItems().addAll(DB.getitemsNames());
		sarfSpinner.getValueFactory().setValue(1);

	}
	
	public void showAddItemPane(ActionEvent event) {
		addItemPane.toFront();	
		addItemSpinner.getValueFactory().setValue(1);
	}
	
	public void showMsrofPane(ActionEvent event) {
		msrofTable.setItems(msrofData);
		allItemsBtn1.setStyle("-fx-background-color: rgb(72, 202, 228);");
		sabtBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		msrofPane.toFront();	
		DB.getSarfElements("all");
	}
	
	public void showMsrofall(ActionEvent event) {
	
		
		allItemsBtn1.setStyle("-fx-background-color: rgb(72, 202, 228);");
		sabtBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		DB.getSarfElements("all");
	}
	
	public void showMsrofSabt(ActionEvent event) {
	
		
		sabtBtn1.setStyle("-fx-background-color: rgb(72, 202, 228);");
		allItemsBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		DB.getSarfElements("sabt");
	}
	
	public void showMsrofMotghir(ActionEvent event) {
	
		
		motghirBtn1.setStyle("-fx-background-color: rgb(72, 202, 228);");
		allItemsBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		sabtBtn1.setStyle("-fx-background-color: rgb(246, 241, 209);");
		DB.getSarfElements("Motghir");
	}
	
	public void showAddToItemPane(ActionEvent event) {
		addToItemPane.toFront();
		addToItemChoiceBox.getItems().clear();
		addToItemChoiceBox.getItems().addAll(DB.getitemsNames());
		addToItemSpinner.getValueFactory().setValue(1);
	}
	
	public void showShowItemsPane(ActionEvent event) {
		dataTable.setItems(data);
		
		allItemsBtn.setStyle("-fx-background-color: rgb(72, 202, 228);");
		sabtBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		showItemsPane.toFront();

		DB.getItems("all");

	}
	
	public void showAllItems() {
		
		
		allItemsBtn.setStyle("-fx-background-color: rgb(72, 202, 228);");
		sabtBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");

		
		DB.getItems("all");


	}
	
	public void showSabtItems() {
		
		sabtBtn.setStyle("-fx-background-color: rgb(72, 202, 228);");
		allItemsBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		motghirBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		
		DB.getItems("sabt");


	}
	
	public void showMotghirItems() {
		
		motghirBtn.setStyle("-fx-background-color: rgb(72, 202, 228);");
		allItemsBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		sabtBtn.setStyle("-fx-background-color: rgb(246, 241, 209);");
		
		DB.getItems("Motghir");


	}
	
	public void addItem(ActionEvent event) {
		
		if (addItemTextArea.getText().equals("") || addItemTextArea.getText().equals(null)
				|| addItemCompanyTextArea.getText().equals("") || addItemCompanyTextArea.getText().equals(null) 
				||addItemTypeChoiceBox.getValue()== null ) {
			new Alert(Alert.AlertType.ERROR, "برجاء ادخال البيانات كاملة").showAndWait();
		} else {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    	ButtonType okButton = new ButtonType("نعم", ButtonBar.ButtonData.YES);
    	ButtonType noButton = new ButtonType("لا", ButtonBar.ButtonData.NO);
        alert.setTitle("اضافة صنف ");
		alert.setContentText("هل أنت متأكد من اضافة عدد ("+addItemSpinner.getValue()+") من "+addItemTextArea.getText()+" ؟");  
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
		        if (type == okButton) {
		        	
		
		DB.insertItem(addItemTextArea.getText(), addItemSpinner.getValue(), addItemCompanyTextArea.getText(),addItemTypeChoiceBox.getValue());
		
		        }});
		
		}
	}
	
	public void sarf(ActionEvent event) {

		
		if (sarfChoiceBox.getValue()== null || sarfDateField.getValue()== null
				|| sarfDepTextField.getText().equals("") || sarfDepTextField.getText().equals(null)) {
			new Alert(Alert.AlertType.ERROR, "برجاء ادخال البيانات كاملة").showAndWait();
		} else {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    	ButtonType okButton = new ButtonType("نعم", ButtonBar.ButtonData.YES);
    	ButtonType noButton = new ButtonType("لا", ButtonBar.ButtonData.NO);
        alert.setTitle("صرف ");
		alert.setContentText("هل أنت متأكد من صرف عدد ("+sarfSpinner.getValue()+") من "+sarfChoiceBox.getValue()+" ؟");  
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
		        if (type == okButton) {
		
    				int newQuantity = DB.getQuantity(sarfChoiceBox.getValue())-sarfSpinner.getValue();
//    				
    				if(newQuantity<0)
    					{
    					newQuantity = 0;
    					
    					new Alert(Alert.AlertType.WARNING, "لا يوجد ما يكفي للصرف").showAndWait();
    					}
    				else {
    		    		DB.insertToSarf(sarfChoiceBox.getValue(), sarfSpinner.getValue(), sarfDepTextField.getText(), sarfDateField.getValue());
    		        	
    		        	new Alert(Alert.AlertType.CONFIRMATION, "تم الصرف").showAndWait();
    				}
		
    				DB.updateItemQuantity(sarfChoiceBox.getValue(), newQuantity);

		        }});
		
		
		}

		

	}
	
	public void addToItem(ActionEvent event) {
		
		if (addToItemChoiceBox.getValue()== null || addtoItemDatePicker.getValue()== null) {
			new Alert(Alert.AlertType.ERROR, "برجاء ادخال البيانات كاملة").showAndWait();
		} else {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    	ButtonType okButton = new ButtonType("نعم", ButtonBar.ButtonData.YES);
    	ButtonType noButton = new ButtonType("لا", ButtonBar.ButtonData.NO);
        alert.setTitle("اضافة الى صنف ");
		alert.setContentText("هل أنت متأكد من اضافة عدد ("+addToItemSpinner.getValue()+") الى "+addToItemChoiceBox.getValue()+" ؟");  
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
		        if (type == okButton) {
		
					DB.addtoItem(addToItemChoiceBox.getValue(), addToItemSpinner.getValue(), addtoItemDatePicker.getValue());

    		        	new Alert(Alert.AlertType.CONFIRMATION, "تم الاضافة").showAndWait();
    				
		

		        }});
		
	
		}

	}
	
	
	public void msrofSearch() {

		FilteredList<msrofColumn> result = msrofData.filtered(item -> item.name.startsWith(searchBox.getText()) || item.dep.contains(searchBox.getText()));

		 msrofTable.setItems(result);
	}
	

	public void itemsSearch() {

		FilteredList<column> result = data.filtered(item -> item.name.startsWith(searchItems.getText()));
		 dataTable.setItems(result);
	}
	
	
	public void login(ActionEvent event) {
//		System.out.println(usernameTextArea.getText());
		if (usernameTextArea.getText().equals("Store") && passwordTextArea.getText().equals("162021") ) {
			loginPane.setVisible(false);
			
			
		} else {
			wrongDataMessage.setOpacity(100);
		}
		
	}

	
	public void exitApp(ActionEvent event) {
		try {
			DB.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
		
	}




}
