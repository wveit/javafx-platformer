package renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


import shape.Rectangle;
import game.Player;
import game.World;
import game.Boundary;
import sprite.SpriteAnimator;


public class Renderer {
	private GraphicsContext gc;
	private Canvas canvas;
	private SpriteAnimator ninjaAnimator;
	
	public Renderer(GraphicsContext gc, Canvas canvas){
		this.gc = gc;
		this.canvas = canvas;
		
		Image img = new Image("myninja.png");
		ninjaAnimator = new SpriteAnimator(img);
		ninjaAnimator.addMode();
		for(int i = 0; i < 4; i++)
			ninjaAnimator.addRectToMode(0, new Rectangle(0 + i * 150, 0, 150, 200));
	}
	
	public void render(World world){
		Rectangle boundaryRect = flipY( world.boundary );
		
		for(Rectangle rect : world.platformList){
			render(rect, boundaryRect);
		}
		
		render(world.player, boundaryRect);
		render(world.boundary);
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
	
	public void render(Rectangle platform, Rectangle boundary){
		Rectangle rect = flipY( platform );
		gc.setFill(Color.BLACK);
		gc.fillRect(rect.minX(), rect.minY() - boundary.minY(), rect.width(), rect.height());
	}
	
	public void render(Boundary boundary){
		Rectangle rect = flipY( boundary );
		gc.setStroke(Color.BLUE);
		gc.strokeRect(rect.minX(), rect.minY() - rect.minY(), rect.width(), rect.height());
	}

	private Rectangle flipY(Rectangle rect){
		return new Rectangle(rect.minX(), canvas.getHeight() - rect.minY() - rect.height(), rect.width(), rect.height());
	}
	
}
