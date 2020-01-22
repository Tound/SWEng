package main;


import java.io.File;

import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import tools.MouseCoords;

public class EngineeringApplication extends Application{

	private int defaultHeight = 720;
	private int defaultWidth = 1280;
	
	private int height = defaultHeight;
	private int width = defaultWidth;
	
	private Scene home;
	private Scene main;
	private Scene settings;
	private Scene scene1;
	private Scene scene2;
	private Stage stage;
	private Timeline timeline;
	private int animationDuration = 15;
	private boolean mainModelPaused = false;
	private int angleRotated;
	
	private TriggerPoint testTriggerPoint;
	
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
		setupSettings();
		setupSlide();
		
		setupTriggerPoints();
		testTriggerPoint = new TriggerPoint(scene1,260,420,215,345,180);
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
		Button mainButton = new Button("Learn!");
		mainButton.setOnAction(e -> mainButtonPress());
		
		Button settingsButton = new Button("Settings");
		settingsButton.setOnAction(e -> settingsButtonPress());
		
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> quitButtonPress());
		
		//Add elements
		gridPane.add(title,1,1);
		gridPane.add(mainButton,1,2);
		gridPane.add(settingsButton,1,3);
		gridPane.add(quitButton,1,4);
		
		//Finalise Scene
		home = new Scene(gridPane, width, height);
		home.getStylesheets().add("style/homeScreen.css");
		System.out.println("Home Screen Setup...");
		
	}
	private void setupMain() {
		//Scene Setup
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setAlignment(Pos.CENTER);
		
		//Set title
		Text title = new Text("3D Model!");
		title.setTextAlignment(TextAlignment.CENTER);
		//Setup Buttons
		Button mainButton = new Button("Learn!");
		mainButton.setOnAction(e -> mainButtonPress());
		
		Button settingsButton = new Button("Settings");
		settingsButton.setOnAction(e -> settingsButtonPress());
		
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> quitButtonPress());
		
		Camera camera = new PerspectiveCamera();
		Group model = new Group();
		
		//Import 3D model
		
		//File modelUrl = new File("src/3D_Models/HST-3DS/hst.3ds");
		String fileExtention = "3ds";
		
		if(fileExtention == "3ds") {
			//3DS
			TdsModelImporter tdsImporter = new TdsModelImporter();
			tdsImporter.read("3D_Models/HST-3DS/hst.3ds");
			Node[] tdsMesh = (Node[]) tdsImporter.getImport();
			tdsImporter.close();
			model.getChildren().addAll(tdsMesh);
		}else if(fileExtention == "stl") {
			//STL
			StlMeshImporter stlImporter = new StlMeshImporter();
			stlImporter.read("src/3D_Models/");
			TriangleMesh stlMesh = stlImporter.getImport();
			
			MeshView stlMeshView = new MeshView();
			stlMeshView.setMaterial(new PhongMaterial(Color.GRAY));
			stlMeshView.setMesh(stlMesh);
			stlImporter.close();
			model.getChildren().add(stlMeshView);
		}else {
			System.out.println("Model file type not supported/File not found");
		}
		
		//Add Elements
		model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		
		Translate pivot =  new Translate();
		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
		
		camera.getTransforms().addAll(pivot, yRotate, new Rotate(-20, Rotate.X_AXIS));
		camera.getTransforms().add(new Translate(-340,-200,-300));
		
		//Set Camera Animation
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
		
		//Add to pane
		gridPane.add(title,3,0);
		gridPane.add(mainButton,4,0);
		gridPane.add(settingsButton,5,0);
		gridPane.add(quitButton,6,0);
		
		SubScene modelScene = new SubScene(model, 680, 400, true, SceneAntialiasing.BALANCED);
		modelScene.setCamera(camera);
		modelScene.setOnMouseEntered(e -> mouseOnModel());
		modelScene.setOnMouseExited(e -> mouseOffModel());
		modelScene.setOnMousePressed(e -> checkMouseCoords(e.getX(), e.getY()));
		gridPane.add(modelScene, 3, 1);
		
		//gridPane.setRowSpan(title, 3);
		//gridPane.setColumnSpan(modelScene, 1);
		
		//Finalise Scene
		main = new Scene(gridPane, width, height);
		main.getStylesheets().add("style/mainScreen.css");
		System.out.println("Main Screen Setup...");
	}
	
	private void setupSettings() {
		//Scene Setup
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setAlignment(Pos.CENTER);
		
		//Set title
		Text title = new Text("Settings!");
		title.setTextAlignment(TextAlignment.CENTER);
		//Setup Buttons
		Button homeButton = new Button("Back!");
		homeButton.setOnAction(e -> homeButtonPress());

		gridPane.add(title, 1, 0);
		gridPane.add(homeButton, 1, 1);
		settings = new Scene(gridPane, width, height);
		settings.getStylesheets().add("style/settingsScreen.css");
		System.out.println("Settings Screen Setup...");
	}
	
	private void setupSlide() {
		//Scene Setup
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setAlignment(Pos.CENTER);
		
		//Set title
		Text title = new Text("Content!");
		title.setTextAlignment(TextAlignment.CENTER);
		//Setup Buttons
		Button homeButton = new Button("Back!");
		homeButton.setOnAction(e -> homeButtonPress());

		gridPane.add(title, 1, 0);
		gridPane.add(homeButton, 1, 1);
		scene1 = new Scene(gridPane, width, height);
		scene1.getStylesheets().add("style/contentScreen.css");
		System.out.println("Content Screen Setup...");
	}
	
	private void homeButtonPress() {
		stage.setScene(home);
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
	private void mouseOnModel() {
		timeline.pause();
		System.out.println(timeline.getCurrentTime().toSeconds());
		double currentTime = timeline.getCurrentTime().toSeconds();
		angleRotated = (int)(currentTime/animationDuration * 360);
		System.out.println(angleRotated + " degrees");
		mainModelPaused = true;
	}
	private void mouseOffModel() {
		timeline.play();
		System.out.println("Model Resumed");
		mainModelPaused = false;
	}
	
	private void checkMouseCoords(double mouseX, double mouseY) {
		MouseCoords mouseCoords = new MouseCoords(mouseX, mouseY);
		if(mouseOnArea(mouseCoords)) {
			System.out.println(testTriggerPoint.getScene());
			stage.setScene(testTriggerPoint.getScene());
		}
		System.out.println(mouseX + "," + mouseY);
	}
	private boolean mouseOnArea(MouseCoords mouseCoords) {
		if(260 < mouseCoords.getMouseX() && mouseCoords.getMouseX() < 440 && angleRotated == testTriggerPoint.getAngle()) {
			if(215 < mouseCoords.getMouseY() && mouseCoords.getMouseY() < 348) {
				return true;
			}
		}
		System.out.println("Click not in area");
		return false;

	}
	
	private void setupTriggerPoints() {
		
		
	}
}
