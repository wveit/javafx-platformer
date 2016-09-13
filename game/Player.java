package game;

import engine.physics.Collision;
import engine.physics.CollisionInfo;
import engine.shape.Rectangle;

public class Player extends Rectangle{
	
	private double vX, vY;
	private boolean requestLeft, requestRight, requestJump;
	private double moveSpeed = 250;
	private double jumpSpeed = 1000;
	private boolean isOnPlatform = false;
	private boolean isDead;
	
	public Player(double x, double y, double width, double height){
		super(x, y, width, height);
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
		
		move(vX * deltaTime, vY * deltaTime);
		
		isOnPlatform = false;
		
		for(Rectangle pRect : world.platformList){
			if(overlaps(pRect)){
				CollisionInfo ci = Collision.resolve(this, pRect);
				move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
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
		
		if(!inside(world.boundary)){
			CollisionInfo ci = Collision.resolveUncollision(this, world.boundary);
			if(ci.getX() != 0){
				move(ci.getX() * ci.getDistance(), 0);
			}
				
		}
		
		for(Monster m : world.monsterList){
			if(this.overlaps(m)){
				isDead = true;
			}
		}
		
		if(overlaps(world.lava)){
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
	

}
