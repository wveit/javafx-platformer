package sprite;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import shape.Rectangle;


public class SpriteAnimator {
	Image img;
	ArrayList< ArrayList<Rectangle> > rectMatrix = new ArrayList< ArrayList<Rectangle> >();;
	int currentMode = -1;
	int currentRect = -1;
	boolean flippedHorizontal = false;
	boolean flippedVertical = false;
	
	public SpriteAnimator(Image spritesheet){
		img = spritesheet;
	}
		
	public void addMode(){
		rectMatrix.add(new ArrayList<Rectangle>());
		if(currentMode == -1)
			currentMode = 0;
	}
	
	public void addRectToMode(int mode, Rectangle rect){
		rectMatrix.get(mode).add(rect);
	}

	public void setMode(int mode){
		this.currentMode = mode;
		
		if(rectMatrix.get(mode).isEmpty())
			currentRect = -1;
		else
			currentRect = 0;
	}
	
	public void flipHorizontal(){
		flippedHorizontal = !flippedHorizontal;
	}
	
	public void flipVertical(){
		flippedVertical = !flippedVertical;
	}
	
	public void advanceAnimation(){
		currentRect++;
		if(rectMatrix.get(currentMode).size() <= currentRect){
			if(rectMatrix.get(currentMode).isEmpty()){
				currentRect = -1;
			}
			else{
				currentRect = 0;
			}
		}
	}

	public void draw(GraphicsContext gc, Rectangle dest){
		draw(gc, dest, currentRect);
	}
	
	public void draw(GraphicsContext gc, Rectangle dest, long gameTimeNanos, long delayNanos){
		int numRects = getNumRects(currentMode);
		int rectNum = (int)(gameTimeNanos / delayNanos % numRects);
		
		draw(gc, dest, rectNum);
	}
	
	private void draw(GraphicsContext gc, Rectangle dest, int rectNum){
		Rectangle rect = rectMatrix.get(currentMode).get(rectNum);
		
		if(!flippedHorizontal && !flippedVertical){
			gc.drawImage(img, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), dest.getX(), dest.getY(), rect.getWidth(), rect.getHeight());
		}
		else if(flippedHorizontal && !flippedVertical){
			gc.drawImage(img, rect.getX() + rect.getWidth(), rect.getY(), -rect.getWidth(), rect.getHeight(), dest.getX(), dest.getY(), rect.getWidth(), rect.getHeight());
		}
		else if(!flippedHorizontal && flippedVertical){
			gc.drawImage(img, rect.getX(), rect.getY() + rect.getHeight(), rect.getWidth(), -rect.getHeight(), dest.getX(), dest.getY(), rect.getWidth(), rect.getHeight());
		}
		else{
			gc.drawImage(img, rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), -rect.getWidth(), -rect.getHeight(), dest.getX(), dest.getY(), rect.getWidth(), rect.getHeight());
		}
		
	}
	
	public int getNumModes(){
		return rectMatrix.size();
	}
	
	public int getNumRects(int mode){
		return rectMatrix.get(mode).size();
	}
	
	public boolean isFlippedHorizontal(){
		return flippedHorizontal;
	}
	
	public boolean isFlippedVertical(){
		return flippedVertical;
	}
}
