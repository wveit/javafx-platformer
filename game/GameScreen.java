package game;

import java.io.File;

import engine.screen.MyScreen;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.util.Duration;

public class GameScreen extends MyScreen{
	
	
	private World world;
	private Renderer renderer;
	private MediaPlayer mediaPlayer;
	
	private long lastNanoseconds = 0;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		world = new World(width, height);
		renderer = new Renderer(this.getGraphicsContext2D(), width, height);
		
		File file = new File("assets/airwolf.wav");
		if(file.exists()){			
			Media media = null;
			try{
				media = new Media("file:" + file.getAbsolutePath());
			}catch(Exception e){
				System.out.println("Could not play music because file was not found.");
			}
			
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(new Runnable(){
				@Override
				public void run(){
					mediaPlayer.seek(Duration.ZERO);
				}
			});
			mediaPlayer.play();
		}
		else{
			System.out.println("file does not exist");
		}

		
	}

	@Override
	public void tick(long nanoseconds){
		
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		if(world.player.isDead()){
			renderer.renderGameOver();
			
		}
		else{
			// Update game logic
			world.update(deltaTime);
			
			// Draw
			GraphicsContext gc = this.getGraphicsContext2D();
			gc.clearRect(0, 0, getWidth(), getHeight());
			renderer.render(world);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		
		if(e.getCode() == KeyCode.UP){
			world.player.requestJump();
		}
		else if(e.getCode() == KeyCode.RIGHT){
			world.player.requestRight(true);
		}
		else if(e.getCode() == KeyCode.LEFT){
			world.player.requestLeft(true);
		}
		else if(e.getCode() == KeyCode.ESCAPE){
			Platform.exit();
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		
		if(e.getCode() == KeyCode.RIGHT){
			world.player.requestRight(false);
		}
		else if(e.getCode() == KeyCode.LEFT){
			world.player.requestLeft(false);
		}
	}
	
}

