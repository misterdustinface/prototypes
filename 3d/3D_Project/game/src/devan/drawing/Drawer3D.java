package devan.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import devan.core.Camera;
import devan.core.GameObject;
import devan.core.Wall;
import devan.core.actors.Actor;
import devan.rendering.Perspective;

/**
 * @author dustin
 */
final public class Drawer3D {
	
	private Drawer3D(){};
	
	//TODO
	public static void draw(GraphicsData g, Camera cam, Wall w){
		drawTexture(g, g.gameSprites.tempWallTexture, Perspective.getObservedWallVertices(cam, w, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	//TODO
	public static void draw(GraphicsData g, Camera cam, GameObject gObject){
		drawTexture(g, g.gameSprites.tempGameObjectTexture, Perspective.getObservedGameObjectVertices(cam, gObject, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	//TODO
	public static void draw(GraphicsData g, Camera cam, Actor actor){
		drawTexture(g, g.gameSprites.tempActorTexture, Perspective.getObservedActorVertices(cam, actor, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	
	public static void draw(GraphicsData g, Camera cam, Wall w, Color color){
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.polygon(Perspective.getObservedWallVertices(cam, w, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		g.shapeRenderer.end();
	}
	public static void draw(GraphicsData g, Camera cam, GameObject gObject, Color color){
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.polygon(Perspective.getObservedGameObjectVertices(cam, gObject, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		g.shapeRenderer.end();
	}
	public static void draw(GraphicsData g, Camera cam, Actor actor, Color color){
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.polygon(Perspective.getObservedActorVertices(cam, actor, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		g.shapeRenderer.end();
	}
	
	private static void drawTexture(GraphicsData g, Texture texture, float[] vertices){
		g.spriteBatch.begin();
		g.spriteBatch.draw(texture, toLibGDXVertexArray(vertices), 0, 20);
		g.spriteBatch.end();
	}
	
	private static float[] toLibGDXVertexArray(float[] VERTICES){
		
		return new float[] { 	VERTICES[0], VERTICES[1], Color.toFloatBits(255, 255, 255, 255), 1f, 0f,
								VERTICES[2], VERTICES[3], Color.toFloatBits(255, 255, 255, 255), 1f, 1f,
								VERTICES[4], VERTICES[5], Color.toFloatBits(255, 255, 255, 255), 0f, 1f,
								VERTICES[6], VERTICES[7], Color.toFloatBits(255, 255, 255, 255), 0f, 0f
		};
	}
	
	// TODO if polygons are very small render them in some other manner (: [from here]
}
