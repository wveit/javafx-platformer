package shape;


public class Rectangle {
	
	private double x, y, width, height;
	
	public Rectangle(){
		x = y = width = height = 0;
	}
	
	public Rectangle(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setSize(double width, double height){
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(double width){
		this.width = width;
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	public void move(double dx, double dy){
		this.x += dx;
		this.y += dy;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public boolean overlaps(Rectangle other){
		if(x > other.x + other.width)
			return false;
		if(other.x > x + width)
			return false;
		if(y > other.y + other.height)
			return false;
		if(other.y > y + height)
			return false;
		
		return true;
	}
	
}
