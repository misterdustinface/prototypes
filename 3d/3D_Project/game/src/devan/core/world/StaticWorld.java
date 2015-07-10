package devan.core.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import devan.core.Camera;
import devan.core.Wall;
import devan.core.actors.Stage;
import devan.drawing.GraphicsData;
import devan.rendering.Renderable;

/**
 * @author dustin
 */
public class StaticWorld implements Renderable{
	final public ArrayList<Wall> 	WALLS;
	//public PointHeightMap 			WALL_HEIGHT_MAP;
	public Stage 					STAGE;
	
	public StaticWorld(final ArrayList<Wall> walls, //PointHeightMap wallHeightMap, 
						Stage stage){
		WALLS 				= walls;
		//WALL_HEIGHT_MAP 	= wallHeightMap;
		STAGE 				= stage;
	}
	public StaticWorld(StaticWorld world){
		WALLS 				= world.WALLS;
		//WALL_HEIGHT_MAP 	= world.WALL_HEIGHT_MAP;
		STAGE 				= world.STAGE;
	}
	
	@Override
	public void renderTopDownView(GraphicsData g){
		for(Wall wall : WALLS){
			wall.renderTopDownView(g);
		}
		STAGE.renderTopDownView(g);
	}

	@Override
	public void renderTopDownViewDEBUG(GraphicsData g, Color c) {
		for(Wall wall : WALLS){
			wall.renderTopDownViewDEBUG(g, c);
		}
		STAGE.renderTopDownViewDEBUG(g, c);
	}
	
	@Override
	public void render3dView(Camera camera, GraphicsData g) {
		for(Wall wall : WALLS){
			wall.render3dView(camera, g);
		}
		STAGE.render3dView(camera, g);
	}

	@Override
	public void render3dViewDEBUG(Camera camera, GraphicsData g, Color c) {
		for(Wall wall : WALLS){
			wall.render3dViewDEBUG(camera, g, c);
		}
		STAGE.render3dViewDEBUG(camera, g, c);
	}
}
