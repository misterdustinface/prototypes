package devan.core.actors;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObjectTypes;
import devan.core.Updatable;
import devan.drawing.Drawer3D;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.Vector;
import devan.rendering.Renderable;


public class Player extends Actor implements Updatable, Renderable{
	
	public static Color PLAYER_DEBUG_COLOR = Color.BLUE;
	
	// TEMPORARY STUFF FOR TESTING
	public boolean rotating = false;
	public float rotationAmount = 0;
	public boolean walking = false;
	public float walkSpeed = 0;
	public boolean strafing = false;
	public float strafeSpeed = 0;
	public boolean flying = false;
	public float flySpeed = 0;
	// END TEMP STUFF FOR TESTING
	
	public Player(Vector position, float radius) {
		super(position, radius);
		objectType = GameObjectTypes.PLAYER;
	}

	@Override
	public void update() {
		super.update();
		
		// TEMPORARY STUFF FOR TESTING
		if(rotating){
			rotate(rotationAmount);
		}
		if(walking){
			shiftX(walkSpeed * (float)Math.cos(getOrientationAngleInRadians()));
			shiftY(walkSpeed * (float)Math.sin(getOrientationAngleInRadians()));
		}
		if(strafing){
			shiftX(strafeSpeed * (float)Math.cos(devan.geometry.Math.normalizeAngle(getOrientationAngleInRadians()- Math.PI/2)));
			shiftY(strafeSpeed * (float)Math.sin(devan.geometry.Math.normalizeAngle(getOrientationAngleInRadians()- Math.PI/2)));
		}
		if(flying){
			shiftZ(flySpeed);
		}
		// END TEMP STUFF FOR TESTING
	
		// STUFF FOR EVAN TO DO. You can use the staticWorld that all actors share (:
	}
	
	@Override
	public void renderTopDownView(GraphicsData g) {
		super.renderTopDownView(g);
		GeometryDrawer.drawCircle(g, bounding, PLAYER_DEBUG_COLOR);
	}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		super.renderTopDownView(g);
		GeometryDrawer.drawCircle(g, bounding, c);
	}
	
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		Drawer3D.draw(g, camera, this);
	}
	
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		Drawer3D.draw(g, camera, this, PLAYER_DEBUG_COLOR);
	}
	
	
	
}
