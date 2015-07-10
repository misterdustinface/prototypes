package devan.geometry;

public class Interval {

	final public static float POSITIVE_INFINITY = Float.POSITIVE_INFINITY;
	final public static float NEGATIVE_INFINITY = Float.NEGATIVE_INFINITY;
	
	private float upperBound;
	private float lowerBound;
	
	public Interval(){
		upperBound = POSITIVE_INFINITY;
		lowerBound = NEGATIVE_INFINITY;
	}
	public Interval(float UPPER_BOUND, float LOWER_BOUND){
		upperBound = UPPER_BOUND;
		lowerBound = LOWER_BOUND;
	}
	
	public void raise(float amount){
		upperBound += amount;
		lowerBound += amount;
	}
	public void lower(float amount){
		upperBound -= amount;
		lowerBound -= amount;
	}
	
	public void raiseCeiling(float amount){ upperBound += amount; }
	public void lowerCeiling(float amount){ upperBound -= amount; }
	public void raiseFloor(float amount){   lowerBound += amount; }
	public void lowerFloor(float amount){   lowerBound -= amount; }
	
	public float getUpperBound(){ 	return upperBound; }
	public float getCeiling(){		return upperBound; }
	public float getLowerBound(){ 	return lowerBound; }
	public float getFloor(){		return lowerBound; }
	
	public Interval copy(){ return new Interval(upperBound, lowerBound); }
}
