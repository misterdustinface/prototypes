package devan.animation;

/**
 * All the images used to portray one full animation from one single angle of view of the subject.
 * @author dustin
 */
public class SpriteAnimationFacet {

	// TODO make these textures instead of char.
	final public char[] TEXTURES;
	
	public SpriteAnimationFacet(char[] textures){
		TEXTURES = textures;
	}
	
	public int getNumberOfFrames(){
		return TEXTURES.length;
	}
}
