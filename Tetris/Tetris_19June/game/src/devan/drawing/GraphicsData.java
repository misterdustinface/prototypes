package devan.drawing;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import devan.animation.SpriteMap;
import devan.rendering.Display;

/**
 * TODO add more variables
 * @author dustin
 */
public class GraphicsData implements Disposable{
	
	final public static String MAIN_DISPLAY = "Main";
	
	private Display 			currentDisplay;
	private HashMap<String, Display> displays;
	public ShapeRenderer 		shapeRenderer;
	public SpriteBatch	 		spriteBatch;
	//public PolygonSpriteBatch 	polygonSpriteBatch;
	public BitmapFont 			font;
	public SpriteMap			gameSprites;
	public int worldWidth;
	public int worldHeight;
	
	public GraphicsData(Display DISPLAY){
		shapeRenderer 	= new ShapeRenderer();
		spriteBatch 	= new SpriteBatch();
		//polygonSpriteBatch = new PolygonSpriteBatch();
		font			= new BitmapFont();
		gameSprites		= new SpriteMap();

		displays = new HashMap<String, Display>();
		displays.put(MAIN_DISPLAY, DISPLAY);
		currentDisplay = DISPLAY;
		
		worldWidth = DISPLAY.getWidth();
		worldHeight = DISPLAY.getHeight();
	}
	
	public void drawText(String text, int xPos, int yPos, Color c){
		spriteBatch.begin();
		font.setColor(c);
		if(yPos + font.getCapHeight() < currentDisplay.getHeight()){
			font.draw(spriteBatch, text, currentDisplay.getX() + xPos, currentDisplay.getY() + yPos + font.getCapHeight());
		}else{
			font.draw(spriteBatch, text, currentDisplay.getX() + xPos, currentDisplay.getY() + currentDisplay.getHeight() - font.getCapHeight());
		}
		spriteBatch.end();
	}
	private TextBounds textBounds;
	public void drawCenteredText(String text, int xCenterPos, int yCenterPos, Color c){
		textBounds = font.getBounds(text);
		spriteBatch.begin();
		font.setColor(c);
		font.draw(spriteBatch, text, currentDisplay.getX() + (int)(xCenterPos - textBounds.width/2), 
									 currentDisplay.getY() + (int)(yCenterPos - textBounds.height/2));
		spriteBatch.end();
	}
	public void scaleText(float amount){ font.scale(amount); }
	
	public void addDisplay(String name, Display display){ displays.put(name, display); }
	public void setTargetDisplay(String name){ currentDisplay = displays.get(name); }
	public void resetTargetDisplayToMainDisplay(){ currentDisplay = displays.get(MAIN_DISPLAY); }
	public Display getDisplay(){ return currentDisplay; }
	
	@Override
	public void dispose(){
		shapeRenderer.dispose();
		spriteBatch.dispose();
		//polygonSpriteBatch.dispose();
		font.dispose();
		gameSprites.dispose();
	}
	
	public float transX(float x){
		return currentDisplay.getX() + transWidth(x);
	}
	public float transY(float y){
		return currentDisplay.getY() + transHeight(y);
	}
	public float transWidth(float w){
		return w/worldWidth * currentDisplay.getWidth();
	}
	public float transHeight(float h){
		return h/worldHeight * currentDisplay.getHeight();
	}
	
	final public void drawDisplayBounds(Color c){
		GeometryDrawer.drawRectangle(this, 0, 0, getDisplay().getWidth(), getDisplay().getHeight(), c);
	}
}
