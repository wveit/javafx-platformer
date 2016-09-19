package game;

import engine.shape.Rectangle;

public class SideRock {
	
	private Rectangle rect;
	public SideRock(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	public Rectangle rect(){ return rect; }
}
