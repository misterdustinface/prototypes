package rendering;

import world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldRenderer {
	protected World 				world;
	protected OrthographicCamera 	cam;
	protected SpriteBatch 			batch;
	protected ShapeRenderer 		shapeRender;
	
	float w = 480;
	float h = 320;

	public WorldRenderer(World world) {
		this.world = world;

		cam 		= new OrthographicCamera(w, h);
		batch 		= new SpriteBatch();
		shapeRender = new ShapeRenderer();
		shapeRender.setColor(Color.GREEN);
	}
	
	public void render() {
		//Gdx.gl.glClearColor(0, 194, 206, 0);
		Gdx.gl.glClearColor(6/255f, 5/255f, 3/255f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(w/2, h/2, cam.position.z);
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
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
		
		//Render Bat
		shapeRender.setColor(0, 0.2f, 0.8f, 0);
		shapeRender.begin(ShapeType.Filled);
		shapeRender.rect(world.bat.getxOffset(), 
						 world.bat.getLowerBound(), 
						 world.bat.getWidth(), 
						 world.bat.getHeight());
		shapeRender.end();
		
		
//		//Render ceiling and floor
//		shapeRender.begin(ShapeType.Line);
//		shapeRender.line(world.getCenter().x - w, Globals.WORLD_Ceiling, world.getCenter().x + 2 * w, Globals.WORLD_Ceiling);
//		shapeRender.end();
	}
	
	public void dispose() {
		batch.dispose();
		shapeRender.dispose();
	}
}
