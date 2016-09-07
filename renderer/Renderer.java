package renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;


import shape.Rectangle;
import game.Player;
import game.World;
import game.Boundary;


public class Renderer {
	private GraphicsContext gc;
	private Canvas canvas;
	
	public Renderer(GraphicsContext gc, Canvas canvas){
		this.gc = gc;
		this.canvas = canvas;
	}
	
	public void render(World world){
		for(Rectangle rect : world.platformList){
			render(rect);
		}
		
		render(world.player);
		
		render(world.boundary);
	}
	
	public void render(Player player){
		Rectangle rect = flipY( player.rect() );
		gc.setFill(Color.RED);
		gc.fillRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}
	
	public void render(Rectangle platform){
		Rectangle rect = flipY( platform );
		gc.setFill(Color.BLACK);
		gc.fillRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}
	
	public void render(Boundary boundary){
		Rectangle rect = flipY( boundary );
		gc.setStroke(Color.BLUE);
		gc.strokeRect(rect.minX(), rect.minY(), rect.width(), rect.height());
	}

	private Rectangle flipY(Rectangle rect){
		return new Rectangle(rect.minX(), canvas.getHeight() - rect.minY() - rect.height(), rect.width(), rect.height());
	}
	
}
