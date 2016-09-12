package game;

import java.util.ArrayList;

import engine.shape.Rectangle;

public class World {
	public double gravity = -1000;
	public Player player;
	public Boundary boundary;
	public ArrayList<Rectangle> platformList = new ArrayList<Rectangle>();
	public Lava lava;
	
	public World(double width, double height){
		boundary = new Boundary(0, 0, width, height);
		
		platformList.add( new Rectangle(0, 0, width, 10) );
		platformList.add( new Rectangle(0, 250, width / 3, 10) );
		platformList.add( new Rectangle(2 * width / 3, 250, width / 3, 10) );
		
		double start = 500;
		double size = 500;
		for(int i = 0; i < 100; i++){
			platformList.add( new Rectangle(width / 3, start + i * size, width / 3, 10) );
			platformList.add( new Rectangle(0, start + i * size + 250, width / 3, 10) );
			platformList.add( new Rectangle(2 * width / 3, start + i * size + 250, width / 3, 10) );
		}

		
		player = new Player( new Rectangle(250, 250, 50, 75) );
		lava = new Lava(0, -100 - height, width, height);
		
	}
	
	public void update(double deltaTime){
		player.update(deltaTime, this);
		boundary.update(deltaTime, this);
		lava.update(deltaTime, this);
	}
}
