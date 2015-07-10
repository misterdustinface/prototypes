package devan.rendering;

import java.util.Set;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObject;
import devan.drawing.GraphicsData;

/**
 * @author dustin
 */
abstract public class Renderer {
	abstract public void renderTopDownView(GraphicsData g);
	abstract public void renderTopDownViewFromPerspective(Camera perspective, GraphicsData g);
	abstract public void renderTopDownViewFromPerspectiveDEBUG(Camera perspective, GraphicsData g, Color color);
	abstract public void render1stPerson(Camera perspective, GraphicsData g);
	abstract public void render1stPersonDEBUG(Camera perspective, GraphicsData g, Color color);
	abstract public void render3rdPerson(Camera camera, GameObject actor, GraphicsData g);
	
	final public void renderAllTopDown(GraphicsData g, Set<Renderable> RENDERABLE_SET){
		for(Renderable renderableThing : RENDERABLE_SET){
			renderableThing.renderTopDownView(g);
		}
	};
	final public void renderAllTopDown(GraphicsData g, Set<Renderable> RENDERABLE_SET, Color color){
		for(Renderable renderableThing : RENDERABLE_SET){
			renderableThing.renderTopDownViewDEBUG(g, color);
		}
	};
	final public void renderAll3D(GraphicsData g, Camera perspective, Set<Renderable> RENDERABLE_SET){
		for(Renderable renderableThing : RENDERABLE_SET){
			renderableThing.render3dView(perspective, g);
		}
	};
	final public void renderAll3D(GraphicsData g, Camera perspective, Set<Renderable> RENDERABLE_SET, Color color){
		for(Renderable renderableThing : RENDERABLE_SET){
			renderableThing.render3dViewDEBUG(perspective, g, color);
		}
	};
			
}
