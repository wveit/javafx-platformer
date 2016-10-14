package platformer.game;

import platformer.engine.shape.Rectangle;

public class Lava implements Enemy{
	
	private double speed = 50;
	Rectangle rect;

	public Lava(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	@Override
	public void update(double deltaTime, World world){
		rect.move(0, speed * deltaTime);
	}
	
	@Override
	public Rectangle rect(){ return rect; }

	@Override
	public boolean isDead() { return false; }
}
