package devan.animation;

import java.util.ArrayList;
import devan.geometry.Math;

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
	
	public int getNumberOfFacets(){ return facets.size(); }
	
	/**
	 * Facets Increment in Counter-Clockwise Order
	 * 
	 * Example: 2 Faceted Figure. Figure Facing:
	 * 0 : Back
	 * 1 : Front
	 * 
	 * Example: 4 Faceted Figure. Figure Facing:
	 * 0 : Back
	 * 1 : Left
	 * 2 : Front
	 * 3 : Right
	 * 
	 * Example: 6 Faceted Figure. Figure Facing:
	 * 0 : Back
	 * 1 : Back Left
	 * 2 : Front Left
	 * 3 : Front
	 * 4 : Front Right
	 * 5 : Back Right
	 * 
	 * Example: 8 Faceted Figure. Figure Facing:
	 * 0 : Back
	 * 1 : Back Left
	 * 2 : Left
	 * 3 : Front Left
	 * 4 : Front
	 * 5 : Front Right
	 * 6 : Right
	 * 7 : Back Right
	 * 
	 * @param  relative theta (use (signed)"difference in theta" between object and observer)
	 * @return facet number
	 */
	public int getAppropriateFacet(double theta){
		return (int)( Math.normalizeAngleTo2Pi( (  Math.normalizeAngle(theta) + (Math.PI / getNumberOfFacets()) ) 
											  ) % (Math.TWO_PI/getNumberOfFacets()) 
					);
	}
	
	// TODO change return value to a Texture
	public char getCurrentTexture(int facet){	
		return facets.get(facet).TEXTURES[getAppropriateFrame(facet)];
	}
	
	/////////////////////////////////////////////////////////////////////////////S
	
	private int getAppropriateFrame(int facet){ 
		return animator.getCurrentFrame() % facets.get(facet).getNumberOfFrames(); 
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
