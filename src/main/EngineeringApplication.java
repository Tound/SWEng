package main;


import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

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
		//Setup Buttons
		Button mainButton = new Button("Learn!");
		mainButton.setOnAction(e -> mainButtonPress());
		
		Button settingsButton = new Button("Settings");
		settingsButton.setOnAction(e -> settingsButtonPress());
		
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> quitButtonPress());
		
		//Import 3D model
		//3DS
		TdsModelImporter tdsImporter = new TdsModelImporter();
		tdsImporter.read("src/3D_Models/HST-3DS/hst.3ds");
		Node[] tdsMesh = (Node[]) tdsImporter.getImport();
		tdsImporter.close();
		
		//STL
		StlMeshImporter stlImporter = new StlMeshImporter();
		stlImporter.read("src/3D_Models/");
		TriangleMesh stlMesh = stlImporter.getImport();
		
		MeshView stlMeshView = new MeshView();
		stlMeshView.setMaterial(new PhongMaterial(Color.GRAY));
		stlMeshView.setMesh(stlMesh);
		stlImporter.close();
		
		//Add Elements
		Camera camera = new PerspectiveCamera();
		Group model = new Group();
		
		model.getChildren().addAll(tdsMesh);
		model.getChildren().add(stlMeshView);
		model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		
		//Add to pane
		gridPane.add(title,1,0);
		gridPane.add(mainButton,4,0);
		gridPane.add(settingsButton,5,0);
		gridPane.add(quitButton,6,0);
		
		SubScene modelScene = new SubScene(model, 680, 400, true, SceneAntialiasing.BALANCED);
		
		gridPane.add(modelScene, 3, 1);
		//Finalise Scene
		main = new Scene(gridPane, width, height);
		main.getStylesheets().add("style/mainScreen.css");
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
