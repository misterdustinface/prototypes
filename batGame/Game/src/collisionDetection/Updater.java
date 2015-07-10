package collisionDetection;

import java.util.List;
import java.util.ListIterator;

import collisionDetection.handeler.Handeler;

import com.badlogic.gdx.Gdx;

public class Updater {
	
	public static void update(DynamicObject dynamic, Buffer caveUpper, Buffer caveLower, Handeler collisionHandeler) {		
		Buffer upperBounds = caveUpper.subBuffer(dynamic.getxOffset(), dynamic.getCollisionBoundsEndIndex());
		Buffer lowerBounds = caveLower.subBuffer(dynamic.getxOffset(), dynamic.getCollisionBoundsEndIndex());

		Buffer currentUpper = new Buffer();
		Buffer currentLower = new Buffer();
				
		initializeCurrent(dynamic, upperBounds, lowerBounds, currentUpper, currentLower);
		checkCurrentCollisions(dynamic, currentUpper, currentLower, collisionHandeler);
		dynamic.applyVelocitySlope();
		
		while(!upperBounds.isEmpty()) {
			updateCurrent(upperBounds, lowerBounds, currentUpper, currentLower);
			dynamic.applyVelocitySlope();
			checkCurrentCollisions(dynamic, currentUpper, currentLower, collisionHandeler);
		}
				
		dynamic.applyGravity();
	}

	public static void updateAll(Bat bat, List<DynamicObject> objects, Buffer caveUpper, Buffer caveLower, Handeler collisionHandeler) {
		update(bat, caveUpper, caveLower, collisionHandeler);

		ListIterator<DynamicObject> i = objects.listIterator(); 
		while (i.hasNext())
		{
			DynamicObject object = i.next();
			if(object.getxOffset() < 0) {
				i.remove();
				Gdx.app.log("Remove", "hit thing");

			} else {
				update(object, caveUpper, caveLower, collisionHandeler);
				object.setxOffset(object.getxOffset() + object.getXVeloctiy() - bat.getXVeloctiy());
				if(bat.isIntersecting(object)) {
					collisionHandeler.batWithDynamic(bat, object);
				}
			}
		}
		
		removeUsedBounds(bat, caveUpper, caveLower);
	}
	
	private static void removeUsedBounds(DynamicObject dynamic, Buffer caveUpper,
			Buffer caveLower) {
		for(int i = 0; i < dynamic.getXVeloctiy(); ++i) {
			caveUpper.removeFirst();
			caveLower.removeFirst();
		}
	}

	private static void initializeCurrent(DynamicObject dynamic, Buffer upperBounds, Buffer lowerBounds, Buffer currentUpper, Buffer currentLower) {
		currentUpper.readFrom(dynamic.getWidth(), upperBounds);
		currentLower.readFrom(dynamic.getWidth(), lowerBounds);
	}

	private static void updateCurrent(Buffer upperBounds, Buffer lowerBounds, Buffer currentUpper, Buffer currentLower) {
		currentUpper.streamFrom(upperBounds);
		currentLower.streamFrom(lowerBounds);
	}

	private static void checkCurrentCollisions(DynamicObject dynamic, Buffer currentUpper, Buffer currentLower, Handeler collisionHandeler) {
		for(int check = 0; check < dynamic.getWidth(); ++check) {
			if(dynamic.isAboveBound(currentUpper.get(check))) {
				collisionHandeler.dynamicWithCeiling(dynamic, currentUpper.get(check));
				break;
			} else if(dynamic.isBellowBound(currentLower.get(check))) {
				collisionHandeler.dynamicWithFloor(dynamic, currentUpper.get(check));
				break;
			}
		}
	}
}
