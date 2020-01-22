package main;

import java.util.ArrayList;

import javafx.scene.Scene;

public class TriggerPoint {
	Scene scene;
	ArrayList<Integer> xBounds = new ArrayList<>();
	ArrayList<Integer> yBounds = new ArrayList<>();
	int angle;
	
	public TriggerPoint(Scene scene,int xMin, int xMax, int yMin, int yMax, int angle) {
		this.xBounds.add(xMin);
		this.yBounds.add(yMin);
		this.xBounds.add(xMax);
		this.yBounds.add(yMax);
		this.angle = angle;
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public ArrayList<Integer> getxBounds() {
		return xBounds;
	}
	public void setxBounds(ArrayList<Integer> xBounds) {
		this.xBounds = xBounds;
	}
	public ArrayList<Integer> getyBounds() {
		return yBounds;
	}
	public void setyBounds(ArrayList<Integer> yBounds) {
		this.yBounds = yBounds;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
}