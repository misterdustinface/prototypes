package collisionDetection;


public class DynamicObject {
	private int width;
	private float height;
	private float lowerBound, upperBound;
	private int xVelocity;
	private float yVelocity;
	private float gravity;
	private float velocitySlope;  //For every one unit of x you move this much y
	private int xOffset;
	
	public DynamicObject(int width, float height){
		this.height = height;
		this.width = width;
		lowerBound = 0;
		xVelocity = 0;
		yVelocity = 0;
		gravity = 0;
		xOffset = 0;
	}
	
	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public void applyGravity() {
		yVelocity += gravity;
		calculateVelocitySlope();
	}
	
	public void applyVelocitySlope() {
		lowerBound += velocitySlope;
		upperBound += velocitySlope;
	}
	
	public void setYVelocity(float newYVelocity) {
		yVelocity = newYVelocity;
		calculateVelocitySlope();
	}
	
	public void setXVelocity(int newXVelocity) {
		xVelocity = newXVelocity;
		calculateVelocitySlope();
	}
	
	public void setGravity(float newGravity) {
		gravity = newGravity;
	}
	
	public boolean isBellowBound(float lowerBound) {
		return this.lowerBound < lowerBound;
	}

	public boolean isAboveBound(float uppderBound) {
		return this.upperBound > uppderBound;
	}
	
	public int getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getLowerBound() {
		return lowerBound;
	}
	
	public int getXVeloctiy() {
		return xVelocity;
	}
	
	public int getCollisionBoundsEndIndex () {
		return getxOffset() + getWidth() + getXVeloctiy();
	}
	
	private void calculateVelocitySlope() {
		if(xVelocity != 0) {		
			velocitySlope = yVelocity / xVelocity;
		} else {
			velocitySlope = yVelocity;
		}
	}
	
	public void setLowerBounds (float newLowerBound) {
		lowerBound = newLowerBound;
		upperBound = lowerBound + height;
	}
	
	public boolean isIntersecting (DynamicObject otherObject) {
		return xOffset < otherObject.xOffset + otherObject.width 
		    && xOffset + width > otherObject.xOffset 
		    && lowerBound < otherObject.lowerBound + otherObject.height 
		    && lowerBound + height > otherObject.lowerBound;
	}
}
