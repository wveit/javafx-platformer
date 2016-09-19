package game;

import engine.shape.Rectangle;

public interface Enemy{
	
	public void update(double deltaTime, World world);
	public Rectangle rect();
	public boolean isDead();
	
}
