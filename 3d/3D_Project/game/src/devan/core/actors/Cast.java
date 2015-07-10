package devan.core.actors;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.Updatable;
import devan.core.world.StaticWorld;
import devan.drawing.GraphicsData;
import devan.rendering.Renderable;

/**
 * @author dustin
 */
final public class Cast implements Updatable, Renderable{
	
	public ArrayList<Actor> actors;

	public Cast(){ actors = new ArrayList<Actor>();}
	public Cast(ArrayList<Actor> ACTORS){ actors = ACTORS;}
	
	public void setWorld(StaticWorld STATIC){
		Actor.staticWorld = STATIC;
	}
	
	public void add(Actor actor){
		actors.add(actor);
	}
	public void add(Collection<Actor> ACTORS){
		actors.addAll(ACTORS);
	}
	public void remove(Actor actor){
		actors.remove(actor);
	}
	public void clear(){
		actors.clear();
	}
	
	@Override
	public void update() {
		for(Actor actor : actors){
			actor.update();
		}
	}
	
	@Override
	public void renderTopDownView(GraphicsData g) {
		for(Actor actor : actors){
			actor.renderTopDownView(g);
		}
	}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		for(Actor actor : actors){
			actor.renderTopDownViewDEBUG(g, c);
		}
	}
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		for(Actor actor : actors){
			actor.render3dView(camera, g);
		}
	}
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		for(Actor actor : actors){
			actor.render3dViewDEBUG(camera, g, c);
		}
	}
}
