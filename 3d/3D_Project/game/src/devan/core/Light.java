package devan.core;

import com.badlogic.gdx.graphics.Color;

import devan.drawing.Drawer3D;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.Vector;
import devan.rendering.Renderable;

/**
 * TODO
 * @author dustin
 */
public class Light extends GameObject implements Renderable{

	public static Color LIGHT_COLOR = Color.WHITE;
	
	public Light(Vector position, float radius) {
		super(position, radius);
		objectType = GameObjectTypes.LIGHT;
	}
	
	public Light(GameObject other){
		super(other);
		objectType = GameObjectTypes.LIGHT;
	}

	@Override
	public void renderTopDownView(GraphicsData g) {
		GeometryDrawer.drawCircle(g, bounding, LIGHT_COLOR);
	}
	
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		GeometryDrawer.drawCircle(g, bounding, c);
	}

	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		Drawer3D.draw(g, camera, this);
	};
	
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		Drawer3D.draw(g, camera, this, c);
	};
}
