package screen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameScreen extends MyScreen{
	
	private float x;
	private float y;
	private Image ninja;
	private boolean toTheRight = true;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		x = 0;
		y = 0;
		ninja = new Image("myninja1.png", 600, 200, true, false);
		
	}

	@Override
	public void tick(long nanoseconds){
		
		GraphicsContext gc = this.getGraphicsContext();
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		long nanosecondsPerFrame = 16000000 * 10;
		int frame = (int)(nanoseconds / nanosecondsPerFrame % 4);
		int start = frame * 150;
		int end = start + 150;
		
		
		if(toTheRight)
			gc.drawImage(ninja, start, 0, 150, 200, x, y, 150, 200);
		else
			gc.drawImage(ninja, end, 0, -150, 200, x, y, 150, 200);
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		float movement = 10;
		
		if(e.getCode() == KeyCode.UP)
			y -= movement;
		else if(e.getCode() == KeyCode.DOWN)
			y += movement;
		else if(e.getCode() == KeyCode.RIGHT){
			x += movement;
			toTheRight = true;
		}
		else if(e.getCode() == KeyCode.LEFT){
			x -= movement;
			toTheRight = false;
		}
		
	}
	
}

