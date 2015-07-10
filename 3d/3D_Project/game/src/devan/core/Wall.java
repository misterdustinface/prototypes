package devan.core;

import com.badlogic.gdx.graphics.Color;

import devan.drawing.Drawer3D;
import devan.drawing.GeometryDrawer;
import devan.drawing.GraphicsData;
import devan.geometry.LineSegment;
import devan.rendering.Renderable;

/**
 * @author dustin
 */
public class Wall extends LineSegment implements Renderable{

	public static Color DEBUG_COLOR = Color.BLACK;
	public String spriteID;
	
	public Wall(LineSegment other) { super(other); }
	
	@Override
	public void renderTopDownView(GraphicsData g){
		GeometryDrawer.drawLine(g, this, DEBUG_COLOR);
	}
	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		GeometryDrawer.drawLine(g, this, c);
	}
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		Drawer3D.draw(g, camera, this); // TODO
	}
	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		Drawer3D.draw(g, camera, this, c);
	}

}
