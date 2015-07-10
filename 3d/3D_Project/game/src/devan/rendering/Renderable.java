package devan.rendering;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.drawing.GraphicsData;

/**
 * @author dustin
 */
public interface Renderable {
	public void renderTopDownView(GraphicsData g);
	public void renderTopDownViewDEBUG(GraphicsData g, Color c);
	public void render3dView(Camera camera, GraphicsData g);
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c);
}
