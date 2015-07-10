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
import devan.geometry.Point;
import devan.geometry.Sector;
import devan.geometry.Vector;

/**
 * @author dustin
 */
public class SectorVision2D implements Eye{

	public Color SECTOR_COLOR = Color.CYAN;
	
	private TreeSet<Renderable>	visibleTree;
	private Sector 				sightSector;
	
	public SectorVision2D(double SIGHT_LINE_DISTANCE){
		sightSector = new Sector(new Vector(new Point(0,0), SIGHT_LINE_DISTANCE, 0), 0);
		visibleTree = new TreeSet<Renderable>(new RenderableComparator(sightSector.getOrientationVector().origin));
	}
	
	@Override
	public void calculateVisibleEntities(Camera camera, DynamicWorld world) {
		resetVisibilityData();
		resetSightlineData(camera);
		collectAllEntitiesThatIntersectWithVisionSector(world);
	}

	@Override
	public Set<Renderable> getRelativelyOrderedVisibleSet() {
		return visibleTree.descendingSet();
	}
	
	private void resetVisibilityData(){
		visibleTree.clear(); 
	}

	private void resetSightlineData(final Camera camera){
		camera.setSectorData(sightSector);
	}
	
	private void collectAllEntitiesThatIntersectWithVisionSector(DynamicWorld world){
		collectAllWallsThatIntersectWithVisionSector(world.staticWorld.WALLS);
		collectAllActorsThatIntersectWithVisionSector(world.cast.actors);
		collectAllGameObjectsThatIntersectWithVisionSector(world.staticWorld.STAGE.props);
	}
	
	private void collectAllWallsThatIntersectWithVisionSector(final Collection<Wall> WALLS){
		for(Wall wall : WALLS){
			if(CollisionChecker.intersects(sightSector, wall)){
				visibleTree.add(wall);
			}
		}
	}
	private void collectAllActorsThatIntersectWithVisionSector(final Collection<Actor> ACTORS){
		for(GameObject actor : ACTORS){
			if(CollisionChecker.intersects(sightSector, actor.getBoundingCircle())){
				visibleTree.add(actor);
			}
		}
	}
	private void collectAllGameObjectsThatIntersectWithVisionSector(final Collection<GameObject> GAME_OBJECTS){
		for(GameObject gObject : GAME_OBJECTS){
			if(CollisionChecker.intersects(sightSector, gObject.getBoundingCircle())){
				visibleTree.add(gObject);
			}
		}
	}

	@Override
	public void render(GraphicsData g) {
		GeometryDrawer.drawSector(g, sightSector, SECTOR_COLOR);
		
		//int drawOrderCounter = 1;
		for(Renderable element : getRelativelyOrderedVisibleSet()){
			if(element instanceof GameObject){
				GeometryDrawer.drawLine(g, Perspective.getObservedCircleSubsection(sightSector.getVertexCopy(), ((GameObject)element).getBoundingCircle()), SECTOR_COLOR);
				//g.drawText(""+drawOrderCounter, (int)((GameObject)element).position.x(), (int)((GameObject)element).position.y(), Color.WHITE);
				//drawOrderCounter++;
			}
		}
	}
}
