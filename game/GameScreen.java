package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import renderer.Renderer;
import screen.MyScreen;

public class GameScreen extends MyScreen{
	
	
	private World world;
	private Renderer renderer;
	
	private long lastNanoseconds = 0;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		world = new World(width, height);
		renderer = new Renderer(this.getGraphicsContext2D(), this);
	}

	@Override
	public void tick(long nanoseconds){
		
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		// Update game logic
		world.update(deltaTime);
		
		// Draw
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		renderer.render(world);		
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		
		if(e.getCode() == KeyCode.UP){
			world.player.requestJump();
		}
		else if(e.getCode() == KeyCode.RIGHT){
			world.player.requestRight(true);;
		}
		else if(e.getCode() == KeyCode.LEFT){
			world.player.requestLeft(true);
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

