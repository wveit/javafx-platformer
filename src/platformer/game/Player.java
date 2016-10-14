package platformer.game;

import java.util.ArrayList;

import platformer.engine.physics.Collision;
import platformer.engine.physics.CollisionInfo;
import platformer.engine.shape.Rectangle;

public class Player{
	
	private Rectangle rect;
	private double vX, vY;
	private boolean requestLeft, requestRight, requestJump;
	private double moveSpeed = 250;
	private double jumpSpeed = 1000;
	private boolean isOnPlatform = false;
	private boolean isDead;
	
	public Player(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
		privateReset();
	}
	
	public boolean isDead(){ return isDead; }
	

	


	
	public void update(double deltaTime, World world){
		
		vX = 0;
		
		if(requestLeft){
			vX -= moveSpeed;
		}
		if(requestRight){
			vX += moveSpeed;
		}
		
		if(requestJump && isOnPlatform){
			vY = jumpSpeed;
		}
		else{
			vY += world.gravity * deltaTime;
		}
		if(requestJump){
			requestJump = false;
		}
		
		rect.move(vX * deltaTime, vY * deltaTime);
		
		isOnPlatform = false;
		
		for(Platform p : world.platformList){
			Rectangle pRect = p.rect();
			if(rect.overlaps(pRect)){
				CollisionInfo ci = Collision.resolve(rect, pRect);
				rect.move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
				if(ci.getX() != 0)
					vX = 0;
				else if(ci.getY() > 0){
					isOnPlatform = true;
					vY = 0;
				}
				else if (ci.getY() < 0){
					vY = 0;
				}
			}
		}
		
		if(rect.overlaps(world.leftBoundary)){
			CollisionInfo ci = Collision.resolve(rect, world.leftBoundary);
			if(ci.getX() != 0){
				rect.move(ci.getX() * ci.getDistance(), 0);
			}
		}
		
		if(rect.overlaps(world.rightBoundary)){
			CollisionInfo ci = Collision.resolve(rect, world.leftBoundary);
			if(ci.getX() != 0){
				rect.move(ci.getX() * ci.getDistance(), 0);
			}
		}
		
		for(Enemy e : world.enemyList){
			if(rect.overlaps(e.rect())){
				if(!(e instanceof Vulcor))
					isDead = true;
			}
		}
		
		if(rect.overlaps(world.lava.rect())){
			isDead = true;
		}
		
	}
	
	public boolean isOnPlatform(){
		return isOnPlatform;
	}
	
	public void requestLeft(boolean request){
		requestLeft = request;
	}
	
	public void requestRight(boolean request){
		requestRight = request;
	}
	
	public void requestJump(){
		requestJump = true;
	}
	
	
	private void privateReset(){
		vX = vY = 0;
		requestLeft = requestRight = requestJump = isDead = false;
	}
	
	public double vX(){ return vX; }
	public double vY(){ return vY; }
	
	public Rectangle rect(){ return rect; }

}
