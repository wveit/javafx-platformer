package screen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import shape.Rectangle;
import sprite.SpriteAnimator;

public class GameScreen extends MyScreen{
	
	private Rectangle ninjaRect;
	private SpriteAnimator ninjaAnimator;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		ninjaRect = new Rectangle(width / 2, height / 2, 150, 200);
		
		Image img = new Image("myninja1.png", 600, 200, true, false);
		ninjaAnimator = new SpriteAnimator(img);
		ninjaAnimator.addMode();
		ninjaAnimator.addRectToMode(0, new Rectangle(0, 0, 150, 200));
		ninjaAnimator.addRectToMode(0, new Rectangle(150, 0, 150, 200));
		ninjaAnimator.addRectToMode(0, new Rectangle(300, 0, 150, 200));
		ninjaAnimator.addRectToMode(0, new Rectangle(450, 0, 150, 200));
	}

	@Override
	public void tick(long nanoseconds){
		
		GraphicsContext gc = this.getGraphicsContext();
		gc.clearRect(0, 0, getWidth(), getHeight());
		

		

		
		ninjaAnimator.draw(gc, ninjaRect, nanoseconds, (long)(500 * 1000000));
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		float movement = 10;
		
		if(e.getCode() == KeyCode.UP){
			ninjaRect.move(0,  -movement);
		}
		else if(e.getCode() == KeyCode.DOWN){
			ninjaRect.move(0,  movement);
		}
		
		else if(e.getCode() == KeyCode.RIGHT){
			ninjaRect.move(movement, 0);
			if(ninjaAnimator.isFlippedHorizontal())
				ninjaAnimator.flipHorizontal();
		}
		else if(e.getCode() == KeyCode.LEFT){
			ninjaRect.move(-movement, 0);
			if(!ninjaAnimator.isFlippedHorizontal())
				ninjaAnimator.flipHorizontal();
		}
		
	}
	
}

