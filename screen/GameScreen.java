package screen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import physics.Collision;
import shape.Rectangle;
import sprite.SpriteAnimator;
import physics.CollisionInfo;

public class GameScreen extends MyScreen{
	
	private Rectangle ninjaRect;
	private SpriteAnimator ninjaAnimator;
	private double gravity = 1000;
	private double vX = 0;
	private double vY = 0;
	
	boolean jumpRequest = false;
	boolean leftRequest = false;
	boolean rightRequest = false;
	double moveSpeed = 500;
	double jumpSpeed = 750;
	
	private Rectangle[] platformList = new Rectangle[3];
	
	private long lastNanoseconds = 0;
	
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
		
		platformList[0] = new Rectangle(0, getHeight() - 10, getWidth(), 10);
		platformList[1] = new Rectangle(0, getHeight() / 2, getWidth() / 3, 10);
		platformList[2] = new Rectangle(2 * getWidth() / 3, getHeight() / 2, getWidth() / 3, 10);
	}

	@Override
	public void tick(long nanoseconds){
		
		
		// Update Logic
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		if(jumpRequest){
			vY = -jumpSpeed;
			jumpRequest = false;
		}
		else{
			vY += gravity * deltaTime;
		}
		
		vX = 0;
		if(leftRequest){
			vX -= moveSpeed;
		}
		if(rightRequest){
			vX += moveSpeed;
		}
		
		if(vX > 0.1)
			ninjaAnimator.setFlippedHorizontal(false);
		else if(vX < -0.1)
			ninjaAnimator.setFlippedHorizontal(true);
		
		ninjaRect.move(vX * deltaTime, vY * deltaTime);
		
		for(Rectangle rect : platformList){
			if(ninjaRect.overlaps(rect)){
				CollisionInfo ci = Collision.resolve(ninjaRect, rect);
				if(ci.getX() != 0){
					vX = 0;
					ninjaRect.move(ci.getX() * ci.getDistance(), 0);
				}
				else if(ci.getY() != 0){
					vY = 0;
					ninjaRect.move(0, ci.getY() * ci.getDistance());
				}
			}
		}
		
		
		
		
		// Draw
		GraphicsContext gc = this.getGraphicsContext();
		gc.clearRect(0, 0, getWidth(), getHeight());
				
		ninjaAnimator.draw(gc, ninjaRect, nanoseconds, (long)(500 * 1000000));
		
		for(Rectangle rect : platformList){
			gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		
		if(e.getCode() == KeyCode.UP){
			jumpRequest = true;
		}
		else if(e.getCode() == KeyCode.RIGHT){
			rightRequest = true;
		}
		else if(e.getCode() == KeyCode.LEFT){
			leftRequest = true;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		
		if(e.getCode() == KeyCode.RIGHT){
			rightRequest = false;
		}
		else if(e.getCode() == KeyCode.LEFT){
			leftRequest = false;
		}
	}
	
}

