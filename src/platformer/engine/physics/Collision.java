package platformer.engine.physics;

import platformer.engine.shape.Rectangle;

public class Collision {
	public static CollisionInfo resolve(Rectangle r1, Rectangle r2){
		CollisionInfo []infos = new CollisionInfo[4];
		
		infos[0] = new CollisionInfo(-1, 0, r1.maxX() - r2.minX());
		infos[1] = new CollisionInfo(1, 0, r2.maxX() - r1.minX());
		infos[2] = new CollisionInfo(0, -1, r1.maxY() - r2.minY());
		infos[3] = new CollisionInfo(0, 1, r2.maxY() - r1.minY());
		
		int smallestIndex = smallestNonNegCollisionInfo(infos);
		return (smallestIndex >= 0 ? infos[smallestIndex] : new CollisionInfo());
	}
	
	public static CollisionInfo resolveUncollision(Rectangle r1, Rectangle r2){
		CollisionInfo []infos = new CollisionInfo[4];
		
		infos[0] = new CollisionInfo(-1, 0, r1.maxX() - r2.maxX());
		infos[1] = new CollisionInfo(1, 0, r2.minX() - r1.minX());
		infos[2] = new CollisionInfo(0, -1, r1.maxY() - r2.maxY());
		infos[3] = new CollisionInfo(0, 1, r2.minY() - r1.minY());
		
		int smallestIndex = smallestNonNegCollisionInfo(infos);
		return (smallestIndex >= 0 ? infos[smallestIndex] : new CollisionInfo());
	}
	
	private static int smallestNonNegCollisionInfo(CollisionInfo[] infos){
		int smallest = -1;
		for(int i = 0; i < infos.length; i++){
			if( infos[i].getDistance() >= 0 && ( smallest == -1 || infos[i].getDistance() < infos[smallest].getDistance() ) ){
				smallest = i;
			}
		}
		
		return smallest;
	}

}
