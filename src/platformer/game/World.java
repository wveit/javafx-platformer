package platformer.game;

import java.util.ArrayList;

import platformer.engine.shape.Rectangle;


public class World {
	public double gravity = -1000;
	
	public Player player;
	public Rectangle leftBoundary = null;
	public Rectangle rightBoundary = null;
	public ArrayList<Platform> platformList = new ArrayList<>();
	public ArrayList<Enemy> enemyList = new ArrayList<>();
	public Lava lava;
	
	public World(String filename){
		this.load(filename);
	}
	
	public boolean load(String filename){

		if(filename.equals("volcano_level.lvl")){
			loadVolcano();
			return true;
		}
		else if(filename.equals("jungle_level.lvl")){
			loadVolcano();
			return true;
		}
		else
			return false;
	}
	
	private void loadVolcano(){
		double width = 1200;
		double height = 800;
		double levelHeight = 10000;
		
		leftBoundary = new Rectangle(-50, 0, 50, levelHeight * 100);
		rightBoundary = new Rectangle(width, 0, 50, levelHeight * 100);
		
		platformList.add( new Platform(0, 0, width, 50) );
		platformList.add( new Platform(0, 250, width / 3, 50) );
		platformList.add( new Platform(2 * width / 3, 250, width / 3, 50) );
		
		double start = 500;
		double size = 500;
		for(int i = 0; i < 100; i++){
			platformList.add( new Platform(width / 3, start + i * size, width / 3, 50) );
			platformList.add( new Platform(0, start + i * size + 250, width / 3, 50) );
			platformList.add( new Platform(2 * width / 3, start + i * size + 250, width / 3, 50) );
		}
		
		enemyList.add(new LavaMonster(800, 1000, 50, 50));
		enemyList.add(new Vulcor(600, 600, 50, 50));
		enemyList.add(new Spikey(600, 600, 50, 50));
		
		player = new Player(250, 250, 50, 75);
		lava = new Lava(0, -100 - height, width, height);
	}
	
	private void loadJungle(){
		
	}
	
	public void update(double deltaTime){
		
		// update level entities
		player.update(deltaTime, this);
		lava.update(deltaTime, this);
		
		// remove dead enemies from the enemy list and update live enemies
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).isDead()){
				enemyList.remove(i);
				i--;				
			}
			else{
				enemyList.get(i).update(deltaTime, this);
			}
		}

	}
}
