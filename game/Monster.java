package game;

import engine.physics.Collision;
import engine.physics.CollisionInfo;
import engine.shape.Rectangle;

public class Monster extends Rectangle{
	
	private double vX, vY;
	private double moveSpeed = 100;

	public Monster(double x, double y, double width, double height){
		super(x, y, width, height);
		vX = moveSpeed;
	}
	
	public void update(double deltaTime, World world){
		
		vY += world.gravity * deltaTime;
		this.move(vX * deltaTime, vY * deltaTime);
		
		for(Rectangle pRect : world.platformList){
			if(overlaps(pRect)){
				CollisionInfo ci = Collision.resolve(this, pRect);
				move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
				if(ci.getX() != 0)
					vX = -vX;
				else 
					vY = 0;
			}
		}
		
		if(!inside(world.boundary)){
			CollisionInfo ci = Collision.resolveUncollision(this, world.boundary);
			if(ci.getX() != 0){
				move(ci.getX() * ci.getDistance(), 0);
				vX = -vX;
			}
				
		}
	}
}
