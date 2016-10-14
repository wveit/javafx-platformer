package platformer.game;

import platformer.engine.physics.Collision;
import platformer.engine.physics.CollisionInfo;
import platformer.engine.shape.Rectangle;

public class Spikey implements Enemy{

	private Rectangle rect;
	private double vX, vY;
	private double timer;
	private int aiMode = 0; // 0: Standing, 1: Moving Left, 2: Moving Right, 3: Getting Ready to Jump, 4: Moment of Jump, 5: In the Air, 6: Landed
	private boolean jumping = false;
	private double walkSpeed = 100;
	private double jumpSpeed = 500;
	
	public Spikey(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
		vX = vY = 0;
	}
	
	@Override
	public void update(double deltaTime, World world){
		timer += deltaTime;
		
		// update AI
		switch(aiMode){
			case 0:
				vX = 0;
				if(timer > 1){
					aiMode++;
					timer = 0;
				}
				break;
			case 1:
				vX = -walkSpeed;
				if(timer > 1.5){
					aiMode++;
					timer = 0;
				}
				break;
			case 2:
				vX = walkSpeed;
				if(timer > 1.5){
					aiMode++;
					timer = 0;
				}
				break;
			case 3:
				vX = 0;
				if(timer > 0.5){
					aiMode++;
					timer = 0;
				}
				break;
			case 4:
				vX = walkSpeed;
				vY = jumpSpeed;
				jumping = true;
				aiMode++;
				break;
			case 5:
				if(!jumping){
					aiMode++;
				}
				break;
			case 6:
				aiMode = 0;
				break;
			default:
				System.out.println("Spikey AI switch statement got to default branch - must be a bug!");
				break;
		}
		
		// update velocity and position based on mode
		vY += world.gravity * deltaTime;
		rect.move(vX * deltaTime, vY * deltaTime);
		
		// do collision detection/fixing

		
		for(Platform p : world.platformList){
			Rectangle pRect = p.rect();
			if(rect.overlaps(pRect)){
				CollisionInfo ci = Collision.resolve(rect, pRect);
				rect.move(ci.getX() * ci.getDistance(), ci.getY() * ci.getDistance());
				
				if(ci.getX() != 0)
					vX = -vX;
				else if(ci.getY() > 0){
					vY = 0;
					jumping = false;
				}
				else{
					vY = 0;
				}


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
	public boolean isDead(){
		return false;
	}
}
