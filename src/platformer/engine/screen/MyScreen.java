package platformer.engine.screen;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class MyScreen extends Canvas{

	private Timeline timeline;
	
	public MyScreen(int width, int height){
		super(width, height);
		
		Duration d = Duration.millis(16);
		timeline = new Timeline(new KeyFrame(d, e->{tick((long)(System.currentTimeMillis()*1000000));}));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
            public void handle(KeyEvent e){
               keyPressed(e);               
            }
        });
		
		setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
            public void handle(KeyEvent e){
               keyReleased(e);               
            }
        });
		

	}
	

	
	public void start(){
		timeline.play();
	}
	
	public void stop(){
		timeline.stop();
	}
	
	public void tick(long nanoseconds){
		
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
}
