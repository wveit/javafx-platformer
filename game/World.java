package game;

import java.util.ArrayList;
import shape.Rectangle;

public class World {
	public double gravity = -1000;
	public Player player;
	public Boundary boundary;
	public ArrayList<Rectangle> platformList = new ArrayList<Rectangle>();
	
	public World(){
		boundary = new Boundary(0, 0, 500, 500);
		
		platformList.add( new Rectangle(0, 0, 500, 10) );
		platformList.add( new Rectangle(0, 250, 150, 10) );
		platformList.add( new Rectangle(350, 250, 150, 10) );
		
		player = new Player( new Rectangle(250, 250, 150, 200) );
	}
	
	public void update(double deltaTime){
		player.update(deltaTime, this);
		boundary.update(deltaTime, this);
	}
}
