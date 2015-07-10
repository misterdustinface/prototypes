package devan.geometry;

/**
 * @author dustin
 */
public class Sector {
	private double 	sectorAngleInRadians;
	private Vector 	center;
	
	public Sector(Vector vector, final double SECTOR_ANGLE_IN_RADIANS){
		center = vector.copy();
		sectorAngleInRadians = SECTOR_ANGLE_IN_RADIANS;
	}
	public Sector(final Circle circle, final float theta, final double SECTOR_ANGLE_IN_RADIANS){
		center = new Vector(circle.getCenterCopy());
		center.setMagnitude(circle.getRadius());
		center.setTheta(theta);
		sectorAngleInRadians = SECTOR_ANGLE_IN_RADIANS;
	}
	
	void setPosition(float x, float y){center.origin.set(x, y);}
	void setRadius(float r){center.setMagnitude(Math.abs(r));}
	
	Point 			getVertex(){	return center.origin;}
	public Point 	getVertexCopy(){return center.origin.copy();}
	public double 	getRadius(){	return center.getMagnitude();}
	public float 	getX(){			return center.getX();}
	public float 	getY(){			return center.getY();}
	
	public double 	getDirection(){ 			return center.getTheta(); }
	public double 	getSectorLengthInRadians(){ return sectorAngleInRadians; }
	public double 	getArcLength() { 			return sectorAngleInRadians * getRadius();}
	public double 	getArea(){					return sectorAngleInRadians/2 * Math.squared(getRadius());}
	
	public double getLeftAngleRelativeToSectorOrientation(){	return center.getTheta() - sectorAngleInRadians/2;}
	public double getRightAngleRelativeToSectorOrientation(){	return center.getTheta() + sectorAngleInRadians/2;}
	
	public Vector getOrientationVector(){
		return center;
	}
	public void setSectorLength(final double LENGTH_IN_RADIANS){
		sectorAngleInRadians = LENGTH_IN_RADIANS;
	}
	
	// FIXME?
	public boolean contains(Point point){
		return Math.distanceSquared(center.origin, point) <= Math.squared(getRadius())
			&& Math.thetaIsInRange(Math.theta(center.origin, point), (center.getTheta() - sectorAngleInRadians/2), (center.getTheta() + sectorAngleInRadians/2), sectorAngleInRadians);
	}
	// FIXME?
	public boolean contains(final float X, final float Y){
		return Math.distanceSquared(center.getX(), center.getY(), X, Y) <= Math.squared(getRadius())
			&& Math.thetaIsInRange(Math.theta(center.getX(), center.getY(), X, Y), (center.getTheta() - sectorAngleInRadians/2), (center.getTheta() + sectorAngleInRadians/2), sectorAngleInRadians);
	}
	
//function in_sector( x as integer, y as integer )
//	
//	`check for distance
//	if get_distance( x, y, sector.x, sector.y ) < sector.radius
//		angle# = wrapvalue( atanfull( sector.x - x, sector.y - y ) )
//		
//		`check angle
//		if abs( angle# - sector.angle ) < sector.size
//			in = 1
//		endif 
//		
//		`Check for angle wrap around (0 becomes 360 etc.)
//		if sector.angle < sector.size
//			if abs( angle# - sector.angle ) > 360 - sector.size
//				in = 1
//			endif
//		endif
//		if sector.angle > 360 - sector.size
//			if abs( angle# - sector.angle ) > 360 - sector.size
//				in = 1
//			endif
//		endif
//	endif
//	
//endfunction in
	
	@Override
	public int hashCode(){
		return center.hashCode();
	}
	
	public String toString(){
		return "SECTOR{" + "Center-" + center.toString() + " Radius: " + getRadius() + " Direction: " +  getDirection() + " Sector Length: " + getSectorLengthInRadians() + " Arc Length: " + getArcLength() + "}";
	}
}
