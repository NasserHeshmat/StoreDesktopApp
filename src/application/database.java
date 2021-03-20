package application;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.h2.jdbc.JdbcSQLNonTransientConnectionException;

import application.MainController.msrofColumn;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class database {
	
	   // JDBC driver name and database URL 
	   static final String JDBC_DRIVER = "org.h2.Driver";
	   Connection conn = null;
	
	public void connectToDB(){

		  try { 
		         // STEP 1: Register JDBC driver 
		         Class.forName(JDBC_DRIVER); 
		             
		         //STEP 2: Open a connection 
		         System.out.println("Connecting to database..."); 
		         try {
					conn = DriverManager.getConnection("jdbc:h2:./resources/Store","sa","");
				} catch (JdbcSQLNonTransientConnectionException e) {
					new Alert(Alert.AlertType.ERROR, "التطبيق يعمل بالفعل").showAndWait();
					System.exit(0);
				}  

		         
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } //end try
		
		
	}
	
	public void insertItem(String name,int quantity,String company,String type){
		
		Statement stmt = null; 
		try { 
        stmt = conn.createStatement(); 
        String sql =  "INSERT INTO ITEMS VALUES ( '"+ name+"',"+ quantity+",'"+ company+"','"+type+"');";  
        
         stmt.executeUpdate(sql);
        stmt.close();
		
        new Alert(Alert.AlertType.CONFIRMATION, "تم الاضافة").showAndWait();
        
		}catch (JdbcSQLIntegrityConstraintViolationException e) {
			new Alert(Alert.AlertType.ERROR, "هذا الصنف موجود بالفعل.. لا يمكن الاضافة بنفس الاسم").showAndWait();
		
		} catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
	
	}
	
public void insertToSarf(String name,int quantity,String dep,LocalDate Date){
		
		Statement stmt = null; 
		try { 
        stmt = conn.createStatement(); 
        String sql =  "INSERT INTO sarf(sarfname,quantity,department,date) VALUES ( '"+ name+"',"+ quantity+",'"+ dep+"','"+ Date+"');";  
        
        
         stmt.executeUpdate(sql);
        stmt.close();
		
		
		} catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
	
	}

	public void getItems(String type) {
		
		Statement stmt = null; 
		String sql="";
		  try { 
			  
			  stmt = conn.createStatement(); 
			  if (type.equals("all")) {
		        sql =  "SELECT * FROM items;";  
			  }
			  else if (type.equals("sabt")) {
				  sql =  "select* from items where type='ثابت';";
			  }
			  else if (type.equals("Motghir")){
				  sql =  "select* from items where type='متغير';";
			}
		        
		        ResultSet rs = stmt.executeQuery(sql);
		        MainController.data.clear();
		        while (rs.next()) {
		            
		            String name = rs.getString("NAME"); // Assuming there is a column called name.
		            String quantity = rs.getString("quantity");
		            String company = rs.getString("company");
		            
//		            System.out.print(name+" ");
//		            System.out.print(quantity+" ");
//		            System.out.println(company);
		            
		    		Hyperlink link = new Hyperlink("عرض التواريخ");
		    		link.setOnAction(new EventHandler<ActionEvent>() {
		    		    @Override
		    		    public void handle(ActionEvent e) {
		    		        getAdditionDates(name);
		    		    }
		    		});
		    		
		    		Hyperlink delete = new Hyperlink("حذف");
		    		delete.setOnAction(new EventHandler<ActionEvent>() {
		    		    @Override
		    		    public void handle(ActionEvent e) {
		    		        deleteItem(name);
		    		    }
		    		});
		            
		            MainController.data.add(new MainController.column(name,quantity,company,link,delete));
		        }
		        
		        // STEP 4: Clean-up environment 
		        stmt.close(); 
		         
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } finally { 
		         //finally block used to close resources 
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } // nothing we can do 

		      } //end try

	}
	
public void getSarfElements(String type) {
		
		Statement stmt = null; 
		String sql="";
		  try { 
			  
			  stmt = conn.createStatement(); 
			  if (type.equals("all")) {
			        sql =  "SELECT * FROM sarf;";  
				  }
				  else if (type.equals("sabt")) {
					  sql =  "SELECT SARFNAME, sarf.quantity,department,date\r\n" + 
					  		"FROM items\r\n" + 
					  		"INNER JOIN sarf ON sarf.sarfname=items.name\r\n" + 
					  		"where type= 'ثابت'";
				  }
				  else if (type.equals("Motghir")){
					  sql =  "SELECT SARFNAME, sarf.quantity,department,date\r\n" + 
					  		"FROM items\r\n" + 
					  		"INNER JOIN sarf ON sarf.sarfname=items.name\r\n" + 
					  		"where type= 'متغير'";
				} 
		        
		        
		        ResultSet rs = stmt.executeQuery(sql);
		        MainController.msrofData.clear();
		        while (rs.next()) {
		            
		            String name = rs.getString("SARFNAME"); // Assuming there is a column called name.
		            String quantity = rs.getString("quantity");
		            String DEPARTMENT = rs.getString("DEPARTMENT");
		            Date date = rs.getDate("DATE");
		            

		            
		            MainController.msrofData.add(new MainController.msrofColumn(name,quantity,DEPARTMENT,date));
		            
		            

		        }
		        
		        // STEP 4: Clean-up environment 
		        stmt.close(); 
		         
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } finally { 
		         //finally block used to close resources 
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } // nothing we can do 

		      } //end try


        
		
	}
	
	
	
public void updateItemQuantity(String name,int quantity) {
		
		Statement stmt = null; 
		  try { 
			  
			  stmt = conn.createStatement(); 
		        String sql =  "UPDATE items\r\n" + 
		        		"SET quantity = '"+quantity +"' WHERE name='"+name+"';";  
		        
		        
		        stmt.execute(sql);

		        
		        // STEP 4: Clean-up environment 
		        stmt.close(); 
		         
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } finally { 
		         //finally block used to close resources 
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } // nothing we can do 

		      } //end try


        
		
	}

public ArrayList<String> getitemsNames(){
	ArrayList<String> itemsNames = new ArrayList<>();
	
	Statement stmt = null; 
	  try { 
		  
		  stmt = conn.createStatement(); 
    
	        ResultSet rs = stmt.executeQuery("SELECT name FROM items;");
	        
	        while (rs.next()) {
	            String name = rs.getString("NAME"); // Assuming there is a column called name.
	            itemsNames.add(name);
	        }
	        
	        // STEP 4: Clean-up environment 
	        stmt.close(); 
	        
	  } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
	
	return itemsNames;
}


public int getQuantity(String itemName) {
	
	Statement stmt = null; 
	ResultSet rs = null;
	int quantity =0;
	  try { 
		  
		  stmt = conn.createStatement(); 
  
	        rs = stmt.executeQuery("SELECT quantity FROM items where name='"+itemName+"';");
	        
	        while (rs.next()) {
	            quantity = rs.getInt("quantity"); // Assuming there is a column called name.
//	            System.out.print(quantity);
	        }
	        	        
	        // STEP 4: Clean-up environment 
	        stmt.close(); 
	        
	  } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
	
	return quantity;
}


public void addtoItem(String itemName, int quantity,LocalDate date) {

	updateItemQuantity(itemName, quantity+getQuantity(itemName));
	
	Statement stmt = null; 

	
	  try { 
		  
		  stmt = conn.createStatement(); 
  
	        stmt.execute("INSERT INTO additiondate VALUES ( '"+itemName+"', "+quantity+", '"+date+"'); ");
	        
	        	        
	        // STEP 4: Clean-up environment 
	        stmt.close(); 
	        
	  } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
}

public void getAdditionDates(String name) {
	
	Statement stmt = null; 
	ResultSet rs = null;
	int quantity =0;
	Date date = null;
	  try { 
		  
		  stmt = conn.createStatement(); 
  
	        rs = stmt.executeQuery("SELECT * FROM additiondate where itemname='"+name+"';");
	        
	        
	        final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("تواريخ اضافة "+ name );
            VBox dialogVbox = new VBox();
            
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialogVbox.setPadding(new Insets(10, 50, 50, 50));

  
	        
	        while (rs.next()) {
	            quantity = rs.getInt("quantity");
	            date = rs.getDate("date");
	            Text text =new Text(Integer.toString(quantity)+" -->  "+date.toString());
	            text.setFont(Font.font ("Verdana", 20));
	            dialogVbox.getChildren().add(text);	            

	        }
	        
	        dialog.show();
	        
	        
	        // STEP 4: Clean-up environment 
	        stmt.close(); 
	        
	  } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         //finally block used to close resources 
	         try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 

	      } //end try
	
	
}

public void deleteItem(String name) {
   
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

	    	ButtonType okButton = new ButtonType("نعم", ButtonBar.ButtonData.YES);
	    	ButtonType noButton = new ButtonType("لا", ButtonBar.ButtonData.NO);
	        alert.setTitle("حذف "+name);
			alert.setContentText("هل أنت متأكد من عملية الحذف ؟");  
			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {
			        if (type == okButton) {
	
			      	  try { 
			      		  Statement stmt = null;
			    		  stmt = conn.createStatement(); 
				         stmt.execute("DELETE FROM items WHERE name='"+name+"';");
		
				         
				         stmt.close();
				         getItems("all");
				         
			 	      } catch(Exception e) { 
			 	    	 new Alert(Alert.AlertType.ERROR, "لا يمكن الحذف لأرتباطه ببيانات اخرى").showAndWait();
			 	         e.printStackTrace(); 
			 	      } 
	        
			        }});
	        
	        // STEP 4: Clean-up environment 
	       
	        

	
}
}
