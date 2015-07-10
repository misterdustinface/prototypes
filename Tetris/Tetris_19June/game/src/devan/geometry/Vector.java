package devan.geometry;

/**
 * @author dustin
 */
public class Vector extends Ray{

	private double magnitude;
	
	public Vector(Point position, double MAGNITUDE, double THETA_IN_RADIANS) {
		super(position, THETA_IN_RADIANS);
		magnitude = MAGNITUDE;
	}
	
	public Vector(Point position) {
		super(position, 0);
		magnitude = 0;
	}
	
	public Vector copy(){
		return new Vector(origin.copy(), magnitude, getTheta());
	}
	
	public double getMagnitude(){
		return magnitude;
	}
	public void scale(double factor){
		magnitude = magnitude * factor;
	}
	public void setMagnitude(double MAGNITUDE){
		magnitude = MAGNITUDE;
	}
	
//	final public static Vector dotProduct(Vector A, Vector B){
//		
//		return new Vector(new Point(B.x() - A.x(), B.y() - A.y()));
//	}

//	public void plus(Vector other){
//		
//	}
//	public void minus(Vector other){
//		
//	}
}
