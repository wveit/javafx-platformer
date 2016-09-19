package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;

import engine.shape.Rectangle;
import javafx.scene.image.Image;

public class Renderer2 {
	private GraphicsContext gc;
	private Rectangle viewport;
	
	private Image ninjaImage;
	private Image lavaMonsterImage;
	private Image spikeyImage;
	private Image vulcorImage;
	private Image backgroundImage;
	private Image platformImage;
	private Image gameOverImage;
	
	public Renderer2(GraphicsContext gc, double width, double height){
		this.gc = gc;

		viewport = new Rectangle(0, 0, width, height);
		
		// load images		
		try{
			ninjaImage = new Image(new File("assets/ninja.png").toURI().toURL().toString());
			lavaMonsterImage = new Image(new File("assets/lava_monster.png").toURI().toURL().toString());
			spikeyImage = new Image(new File("assets/spikey.png").toURI().toURL().toString());
			vulcorImage = new Image(new File("assets/vulcor.png").toURI().toURL().toString());
			backgroundImage = new Image(new File("assets/volcano_background.png").toURI().toURL().toString());
			platformImage = new Image(new File("assets/platform.png").toURI().toURL().toString());
			gameOverImage = new Image(new File("assets/gameover.png").toURI().toURL().toString());
		
		}catch(Exception e){
			System.out.println("Renderer2 had problems loading images.");
		}
		
	}
	
	
	public void render(World world){
		
		// update viewport based on player position
		double diff = viewport.centerY() - world.player.rect().centerY();
		viewport.move(0, -diff);
		if(viewport.minY() < 0)
			viewport.setY(0);
		
		
		// draw each of the world's items in terms of the viewport
		renderBackground();
		
		for(Platform p : world.platformList)
			render(p);
		
		for(Enemy e : world.enemyList){
			if(e instanceof LavaMonster)
				render((LavaMonster)e);
			else if(e instanceof Spikey)
				render((Spikey)e);
			else if(e instanceof Vulcor)
				render((Vulcor)e);
			else
				render(e);
		}
			
		
		render(world.player);
		
		render(world.lava);
		
	}
	
	public void renderBackground(){
		Rectangle r = translateRect(new Rectangle(0, 0, 1200, 10000));
		draw(backgroundImage, new Rectangle(0, 0, 200, 800), r);
		
	}
	
	public void render(LavaMonster m){
		Rectangle r = translateRect(m.rect());
		draw(lavaMonsterImage, new Rectangle(0, 0, 191, 263), r);
	}
	
	public void render(Spikey s){
		Rectangle r = translateRect(s.rect());
		draw(spikeyImage, new Rectangle(0, 0, 75, 110), r);
	}
	
	public void render(Vulcor v){
		Rectangle r = translateRect(v.rect());
		if(v.actionMode() == Vulcor.ActionMode.READY)
			draw(vulcorImage, new Rectangle(100, 0, 100, 100), r);
		else
			draw(vulcorImage, new Rectangle(0, 0, 100, 100), r);
	}
	
	public void render(Enemy enemy){
		Rectangle r = translateRect(enemy.rect());
		gc.setFill(Color.BROWN);
		gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
	}
	
	public void render(Player player){
		Rectangle r = translateRect(player.rect());
		draw(ninjaImage, new Rectangle(0, 0, 150, 200), r);
	}
	
	public void render(Platform platform){
		Rectangle r = translateRect(platform.rect());
		draw(platformImage, new Rectangle(0, 0, Math.min(r.width(), 400), r.height()), r);
	}
	
	public void render(Lava lava){
		Rectangle r = translateRect(lava.rect());
		gc.setFill(Color.ORANGE);
		gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
	}
	
	
	private Rectangle translateRect(Rectangle r){
		return new Rectangle(r.minX() - viewport.minX(), viewport.maxY() - r.maxY(), r.width(), r.height());
	}

	public void renderGameOver(){
		gc.drawImage(gameOverImage, 0, 0, viewport.width(), viewport.height());
	}
	
	private void draw(Image img, Rectangle srcRect, Rectangle destRect){
		gc.drawImage(
				img, 
				srcRect.minX(),  srcRect.minY(), srcRect.width(), srcRect.height(),
				destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
	}

}
