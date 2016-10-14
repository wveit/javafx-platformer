package platformer.game;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.util.Duration;
import platformer.engine.screen.MyScreen;

public class GameScreen extends MyScreen{
	
	
	private World world;
	private Renderer renderer;
	private MediaPlayer mediaPlayer;
	
	private long lastNanoseconds = 0;
	private int logicFPS = 60;
	private double logicAccumulator = 0;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		world = new World("volcano_level.lvl");
		renderer = new Renderer(this.getGraphicsContext2D(), width, height);
		
		startGameMusic();
	}
	
	public void startGameMusic(){
		File file = new File("platformer/assets/airwolf.wav");
		if(file.exists()){			
			Media media = null;
			try{
				media = new Media("file:" + file.getAbsolutePath());
			}catch(Exception e){
				System.out.println("Exception while loading audio.");
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
			System.out.println("could not find audio file.");
		}
	}

	@Override
	public void tick(long nanoseconds){
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		System.out.println(deltaTime);
		
		for(int i = 0; i < 1000; i++){
			Math.sqrt(i);
		}
		
		if(world.player.isDead()){
			renderer.renderGameOver();
			
		}
		else{
			
			logicAccumulator += deltaTime;
			while(logicAccumulator >= 1.0 / logicFPS){
				// Update game logic
				world.update(1.0 / logicFPS);
				
				logicAccumulator -= 1.0 / logicFPS;
			}


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
		else if(e.getCode() == KeyCode.R){
			if(world.player.isDead()){
				world = new World("volcano_level.lvl");
			}
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

