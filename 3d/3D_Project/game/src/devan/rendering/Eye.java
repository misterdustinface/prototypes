package devan.rendering;

import java.util.Set;

import devan.core.Camera;
import devan.core.world.DynamicWorld;
import devan.drawing.GraphicsData;

/**
 * @author dustin
 */
public interface Eye {
	public void calculateVisibleEntities(Camera camera, DynamicWorld world);
	public Set<Renderable> getRelativelyOrderedVisibleSet();
	public void render(GraphicsData g);
}
