package spikes.graphicsGenerator;

import rendering.WorldRenderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GraphicsSpikeWorldRenderer extends WorldRenderer{

	SpriteBatch 		batch;
	BitmapFont 			font;
	
	Color deep 		= new Color(6/255f, 5/255f, 3/255f, 1);
	Color surface 	= new Color(51/255f, 25/255f, 0, 0);
	
	public GraphicsSpikeWorldRenderer(GraphicsSpikeWorld world) {
		super(world);
		Texture.setEnforcePotImages(false);
		batch 		= new SpriteBatch();
		font 		= new BitmapFont();
	}
	
	@Override
	public void render() {
		//super.render();
		
		cam.position.set(	((GraphicsSpikeWorld)world).getCenter().x, 
							((GraphicsSpikeWorld)world).getCenter().y, 
							cam.position.z);
		cam.update();
		
		batch.begin();
		
		shapeRender.setProjectionMatrix(cam.combined);
		
		///////////////////////////////////////////////////
		//Render bounds
		shapeRender.begin(ShapeType.Point);
		shapeRender.setColor(0.6f, 0.4f, 0, 0);
		int x = 0;
		for(int lower : world.caveLower){
			shapeRender.point(x, lower, 0);
			++x;
		}
		x = 0;
		for(int upper : world.caveUpper){
			shapeRender.point(x, upper, 0);
			++x;
		}
		shapeRender.end();
		
		/////////////////////////////////////////////////////
		// RENDER BOUNDS AS RECTANGLES
//		shapeRender.begin(ShapeType.Filled);
//		//shapeRender.setColor(0.6f, 0.3f, 0, 0);
//		int i = 0;
//		for(int lower : world.caveLower){
//			shapeRender.rect(i, 0, 1, lower, deep, deep, surface, surface);
//			++i;
//		}
//		i = 0;
//		for(int upper : world.caveUpper){
//			shapeRender.rect(i, upper, 1, h - upper, surface, surface, deep, deep);
//			++i;
//		}
//		shapeRender.end();
		
		
		///////////////////////////////////////////////////
		//Render boxes
		shapeRender.begin(ShapeType.Line);
		for(Rectangle r : ((GraphicsSpikeWorld)world).boxes) {
			shapeRender.setColor(0, 0.8f, 0.3f, 0);
			if(r.contains(((GraphicsSpikeWorld)world).getCenter().x, 
					((GraphicsSpikeWorld)world).getCenter().y))
				shapeRender.setColor(1,1,1,0);
			shapeRender.rect(r.x, r.y, r.width, r.height);
		}
		
		///////////////////////////////////////////////////
		//Render ruler lines and distance text
		for(Line l : ((GraphicsSpikeWorld)world).lines){
			shapeRender.setColor(0.6f, 0.5f, 0, 0);
			shapeRender.line(l.A, l.B);
			shapeRender.setColor(1,1,1,0);
			font.drawWrapped(batch, l.distance(), l.midPoint().x, l.midPoint().y, 40, HAlignment.CENTER);
		}
		shapeRender.end();
		
//		///////////////////////////////////////////////////
//		//Render Min and Max heightmaps
//		shapeRender.begin(ShapeType.Line);
//		shapeRender.setColor(0.3f, 0, 0.7f, 0);
//		shapeRender.x(world.getCenter().x, world.caveUpper.getLowest(), 1);
//		shapeRender.x(world.getCenter().x, world.caveLower.getHighest(), 1);
//		shapeRender.setColor(0.7f, 0, 0.2f, 0);
//		shapeRender.x(world.getCenter().x, world.caveUpper.getHighest(), 1);
//		shapeRender.x(world.getCenter().x, world.caveLower.getLowest(), 1);
//		shapeRender.end();
		
		///////////////////////////////////////////////////
		//Render Cursor
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(1, 0, 0, 0.1f);
		shapeRender.x(cam.position.x,  cam.position.y, 2);
		shapeRender.end();
		
		batch.end();
	}
	
	public void dispose() {
		super.dispose();
		font.dispose();
		batch.dispose();
	}
}
