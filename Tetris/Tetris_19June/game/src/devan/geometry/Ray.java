package devan.geometry;

/**
 * @author dustin
 */
public class Ray {

	public 	Point 	origin;
	private double 	theta; // θ IN RADIANS
	
	public Ray(){
		origin = new Point(0,0);
		theta = 0;
	}
	
	public Ray(Point position, double THETA_IN_RADIANS){
		origin = position;
		theta  = devan.geometry.Math.normalizeAngle(THETA_IN_RADIANS);
	}
	
	public float getX(){ return origin.x; }
	public float getY(){ return origin.y; }
	
	public void setTheta(double THETA_IN_RADIANS){
		theta = devan.geometry.Math.normalizeAngle(THETA_IN_RADIANS);
	}
	
	/**
	 * @return θ
	 */
	public double getTheta(){
		return theta;
	}
	
	public void rotate(double THETA_IN_RADIANS){
		theta = devan.geometry.Math.normalizeAngle(theta + THETA_IN_RADIANS);
	}

	
}
