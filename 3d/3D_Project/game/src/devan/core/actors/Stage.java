package devan.core.actors;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObject;
import devan.core.world.StaticWorld;
import devan.drawing.GraphicsData;
import devan.rendering.Renderable;

/**
 * Stage is filled with all "non-living" things (props).
 * @author dustin
 */
final public class Stage implements Renderable{
	
	public ArrayList<GameObject> props;

	public Stage(){ props = new ArrayList<GameObject>();}
	public Stage(ArrayList<GameObject> PROPS){ props = PROPS;}
	
	public void setWorld(StaticWorld STATIC){
		Actor.staticWorld = STATIC;
	}
	
	public void add(GameObject prop){
		props.add(prop);
	}
	public void add(Collection<GameObject> PROPS){
		props.addAll(PROPS);
	}
	public void remove(GameObject prop){
		props.remove(prop);
	}
	public void clear(){
		props.clear();
	}
	
	@Override
	public void renderTopDownView(GraphicsData g) {
		for(GameObject prop : props){
			prop.renderTopDownView(g);
		}
	}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		for(GameObject prop : props){
			prop.renderTopDownViewDEBUG(g, c);
		}
	}
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		for(GameObject prop : props){
			prop.render3dView(camera, g);
		}
	}
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		for(GameObject prop : props){
			prop.render3dViewDEBUG(camera, g, c);
		}
	}
}