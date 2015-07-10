package devan.core.actors;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.GameObject;
import devan.core.GameObjectTypes;
import devan.core.Updatable;
import devan.core.world.StaticWorld;
import devan.drawing.Drawer3D;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.Vector;
import devan.rendering.Renderable;

public class Actor extends GameObject implements Updatable, Renderable{

	public static Color ACTOR_DEBUG_COLOR 			= Color.RED;
	public static Color VELOCITY_DEBUG_COLOR 		= Color.ORANGE;
	public static Color ACCELERATION_DEBUG_COLOR 	= Color.PINK;
	
	protected static StaticWorld staticWorld;
	
	private Vector velocity;
	private Vector acceleration;
	
	public Actor(Vector position, float radius) {
		super(position, radius);
		velocity 		= new Vector(position.origin);
		acceleration 	= new Vector(position.origin);
		objectType 		= GameObjectTypes.PROP;
	}
	
	public Actor(Actor other) {
		super(other.getPositionVector().copy(), other.getBoundingCircle().getRadius());
		velocity 		= other.velocity.copy();
		acceleration 	= other.acceleration.copy();
		objectType 		= GameObjectTypes.PROP;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		// STUFF FOR EVAN TO DO. You can use the staticWorld that all actors share (:

	}


	@Override
	public void renderTopDownView(GraphicsData g) {
		GeometryDrawer.drawCircle(g, bounding, ACTOR_DEBUG_COLOR);
		GeometryDrawer.drawVector(g, getPositionVector(), ORIENTATION_DEBUG_COLOR, bounding.getRadius());
		GeometryDrawer.drawVector(g, velocity, VELOCITY_DEBUG_COLOR);
		GeometryDrawer.drawVector(g, acceleration, ACCELERATION_DEBUG_COLOR);
	}
	
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		GeometryDrawer.drawCircle(g, bounding, c);
		GeometryDrawer.drawVector(g, getPositionVector(), ORIENTATION_DEBUG_COLOR, bounding.getRadius());
		GeometryDrawer.drawVector(g, velocity, VELOCITY_DEBUG_COLOR);
		GeometryDrawer.drawVector(g, acceleration, ACCELERATION_DEBUG_COLOR);
	}

	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		Drawer3D.draw(g, camera, this);

		//GeometryDrawer.drawLine(g, Collision.getObservedCircleSubsection(camera.position.origin, bounding), Color.WHITE);
	};
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		Drawer3D.draw(g, camera, this, ACTOR_DEBUG_COLOR);

		//GeometryDrawer.drawLine(g, Collision.getObservedCircleSubsection(camera.position.origin, bounding), Color.WHITE);
	};

	
}
