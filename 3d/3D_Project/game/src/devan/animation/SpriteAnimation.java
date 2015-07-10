package devan.animation;

import java.util.ArrayList;

/**
 * An animation for a sprite.
 * @author dustin
 */
public class SpriteAnimation {
	
	private ArrayList<SpriteAnimationFacet> facets; 	// The different points of view of this animation
	private SpriteAnimator 					animator;	// controls this SpriteAnimation
	
	public SpriteAnimation(ArrayList<SpriteAnimationFacet> FACETS){
		facets 		= FACETS;
		animator 	= createAnimator();
	}
	
	public void animate(){
		animator.animate();
	}
	
	public int getNumberOfFaces(){ return facets.size(); }
	
	// TODO change return value to a Texture
	public char getCurrentTexture(int face){	
		return facets.get(face).TEXTURES[getAppropriateFrame(face)];
	}
	
	private int getAppropriateFrame(int face){ 
		return animator.getCurrentFrame() % facets.get(face).getNumberOfFrames(); 
	}
	
	private SpriteAnimator createAnimator(){
		return new SpriteAnimator(getLongestFrameLengthFromListOfSpriteFacets());
	}
	
	private int getLongestFrameLengthFromListOfSpriteFacets(){
		int longestFrameLength = 0;
		for(SpriteAnimationFacet facet : facets){
			longestFrameLength = facet.getNumberOfFrames() > longestFrameLength ? facet.getNumberOfFrames() : longestFrameLength;
		}
		return longestFrameLength;
	}
}
