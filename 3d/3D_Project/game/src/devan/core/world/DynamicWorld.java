package devan.core.world;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.Updatable;
import devan.core.actors.Cast;
import devan.drawing.GraphicsData;
import devan.rendering.Renderable;

/**
 * TODO - handle collisions here. N^2?
 * @author dustin
 */
public class DynamicWorld implements Updatable, Renderable{

	public StaticWorld 	staticWorld;
	public Cast			cast;
	
	public DynamicWorld(StaticWorld STATIC){
		staticWorld = STATIC;
		cast 		= new Cast();
		cast.setWorld(STATIC);
	}
	
	public DynamicWorld(StaticWorld STATIC, Cast CAST){
		staticWorld = STATIC;
		cast 		= CAST;
		cast.setWorld(STATIC);
	}
	
	public void load(StaticWorld STATIC, Cast CAST){
		staticWorld = STATIC;
		cast 		= CAST;
		cast.setWorld(STATIC);
	}

	@Override
	public void renderTopDownView(GraphicsData g){
		staticWorld.renderTopDownView(g);
		cast.renderTopDownView(g);
	}

	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		staticWorld.renderTopDownViewDEBUG(g, c);
		cast.renderTopDownViewDEBUG(g, c);
	}
	
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		staticWorld.render3dView(camera, g);
		cast.render3dView(camera, g);
	}
	
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		staticWorld.render3dViewDEBUG(camera, g, c);
		cast.render3dViewDEBUG(camera, g, c);
	}
	
	@Override
	public void update() {
		cast.update();
	}
	 
}
