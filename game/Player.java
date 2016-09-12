package game;

import engine.physics.Collision;
import engine.physics.CollisionInfo;
import engine.shape.Rectangle;

public class Player {
	
	private Rectangle rect;
	private double vX, vY;
	private boolean requestLeft, requestRight, requestJump;
	private double moveSpeed = 250;
	private double jumpSpeed = 1000;
	private boolean isOnPlatform = false;
	
	public Player(){
		rect = new Rectangle(0, 0, 50, 75);
		privateReset();
	}
	
	public Player(Rectangle rect){
		this.rect = rect;
		privateReset();
	}
	
	public Rectangle rect(){
		return rect;
	}
	
	public void setRect(Rectangle rect){
		this.rect = rect;
	}
	
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
		
		for(Rectangle pRect : world.platformList){
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
		
		if(!rect.inside(world.boundary)){
			CollisionInfo ci = Collision.resolveUncollision(rect, world.boundary);
			if(ci.getX() != 0){
				rect.move(ci.getX() * ci.getDistance(), 0);
			}
				
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
		requestLeft = requestRight = requestJump = false;
	}
	
	public double vX(){ return vX; }
	public double vY(){ return vY; }
	

}
