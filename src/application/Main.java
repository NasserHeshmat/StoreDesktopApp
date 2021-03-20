package application;
	
import javafx.application.Application;

import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;




public class Main extends Application {
	
	public static ArrayList<String> names = new ArrayList<String>();
	public static ArrayList<String> quantities = new ArrayList<String>();
	

	

	@Override
	public void start(Stage primaryStage) {
	
		try {


			Parent root =FXMLLoader.load(getClass().getResource("/application/Main.fxml"));

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
