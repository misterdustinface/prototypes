package devan.rendering;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObject;
import devan.core.Wall;
import devan.core.actors.Actor;
import devan.core.world.DynamicWorld;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.CollisionChecker;
import devan.geometry.Ray;

/**
 * @author dustin
 */
public class RayCaster2D implements Eye{
	
	public Color RAY_COLOR 			= Color.CYAN;
	public float MAX_VIEW_DISTANCE 	= Float.POSITIVE_INFINITY;
	
	public int 		raysToCast;
	private int 	rayCounter;
	private double 	rayThetaStart;
	private double 	rayThetaIncrement;
	private Ray 	ray;
	private TreeSet<Renderable>	visibleTree;

	public RayCaster2D(int RAYS_TO_CAST_PER_CAST){
		raysToCast = RAYS_TO_CAST_PER_CAST;
		ray = new Ray();
		visibleTree = new TreeSet<Renderable>(new RenderableComparator(ray.origin));
	}
	
	public RayCaster2D(int RAYS_TO_CAST_PER_CAST, float MAX_VIEW_DISTANCE){
		raysToCast = RAYS_TO_CAST_PER_CAST;
		ray = new Ray();
		visibleTree = new TreeSet<Renderable>(new RenderableComparator(ray.origin));
		this.MAX_VIEW_DISTANCE = MAX_VIEW_DISTANCE;
	}
	
	@Override
	public void calculateVisibleEntities(final Camera camera, final DynamicWorld world){
		resetVisibilityData();
		resetRayData(camera);
		castRays(world);
	}
	@Override
	public Set<Renderable> getRelativelyOrderedVisibleSet() {
		return visibleTree.descendingSet();
	}
	
	private void castRays(final DynamicWorld world){
		ray.setTheta(rayThetaStart);
		for(rayCounter = 0; rayCounter < raysToCast; ++rayCounter){
			castRayAtWallsAndAddClosestHitsToVisibleList(ray, world.staticWorld.WALLS);
			castRayAtActorsAndAddClosestHitsToVisibleList(ray, world.cast.actors);
			castRayAtGameObjectsAndAddClosestHitsToVisibleList(ray, world.staticWorld.STAGE.props);
			ray.rotate(rayThetaIncrement);
		}
	}
	
	private void resetRayData(final Camera camera){
		ray.origin.set(camera.getCenter());
		ray.origin.shift(camera.xOffset, camera.yOffset);
		rayThetaStart 		= camera.getLeftmostFOVTheta();
		rayThetaIncrement 	= camera.getFieldOfViewInRadians() / raysToCast;
	}
	
	private void resetVisibilityData(){
		visibleTree.clear(); 
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void castRayAtWallsAndAddClosestHitsToVisibleList(final Ray currentRay, final Collection<Wall> walls){;
		for(Wall wall : walls){
			ifRayIntersectionKeepRecord(currentRay, wall);
		}
	}

	private void castRayAtGameObjectsAndAddClosestHitsToVisibleList(final Ray currentRay, final Collection<GameObject> objects){
		for(GameObject gameObject : objects){
			ifRayIntersectionKeepRecord(currentRay, gameObject);
		}
	}
	private void castRayAtActorsAndAddClosestHitsToVisibleList(final Ray currentRay, final Collection<Actor> actors){
		for(Actor actor : actors){
			ifRayIntersectionKeepRecord(currentRay, actor);
		}
	}
	
	private void ifRayIntersectionKeepRecord(final Ray currentRay, final GameObject gameObject){
		if(CollisionChecker.intersects(currentRay, gameObject.getBoundingCircle())
		&& devan.geometry.Math.distance(currentRay.origin, gameObject.getCenter()) <= MAX_VIEW_DISTANCE){
			visibleTree.add(gameObject);
		}
	}
	
	private void ifRayIntersectionKeepRecord(final Ray currentRay, final Wall wall){
		if(CollisionChecker.intersects(currentRay, wall)
		&& devan.geometry.Math.distance(currentRay.origin, wall) <= MAX_VIEW_DISTANCE){
			visibleTree.add(wall);
		}
	}

	@Override
	public void render(GraphicsData g) {
		GeometryDrawer.drawRay(g, ray.origin, rayThetaStart, RAY_COLOR, MAX_VIEW_DISTANCE);
		GeometryDrawer.drawRay(g, ray, RAY_COLOR, MAX_VIEW_DISTANCE);
	}

}

