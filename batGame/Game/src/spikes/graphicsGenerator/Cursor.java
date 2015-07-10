package spikes.graphicsGenerator;

public class Cursor {

	final float MOVE_SPEED;
	float x;
	float y;
	
	private boolean right;
	private boolean up;
	private boolean down;
	private boolean left;
	
	Cursor(float x, float y, final float MOVE_SPEED){
		this.MOVE_SPEED = MOVE_SPEED;
		this.x = x;
		this.y = y;
		right = up = down = left = false;
	}
	
	public void moveUp(){
		up = !up;
	}
	public void moveDown(){
		down = !down;
	}
	public void moveRight(){
		right = !right;
	}
	public void moveLeft(){
		left = !left;
	}
	
	public void update(){
		
		if(right){
			x += MOVE_SPEED;
		}else if(left){
			x -= MOVE_SPEED;
		}
		
		if(up){
			y += MOVE_SPEED;
		}else if(down){
			y -= MOVE_SPEED;
		}
		
	}
	
	
}
