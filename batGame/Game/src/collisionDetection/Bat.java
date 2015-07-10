package collisionDetection;

public class Bat extends DynamicObject{
	public Bat(){
		super(Constants.WIDTH, Constants.HEIGHT);
	}
	
	public void flap() {
		setYVelocity(Constants.VELOCITY_FLAP);
		setGravity(Constants.GRAVITY_FLAP);
	}
	
	public void glide() {
		setYVelocity(Constants.VELOCITY_GLIDE);
		setGravity(Constants.GRAVITY_GLIDE);
	}
	
	public void dive() {
		setGravity(Constants.GRAVITY_DIVE);
	}	
}
