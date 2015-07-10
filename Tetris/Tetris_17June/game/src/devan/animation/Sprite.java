package devan.animation;

import java.util.HashMap;

/**
 * Is lord of texture kingdom. Graphically defines a Character.
 * @author dustin
 */
public class Sprite {
	
	private HashMap<String, SpriteAnimation> animationsMap;
	
	public Sprite(){
		animationsMap = new HashMap<String, SpriteAnimation>();
	}
	
	public SpriteAnimation getAnimation(String SPRITE_ID){
		return animationsMap.get(SPRITE_ID);
	}
	public void addAnimation(String SPRITE_ID, SpriteAnimation SPRITE){
		animationsMap.put(SPRITE_ID, SPRITE);
	}
	
	public int getNumberOfAnimations(){ return animationsMap.size(); }
	
	
}
