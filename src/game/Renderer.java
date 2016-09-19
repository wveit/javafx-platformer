package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import engine.shape.Rectangle;
import engine.sprite.SpriteAnimator;
import javafx.scene.image.Image;


public class Renderer {
	private GraphicsContext gc;
	private SpriteAnimator ninjaAnimator;
	private SpriteAnimator monsterAnimator;
	private Image platformImage;
	private Image bkgdImage;
	private Image gameOverImage;
	private Image sideRockImage;
	private double screenWidth;
	private double screenHeight;
	
	public Renderer(GraphicsContext gc, double width, double height){
		this.gc = gc;
		this.screenWidth = width;
		this.screenHeight = height;
		
		// Need some try/catch blocks just in case assets are missing
		
		platformImage = new Image("assets/platform.png");
		
		Image img = new Image("assets/myninja.png");
		ninjaAnimator = new SpriteAnimator(img);
		ninjaAnimator.addMode();
		for(int i = 0; i < 4; i++)
			ninjaAnimator.addRectToMode(0, new Rectangle(0 + i * 150, 0, 150, 200));
		
		img = new Image("assets/monster.png");
		monsterAnimator = new SpriteAnimator(img);
		monsterAnimator.addMode();
		monsterAnimator.addRectToMode(0, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
		
		gameOverImage = new Image("assets/gameover.png");

		bkgdImage = new Image("assets/volcano1.png");
		sideRockImage = new Image("assets/walls.png");
	}
	
	
	public void render(World world){
		
		Rectangle boundaryRect = flipY( world.boundary );
		
		renderBkgd(world.boundary);
		
		for(Platform p : world.platformList){
			render(p, boundaryRect);
		}
		
		for(Enemy e : world.enemyList){
			if(e instanceof LavaMonster)
				render((LavaMonster)e, boundaryRect);
			else if(e instanceof Vulcor)
				render((Vulcor)e, boundaryRect);
			else if(e instanceof LavaBall)
				render((LavaBall)e, boundaryRect);
			else if(e instanceof Spikey)
				render((Spikey)e, boundaryRect);
			
		}
		
		render(world.player, boundaryRect);
		render(world.boundary);
		render(world.lava, boundaryRect);
		render(world.sideRock, boundaryRect, world.boundary);
	}
	
	public void renderBkgd(Rectangle boundary){
		double parallaxFactor = 1 / 32.0;

		gc.drawImage(
				bkgdImage, 
				
				0, 
				0 + bkgdImage.getHeight() - bkgdImage.getWidth() * screenHeight / screenWidth - boundary.minY() * parallaxFactor,
				bkgdImage.getWidth(), 
				bkgdImage.getWidth() * screenHeight / screenWidth, 
				
				0, 
				0, 
				screenWidth, 
				screenHeight);
	
	}
	
	public void render(Player player, Rectangle boundary){
		Rectangle rect = flipY( player.rect() );
		rect.move(0, -boundary.minY());

		long millisPerFrame = 100;
		long millis = System.currentTimeMillis();
		int numFrames = 4;
		
		int frameNum = (int)(millis / millisPerFrame % numFrames);
		
		if(player.vX() < -0.1)
			ninjaAnimator.setFlippedHorizontal(true);
		else if (player.vX() > 0.1)
			ninjaAnimator.setFlippedHorizontal(false);
		
		
		ninjaAnimator.draw(gc, rect, frameNum);

	}
	
	public void render(Platform platform, Rectangle boundary){
		Rectangle rect = flipY( platform.rect() );
		// figure out number of blocks
		int numBlocks = (int)rect.width() / 50;
		int currentBlock = 0;
		int blockPicNum = 0;
		
		// draw first block
		gc.drawImage(platformImage, blockPicNum * 50, 0, 50, rect.height(), rect.minX() + currentBlock * 50, rect.minY() - boundary.minY(), 50, rect.height());
		currentBlock++;
		
		// draw middle blocks
		while(currentBlock + 1 < numBlocks){
			blockPicNum = 1;
			gc.drawImage(platformImage, blockPicNum * 50, 0, 50, rect.height(), rect.minX() + currentBlock * 50, rect.minY() - boundary.minY(), 50, rect.height());
			
			currentBlock++;
		}

		// draw last block
		blockPicNum = 7;
		gc.drawImage(platformImage, blockPicNum * 50, 0, 50, rect.height(), rect.minX() + currentBlock * 50, rect.minY() - boundary.minY(), 50, rect.height());
	}
	
	public void render(LavaMonster monster, Rectangle boundary){
		Rectangle rect = flipY( monster.rect() );
		rect.move(0, -boundary.minY());
		monsterAnimator.draw(gc, rect);
		
		if(System.currentTimeMillis() / 1000 % 2 == 0){
			monsterAnimator.setFlippedHorizontal(false);
		}
		else{
			monsterAnimator.setFlippedHorizontal(true);
		}
		
	}
	
	public void render(SideRock sr, Rectangle flippedBoundary, Rectangle boundary){
		Rectangle rect = flipY( sr.rect() );
		rect.move(0, -boundary.minY());
		
		// there are 8 levels of rock
		int numRockPics = 8;
		
		// find which number the lowest visible rock number should be, and it's height
		int rockLevel = (int)boundary.minY() / 50;
		double rockHeight = -rockLevel * 50;
		int currentRockPic = rockLevel % numRockPics;

		System.out.println(rockLevel);
		
		while(rockHeight > boundary.maxY()){
			gc.drawImage(sideRockImage, 0, currentRockPic * 50, 50, 50, 0, rockHeight - boundary.minY(), 50, 50);
			gc.drawImage(sideRockImage, 150, currentRockPic * 50, 50, 50, boundary.maxX() - 50, rockHeight - boundary.minY(), 50, 50);
			
			rockLevel++;
			rockHeight -= 50;
			currentRockPic = rockLevel % numRockPics;
		}
		
		
	}
	
	public void render(LavaBall monster, Rectangle boundary){
		Rectangle rect = flipY( monster.rect() );
		rect.move(0,  -boundary.minY());
		
		if(monster.actionMode() == 0)
			gc.setFill(Color.RED);
		else
			gc.setFill(Color.BLANCHEDALMOND);
		
		gc.fillRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}
	
	public void render(Spikey monster, Rectangle boundary){
		Rectangle rect = flipY( monster.rect() );
		rect.move(0,  -boundary.minY());
		
		gc.setFill(Color.GREEN);
		
		gc.fillRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}
	
	public void render(Vulcor v, Rectangle boundary){
		Rectangle rect = flipY( v.rect() );
		rect.move(0,  -boundary.minY());
		if(v.actionMode() == 0)
			gc.setFill(Color.AZURE);
		else
			gc.setFill(Color.BISQUE);
		gc.fillRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}
	

	public void render(Lava lava, Rectangle boundary){
		Rectangle rect = flipY( lava.rect() );
		gc.setFill(Color.ORANGE);
		gc.fillRect(rect.minX(), rect.minY() - boundary.minY(), rect.width(), rect.height());
	}
	
	public void render(Boundary boundary){
		Rectangle rect = flipY( boundary );
		gc.setStroke(Color.BLUE);
		gc.strokeRect(rect.minX(), 0, rect.width(), rect.height());
	}

	private Rectangle flipY(Rectangle rect){
		return new Rectangle(rect.minX(), screenHeight - rect.minY() - rect.height(), rect.width(), rect.height());
	}
	
	public void renderGameOver(){
		gc.drawImage(gameOverImage, 0, 0, screenWidth, screenHeight);
	}

}
