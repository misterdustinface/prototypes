package devan.geometry;

import devan.geometry.Math;

/**
 * @author dustin
 */
final public class CollisionChecker {
	private CollisionChecker(){};
	
	// Dustin Algorithm
	public static boolean intersects(Circle A, Circle B){
		return Math.distanceSquared(A.getCenter(), B.getCenter()) <= Math.squared(A.getRadius()) + Math.squared(B.getRadius());
	}
	
	// Triangle method from http://stackoverflo1w.com/questions/1073336/circle-line-collision-detection
	public static boolean intersects(Circle C, LineSegment L){
		return Math.abs( Math.determinant(C.getCenter(), L) ) / Math.length(L) <= C.getRadius(); // this seems faster somehow. Probably function call overhead on the other?
		//return Math.abs( Math.squared(Math.determinant(C.center(), L)) ) / Math.lengthSquared(L) <= Math.squared(C.radius());
	}
	
	/**
	 * Simplified from libGDX Intersection.1
	 * @param LineSegment
	 * @param LineSegment
	 * @return True if lines intersect at a point
	 */
	public static boolean intersects(LineSegment J, LineSegment Q){
		return Math.determinant(J,Q) != 0; // TODO add more stuff. Only == 0 if parallel.
	}
	
	// Dustin Algorithm
	public static boolean intersects(Ray R, Circle C){
		return intersects(R, LineSegment.generateLineWithOrientation(C.getCenter(), Math.getPerpendicularTheta(R.getTheta()), C.getDiameter()));
	}
	
	// Dustin Algorithm
	public static boolean intersects(Ray R, LineSegment L){
		return Math.thetaIsInRange(R.getTheta(), Math.theta(R.origin, L.a), Math.theta(R.origin, L.b), Math.PI);
	}
	
	// Dustin Algorithm
	public static boolean intersects(Vector V, Circle C){
		return Math.distanceSquared(V.origin, C.getCenter()) <= Math.squared(C.getRadius()) - Math.squared(V.getMagnitude()) 
			&& intersects(C, LineSegment.generateLineWithOrientation(C.getCenter(), Math.getPerpendicularTheta(V.getTheta()), C.getDiameter()));
	}
	
	// Dustin Algorithm
	public static boolean intersects(Vector V, LineSegment L){
		return Math.thetaIsInRange(V.getTheta(), Math.theta(V.origin, L.a), Math.theta(V.origin, L.b), Math.PI)
			&& Math.distance(V.origin, L) <= V.getMagnitude();
	}
	
//	// TODO
//	public static Point getPointOfIntersection(Ray R, Line L){
//
////		Math.theta(L.a, L.b);
////		R.x() + X_MAG * java.lang.Math.cos(R.theta);
////		R.y() + Y_MAG * java.lang.Math.sin(R.theta);
//		
//		return L.midpoint();
//	}
	
	// Dustin Algorithm
	public static boolean intersects(Sector S, Circle C){
		return intersects(S, LineSegment.generateLineWithOrientation(C.getCenter(), Math.getPerpendicularTheta(S.getDirection()), C.getDiameter()));
	}
	
	// Dustin Algorithm
	public static boolean intersects(Sector S, LineSegment L){
		
		if(Math.distance(S.getVertex(), L) <= S.getRadius()){
			double thetaFromSectorCenterToLinePointA = Math.theta(S.getVertex(), L.a);
			double thetaFromSectorCenterToLinePointB = Math.theta(S.getVertex(), L.b);
			return Math.thetaIsInRange(S.getDirection(), thetaFromSectorCenterToLinePointA, thetaFromSectorCenterToLinePointB, Math.PI)
	//			|| Math.thetaIsInRange(S.start(), thetaFromSectorCenterToLinePointA, thetaFromSectorCenterToLinePointB, Math.PI)
	//			|| Math.thetaIsInRange(S.end(), thetaFromSectorCenterToLinePointA, thetaFromSectorCenterToLinePointB, Math.PI)
				|| Math.thetaIsInRange(thetaFromSectorCenterToLinePointA, S.getLeftAngleRelativeToSectorOrientation(), S.getRightAngleRelativeToSectorOrientation(), S.getSectorLengthInRadians())
				|| Math.thetaIsInRange(thetaFromSectorCenterToLinePointB, S.getLeftAngleRelativeToSectorOrientation(), S.getRightAngleRelativeToSectorOrientation(), S.getSectorLengthInRadians());
		}
		return false;
	}
	
}
