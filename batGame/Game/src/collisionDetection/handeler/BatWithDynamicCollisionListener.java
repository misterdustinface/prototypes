package collisionDetection.handeler;

import collisionDetection.Bat;
import collisionDetection.DynamicObject;

public interface BatWithDynamicCollisionListener {
	public void collisionOccured(Bat bat, DynamicObject object);
}
