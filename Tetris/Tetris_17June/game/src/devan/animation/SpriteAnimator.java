package devan.animation;

/**
 * Animates a SpriteAnimation.
 * 
 * TODO
 * Individual to each game object since the sprites in this model should be universal. ???
 * @author dustin
 */
public class SpriteAnimator {

	private int numberOfFrames;
	private int currentFrame;
	
	SpriteAnimator(int NUMBER_OF_FRAMES){
		numberOfFrames = NUMBER_OF_FRAMES;
		currentFrame   = 0;
	}
	
	public void animate(){
		currentFrame = (currentFrame + 1) % numberOfFrames;
	}
	
	public int getCurrentFrame(){ return currentFrame; }
}
