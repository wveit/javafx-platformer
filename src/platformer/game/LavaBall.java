package platformer.game;

import platformer.engine.physics.Collision;
import platformer.engine.physics.CollisionInfo;
import platformer.engine.shape.Rectangle;

public class LavaBall implements Enemy{

	private Rectangle rect;
	private double vX, vY;
	private int actionMode = 0;
	private double dyingTime = 0;
	private boolean isDead = false;
	
	public LavaBall(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	@Override
	public void update(double deltaTime, World world){
		if(actionMode == 0){
			vY += world.gravity * deltaTime;
			rect.move(vX * deltaTime, vY * deltaTime);
			
			for(Platform p : world.platformList){
				if(p.rect().overlaps(rect)){
					actionMode = 1;
					
					CollisionInfo ci = Collision.resolve(rect, p.rect());
					rect.move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
				}
			}
		}
		else{
			dyingTime += deltaTime;
			if(dyingTime >= 3)
				isDead = true;
		}
		
		
	}
	
	public void setVelocity(double vX, double vY){
		this.vX = vX;
		this.vY = vY;
	}
	
	public boolean isDead(){ return isDead; }
	
	@Override
	public Rectangle rect(){ return rect; }
	
	public int actionMode(){ return actionMode; }
}
