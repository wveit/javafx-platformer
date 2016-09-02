package screen;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class MyScreen extends Group{

	private Canvas canvas;
	private GraphicsContext gc;
	private AnimationTimer timer;
	
	public MyScreen(int width, int height){
		canvas = new Canvas(width, height);
		this.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
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
		

	}
	
	public double getWidth(){
		return canvas.getWidth();
	}
	
	public double getHeight(){
		return canvas.getHeight();
	}
	
	public GraphicsContext getGraphicsContext(){
		return gc;
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
}
