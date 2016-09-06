package physics;

import shape.Rectangle;

public class Collision {
	public static CollisionInfo resolve(Rectangle r1, Rectangle r2){
		CollisionInfo []infos = new CollisionInfo[4];
		
		infos[0] = new CollisionInfo(-1, 0, r1.getX() + r1.getWidth() - r2.getX());
		infos[1] = new CollisionInfo(1, 0, r2.getX() + r2.getWidth() - r1.getX());
		infos[2] = new CollisionInfo(0, -1, r1.getY() + r1.getHeight() - r2.getY());
		infos[3] = new CollisionInfo(0, 1, r2.getY() + r2.getHeight() - r1.getY());
		
		return smallestCollisionInfo(infos);
	}

	private static CollisionInfo smallestCollisionInfo(CollisionInfo[] infos){
		int smallest = 0;
		for(int i = 1; i < infos.length; i++){
			if(infos[i].getDistance() < infos[smallest].getDistance()){
				smallest = i;
			}
		}
		
		return infos[smallest];
	}
}
