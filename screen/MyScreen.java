package screen;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

public class MyScreen extends Canvas{

	private AnimationTimer timer;
	
	public MyScreen(int width, int height){
		super(width, height);
		
		timer = new AnimationTimer(){
			@Override
			public void handle(long nanoseconds){
				tick(nanoseconds);
			}
		};
		
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
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
	
	public void tick(long nanoseconds){
		
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
}
