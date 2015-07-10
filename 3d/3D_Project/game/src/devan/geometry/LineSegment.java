package devan.geometry;

/**
 * @author dustin
 */
public class LineSegment {

	Point a;
	Point b;
	
	public LineSegment(Point A, Point B){
		a = A; b = B;
	}
	public LineSegment(LineSegment other){
		a = other.a.copy();
		b = other.b.copy();
	}
	
	public float length(){
		return Math.distance(a, b);
	}
	
	public Point midpoint(){
		return Math.midpoint(a, b);
	}
	
	public Point getCopyOfPointA(){
		return a.copy();
	}
	public Point getCopyOfPointB(){
		return b.copy();
	}
	
	/**
	 * @param percent 0.0 - 1.0
	 * @return Point within range from point A to point B
	 */
	public Point generatePointAt(double percent){
		//percent = percent > 1.0f ? (percent < 0.0f ? 0.0f : percent) : 1.0f;
		double offset = percent * length();
		double theta  = Math.theta(a, b);
		return new Point(a.x + (float)(offset*java.lang.Math.cos(theta)), a.y + (float)(offset*java.lang.Math.sin(theta)));
	}
	
	public static LineSegment generateLineWithOrientation(Point center, double theta, float length){
		float xOff = (float)java.lang.Math.cos(theta) * length/2;
		float yOff = (float)java.lang.Math.sin(theta) * length/2;
		return new LineSegment(new Point(center.x - xOff, center.y - yOff), new Point(center.x + xOff, center.y + yOff));
	}
	
	// TODO is this correct?
	public boolean isPointOnLine(Point P){
		return Math.determinant(P, this) == 0;
	}
	
//	void shift(float x, float y){
//		a.x += x; b.x += x;
//		a.y += y; b.y += y;
//	}
//	
//	void scale(double percent){
//		double theta = Math.theta(a, b);
//		float halfLength = length()/2;
//		resize(	(float)(java.lang.Math.cos(theta) * halfLength * percent),
//				(float)(java.lang.Math.sin(theta) * halfLength * percent));
//	}
//	
//	void rotate(int degrees){
//		rotate(java.lang.Math.toRadians(degrees));
//	}
//	
//	void rotate(double theta){
//		theta += Math.theta(a, b);
//		float halfLength = length()/2;
//		resize(	(float)(java.lang.Math.cos(theta) * halfLength),
//				(float)(java.lang.Math.sin(theta) * halfLength));
//	}
//	
//	/**
//	 * @param percent 0.0 - 1.0
//	 * @return Line
//	 */
//	Line split(double percent){
//		Point temp = b;
//		b = pointAt(percent);
//		return new Line(b, temp);
//	}
//	
//	/**
//	 * Offsets from center
//	 * @param xOff
//	 * @param yOff
//	 */
//	private void resize(float xOff, float yOff){
//		Point midpoint = midpoint();
//		
//		if(a.x > b.x){	a.x = midpoint.x + xOff; b.x = midpoint.x - xOff;}
//		else{			a.x = midpoint.x - xOff; b.x = midpoint.x + xOff;}
//		
//		if(a.y > b.y){	a.y = midpoint.y + yOff; b.y = midpoint.y - yOff;}
//		else{			a.y = midpoint.y - yOff; b.y = midpoint.y + yOff;}
//	}
//	
}
