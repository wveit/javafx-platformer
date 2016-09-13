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
	private Image bkgdImage;
	private Image gameOverImage;
	private double screenWidth;
	private double screenHeight;
	private double scaleX;
	private double scaleY;
	
	public Renderer(GraphicsContext gc, double width, double height){
		this.gc = gc;
		this.screenWidth = width;
		this.screenHeight = height;
		
		
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
	}
	
	public void resize(double width, double height){
		this.screenWidth = width;
		this.screenHeight = height;
	}

	
	public void render(World world){
		scaleX = screenWidth / world.boundary.width();
		scaleY = screenHeight / world.boundary.height();
		
		Rectangle boundaryRect = flipY( world.boundary );
		
		renderBkgd(world.boundary);
		
		for(Rectangle rect : world.platformList){
			render(rect, boundaryRect);
		}
		
		for(Monster m : world.monsterList)
			render(m, boundaryRect);
		
		render(world.player, boundaryRect);
		render(world.boundary);
		render(world.lava, boundaryRect);
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
		Rectangle rect = flipY( player );
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
	
	public void render(Rectangle platform, Rectangle boundary){
		Rectangle rect = flipY( platform );
		gc.setFill(Color.BLACK);
		gc.fillRect(rect.minX(), rect.minY() - boundary.minY(), rect.width(), rect.height());
	}
	
	public void render(Monster monster, Rectangle boundary){
		Rectangle rect = flipY( monster );
		rect.move(0, -boundary.minY());
		monsterAnimator.draw(gc, rect);
		
		if(System.currentTimeMillis() / 1000 % 2 == 0){
			monsterAnimator.setFlippedHorizontal(false);
		}
		else{
			monsterAnimator.setFlippedHorizontal(true);
		}
		

		
	}
	

	public void render(Lava lava, Rectangle boundary){
		Rectangle rect = flipY( lava );
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
