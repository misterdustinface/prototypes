package spikes.movement;

import com.badlogic.gdx.math.Rectangle;

public class Bat {
	Rectangle bounds;
	float xVelocity;
	float minXVelocity;
	//float maxXVelocity;
	float yVelocity;
	float gravity;
	boolean dive = false;
	
	Bat(float x, float y, float width, float height) {
		bounds = new Rectangle(x, y, width, height);
		minXVelocity = Globals.BAT_Velocity_X;
		//maxXVelocity = Globals.MAX_X_VELOCITY;
		glide();
		xVelocity = 0;
	}
	
	public void update() {
		if(xVelocity < minXVelocity) {
			xVelocity = minXVelocity;
		}
//		else if(xVelocity > maxXVelocity){
//			xVelocity = maxXVelocity;
//		}
		bounds.x  += xVelocity;
		bounds.y  += yVelocity;
		yVelocity += gravity;
	}

	public boolean isCollidingWith(Rectangle r) {
		return bounds.overlaps(r);
	}
	
	public float getX() {
		return bounds.x;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isBellowLine(float flatline) {
		return bounds.y < flatline;
	}

	public boolean isAboveLine(float flatline) {
		return bounds.y + bounds.height > flatline;
	}
	
	public void flap() {
		dive = false;
		yVelocity = Globals.BAT_Velocity_Flap;
		gravity = Globals.BAT_Gravity_Flap;
	}
	
	public void glide() {
		dive = false;
		yVelocity = Globals.BAT_Velocity_Glide;
		gravity = Globals.BAT_Gravity_Glide;
	}
	
	public void dive() {
		dive = true;
		gravity = Globals.BAT_Gravity_Dive;
	}
	
	public void speedBoost(float boost) {
		xVelocity += boost;
	}
}
