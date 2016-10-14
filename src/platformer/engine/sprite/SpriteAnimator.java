package platformer.engine.sprite;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import platformer.engine.shape.Rectangle;


public class SpriteAnimator {
	Image img;
	ArrayList< ArrayList<Rectangle> > rectMatrix = new ArrayList< ArrayList<Rectangle> >();;
	int currentMode = -1;
	int currentRect = -1;
	boolean flippedHorizontal = false;
	boolean flippedVertical = false;
	boolean showBox = false;
	
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
		if(currentRect == -1)
			currentRect = 0;
	}

	public void setMode(int mode){
		this.currentMode = mode;
		
		if(rectMatrix.get(mode).isEmpty())
			currentRect = -1;
		else
			currentRect = 0;
	}
	
	public void setRect(int rect){
		this.currentRect = rect;
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
		draw(gc, dest, currentMode, currentRect);
	}

	
	public void draw(GraphicsContext gc, Rectangle destRect, int modeNum, int rectNum){
		Rectangle srcRect = rectMatrix.get(modeNum).get(rectNum);
		
		if(showBox)
			gc.strokeRect(destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
		
		if(!flippedHorizontal && !flippedVertical){
			gc.drawImage(img, srcRect.minX(), srcRect.minY(), srcRect.width(), srcRect.height(), destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
		}
		else if(flippedHorizontal && !flippedVertical){
			gc.drawImage(img, srcRect.maxX(), srcRect.minY(), -srcRect.width(), srcRect.height(), destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
		}
		else if(!flippedHorizontal && flippedVertical){
			gc.drawImage(img, srcRect.minX(), srcRect.maxY(), srcRect.width(), -srcRect.height(), destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
		}
		else{
			gc.drawImage(img, srcRect.maxX(), srcRect.maxY(), -srcRect.width(), -srcRect.height(), destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
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
	
	public void setFlippedHorizontal(boolean isFlipped){
		flippedHorizontal = isFlipped;
	}
	
	public void setFlippedVertical(boolean isFlipped){
		flippedVertical = isFlipped;
	}
	
	public void showBox(boolean show){
		showBox = show;
	}
	
	public int getCurrentMode(){ return currentMode; }
	public int getCurrentRect(){ return currentRect; }
}
