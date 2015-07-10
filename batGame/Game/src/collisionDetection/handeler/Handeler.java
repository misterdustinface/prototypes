package collisionDetection.handeler;

import java.util.LinkedList;

import org.hamcrest.core.IsInstanceOf;

import collisionDetection.Bat;
import collisionDetection.DynamicObject;

public class Handeler {
	
	private LinkedList<BoundsCollisionListener> batWithCeilingListeners;
	private LinkedList<BoundsCollisionListener> batWithFloorListeners;
	private LinkedList<BatWithDynamicCollisionListener> batWithDynamicListeners;
	private LinkedList<BoundsCollisionListener> dynamicWithCeilingListeners;
	private LinkedList<BoundsCollisionListener> dynamicWithFloorListeners;


	public Handeler() {
		batWithCeilingListeners = new LinkedList<BoundsCollisionListener>();
		batWithFloorListeners = new LinkedList<BoundsCollisionListener>();
		batWithDynamicListeners = new LinkedList<BatWithDynamicCollisionListener>();
		dynamicWithCeilingListeners = new LinkedList<BoundsCollisionListener>();
		dynamicWithFloorListeners = new LinkedList<BoundsCollisionListener>();
	}
	
	public void addBatWithCeilingListener(BoundsCollisionListener listener) {
		batWithCeilingListeners.add(listener);
	}
	
	public void addBatWithFloorListener(BoundsCollisionListener listener) {
		batWithFloorListeners.add(listener);
	}
	
	public void addBatWithDynamicListener(BatWithDynamicCollisionListener listener) {
		batWithDynamicListeners.add(listener);
	}
	
	public void addDynamicWithCeilingListener(BoundsCollisionListener listener) {
		dynamicWithCeilingListeners.add(listener);
	}
	
	public void addDynamicWithFloorListener(BoundsCollisionListener listener) {
		dynamicWithFloorListeners.add(listener);
	}
	
	public void removeBatWithCeilingListener(BoundsCollisionListener listener) {
		batWithCeilingListeners.remove(listener);
	}
	
	public void removeBatWithFloorListener(BoundsCollisionListener listener) {
		batWithFloorListeners.remove(listener);
	}
	
	public void removeBatWithDynamicListener(BatWithDynamicCollisionListener listener) {
		batWithDynamicListeners.remove(listener);
	}
	
	public void removeDynamicWithCeilingListener(BoundsCollisionListener listener) {
		dynamicWithCeilingListeners.remove(listener);
	}
	
	public void removeDynamicWithFloorListener(BoundsCollisionListener listener) {
		dynamicWithFloorListeners.remove(listener);
	}
	
	
	public void batWithDynamic(Bat bat, DynamicObject object) {
		for(BatWithDynamicCollisionListener batWithDynamicListener : batWithDynamicListeners) {
			batWithDynamicListener.collisionOccured(bat, object);
		}
	}
	
	public void dynamicWithCeiling(DynamicObject object, int ceiling) {
		if(object instanceof Bat) {
			for(BoundsCollisionListener batWithCeilingListener : batWithCeilingListeners) {
				batWithCeilingListener.collisionOccured(object, ceiling);
			}
		} else {
			for(BoundsCollisionListener objectWithCeilingListener : batWithFloorListeners) {
				objectWithCeilingListener.collisionOccured(object, ceiling);
			}
		}
	}
	
	public void dynamicWithFloor(DynamicObject object, int floor) {
		if(object instanceof Bat) {

			for(BoundsCollisionListener batWithFloorListener : batWithFloorListeners) {
				batWithFloorListener.collisionOccured(object, floor);
			}
		} else {
			for(BoundsCollisionListener objectWithFloorListener : batWithFloorListeners) {
				objectWithFloorListener.collisionOccured(object, floor);
			}
		}
	}
	
}
