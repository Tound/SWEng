package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EngineeringApplication extends Application{

	private int defaultHeight = 720;
	private int defaultWidth = 1280;
	
	private int height = defaultHeight;
	private int width = defaultWidth;
	
	private Scene home;
	private Scene main;
	private Scene scene1;
	private Scene scene2;
	private Stage stage;
	
	public static void main(String[] args) {
		System.out.println("Running...");
		launch(args);
		System.out.println("Finished...");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Setup Stage
		stage = primaryStage;
		stage.setTitle("Engineering Application"); //CHANGE WHEN NEEDED
		stage.setHeight(defaultHeight);
		stage.setWidth(defaultWidth);
		System.out.println("Stage Setup...");
		
		//Setting up Scenes
		setupHome();
		setupMain();
		System.out.println("Scenes Setup...");
		
		stage.setScene(home);
		stage.show();
	}
	
	private void setupHome() {
		Text title = new Text("Engineering Application!");
		GridPane gridPane = new GridPane();
		
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setAlignment(Pos.CENTER);
		
		//Setup Buttons
		Button switchToMain = new Button("Learn!");
		switchToMain.setOnAction(e -> mainButtonPress());
		
		Button switchToSettings = new Button("Settings");
		switchToSettings.setOnAction(e -> settingsButtonPress());
		
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> quitButtonPress());
		
		//Add elements
		gridPane.add(title,1,1);
		gridPane.add(switchToMain,1,2);
		gridPane.add(title,1,0);
		gridPane.add(title,1,0);
		gridPane.add(title,1,0);
		gridPane.add(title,1,0);
		
		//Finalise Scene
		home = new Scene(gridPane, width, height);
		home.getStylesheets().add("");
		
	}
	private void setupMain() {
		
	}
	
	private void mainButtonPress() {
		stage.setScene(main);
	}
	private void settingsButtonPress() {
		stage.setScene(settings);
	}
	private void quitButtonPress() {
		System.exit(1);
	}

}
