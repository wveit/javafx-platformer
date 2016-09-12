package game;

import engine.shape.Rectangle;

public class Lava extends Rectangle{
	
	private double speed = 200;

	public Lava(double x, double y, double width, double height){
		super(x, y, width, height);
	}
	
	public void update(double deltaTime, World world){
		this.move(0, speed * deltaTime);
	}
}
