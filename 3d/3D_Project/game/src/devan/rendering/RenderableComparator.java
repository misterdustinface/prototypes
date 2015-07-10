package devan.rendering;

import java.util.Comparator;

import devan.core.GameObject;
import devan.core.Wall;
import devan.geometry.Math;
import devan.geometry.Point;

/**
 * @author dustin
 */
public class RenderableComparator implements Comparator<Renderable>{

	private Point eye;
	public RenderableComparator(Point EYE_REF){
		eye = EYE_REF;
	}
	
	@Override
	public int compare(Renderable A, Renderable B) {
		return (int)(
				(A instanceof Wall ? Math.squared(Math.distance(eye, (Wall)A)) : Math.distanceSquared(eye, ((GameObject)A).getCenter()))
			 - 	(B instanceof Wall ? Math.squared(Math.distance(eye, (Wall)B)) : Math.distanceSquared(eye, ((GameObject)B).getCenter()))
			 );
	}

}
