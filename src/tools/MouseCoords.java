package tools;

public class MouseCoords {
	double mouseX;
	double mouseY;
	public MouseCoords(double mouseX2, double mouseY2) {
		this.mouseX = mouseX2;
		this.mouseY = mouseY2;
	}
	public double getMouseX() {
		return mouseX;
	}
	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}
	public double getMouseY() {
		return mouseY;
	}
	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}
}