package collisionDetection.handeler;

import collisionDetection.DynamicObject;

public interface BoundsCollisionListener {
	public void collisionOccured(DynamicObject object, int ceiling);
}
