package devan.rendering;

import devan.core.Camera;
import devan.core.GameObject;
import devan.core.Wall;
import devan.core.actors.Actor;
import devan.geometry.Circle;
import devan.geometry.Interval;
import devan.geometry.LineSegment;
import devan.geometry.Math;
import devan.geometry.Point;

/**
 * @author dustin
 */
public class Perspective {
	private Perspective(){}
	
	final public static float FOCAL_DISTANCE = 20f;
	
	// THESE ARE TEMPORARY VARIABLES FOR DESIGNING THE NECESSARY MATHEMATICS FOR THE RENDERER
	private static Interval WALL_HEIGHT  		= new Interval(2.0f, 0.0f);
	private static Interval ACTOR_HEIGHT 		= new Interval(1.2f, 0.0f);
	private static Interval GAME_OBJECT_HEIGHT 	= new Interval(0.3f, 0.0f);
	// END OF THE TEMP VARIABLES
	
	/**
	 * The thing Dustin needs to render enemies. It works.
	 * @param observer
	 * @param observedCircle
	 * @return Line
	 */
	public static LineSegment getObservedCircleSubsection(final Point observer, final Circle observedCircle){
		return LineSegment.generateLineWithOrientation(observedCircle.getCenterCopy(), Math.getPerpendicularTheta(Math.theta(observer.x, observer.y, observedCircle.getX(), observedCircle.getY())), observedCircle.getDiameter());
	}

	public static float[] getObservedWallVertices(final Camera cam, final Wall wall, final int SCREEN_WIDTH, final int SCREEN_HEIGHT){
		// TODO get WALL_HEIGHT from the HEIGHT MAP
		return calculateVertices(cam, wall, WALL_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	public static float[] getObservedGameObjectVertices(final Camera cam, final GameObject gObject, final int SCREEN_WIDTH, final int SCREEN_HEIGHT){
		// TODO change WALL_HEIGHT to gObject Height Interval
		return calculateVertices(cam, Perspective.getObservedCircleSubsection(cam.getCenter(), gObject.getBoundingCircle()), GAME_OBJECT_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	public static float[] getObservedActorVertices(final Camera cam, final Actor actor, final int SCREEN_WIDTH, final int SCREEN_HEIGHT){
		// TODO change WALL_HEIGHT to actor Height Interval
		return calculateVertices(cam, Perspective.getObservedCircleSubsection(cam.getCenter(), actor.getBoundingCircle()), ACTOR_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	// could take my cam.position vector as a matrix.
	private static Point SCREEN_CENTER = new Point(0,0);
	private static Point VIEW_CAM_POSITION = new Point(0,0);
	private static float HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT;
	private static float distanceToA, distanceToB;
	private static double relativeAngleToA, relativeAngleToB;
	private static float viewScaleForA, viewScaleForB;
	private static float floorPositionA, floorPositionB;
	private static float zA_Ceiling, zA_Floor, zB_Ceiling, zB_Floor;
	private static float[] calculateVertices(final Camera cam, final LineSegment line, final Interval OBJECT_HEIGHT, final int SCREEN_WIDTH, final int SCREEN_HEIGHT){
		HALF_SCREEN_WIDTH 	= SCREEN_WIDTH/2;
		HALF_SCREEN_HEIGHT 	= SCREEN_HEIGHT/2;
		SCREEN_CENTER.set(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT);
		VIEW_CAM_POSITION.set(cam.getCenter().x + cam.xOffset, cam.getCenter().y + cam.yOffset);
		
		distanceToA 	= Math.distance(VIEW_CAM_POSITION, line.getCopyOfPointA());
		distanceToB 	= Math.distance(VIEW_CAM_POSITION, line.getCopyOfPointB());
		
		relativeAngleToA = Math.relativeTheta(cam.getPositionVector(), line.getCopyOfPointA());
		relativeAngleToB = Math.relativeTheta(cam.getPositionVector(), line.getCopyOfPointB());
		
		viewScaleForA = FOCAL_DISTANCE/distanceToA;
		viewScaleForB = FOCAL_DISTANCE/distanceToB;
		
//		// if item is behind our field of view, make view scale larger.
//		viewScaleForA = (java.lang.Math.abs(relativeAngleToA) > (cam.getFieldOfViewInRadians()/2)) ?
//						  	1 // TODO scale in some way
//						: FOCAL_DISTANCE/distanceToA;
//		
//		// if item is behind our field of view, make view scale larger.
//		viewScaleForB = (java.lang.Math.abs(relativeAngleToB) > (cam.getFieldOfViewInRadians()/2)) ?
//							1 // TODO scale in some way
//						: FOCAL_DISTANCE/distanceToB;
		
		floorPositionA 	= SCREEN_CENTER.x + ((float)relativeAngleToA / (cam.getFieldOfViewInRadians()/2)) * HALF_SCREEN_WIDTH;
		floorPositionB 	= SCREEN_CENTER.x + ((float)relativeAngleToB / (cam.getFieldOfViewInRadians()/2)) * HALF_SCREEN_WIDTH;
		zA_Ceiling 		= SCREEN_CENTER.y + (((OBJECT_HEIGHT.getCeiling() - cam.getZ() - cam.zOffset) * viewScaleForA) * HALF_SCREEN_HEIGHT);
		zA_Floor 		= SCREEN_CENTER.y + (((OBJECT_HEIGHT.getFloor()   - cam.getZ() - cam.zOffset) * viewScaleForA) * HALF_SCREEN_HEIGHT);
		zB_Floor 		= SCREEN_CENTER.y + (((OBJECT_HEIGHT.getFloor()   - cam.getZ() - cam.zOffset) * viewScaleForB) * HALF_SCREEN_HEIGHT);
		zB_Ceiling 		= SCREEN_CENTER.y + (((OBJECT_HEIGHT.getCeiling() - cam.getZ() - cam.zOffset) * viewScaleForB) * HALF_SCREEN_HEIGHT);
		
		return new float[]{	floorPositionA,zA_Ceiling, 	floorPositionA,zA_Floor, 
							floorPositionB,zB_Floor, 	floorPositionB,zB_Ceiling};
	}
	
}
