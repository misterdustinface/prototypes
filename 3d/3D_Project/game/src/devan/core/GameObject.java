package devan.core;

import com.badlogic.gdx.graphics.Color;

import devan.drawing.Drawer3D;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.Circle;
import devan.geometry.Point;
import devan.geometry.Vector;
import devan.rendering.Renderable;

// Evan should it be abstract?
public class GameObject implements Renderable{
	
	public static Color GAME_OBJECT_DEBUG_COLOR = Color.DARK_GRAY;
	public static Color ORIENTATION_DEBUG_COLOR = Color.GREEN;
	public String spriteID;
	
	final public static void resetObjectCount() 	{ objectCount = 0; }
	final public static int  getCurrentObjectCount(){ return objectCount; }	
	private static int objectCount = 0;
	
	private int 	id;
	private Vector 	position;
	
	protected int 		objectType = GameObjectTypes.NONE;
	protected Circle 	bounding;
	
	public GameObject(Vector position, float radius) {
		id = objectCount++;
		this.position = position;
		bounding = new Circle(position.origin, radius);
	}
	
	public GameObject(GameObject other) {
		id = objectCount++;
		this.position 	= other.position;
		bounding 		= new Circle(position.origin, other.bounding.getRadius());
	}
	
	final public int getId() 		{ return id; }
	final public int getObjectType(){ return objectType; }
	
	final public void pointAt(GameObject other){
		position.setTheta( devan.geometry.Math.theta(position.origin, other.position.origin) );
	}
	
	/**
	 * @return HEIGHT value.
	 */
	final public float getZ(){	return position.origin.z;}
	final public float getX(){	return position.getX();	}
	final public float getY(){	return position.getY();	}
	
	/**
	 * @param Z -HEIGHT value
	 */
	final public void setZ(float Z){ position.origin.z = Z; }
	final public void setX(float X){ position.origin.x = X; }
	final public void setY(float Y){ position.origin.y = Y; }
	
	final public void shiftZ(float amount){ position.origin.z += amount; }
	final public void shiftX(float amount){ position.origin.x += amount; }
	final public void shiftY(float amount){ position.origin.y += amount; }
	
	final public Point 	getCenter(){ return position.origin;}
	final public Vector getPositionVector(){ return position; }
	
	final public void 	rotate(double THETA_IN_RADIANS){ position.rotate(THETA_IN_RADIANS);}
	final public double getOrientationAngleInRadians(){return position.getTheta();}
	final public Circle getBoundingCircle(){ return bounding; }
	
	@Override
	public void renderTopDownView(GraphicsData g){
		GeometryDrawer.drawCircle(g, bounding, GAME_OBJECT_DEBUG_COLOR);
		GeometryDrawer.drawVector(g, getPositionVector(), ORIENTATION_DEBUG_COLOR, bounding.getRadius());
	}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		GeometryDrawer.drawCircle(g, bounding, c);
		GeometryDrawer.drawVector(g, getPositionVector(), ORIENTATION_DEBUG_COLOR);
	}
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		Drawer3D.draw(g, camera, this);
	}
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		Drawer3D.draw(g, camera, this, GAME_OBJECT_DEBUG_COLOR);
	}
}
