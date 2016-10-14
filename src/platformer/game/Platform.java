package platformer.game;

import platformer.engine.shape.Rectangle;

public class Platform {
	private Rectangle rect;
	public Platform(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	public Rectangle rect(){
		return rect;
	}
}
