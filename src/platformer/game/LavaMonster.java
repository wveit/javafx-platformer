package platformer.game;

import platformer.engine.physics.Collision;
import platformer.engine.physics.CollisionInfo;
import platformer.engine.shape.Rectangle;

public class LavaMonster implements Enemy{
	
	private double vX, vY;
	private double moveSpeed = 100;
	private Rectangle rect;

	public LavaMonster(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
		vX = moveSpeed;
	}
	
	@Override
	public void update(double deltaTime, World world){
		
		vY += world.gravity * deltaTime;
		rect.move(vX * deltaTime, vY * deltaTime);
		
		for(Platform p : world.platformList){
			Rectangle pRect = p.rect();
			if(rect.overlaps(pRect)){
				CollisionInfo ci = Collision.resolve(rect, pRect);
				rect.move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
				if(ci.getX() != 0)
					vX = -vX;
				else 
					vY = 0;
			}
		}
		
		if(rect.overlaps(world.leftBoundary) || rect.overlaps(world.rightBoundary)){
			vX = -vX;
		}
	}
	
	@Override
	public Rectangle rect(){
		return rect;
	}
	
	@Override
	public boolean isDead(){ return false; }
}
