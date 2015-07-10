package devan.geometry;

/**
 * @author dustin
 */
public class Circle {
	
	private float r;
	private Point center;
	
	public Circle(final float x, final float y, final float r){
		this.r = Math.abs(r);
		this.center = new Point(x,y);
	}
	public Circle(final Circle other){
		this.r = other.r;
		this.center = other.center.copy();
	}
	public Circle(final Point center, final float r){
		this.center = center;
		this.r = Math.abs(r);
	}
	
	void setPosition(float x, float y){center.x = x; center.y = y;}
	void setRadius(float r){this.r = Math.abs(r);}
	
	Point 			getCenter(){	return center;}
	public Point 	getCenterCopy(){return center.copy();}
	public float 	getRadius(){	return r;}
	public float 	getDiameter(){	return r*2;}
	public float 	getX(){			return center.x;}
	public float 	getY(){			return center.y;}
	public float 	getArea(){		return (float)(Math.PI) * Math.squared(r);}
	public float 	getCircumference(){return getDiameter() * (float)Math.PI;}
	
	public boolean contains(Point point){
		return Math.distanceSquared(center, point) <= Math.squared(r);
	}
	public boolean contains(final float X, final float Y){
		return Math.distanceSquared(center.x, center.y, X, Y) <= Math.squared(r);
	}
	
	@Override
	public int hashCode(){
		return center.hashCode();
	}
	
	public String toString(){
		return "CIRCLE{" + "Center-" + center.toString() + " Radius: " + r + "}";
	}
}
