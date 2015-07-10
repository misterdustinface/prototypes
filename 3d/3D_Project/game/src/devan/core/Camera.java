package devan.core;

import com.badlogic.gdx.graphics.Color;

import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.Sector;
import devan.rendering.Renderable;

/**
 * @author dustin
 */
public class Camera extends GameObject implements Renderable{

	private float fieldOfViewInRadians;
	
	public float xOffset, yOffset, zOffset;
	
	public Camera(GameObject other) {
		super(other);
		fieldOfViewInRadians = (float)java.lang.Math.toRadians(70);
		xOffset = yOffset = zOffset = 0;
		objectType = GameObjectTypes.CAMERA;
	}
	
	public Camera(GameObject other, float FIELD_OF_VIEW) {
		super(other);
		fieldOfViewInRadians = (float)java.lang.Math.toRadians(FIELD_OF_VIEW);
		xOffset = yOffset = zOffset = 0;
		objectType = GameObjectTypes.CAMERA;
	}
	
	public double getLeftmostFOVTheta(){
		return devan.geometry.Math.normalizeAngle(getOrientationAngleInRadians() - fieldOfViewInRadians/2);
	}
	
	public double getRightmostFOVTheta(){
		return devan.geometry.Math.normalizeAngle(getOrientationAngleInRadians() + fieldOfViewInRadians/2);
	}
	
	public double getFieldOfViewInDegrees(){
		return java.lang.Math.toDegrees(fieldOfViewInRadians);
	}
	public float getFieldOfViewInRadians(){
		return fieldOfViewInRadians;
	}

	@Override
	public void renderTopDownView(GraphicsData g){}
	@Override
	public void render3dView(Camera camera, GraphicsData g) {}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		GeometryDrawer.drawRay(g, getPositionVector(), c, 10);
		GeometryDrawer.drawRay(g, getCenter(), getLeftmostFOVTheta(), c, 200);
		GeometryDrawer.drawRay(g, getCenter(), getRightmostFOVTheta(), c, 200);
	}
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {}
	
	public void setSectorData(Sector sightSector){
		sightSector.getOrientationVector().origin.set(getCenter());
		sightSector.getOrientationVector().origin.shift(xOffset, yOffset);
		sightSector.getOrientationVector().setTheta(getOrientationAngleInRadians());
		sightSector.setSectorLength(fieldOfViewInRadians);
	}
	
}
