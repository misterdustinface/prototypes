package devan.geometry;

/**
 * @author dustin
 */
public class Point {

	public float x;
	public float y;
	public float z;
	
	public Point(float X, float Y, float Z){x = X; y = Y; z = Z;}
	public Point(float X, float Y){x = X; y = Y; z = 0;}
	public Point copy(){return new Point(x,y);}
	
	public void set(float X, float Y){x = X; y = Y;}
	public void set(Point other){x = other.x; y = other.y; z = other.z;}
	public void shift(float X, float Y){x += X; y += Y;}
	
	public boolean equals(Point other){return x == other.x & y == other.y;}
	
	@Override
	public int hashCode(){
		//http://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way
		//Szudzik's function:
		//a >= b ? a * a + a + b : a + b * b;  where a, b >= 0
		int tx = Float.floatToIntBits(x);
		int ty = Float.floatToIntBits(y);
		return tx >= ty ? (tx * tx + tx + ty) : (tx + ty * ty);
	}
	
	public String toString(){
		return "Point[x: " + x + " y: " + y + "]";
	}
}
