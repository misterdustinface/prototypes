package devan.core.world;

import java.util.Set;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObject;
import devan.drawing.GraphicsData;
import devan.rendering.Eye;
import devan.rendering.Renderable;
import devan.rendering.Renderer;

/**
 * @author dustin
 */
public class WorldRenderer extends Renderer{
	
	private DynamicWorld world;
	private Eye visionAlgorithm;
	
	private Set<Renderable> renderableSet;
	
	public WorldRenderer(final DynamicWorld WORLD, Eye VISION_ALGORITHM){
		world 			= WORLD;
		visionAlgorithm = VISION_ALGORITHM;
	}
	
	@Override
	public void renderTopDownView(GraphicsData g){
		world.renderTopDownView(g);
	}
	
	@Override
	public void renderTopDownViewFromPerspective(Camera perspective, GraphicsData g){
		visionAlgorithm.calculateVisibleEntities(perspective, world);
		renderableSet = visionAlgorithm.getRelativelyOrderedVisibleSet();
		renderableSet.remove(perspective);
		renderAllTopDown(g, renderableSet);
	}
	
	@Override
	public void renderTopDownViewFromPerspectiveDEBUG(Camera perspective, GraphicsData g, Color color){
		visionAlgorithm.calculateVisibleEntities(perspective, world);
		renderableSet = visionAlgorithm.getRelativelyOrderedVisibleSet();
		renderableSet.remove(perspective);
		renderAllTopDown(g, renderableSet, color);
	}

	@Override
	public void render1stPerson(Camera perspective, GraphicsData g) {
		visionAlgorithm.calculateVisibleEntities(perspective, world);
		renderableSet = visionAlgorithm.getRelativelyOrderedVisibleSet();
		renderableSet.remove(perspective);
		renderAll3D(g, perspective, renderableSet);
	}
	
	@Override
	public void render1stPersonDEBUG(Camera perspective, GraphicsData g, Color color) {
		visionAlgorithm.calculateVisibleEntities(perspective, world);
		renderableSet = visionAlgorithm.getRelativelyOrderedVisibleSet();
		renderableSet.remove(perspective);
		renderAll3D(g, perspective, renderableSet, color);
	}

	@Override
	public void render3rdPerson(Camera camera, GameObject actor, GraphicsData g) {
		camera.pointAt(actor);
		visionAlgorithm.calculateVisibleEntities(camera, world);
		renderAll3D(g, camera, visionAlgorithm.getRelativelyOrderedVisibleSet());
	}
	
	public void renderEye(GraphicsData g){
		visionAlgorithm.render(g);
	}

}
