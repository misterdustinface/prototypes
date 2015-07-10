package devan.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import devan.animation.SpriteMap;

/**
 * TODO add more variables
 * @author dustin
 */
public class GraphicsData implements Disposable{
	
	public ShapeRenderer 		shapeRenderer;
	public SpriteBatch	 		spriteBatch;
	//public PolygonSpriteBatch 	polygonSpriteBatch;
	public BitmapFont 			font;
	public SpriteMap			gameSprites;
	
	public GraphicsData(){
		shapeRenderer 	= new ShapeRenderer();
		spriteBatch 	= new SpriteBatch();
		//polygonSpriteBatch = new PolygonSpriteBatch();
		font			= new BitmapFont();
		gameSprites		= new SpriteMap();
	}
	
	public void drawText(String text, int xPos, int yPos, Color c){
		spriteBatch.begin();
		font.setColor(c);
		font.draw(spriteBatch, text, xPos, yPos);
		spriteBatch.end();
	}
	
	@Override
	public void dispose(){
		shapeRenderer.dispose();
		spriteBatch.dispose();
		//polygonSpriteBatch.dispose();
		font.dispose();
		gameSprites.dispose();
	}
}
