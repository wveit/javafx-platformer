package game;

import shape.Rectangle;

public class Boundary extends Rectangle{
	
	private double speed = 100;

	public Boundary(double x, double y, double width, double height){
		super(x, y, width, height);
	}
	
	public void update(double deltaTime, World world){
		Rectangle rect = world.player.rect();
		
		if(centerY() - rect.centerY() > height() / 4){
			move(0, -speed * deltaTime);
		}
		else if(rect.centerY() - centerY() > height() / 4){
			move(0, speed * deltaTime);
		}
	}
}
