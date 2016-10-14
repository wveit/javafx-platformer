package platformer.game;


import platformer.engine.shape.Rectangle;

public class Vulcor implements Enemy{

	private Rectangle rect;
	private int actionMode = 0;
	private double cycleTime = 0;
	private boolean onPlatform = false;
	private double vY = 0;
	
	public Vulcor(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	@Override 
	public void update(double deltaTime, World world){
		
		if(!onPlatform){
			vY += world.gravity * deltaTime;
			rect.move(0, vY * deltaTime);
			for(Platform p : world.platformList){
				Rectangle pRect = p.rect();
				if(rect.overlaps(pRect)){
					onPlatform = true;
					rect.move(0, pRect.maxY() - rect.minY());
				}
			}
		}
		
		cycleTime += deltaTime;
		if(cycleTime >= 4){
			actionMode = ActionMode.RELAXED;
			cycleTime = 0;
			
			LavaBall lb1 = new LavaBall(rect.centerX() - 10, rect.maxY(), 20, 20);
			LavaBall lb2 = new LavaBall(rect.centerX() - 10, rect.maxY(), 20, 20);
			
			lb1.setVelocity(-100, 800);
			lb2.setVelocity(100, 800);
			
			world.enemyList.add(lb1);
			world.enemyList.add(lb2);
		}
		else if(cycleTime >= 3){
			actionMode = ActionMode.READY;
		}
			
	}
	
	@Override
	public Rectangle rect(){ return rect; }
	
	public int actionMode(){
		return actionMode;
	}
	
	@Override
	public boolean isDead(){ return false; }
	
	static class ActionMode{
		static final int RELAXED = 0;
		static final int READY = 1;
	}
}
