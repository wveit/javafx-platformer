package platformer.engine.physics;

public class CollisionInfo {
	private double x;
	private double y;
	private double distance;
	
	public CollisionInfo(){this.x = 0; this.y = 0; this.distance = 0;}
	public CollisionInfo(double x, double y, double distance){this.x = x; this.y = y; this.distance = distance;}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getDistance(){return distance;}
	
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setDistance(double distance){this.distance = distance;}
}
